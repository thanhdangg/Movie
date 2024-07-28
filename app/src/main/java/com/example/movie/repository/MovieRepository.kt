package com.example.movie.repository

import android.util.Log
import com.example.movie.model.Movie
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import okhttp3.OkHttpClient
import okhttp3.Request
class MovieRepository {
    private val client = OkHttpClient()
    private val gson = Gson()


    fun getMovies(): List<Movie>? {
        val request = Request.Builder()
            .url("https://api.themoviedb.org/3/discover/movie?include_adult=false&include_video=false&language=en-US&page=1&sort_by=popularity.desc")
            .get()
            .addHeader("accept", "application/json")
            .addHeader("Authorization", "Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiI1MmZkOTkwY2Y2MzczNTVmNTFkM2VlYTZiYzkzMGJiYSIsIm5iZiI6MTcyMTk4ODg2Ny45MTU4OTgsInN1YiI6IjY2YTM3MzRjNGIyZjgxNjE4NWFmYWMyNCIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.JJB2UjU9Y92HvUzd5P2rkwbGqgTeUeov_xjMIwpDAOc")
            .build()

        val response = client.newCall(request).execute()
        val responseBody = response.body?.string()

        if (response.isSuccessful && responseBody != null) {
            Log.d("MovieRepository", "API Response: $responseBody")

            val movieResponse = gson.fromJson(responseBody, MovieResponse::class.java)
            gson.toJson(movieResponse)
            return movieResponse.results.map{
                Movie(
                    id = it.id,
                    title = it.title,
                    posterPath = "https://image.tmdb.org/t/p/w500${it.posterPath}",
                    backdropPath = "https://image.tmdb.org/t/p/w500${it.posterPath}",
                    overview = it.overview,
                    releaseDate = it.releaseDate,
                    voteAverage = it.voteAverage,
                    voteCount = it.voteCount,
                    originalLanguage = it.originalLanguage,
                    popularity = it.popularity
                )
            }
        } else {
            return null
        }
    }
}
data class MovieResponse(
    val page: Int,
    val results: List<MovieResult>,
    val total_pages: Int,
    val total_results: Int
)

data class MovieResult(
    val id: Int,
    val title: String,
    val overview: String,
    @SerializedName("poster_path") val posterPath: String,
    @SerializedName("release_date") val releaseDate: String,
    @SerializedName("vote_average") val voteAverage: Double,
    @SerializedName("vote_count") val voteCount: Int,
    @SerializedName("original_language") val originalLanguage: String,
    @SerializedName("popularity") val popularity: Double
)