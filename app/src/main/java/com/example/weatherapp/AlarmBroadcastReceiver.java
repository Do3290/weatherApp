package com.example.weatherapp;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;

public class AlarmBroadcastReceiver extends BroadcastReceiver {
    private final static int NOTICATION_ID = 222;

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("AlarmBroadcastReceiver", "onReceive");

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, context.getString(R.string.notification_channel_id))
                .setColor(Color.WHITE)
                .setSmallIcon(R.mipmap.ic_launcher) //알람 아이콘
                .setContentTitle("알림")  //알람 제목
                .setContentText("날씨: 맑음 / 미세먼지: 보통") //알람 내용
                .setAutoCancel(true)

                .setFullScreenIntent(NotificationJobFireBaseService.pendingIntent,true)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT); //알람 중요도
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        notificationManager.notify(NOTICATION_ID, builder.build()); //알람 생성



    }
}