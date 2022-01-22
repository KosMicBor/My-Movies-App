package kosmicbor.giftapp.mymoviesapp.view.fragments

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import by.kirich1409.viewbindingdelegate.viewBinding
import coil.load
import kosmicbor.giftapp.mymoviesapp.R
import kosmicbor.giftapp.mymoviesapp.databinding.FragmentMoviePageBinding
import kosmicbor.giftapp.mymoviesapp.domain.MovieIntentService
import kosmicbor.giftapp.mymoviesapp.domain.tmdbdata.Genre
import kosmicbor.giftapp.mymoviesapp.domain.tmdbdata.MovieDTO
import kosmicbor.giftapp.mymoviesapp.viewmodels.MoviePageViewModel
import java.lang.StringBuilder

class FragmentMoviePage : Fragment(R.layout.fragment_movie_page) {

    companion object {
        const val RECEIVED_MOVIE = "RECEIVED_MOVIE"
        const val CONST_ONE = 1

        fun newInstance() = FragmentMoviePage()
    }

    private var isInFavorite = false
    private var movie: MovieDTO? = null
    private var movieNote: String? = ""
    private val viewModel: MoviePageViewModel by viewModels()
    private val binding: FragmentMoviePageBinding by viewBinding(FragmentMoviePageBinding::bind)
    private val receiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {

            intent?.getParcelableExtra<MovieDTO>(RECEIVED_MOVIE)?.let { receiverMovie ->
                movie = receiverMovie
                movie?.apply {
                    viewModel.addMovieToLocalDB(this, isInFavorite)

                    viewModel.isMovieFavorite(movie
                    ) { value ->
                        if (value) {
                            binding.favoriteIcon.setBackgroundResource(R.drawable.ic_baseline_favorite_24)
                            isInFavorite = true
                        }
                    }

                    viewModel.getMovieNote(this) { note ->
                        binding.moviePageNote.text = note
                    }
                }
            }

            binding.apply {
                movie?.let {
                    moviePageTitle.text = it.title
                    moviePageImage.load("https://image.tmdb.org/t/p/w500${it.posterPath}")
                    moviePageOverview.text = it.overview
                    moviePageVoteAverage.text = it.voteAverage.toString()
                    moviePageBudget.text = it.budget.toString()
                    moviePageGenres.text = getGenres(it.genres)
                    moviePageOriginalName.text = it.originalTitle
                    moviePageReleaseDate.text = it.releaseDate
                    moviePageRevenue.text = it.revenue.toString()
                    moviePageRuntime.text = it.runtime.toString()
                }
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getMoviePageLiveData().observe(viewLifecycleOwner) {
            //do something
        }

        binding.moviePageToolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.menu_item_add_note -> {
                    val dialog = FragmentMoviePageDialog.newInstance()
                    dialog.show(parentFragmentManager, "NOTE_DIALOG")
                    dialog.okListener = FragmentMoviePageDialog.OkClickListener { note ->
                        binding.moviePageNote.text = note
                        movieNote = note
                        viewModel.updateMovieInDB(movie, isInFavorite, movieNote)
                    }
                    true
                }
                else -> super.onOptionsItemSelected(it)
            }
        }

        binding.favoriteIcon.setOnClickListener { button ->

            viewModel.isMovieFavorite(movie
            ) { value ->
                if (!value) {
                    button.setBackgroundResource(R.drawable.ic_baseline_favorite_24)
                    viewModel.updateMovieInDB(movie, isInFavorite, movieNote)
                } else {
                    button.setBackgroundResource(R.drawable.ic_baseline_favorite_border_24)
                    viewModel.removeMovieFromFavorites(movie)
                }
            }
        }
    }

    private fun getGenres(genres: List<Genre>?): String {
        val builder = StringBuilder()
        val lastIndex = (genres?.size ?: CONST_ONE) - CONST_ONE

        genres?.forEach {
            builder.apply {
                append(it.name)
                if (genres.indexOf(it) != (lastIndex)) {
                    append((", "))
                }
            }

        }
        return builder.toString()
    }


    override fun onStart() {
        super.onStart()
        IntentFilter(MovieIntentService.REQUEST_MOVIE_RESULT).also {
            LocalBroadcastManager.getInstance(requireContext()).registerReceiver(receiver, it)
        }
    }


    override fun onStop() {
        super.onStop()
        LocalBroadcastManager.getInstance(requireContext()).unregisterReceiver(receiver)
    }
}