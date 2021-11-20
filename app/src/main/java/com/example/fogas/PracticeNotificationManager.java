package com.example.fogas;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.net.Uri;
import android.provider.Settings;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class PracticeNotificationManager extends BroadcastReceiver {

    Uri alarmSound = Settings.System.DEFAULT_ALARM_ALERT_URI;
    long [] vibratePattern = {0,100,200,300};



    @Override
    public void onReceive(Context context, Intent intent) {
        Class ourClass = null;

        try {
            ourClass = Class.forName("com.example.fogas.kerdesek");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        Intent startMyActivity = new Intent(context,notificationGame.class);
        startMyActivity.putExtra("com.example.fogas.notificationGame",200);
        PendingIntent myIntent = PendingIntent.getActivity(context,0,startMyActivity,PendingIntent.FLAG_UPDATE_CURRENT);


        NotificationCompat.Builder builder = new NotificationCompat.Builder(context,"notifyUser")
                .setSmallIcon(R.drawable.letterpracticeimage)
                .setContentTitle("It's time to practice")
                .setContentText("You need to recall what you learned so far")
                .setPriority(NotificationCompat.PRIORITY_MAX)
                .setAutoCancel(true)
                .setSound(alarmSound, AudioManager.STREAM_ALARM)
                .setVibrate(vibratePattern)
                .addAction(R.drawable.letterpracticeimage,"Action Button",myIntent)
                .setContentIntent(myIntent)
                .setOngoing(true);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);


        notificationManager.notify(200,builder.build());
    }
}
