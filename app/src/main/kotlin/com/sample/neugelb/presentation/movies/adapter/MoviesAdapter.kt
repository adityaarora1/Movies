package com.sample.neugelb.presentation.movies.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.sample.neugelb.R
import com.sample.neugelb.data.remote.ImageApi
import com.sample.neugelb.domain.model.Movie
import kotlinx.android.synthetic.main.item_list_movies.view.*

internal class MoviesAdapter(
    private val itemClick: (Movie) -> Unit
) : PagingDataAdapter<Movie, MoviesAdapter.ViewHolder>(DataDiff) {

    enum class ViewType {
        DATA,
        FOOTER
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view)

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val movie: Movie? = getItem(position)
        holder.itemView.textTitle.text = movie?.title
        holder.itemView.textReleaseDate.text = movie?.release_date
        movie?.poster_path?.let {
            Glide.with(holder.itemView.context)
                .load(ImageApi.getThumbnailPath(it))
                .centerCrop()
                .thumbnail(0.5f)
                .placeholder(R.drawable.ic_launcher_background)
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.itemView.img_movie)
        }

        movie?.let { movieData ->
            holder.itemView.setOnClickListener {
                itemClick.invoke(movieData)
            }
        }

    }

    override fun getItemViewType(position: Int): Int {
        return if (position == itemCount) {
            ViewType.DATA.ordinal
        } else {
            ViewType.FOOTER.ordinal
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater
                .from(parent.context)
                .inflate(R.layout.item_list_movies, parent, false)
        )
    }

    object DataDiff : DiffUtil.ItemCallback<Movie>() {

        override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem == newItem
        }
    }
}
