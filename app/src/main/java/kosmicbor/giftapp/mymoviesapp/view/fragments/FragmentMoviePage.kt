package kosmicbor.giftapp.mymoviesapp.view.fragments

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.fragment.app.Fragment
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import by.kirich1409.viewbindingdelegate.viewBinding
import kosmicbor.giftapp.mymoviesapp.R
import kosmicbor.giftapp.mymoviesapp.databinding.FragmentMoviePageBinding
import kosmicbor.giftapp.mymoviesapp.domain.MovieIntentService
import kosmicbor.giftapp.mymoviesapp.domain.tmdbdata.Genre
import kosmicbor.giftapp.mymoviesapp.domain.tmdbdata.MovieDTO
import java.lang.StringBuilder

class FragmentMoviePage : Fragment(R.layout.fragment_movie_page) {

    companion object {
        const val RECEIVED_MOVIE = "RECEIVED_MOVIE"
        const val CONST_ONE = 1

        fun newInstance() = FragmentMoviePage()
    }

    private var movie: MovieDTO? = null
    private val binding: FragmentMoviePageBinding by viewBinding(FragmentMoviePageBinding::bind)
    private val receiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {

            intent?.getParcelableExtra<MovieDTO>(RECEIVED_MOVIE)?.apply {
                movie = this
            }

            binding.apply {
                movie?.let {
                    moviePageTitle.text = it.title
                    moviePageImage.setImageResource(R.drawable.ic_launcher_background)
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