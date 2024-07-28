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
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.movie.R
import com.example.movie.adapter.MovieMainAdapter
import com.example.movie.databinding.FragmentDetailBinding
import com.example.movie.model.Movie
import com.example.movie.viewmodel.MovieViewModel


class DetailFragment : Fragment() {

    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!
    private var movie: Movie? = null

    private lateinit var movieViewModel: MovieViewModel
    private lateinit var movieMainAdapter: MovieMainAdapter


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        val view = binding.root

        movieViewModel = ViewModelProvider(this).get(MovieViewModel::class.java)
        movieMainAdapter = MovieMainAdapter(listOf()) { movie ->
            openDetailFragment(movie)
        }
        binding.rvCast.adapter = movieMainAdapter

        movieViewModel.moviesLiveData.observe(viewLifecycleOwner, Observer { movies ->

            if (movies!= null) {
//                movieMainAdapter = MovieMainAdapter(movies)
                movieMainAdapter = MovieMainAdapter(movies) { movie ->
                    openDetailFragment(movie)
                }
                binding.rvCast.adapter = movieMainAdapter
            } else {
                Log.e("DetailFragment", "Failed to fetch movies")
            }
        })
        movieViewModel.fetchMovies()


        movie = arguments?.getParcelable("movie")
        Log.d("DetailFragment", "Movie: $movie")

        binding.tvTitle.text = movie?.title
        binding.tvYear.text = movie?.releaseDate
        binding.tvDescription.text = movie?.overview

        val rating = movie?.voteAverage?.toFloat()
        binding.tvRating.text = rating.toString()

        movie?.posterPath?.let {
            Glide.with(this)
                .load(it)
                .into(binding.ivPoster)

        }



        return view

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView: RecyclerView = binding.rvCast
        recyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
//        recyclerView.adapter = MovieMainAdapter(emptyList())
        recyclerView.adapter = movieMainAdapter

    }
    companion object {
        fun newInstance(movie: Movie): DetailFragment {
            val fragment = DetailFragment()
            val args = Bundle()
            if (movie != null) {
                args.putParcelable("movie", movie)
            }
            fragment.arguments = args
            return fragment
        }
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
//        activity?.window?.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)

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