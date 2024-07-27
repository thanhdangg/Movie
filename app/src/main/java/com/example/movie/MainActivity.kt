package com.example.movie

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI.setupActionBarWithNavController
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.movie.databinding.ActivityMainBinding
import com.example.movie.viewmodel.MovieViewModel

class MainActivity : AppCompatActivity() {
    private val movieViewModel: MovieViewModel by viewModels()
    private lateinit var navController: NavController

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        val windowInsetsController = WindowCompat.getInsetsController(window, window.decorView)
        windowInsetsController?.hide(WindowInsetsCompat.Type.systemBars())
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.nav_host_fragment)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, 0, systemBars.right, 0)
            insets
        }
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController

//        setupActionBarWithNavController(navController)

        movieViewModel.moviesLiveData.observe(this, Observer { movies ->

        })

        // Gọi hàm fetchMovies để lấy dữ liệu từ API
        movieViewModel.fetchMovies()
    }


}