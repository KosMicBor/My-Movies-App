package kosmicbor.giftapp.mymoviesapp

import androidx.fragment.app.FragmentManager
import kosmicbor.giftapp.mymoviesapp.view.fragments.*

object Router {
    fun openFragmentFavorites(supportFragmentManager: FragmentManager) {
        supportFragmentManager.beginTransaction()
            .setReorderingAllowed(true)
            .replace(R.id.main_container, FragmentFavorites.newInstance())
            .addToBackStack("Favorites")
            .commit()
    }

    fun openFragmentMain(supportFragmentManager: FragmentManager) {
        supportFragmentManager.beginTransaction()
            .setReorderingAllowed(true)
            .replace(R.id.main_container, FragmentMain.newInstance())
            .addToBackStack("MainList")
            .commit()
    }

    fun openFragmentRatings(supportFragmentManager: FragmentManager) {
        supportFragmentManager.beginTransaction()
            .setReorderingAllowed(true)
            .replace(R.id.main_container, FragmentRatings.newInstance())
            .addToBackStack("Ratings")
            .commit()
    }

    fun openFragmentProfile(supportFragmentManager: FragmentManager) {
        supportFragmentManager.beginTransaction()
            .setReorderingAllowed(true)
            .replace(R.id.main_container, FragmentProfile.newInstance())
            .addToBackStack("Profile")
            .commit()
    }

    fun openFragmentMoviePage(supportFragmentManager: FragmentManager) {
        supportFragmentManager.beginTransaction()
            .setReorderingAllowed(true)
            .replace(R.id.main_container, FragmentMoviePage.newInstance())
            .addToBackStack("MoviesPage")
            .commit()
    }

    fun openFragmentContacts(supportFragmentManager: FragmentManager) {
        supportFragmentManager.beginTransaction()
            .setReorderingAllowed(true)
            .replace(R.id.main_container, FragmentContacts.newInstance())
            .addToBackStack("Contacts")
            .commit()
    }
}