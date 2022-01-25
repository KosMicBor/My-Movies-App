package kosmicbor.giftapp.mymoviesapp.notifications

import android.util.Log
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import kosmicbor.giftapp.mymoviesapp.R

class AppFirebaseMessagingService: FirebaseMessagingService() {

    companion object {
        private const val TAG_MESSAGE = "FCM onMessageReceived"
        private const val TAG_NEW_TOKEN = "FCM onNewToken"
        private const val TITlE = "title"
        private const val MESSAGE = "message"
        private const val NOTIFICATION_ID = 242
        private const val CHANNEL_ID = "notification channel id"
    }

    private val notificationHelper = NotificationHelper()

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        val data = remoteMessage.data
        if (data.isNotEmpty()) {
            handleMessage(data.toMap())
        }
        Log.d(TAG_MESSAGE, "From: ${remoteMessage.from}")
    }

    private fun handleMessage(data: Map<String, String>) {
        val title = data[TITlE]
        val message = data[MESSAGE]

        if (!title.isNullOrBlank() && !message.isNullOrBlank()) {
            notificationHelper.createNotification(
                applicationContext,
                CHANNEL_ID,
                NOTIFICATION_ID,
                NotificationCompat.PRIORITY_DEFAULT,
                title,
                message,
                R.drawable.ic_baseline_home_24
            )
        }
    }

    override fun onNewToken(token: String) {
        Log.d(TAG_NEW_TOKEN, token)
        super.onNewToken(token)
    }
}