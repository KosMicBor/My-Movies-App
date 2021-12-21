package kosmicbor.giftapp.mymoviesapp.view

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import kosmicbor.giftapp.mymoviesapp.R
import kosmicbor.giftapp.mymoviesapp.databinding.FragmentMoviePageBinding
import kosmicbor.giftapp.mymoviesapp.domain.Movie
import kosmicbor.giftapp.mymoviesapp.viewmodel.MoviePageViewModel

class FragmentMoviePage : Fragment(R.layout.fragment_movie_page) {

    companion object {
        private const val MOVIE_CONST = "MOVIE_CONST"

        fun newInstance(bundle: Bundle): FragmentMoviePage {
            val fragment = FragmentMoviePage()
            fragment.arguments = bundle
            return fragment
        }
    }

    private val viewModel: MoviePageViewModel by viewModels()
    private val binding: FragmentMoviePageBinding by viewBinding(FragmentMoviePageBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.getParcelable<Movie>(MOVIE_CONST)?.let {
            if (viewModel.isMovieFavorite(it)) {
                binding.favoriteIcon.setBackgroundResource(R.drawable.ic_baseline_favorite_24)
            }
        }

        arguments?.getParcelable<Movie>(MOVIE_CONST)?.let { movie ->
            binding.apply {
                moviePageTitle.text = movie.title
                moviePageImage.setImageResource(R.drawable.ic_launcher_background)
                moviePageOverview.text = movie.overview
                moviePageVoteAverage.text = movie.voteAverage.toString()

                favoriteIcon.setOnClickListener { button ->
                    if (!viewModel.isMovieFavorite(movie)) {
                        button.setBackgroundResource(R.drawable.ic_baseline_favorite_24)
                        viewModel.addFavoriteMovie(movie)
                    } else {
                        button.setBackgroundResource(R.drawable.ic_baseline_favorite_border_24)
                        viewModel.removeFavoriteMovie(movie)
                    }
                }
            }
        } ?: throw NullPointerException("The movie is null!")
    }
}