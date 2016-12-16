package com.example.ufjf.chama1;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;

import com.google.firebase.messaging.FirebaseMessagingService;

/**
 * Created by eduar on 12/15/2016.
 */

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    public void sendNotification(String messageBody, Activity currentActivity, Context currentContext) {
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(currentActivity)
                        .setSmallIcon(R.drawable.common_google_signin_btn_icon_dark)
                        .setContentTitle("Aplicativo Chama +1")
                        .setContentText("Estão procurando jogadores em uma pelada!");

        PendingIntent contentIntent = PendingIntent.getActivity(
                currentActivity,
                0,
                new Intent(currentActivity, MainActivity.class),
                PendingIntent.FLAG_UPDATE_CURRENT
        );
        mBuilder.setContentIntent(contentIntent);

        Notification notification = mBuilder.build();
        NotificationManagerCompat.from(currentActivity).notify(0, notification);


    }
}
