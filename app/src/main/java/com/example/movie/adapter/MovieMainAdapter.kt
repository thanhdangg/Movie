package com.example.movie.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.movie.R
import com.example.movie.model.Movie

class MovieMainAdapter (private var movies: List<Movie>,
                        private val listener: (Movie) -> Unit
): RecyclerView.Adapter<MovieMainAdapter.MovieViewHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_movie_popular, parent, false)
        return MovieViewHolder(view)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
//        holder. bind(movies[position])
        holder.bind(movies[position], listener)

    }

    override fun getItemCount(): Int {
        return movies.size
    }

    class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(movie: Movie) {
            Glide.with(itemView.context)
                .load(movie.posterPath)
                .into(itemView.findViewById(R.id.moviePoster))
        }
        fun bind(movie: Movie, listener: (Movie) -> Unit) = with(itemView) {
            Glide.with(itemView.context)
                .load(movie.posterPath)
                .into(itemView.findViewById(R.id.moviePoster))
            setOnClickListener { listener(movie) }
        }
    }
}