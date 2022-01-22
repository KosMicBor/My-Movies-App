package kosmicbor.giftapp.mymoviesapp.map

import android.app.AlertDialog
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.google.android.gms.location.Geofence
import com.google.android.gms.location.GeofencingEvent

class GeofenceBroadCastReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        intent?.apply {
            val geofencingEvent = GeofencingEvent.fromIntent(intent)
            if (geofencingEvent.hasError()) {
                return
            }

            val geofenceTransition = geofencingEvent.geofenceTransition

            if (geofenceTransition == Geofence.GEOFENCE_TRANSITION_ENTER) {
                val triggeringGeofences = geofencingEvent.triggeringGeofences

                AlertDialog.Builder(context)
                    .setTitle(triggeringGeofences[0].requestId)
                    .setMessage("${triggeringGeofences[0].requestId} say \"Hi\" to you")
                    .setPositiveButton("OK") { dialog, _ ->
                        dialog.dismiss()
                    }
            }
        }
    }
}