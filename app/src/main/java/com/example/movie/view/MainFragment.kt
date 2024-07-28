package com.example.movie.view

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.movie.R
import com.example.movie.adapter.MovieMainAdapter
import com.example.movie.databinding.FragmentMainBinding
import com.example.movie.model.Movie
import com.example.movie.viewmodel.MovieViewModel

class MainFragment : FullScreenFragment(R.layout.fragment_main) {
    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    private lateinit var movieViewModel: MovieViewModel
    private lateinit var movieMainAdapter: MovieMainAdapter


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        val view = binding.root

        movieViewModel = ViewModelProvider(this).get(MovieViewModel::class.java)

        // Fetch movies from the API
        movieViewModel.fetchMovies()

//        movieMainAdapter = MovieMainAdapter(listOf())
        movieMainAdapter = MovieMainAdapter(listOf()) { movie ->
            openDetailFragment(movie)
        }

        binding.popularMovies.adapter = movieMainAdapter
        binding.todayPickMovies.adapter = movieMainAdapter

        movieViewModel.moviesLiveData.observe(viewLifecycleOwner, Observer { movies ->

            if (movies!= null) {
//                movieMainAdapter = MovieMainAdapter(movies)
                movieMainAdapter = MovieMainAdapter(movies) { movie ->
                    openDetailFragment(movie)
                }
                binding.popularMovies.adapter = movieMainAdapter
                binding.todayPickMovies.adapter = movieMainAdapter
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
        val recyclerView: RecyclerView = binding.popularMovies
        recyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
//        recyclerView.adapter = MovieMainAdapter(emptyList())
        recyclerView.adapter = movieMainAdapter


        val recyclerView2: RecyclerView = binding.todayPickMovies
        recyclerView2.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, true)
//        recyclerView2.adapter = MovieMainAdapter(emptyList())
        recyclerView2.adapter = movieMainAdapter


    }

    private fun openDetailFragment(movie: Movie) {
        val fragment = DetailFragment.newInstance(movie)
        parentFragmentManager.beginTransaction()
            .replace(R.id.nav_host_fragment, fragment)
            .addToBackStack(null)
            .commit()
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onPause() {
        super.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}