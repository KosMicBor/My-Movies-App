package kosmicbor.giftapp.mymoviesapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import by.kirich1409.viewbindingdelegate.viewBinding
import kosmicbor.giftapp.mymoviesapp.databinding.MainActivityBinding
import android.util.Log
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging


class MainActivity : AppCompatActivity(R.layout.main_activity) {

    companion object {
    private const val FCM_TAG = "FCM Listener"
    }

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

        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.w(FCM_TAG, "Fetching FCM registration token failed", task.exception)
                return@OnCompleteListener
            }

            val token = task.result

            val msg = token.toString()
            Log.d(FCM_TAG, "Token from listener: $msg")
        })
    }
}