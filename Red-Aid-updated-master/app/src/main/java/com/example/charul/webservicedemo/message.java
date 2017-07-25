package com.example.charul.webservicedemo;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

/**
 * Created by ADITYA SI on 6/21/2017.
 */

public class message extends FirebaseMessagingService {

    private static final String TAG = "message";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        // TODO(developer): Handle FCM messages here.
        // If the application is in the foreground handle both data and notification messages here.
        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated. See sendNotification method below.
        Log.d(TAG, "From: " + remoteMessage.getFrom());

        if(remoteMessage.getData().size()>0)
        {
            Log.d(TAG, "Notification Message Body: " + remoteMessage.getData());
        }

        if(remoteMessage.getNotification()!= null){
            Log.d(TAG,"Message Body :" + remoteMessage.getNotification().getBody());
            sendNotification(remoteMessage.getNotification().getBody());
        }

    }

    private void sendNotification(String body) {
        Intent intent = new Intent(this,MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent pend = PendingIntent.getActivity(this,0,intent,PendingIntent.FLAG_ONE_SHOT);


        NotificationCompat.Builder builder=new NotificationCompat.Builder(this);

        builder.setContentTitle("Notification from Red Aid ");
        builder.setContentText("Click here for details");
        builder.setContentIntent(pend);

        NotificationManager nm= (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        nm.notify(0,builder.build());

    }

}
