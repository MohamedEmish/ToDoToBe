package com.example.amosh.todotobe;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.widget.RemoteViews;

import static android.app.NotificationManager.IMPORTANCE_DEFAULT;

public class AlarmReceiver extends BroadcastReceiver {
    private static final String CHANNEL_ID = "com.singhajit.notificationDemo.channelId";

    String title;
    String description;
    String hour;
    String minutes;
    String am_pm;

    @Override
    public void onReceive(Context context, Intent intent) {

        String action = intent.getAction();

        if (SaveSharedPreference.getPrefNotification(context).equals("on")) {

            if (action.equals("my.action.string")) {
                title = intent.getExtras().getString("title");
                description = intent.getExtras().getString("description");
                hour = intent.getExtras().getString("hour");
                minutes = intent.getExtras().getString("minutes");
                am_pm = intent.getExtras().getString("am_pm");

            }


            Intent notificationIntent = new Intent(context, MainScreenActivity.class);

            TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
            stackBuilder.addParentStack(MainScreenActivity.class);
            stackBuilder.addNextIntent(notificationIntent);

            PendingIntent pendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

            RemoteViews contentView = new RemoteViews("com.example.amosh.todotobe", R.layout.custom_notification);
            contentView.setTextViewText(R.id.notification_title, title);
            contentView.setTextViewText(R.id.notification_description, description);
            contentView.setTextViewText(R.id.notification_timeHour, hour);
            contentView.setTextViewText(R.id.notification_timeMinutes, minutes);
            contentView.setTextViewText(R.id.notification_am_pm, am_pm);

            Notification.Builder builder = new Notification.Builder(context);

            Notification notification = builder
                    .setSmallIcon(R.mipmap.logo)
                    .setContent(contentView)
                    .setContentIntent(pendingIntent).build();

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                builder.setChannelId(CHANNEL_ID);
            }

            NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                NotificationChannel channel = new NotificationChannel(
                        CHANNEL_ID,
                        "NotificationDemo",
                        IMPORTANCE_DEFAULT
                );
                notificationManager.createNotificationChannel(channel);
            }

            notificationManager.notify(0, notification);
        }
    }
}
