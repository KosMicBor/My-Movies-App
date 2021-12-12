package kosmicbor.giftapp.mymoviesapp

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import kosmicbor.giftapp.mymoviesapp.view.*

class Router(private val supportFragmentManager: FragmentManager) {
    fun openFragmentFavorites() {
        supportFragmentManager.beginTransaction()
            .setReorderingAllowed(true)
            .replace(R.id.main_container, FragmentFavorites.newInstance())
            .addToBackStack("Favorites")
            .commit()
    }

    fun openFragmentMain() {
        supportFragmentManager.beginTransaction()
            .setReorderingAllowed(true)
            .replace(R.id.main_container, FragmentMain.newInstance())
            .addToBackStack("MainList")
            .commit()
    }

    fun openFragmentRatings() {
        supportFragmentManager.beginTransaction()
            .setReorderingAllowed(true)
            .replace(R.id.main_container, FragmentRatings.newInstance())
            .addToBackStack("Ratings")
            .commit()
    }

    fun openFragmentProfile() {
        supportFragmentManager.beginTransaction()
            .setReorderingAllowed(true)
            .replace(R.id.main_container, FragmentProfile.newInstance())
            .addToBackStack("Profile")
            .commit()
    }

    fun openFragmentMoviePage(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .setReorderingAllowed(true)
            .replace(R.id.main_container, fragment)
            .addToBackStack("MoviesPage")
            .commit()
    }
}