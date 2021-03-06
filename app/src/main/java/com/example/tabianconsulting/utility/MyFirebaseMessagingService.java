package com.example.tabianconsulting.utility;


import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.example.tabianconsulting.MainActivity;
import com.example.tabianconsulting.R;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    public static final String TAG = "MyFirebaseMessagingServ";

    @Override
    public void onDeletedMessages() {
        super.onDeletedMessages();
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        String notificationBody="";
        String notificationTitle="";
        String notificationData="";

        if (remoteMessage.getData().size()>0){
            sendNotification(remoteMessage.getData().get("title"),remoteMessage.getData().get("message"));
        }

        try {
            notificationData = remoteMessage.getData().toString();
            //notificationTitle = remoteMessage.getNotification().getTitle();
            //notificationBody = remoteMessage.getNotification().getBody();

        }catch (NullPointerException e){
            Log.e(TAG, "onMessageReceived: NullPointerException"+e.getMessage() );
        }

        Log.d(TAG, "onMessageReceived: data"+notificationData);
        Log.d(TAG, "onMessageReceived: notification body"+notificationBody);
        Log.d(TAG, "onMessageReceived: notification title"+notificationTitle);

    }

    private void sendNotification(String title, String message) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("data",title);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);

        String channelId = getString(R.string.default_notification_channel_id);
        Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(this,channelId)
                        .setSmallIcon(R.drawable.ic_launcher_background)
                        .setContentTitle(title)
                        .setContentText(message)
                        .setAutoCancel(true)
                        .setSound(defaultSoundUri)
                        .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        // Since android Oreo notification channel is needed.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(channelId,
                    "Channel human readable title",
                    NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(channel);
        }

        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
    }


}
