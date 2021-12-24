package kosmicbor.giftapp.mymoviesapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationMenuView
import com.google.android.material.bottomnavigation.BottomNavigationView
import kosmicbor.giftapp.mymoviesapp.databinding.MainActivityBinding
import kosmicbor.giftapp.mymoviesapp.domain.Movie
import kosmicbor.giftapp.mymoviesapp.view.FragmentMain
import kosmicbor.giftapp.mymoviesapp.view.FragmentMoviePage
import kosmicbor.giftapp.mymoviesapp.view.MoviesListAdapterClickListener
import java.util.*

class MainActivity : AppCompatActivity(),  MoviesListAdapterClickListener{

    private val MOVIE_CONST = "MOVIE_CONST"
    private val router: Router = Router(supportFragmentManager)
    lateinit var bottomNavMenu: BottomNavigationView
    private lateinit var binding: MainActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = MainActivityBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        if (savedInstanceState == null) {
            router.openFragmentMain()
        }
        binding.bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.favorites -> {
                    router.openFragmentFavorites()
                    true
                }
                R.id.ratings -> {
                    router.openFragmentRatings()
                    true
                }
                R.id.profile -> {
                    router.openFragmentProfile()
                    true
                }
                R.id.home -> {
                    router.openFragmentMain()
                    true
                }

                else -> {
                    throw Exception()
                }
            }
        }
    }

    override fun onClick(movie: Movie) {
        val bundle = Bundle()
        bundle.putParcelable(MOVIE_CONST, movie)
        val fragmentMoviePage = FragmentMoviePage()
        fragmentMoviePage.arguments = bundle
        router.openFragmentMoviePage(fragmentMoviePage)
    }
}