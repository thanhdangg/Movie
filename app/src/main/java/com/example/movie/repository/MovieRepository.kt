package com.example.movie.repository

import okhttp3.OkHttpClient
import okhttp3.Request
class MovieRepository {
    private val client = OkHttpClient()

    fun getMovies(): String? {
        val request = Request.Builder()
            .url("https://api.themoviedb.org/3/discover/movie?include_adult=false&include_video=false&language=en-US&page=1&sort_by=popularity.desc")
            .get()
            .addHeader("accept", "application/json")
            .addHeader("Authorization", "Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiI1MmZkOTkwY2Y2MzczNTVmNTFkM2VlYTZiYzkzMGJiYSIsIm5iZiI6MTcyMTk4ODg2Ny45MTU4OTgsInN1YiI6IjY2YTM3MzRjNGIyZjgxNjE4NWFmYWMyNCIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.JJB2UjU9Y92HvUzd5P2rkwbGqgTeUeov_xjMIwpDAOc")
            .build()

        val response = client.newCall(request).execute()
        return if (response.isSuccessful) {
            response.body?.string()
        } else {
            null
        }
    }
}