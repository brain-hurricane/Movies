package com.example.movies.presentation

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import com.example.movies.R
import com.example.movies.data.network.BASE_YOUTUBE_URL
import com.example.movies.data.preferences.AppSettings
import com.example.movies.databinding.ActivityDetailBinding
import com.example.movies.databinding.ActivityMainBinding
import com.example.movies.domain.Movie
import com.example.movies.domain.Videos
import com.example.movies.presentation.adapters.VideosAdapter
import com.squareup.picasso.Picasso

class DetailActivity : AppCompatActivity() {

    private val viewModel by lazy {
        ViewModelProvider(this)[MainViewModel::class.java]
    }
    private val binding by lazy {
        ActivityDetailBinding.inflate(layoutInflater)
    }

    private lateinit var movie: Movie
    private var isFavourite = false
    private lateinit var appSettings: AppSettings
    private val adapter = VideosAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        appSettings = AppSettings(this)

        val movieId = intent?.getIntExtra(EXTRA_MOVIE_ID, 0) ?: return
        viewModel.getMovieInfo(movieId).observe(this) {
            with(binding) {
                Picasso.get().load(it.posterPath).into(imageViewBigPoster)
                textViewTitle.text = it.title
                textViewOriginalTitle.text = it.originalTitle
                textViewOverview.text = it.overview
                textViewRating.text = it.voteAverage.toString()
                textViewReleaseDate.text = it.releaseDate
                isFavourite = appSettings.getFavouriteState(it)
                changeImageFavourite(isFavourite)
                movie = it
            }
        }

        viewModel.getVideos(movieId)
        setupRecyclerView()

        binding.imageViewAddToFavourite.setOnClickListener {
            isFavourite = !isFavourite
            appSettings.changeFavouriteState(movie, isFavourite)
            changeImageFavourite(isFavourite)
        }

        adapter.onItemClickListener = {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(BASE_YOUTUBE_URL + it.key))
            startActivity(intent)
        }
    }

    private fun changeImageFavourite(isFavourite: Boolean) {
        if (isFavourite) {
            binding.imageViewAddToFavourite.setImageResource(R.drawable.favourite_remove)
        } else {
            binding.imageViewAddToFavourite.setImageResource(R.drawable.favourite_add_to)
        }
    }

    private fun setupRecyclerView() {
        binding.rvVideos.adapter = adapter
        viewModel.videos.observe(this) {
            adapter.submitList(it)
        }
    }

    companion object {
        private const val EXTRA_MOVIE_ID = "movie_id"
        fun newIntent(id: Int, context: Context): Intent {
            return Intent(context, DetailActivity::class.java).apply {
                putExtra(EXTRA_MOVIE_ID, id)
            }
        }
    }
}