package com.sample.neugelb.presentation.movies

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.sample.neugelb.R
import com.sample.neugelb.data.remote.ImageApi
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_movie_detail.*

@AndroidEntryPoint
class MovieDetailFragment : Fragment() {
    private val viewModel: MoviesViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val callback: OnBackPressedCallback =
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    if (activity is AppCompatActivity) {
                        findNavController().navigateUp()
                    }
                }
            }
        requireActivity().onBackPressedDispatcher.addCallback(this, callback)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View =
        inflater.inflate(R.layout.fragment_movie_detail, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setActionBar()
        initUI()
    }

    private fun setActionBar() {
        if (activity is AppCompatActivity) {
            (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
            (activity as AppCompatActivity).supportActionBar?.setHomeButtonEnabled(true)
        }
    }

    private fun initUI() {
        viewModel.selected.observe(viewLifecycleOwner, { movie ->
            (activity as AppCompatActivity).supportActionBar?.title = movie.title
            textTitle.text = movie.title
            textReleaseDate.text = movie.release_date
            textDesc.text = movie.overview
            textRating.text = movie.vote_average.toString()

            if (movie.poster_path.isNotEmpty())
                Glide.with(this)
                    .load(ImageApi.getPosterPath(movie.poster_path))
                    .centerInside()
                    .thumbnail(0.5f)
                    .placeholder(R.drawable.ic_launcher_background)
                    .centerInside()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(imageMovie)
        })
    }
}