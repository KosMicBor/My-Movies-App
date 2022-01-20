package kosmicbor.giftapp.mymoviesapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import by.kirich1409.viewbindingdelegate.viewBinding
import kosmicbor.giftapp.mymoviesapp.databinding.MainActivityBinding
import android.content.IntentFilter
import android.net.ConnectivityManager
import kosmicbor.giftapp.mymoviesapp.domain.ConnectivityReceiver
import kosmicbor.giftapp.mymoviesapp.domain.repositories.LocalRepoImpl
import kosmicbor.giftapp.mymoviesapp.domain.repositories.RepositoryImpl


class MainActivity : AppCompatActivity(R.layout.main_activity) {

    private val binding: MainActivityBinding by viewBinding(
        MainActivityBinding::bind,
        R.id.activity_container
    )


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (savedInstanceState == null) {
            Router.openFragmentMain(supportFragmentManager)
        }

        binding.bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.favorites -> {
                    Router.openFragmentFavorites(supportFragmentManager)
                    true
                }
                R.id.ratings -> {
                    Router.openFragmentRatings(supportFragmentManager)
                    true
                }
                R.id.profile -> {
                    Router.openFragmentProfile(supportFragmentManager)
                    true
                }
                R.id.home -> {
                    Router.openFragmentMain(supportFragmentManager)
                    true
                }

                else -> {
                    throw Exception()
                }
            }
        }

        val cR = ConnectivityReceiver()
        IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION).also {
            registerReceiver(cR, it)
        }
    }
}