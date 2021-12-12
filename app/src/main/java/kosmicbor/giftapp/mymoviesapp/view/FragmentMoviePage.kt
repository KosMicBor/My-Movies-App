package kosmicbor.giftapp.mymoviesapp.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kosmicbor.giftapp.mymoviesapp.R
import kosmicbor.giftapp.mymoviesapp.databinding.FragmentMoviePageBinding
import kosmicbor.giftapp.mymoviesapp.domain.Movie

class FragmentMoviePage: Fragment(R.layout.fragment_movie_page) {

    companion object {
        fun newInstance() = FragmentMoviePage()
    }
    private val MOVIE_CONST = "MOVIE_CONST"
    private var _binding: FragmentMoviePageBinding? = null
    private val binding get() = _binding!!
    private var movie: Movie? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMoviePageBinding.inflate(inflater, container, false)
        movie = arguments?.getParcelable(MOVIE_CONST)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.moviePageTitle.text = movie?.title
        binding.moviePageImage.setImageResource(R.drawable.ic_launcher_background)
        binding.moviePageOverview.text = movie?.overview
        binding.moviePageVoteAverage.text = movie?.voteAverage.toString()
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}