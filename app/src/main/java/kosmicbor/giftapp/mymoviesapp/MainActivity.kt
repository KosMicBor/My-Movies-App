package kosmicbor.giftapp.mymoviesapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import kosmicbor.giftapp.mymoviesapp.databinding.MainActivityBinding


class MainActivity : AppCompatActivity(R.layout.main_activity){

    private val router: Router = Router(supportFragmentManager)
    private val binding: MainActivityBinding by viewBinding(MainActivityBinding::bind, R.id.activity_container)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

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
}