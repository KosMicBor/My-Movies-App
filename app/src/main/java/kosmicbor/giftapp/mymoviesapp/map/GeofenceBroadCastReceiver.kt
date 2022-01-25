package kosmicbor.giftapp.mymoviesapp.map

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.app.NotificationCompat
import com.google.android.gms.location.Geofence
import com.google.android.gms.location.GeofencingEvent
import kosmicbor.giftapp.mymoviesapp.R
import kosmicbor.giftapp.mymoviesapp.notifications.NotificationHelper

class GeofenceBroadCastReceiver : BroadcastReceiver() {

    companion object {
        private const val NOTIFICATION_ID = 243
        private const val CHANNEL_ID = "geofence notification channel id"
    }

    private val notificationHelper = NotificationHelper()

    override fun onReceive(context: Context?, intent: Intent?) {
        intent?.apply {
            val geofencingEvent = GeofencingEvent.fromIntent(intent)
            if (geofencingEvent.hasError()) {
                Log.d("Geofence", "On Receive: Error receiving geofence event...")
                return
            }

            val transitionType = geofencingEvent.geofenceTransition
            if (transitionType == Geofence.GEOFENCE_TRANSITION_ENTER) {
                val geofencesList = geofencingEvent.triggeringGeofences

                geofencesList.forEach {
                    Log.d("Geofence", it.requestId)

                    Log.d("Geofence", "Alert!")

                    context?.apply {
                        notificationHelper.createNotification(
                            context,
                            CHANNEL_ID,
                            NOTIFICATION_ID,
                            NotificationCompat.PRIORITY_HIGH,
                            it.requestId,
                            "Come to visit ${it.requestId}",
                            R.drawable.ic_baseline_theaters_24
                        )
                    }
                }
            } else {
                Log.d("Geofence", "Invalid type transition $transitionType")
            }
        }
    }
}