package com.lawyaar.notification

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.net.Uri
import android.preference.PreferenceManager
import android.util.Log
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.lawyaar.MainActivity
import com.lawyaar.R

class CloudMessagingService : FirebaseMessagingService() {

    override fun onNewToken(token: String) {
        super.onNewToken(token)

        updateToken(token)
    }

    private fun updateToken(newToken: String?) {
        val sharedPreference =  getSharedPreferences("device_token",Context.MODE_PRIVATE)
        sharedPreference.edit().putString("device_token", newToken).apply()
        Log.d("TOKEN","token--> "+newToken)

    }

    override fun onMessageReceived(remoteMessage: RemoteMessage)
    {
       Log.d("MSG",""+ remoteMessage.notification!!.body)

        val notificationTitle = remoteMessage.notification!!.title
        val notificationBody = remoteMessage.notification!!.body

        showNotification(notificationTitle, notificationBody)
    }

    fun showNotification(notificationTitle: String?, notificationBody: String?)
    {

        val intent = Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        val pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)

        val defaultSoundUri: Uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val notificationBuilder = NotificationCompat.Builder(this, getString(R.string.appointment_notifications_channel_id))
                .setAutoCancel(true) // Automatically delete the notification
                .setSmallIcon(R.drawable.lawyaar_icon) // Notification icon
                .setContentIntent(pendingIntent)
                .setContentTitle(notificationTitle)
                .setContentText(notificationBody)
                .setSound(defaultSoundUri)
                as NotificationCompat.Builder

        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        notificationManager.notify(0, notificationBuilder.build())
    }
}