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


            val transitionType = geofencingEvent.geofenceTransition
            if (transitionType == Geofence.GEOFENCE_TRANSITION_ENTER) {
                val geofencesList = geofencingEvent.triggeringGeofences

                geofencesList.forEach {
                    Log.d("Geofence", it.requestId)

                    Log.d("Geofence", "Alert!")

                    AlertDialog.Builder(context)
                        .setTitle(it.requestId)
                        .setMessage("${it.requestId} say \"Hi\" to you")
                        .setPositiveButton("OK") { dialog, _ ->
                            dialog.dismiss()
                        }
                }
            } else {
                Log.d("Geofence" , "Invalid type transition $transitionType")
            }
        }
    }
}