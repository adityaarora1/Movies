package com.sample.neugelb.presentation.movies

import android.os.Bundle
import android.view.*
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.coroutineScope
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import com.sample.neugelb.R
import com.sample.neugelb.domain.model.Movie
import com.sample.neugelb.presentation.movies.adapter.MoviesAdapter
import com.sample.neugelb.presentation.movies.adapter.MoviesAdapter.ViewType.FOOTER
import com.sample.neugelb.presentation.movies.adapter.MoviesLoadStateAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_movies.*
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MoviesFragment : Fragment() {
    private var searchJob: Job? = null
    private var nowPlayingJob: Job? = null

    private val itemClick: (Movie) -> Unit = {
        findNavController().navigate(R.id.action_moviesFragment_to_movieDetailFragment)
        moviesViewModel.select(it)
    }
    private val moviesViewModel: MoviesViewModel by activityViewModels()
    private lateinit var searchView: SearchView
    private lateinit var moviesAdapter: MoviesAdapter

    private var rootView: View? = null
    private var currentQueryValue: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (null == rootView) {
            rootView = inflater.inflate(R.layout.fragment_movies, container, false)
            moviesAdapter = MoviesAdapter(itemClick)
            setupObservers()
        }
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        retryButton.setOnClickListener { moviesAdapter.retry() }

        setupRecyclerView()
    }

    override fun onResume() {
        super.onResume()
        setActionBar()
    }

    private fun setActionBar() {
        (activity as AppCompatActivity).supportActionBar?.title = getString(R.string.app_name)
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)
        (activity as AppCompatActivity).supportActionBar?.setHomeButtonEnabled(false)
    }

    private fun setupRecyclerView() {
        val spanCount = 3

        recyclerViewMovies.apply {
            layoutManager =
                GridLayoutManager(
                    context,
                    spanCount
                )
            (layoutManager as GridLayoutManager).spanSizeLookup =
                object : GridLayoutManager.SpanSizeLookup() {
                    override fun getSpanSize(position: Int): Int {
                        val viewType = moviesAdapter.getItemViewType(position)
                        return if (viewType == FOOTER.ordinal) 1
                        else spanCount
                    }
                }
            adapter = moviesAdapter.withLoadStateHeaderAndFooter(
                header = MoviesLoadStateAdapter { moviesAdapter.retry() },
                footer = MoviesLoadStateAdapter { moviesAdapter.retry() }
            )

            moviesAdapter.addLoadStateListener { loadState ->
                recyclerViewMovies.isVisible = loadState.source.refresh is LoadState.NotLoading
                progressBar.isVisible = loadState.source.refresh is LoadState.Loading
                retryButton.isVisible = loadState.source.refresh is LoadState.Error

                val errorState = loadState.source.append as? LoadState.Error
                    ?: loadState.source.prepend as? LoadState.Error
                    ?: loadState.append as? LoadState.Error
                    ?: loadState.prepend as? LoadState.Error
                    ?: loadState.source.refresh as? LoadState.Error
                errorState?.let {
                    Toast.makeText(
                        context,
                        "\uD83D\uDE28 Wooops! ${it.error.localizedMessage}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    private fun setupObservers() {
        lifecycleScope.launch {
            moviesViewModel.nowPlaying().collectLatest {
                moviesAdapter.submitData(it)
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.search, menu)
        searchView = menu.findItem(R.id.search).actionView as SearchView
        searchView.apply {
            queryHint = "Search"
            isSubmitButtonEnabled = true
            onActionViewExpanded()
        }
        searchView.setQuery(currentQueryValue, false)
        search(searchView)
        super.onCreateOptionsMenu(menu, inflater)
    }

    private fun search(searchView: SearchView) {
        searchView.setOnQueryTextListener(DebouncingQueryTextListener(
            this.lifecycle
        ) { newText ->
            newText?.let { searchQuery ->
                currentQueryValue = searchQuery
                if (searchQuery.isEmpty()) {
                    nowPlayingJob?.cancel()
                    nowPlayingJob = lifecycleScope.launch {
                        moviesViewModel.nowPlaying().collectLatest {
                            moviesAdapter.submitData(it)
                        }

                    }
                } else {
                    searchJob?.cancel()
                    searchJob = lifecycleScope.launch {
                        moviesViewModel.searchMovie(searchQuery).collectLatest {
                            moviesAdapter.submitData(it)
                        }
                    }
                }
            }
        }
        )
    }

    internal class DebouncingQueryTextListener(
        lifecycle: Lifecycle,
        private val onDebouncingQueryTextChange: (String?) -> Unit
    ) : SearchView.OnQueryTextListener {
        private var debouncePeriod: Long = 500

        private val coroutineScope = lifecycle.coroutineScope

        private var searchJob: Job? = null

        override fun onQueryTextSubmit(query: String?): Boolean {

            return false
        }

        override fun onQueryTextChange(newText: String?): Boolean {
            searchJob?.cancel()
            searchJob = coroutineScope.launch {
                newText?.let {
                    delay(debouncePeriod)
                    onDebouncingQueryTextChange(newText)
                }
            }
            return false
        }
    }
}