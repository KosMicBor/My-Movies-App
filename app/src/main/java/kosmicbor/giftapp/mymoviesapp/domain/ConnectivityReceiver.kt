package kosmicbor.giftapp.mymoviesapp.domain

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast

class ConnectivityReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        Toast.makeText(context, "You are offline", Toast.LENGTH_LONG).show()
    }
}