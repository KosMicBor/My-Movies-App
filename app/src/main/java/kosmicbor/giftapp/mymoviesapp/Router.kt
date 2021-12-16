package kosmicbor.giftapp.mymoviesapp

import android.os.Bundle
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

    fun openFragmentMoviePage(bundle: Bundle) {
        supportFragmentManager.beginTransaction()
            .setReorderingAllowed(true)
            .replace(R.id.main_container, FragmentMoviePage.newInstance(bundle))
            .addToBackStack("MoviesPage")
            .commit()
    }
}