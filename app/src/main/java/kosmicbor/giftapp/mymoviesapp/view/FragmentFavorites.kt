package kosmicbor.giftapp.mymoviesapp.view

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.snackbar.Snackbar

import kosmicbor.giftapp.mymoviesapp.R
import kosmicbor.giftapp.mymoviesapp.Router
import kosmicbor.giftapp.mymoviesapp.databinding.FragmentFavoritesBinding
import kosmicbor.giftapp.mymoviesapp.domain.Movie
import kosmicbor.giftapp.mymoviesapp.domain.showSnackBar
import kosmicbor.giftapp.mymoviesapp.domain.viewHide
import kosmicbor.giftapp.mymoviesapp.domain.viewShow
import kosmicbor.giftapp.mymoviesapp.viewmodel.FavoritesViewModel

class FragmentFavorites : Fragment(R.layout.fragment_favorites) {

    private val binding: FragmentFavoritesBinding by viewBinding(FragmentFavoritesBinding::bind)
    private val recyclerView: RecyclerView by lazy {
        binding.favoritesRecyclerView
    }
    private val viewModel: FavoritesViewModel by viewModels()
    private val favoritesAdapter = FavoritesRVAdapter()
    private val router: Router by lazy {
        Router(requireActivity().supportFragmentManager)
    }

    companion object {
        fun newInstance() = FragmentFavorites()
        private const val MOVIE_CONST = "MOVIE_CONST"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null) {
            viewModel.getFavoritesList()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView.apply {
            layoutManager = GridLayoutManager(requireActivity(), 2)
            adapter = favoritesAdapter.apply {
                itemClick = FavoritesRVAdapter.FavoritesOnClick { movie ->
                    val bundle = Bundle().apply {
                        putParcelable(MOVIE_CONST, movie)
                    }
                    router.openFragmentMoviePage(bundle)
                }
            }
        }

        viewModel.getFavoritesListLiveData().observe(viewLifecycleOwner) {
            when (it) {
                is Success -> {
                    binding.apply {
                        favoritesRecyclerView.viewShow()
                        favoritesProgressBar.viewHide()
                    }
                    favoritesAdapter.setFavoritesMovies(it.value as List<Movie>)
                }

                is Error -> {
                    binding.favoritesProgressBar.viewShow()
                    binding.root.showSnackBar(
                        binding.root,
                        R.string.error_load_favorites_data_message,
                        Snackbar.LENGTH_INDEFINITE,
                        R.string.try_again_btn,
                    ) {
                        viewModel.getFavoritesList()
                    }
                }
                is LoadingState -> {
                    binding.apply {
                        favoritesProgressBar.viewShow()
                        favoritesRecyclerView.viewHide()
                    }
                }
            }
        }
    }
}

