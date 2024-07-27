package com.example.movie.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movie.model.Movie
import com.example.movie.repository.MovieRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MovieViewModel : ViewModel() {

    private val movieRepository = MovieRepository()
    val moviesLiveData = MutableLiveData<List<Movie>?>()

    fun fetchMovies() {
        viewModelScope.launch(Dispatchers.IO) {
            val movies = movieRepository.getMovies()
            if (movies != null) {
                Log.d("MovieViewModel", "Movies fetched: ${movies.size}")
                movies.forEach { movie ->
                    Log.d("MovieViewModel", "Movie: $movie")
                }
                moviesLiveData.postValue(movies)
            } else {
                Log.e("MovieViewModel", "Failed to fetch movies")
            }
        }
    }
}
