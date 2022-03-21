package com.example.movies.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.example.movies.R
import com.example.movies.data.repository.MovieRepositoryImpl
import com.example.movies.databinding.ActivityMainBinding
import com.example.movies.presentation.adapters.MovieAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private val viewModel by lazy {
        ViewModelProvider(this)[MainViewModel::class.java]
    }
    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    private val adapter = MovieAdapter()
    private val scope = CoroutineScope(Dispatchers.Main)
    private var onlyFavourites: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setupListeners()
        setTextColors(false)
        viewModel.loadData()
        setupRecyclerView()
    }

    private fun updateMoviesList() {
        viewModel.moviesList.removeObservers(this)
        var listMovies = viewModel.moviesList
        if (onlyFavourites) {
            listMovies = viewModel.getFavouritesMovies()
        }
        listMovies.observe(this) {
            adapter.submitList(it)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        onlyFavourites = !onlyFavourites
        if (onlyFavourites) {
            item.icon = AppCompatResources.getDrawable(this, R.drawable.favourite_remove)
        } else {
            item.icon = AppCompatResources.getDrawable(this, R.drawable.favourite_add_to)
        }
        updateMoviesList()
        return super.onOptionsItemSelected(item)
    }

    private fun setupListeners() {
        with(binding) {
            switcherSortBy.setOnCheckedChangeListener { _, isChecked ->
                changeSortType(isChecked)
            }
            tvPopular.setOnClickListener {
                changeSortType(false)
                switcherSortBy.isChecked = false
            }
            tvRating.setOnClickListener {
                changeSortType(true)
                switcherSortBy.isChecked = true
            }
            adapter.onPosterClickListener = {
                startActivity(DetailActivity.newIntent(it.id, this@MainActivity))
            }
            adapter.onReachEndListener = {
                viewModel.loadData()
            }
        }
    }

    private fun changeSortType(isChecked: Boolean) {
        if (isChecked) {
            viewModel.changeSortType(MovieRepositoryImpl.SORT_BY_AVERAGE_VOTE)
        } else {
            viewModel.changeSortType(MovieRepositoryImpl.SORT_BY_POPULARITY)
        }
        updateMoviesList()
        val layoutManager = binding.rvPosters.layoutManager
        scope.launch {
            delay(400)
            layoutManager?.scrollToPosition(0)
        }
        setTextColors(isChecked)
    }

    private fun setTextColors(isChecked: Boolean) {
        with(binding) {
            if (isChecked) {
                tvPopular.setTextColor(ContextCompat.getColor(this@MainActivity, R.color.white))
                tvRating.setTextColor(ContextCompat.getColor(this@MainActivity, R.color.teal_200))
            } else {
                tvPopular.setTextColor(ContextCompat.getColor(this@MainActivity, R.color.teal_200))
                tvRating.setTextColor(ContextCompat.getColor(this@MainActivity, R.color.white))
            }
        }
    }

    private fun setupRecyclerView() {
        binding.rvPosters.adapter = adapter
        viewModel.moviesList.observe(this) {
            adapter.submitList(it)
        }
    }
}