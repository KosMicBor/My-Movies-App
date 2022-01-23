package kosmicbor.giftapp.mymoviesapp.map

import android.app.AlertDialog
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.google.android.gms.location.Geofence
import com.google.android.gms.location.GeofencingEvent

class GeofenceBroadCastReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        intent?.apply {
            val geofencingEvent = GeofencingEvent.fromIntent(intent)
            if (geofencingEvent.hasError()) {
                Log.d("Geofence", "On Receive: Error receiving geofence event...")
                return
            }

            val geofencesList = geofencingEvent.triggeringGeofences
            val transitionType = geofencingEvent.geofenceTransition

            geofencesList.forEach {
                Log.d("Geofence", it.requestId)
                if (transitionType == Geofence.GEOFENCE_TRANSITION_ENTER) {
                    Log.d("Geofence", "Alert!")
                    AlertDialog.Builder(context)
                        .setTitle(it.requestId)
                        .setMessage("${it.requestId} say \"Hi\" to you")
                        .setPositiveButton("OK") { dialog, _ ->
                            dialog.dismiss()
                        }
                }
            }
        }
    }
}