package com.akchauhan2.noticap;

import android.app.Notification;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.util.Log;

public class NotificationListener extends NotificationListenerService {

    private static final String TAG = "NotificationListener";

    @Override
    public void onNotificationPosted(StatusBarNotification sbn) {
        Log.d(TAG, "onNotificationPosted");

        String packageName = sbn.getPackageName();
        String message = "";
        if (sbn.getNotification().tickerText != null) {
            message = sbn.getNotification().tickerText.toString();
        } else {
            if (sbn.getNotification().extras != null && sbn.getNotification().extras.getCharSequence(Notification.EXTRA_TEXT) != null) {
                message = sbn.getNotification().extras.getCharSequence(Notification.EXTRA_TEXT).toString();
            }
        }
        long timestamp = sbn.getPostTime();

        NotificationReceiver.NotificationItem notificationItem = new NotificationReceiver.NotificationItem(packageName, message, timestamp);
        NotificationReceiver.storeNotification(notificationItem);
    }

    @Override
    public void onNotificationRemoved(StatusBarNotification sbn) {
        Log.d(TAG, "onNotificationRemoved");

        String packageName = sbn.getPackageName();
        String message = "";
        if (sbn.getNotification().tickerText != null) {
            message = sbn.getNotification().tickerText.toString();
        } else {
            if (sbn.getNotification().extras != null && sbn.getNotification().extras.getCharSequence(Notification.EXTRA_TEXT) != null) {
                message = sbn.getNotification().extras.getCharSequence(Notification.EXTRA_TEXT).toString();
            }
        }
        long timestamp = sbn.getPostTime();

        NotificationReceiver.NotificationItem notificationItem = new NotificationReceiver.NotificationItem(packageName, message, timestamp);
        NotificationReceiver.removeNotification(notificationItem);
    }
}