package com.example.movie.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.movie.R
import com.example.movie.adapter.MovieAdapter
import com.example.movie.databinding.FragmentSplashBinding
import com.example.movie.viewmodel.MovieViewModel
import androidx.lifecycle.Observer
import com.example.movie.model.Movie

class SplashFragment : FullScreenFragment(R.layout.fragment_splash) {

    private var _binding: FragmentSplashBinding? = null
    private val binding get() = _binding!!
    private lateinit var movieViewModel: MovieViewModel
    private lateinit var movieAdapter: MovieAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentSplashBinding.inflate(inflater, container, false)
        val view = binding.root

        // Initialize the MovieViewModel
        movieViewModel = ViewModelProvider(this).get(MovieViewModel::class.java)

        // Fetch movies from the API
        movieViewModel.fetchMovies()

        // Initialize the MovieAdapter
        movieAdapter = MovieAdapter(listOf())

        // Set the adapter for the RecyclerView
        binding.movieRecyclerView.adapter = movieAdapter

        // Log the initial value of moviesLiveData
        Log.d("SplashFragment", "Adapter set: " + movieViewModel.moviesLiveData.value)

        // Observe the LiveData in the MovieViewModel
        movieViewModel.moviesLiveData.observe(viewLifecycleOwner, Observer { movies ->
            // When the data changes, update the adapter
//            movies?.let {
//                movieAdapter.updateMovies(it)
//            }
            if (movies!= null) {
                Log.d("SplashFragment", "Movies fetched: ${movies.size}")
                movies.forEach { movie ->
                    Log.d("SplashFragment", "Movie: $movie")
                }
                movieAdapter = MovieAdapter(movies)
                binding.movieRecyclerView.adapter = movieAdapter
//                movieAdapter.updateMovies(movies)
            } else {
                Log.e("SplashFragment", "Failed to fetch movies")
            }
        })
        movieViewModel.fetchMovies()


        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Setup RecyclerView
        val recyclerView: RecyclerView = binding.movieRecyclerView
        recyclerView.layoutManager = StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL)
        recyclerView.adapter = MovieAdapter(emptyList())

        // Navigate to MainFragment on button click
        binding.startButton.setOnClickListener {
            findNavController().navigate(R.id.action_splashFragment_to_mainFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
