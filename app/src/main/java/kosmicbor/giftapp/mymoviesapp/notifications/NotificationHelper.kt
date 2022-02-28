package kosmicbor.giftapp.mymoviesapp.notifications

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat

class NotificationHelper() {
    fun createNotification(
        context: Context,
        channelId: String,
        notificationId: Int,
        notificationPriority: Int,
        title: String,
        message: String,
        smallIconRes: Int
    ) {
        val notificationBuilder = NotificationCompat.Builder(
            context,
            channelId
        )
            .setContentTitle(title)
            .setContentText(message)
            .setSmallIcon(smallIconRes)
            .setPriority(notificationPriority)

        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createNotificationChannel(notificationManager, channelId)
        }
        notificationManager.notify(
            notificationId,
            notificationBuilder.build()
        )
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotificationChannel(
        notificationManager: NotificationManager,
        channelId: String
    ) {
        val name = "Channel name"
        val description = "Channel description"
        val importance = NotificationManager.IMPORTANCE_DEFAULT

        val channel =
            NotificationChannel(channelId, name, importance).apply {
                this.description = description
            }

        notificationManager.createNotificationChannel(channel)
    }
}