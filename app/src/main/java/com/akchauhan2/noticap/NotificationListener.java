package com.akchauhan2.noticap;

import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;

public class NotificationListener extends NotificationListenerService {
    @Override
    public void onNotificationPosted(StatusBarNotification sbn) {
        // Extract relevant notification information
        String appName = sbn.getPackageName();
        String message = sbn.getNotification().extras.getString(android.app.Notification.EXTRA_TEXT);
        long timestamp = sbn.getPostTime();

        // Create a new NotificationItem and pass it to the storeNotification() method
        NotificationReceiver.NotificationItem notificationItem = new NotificationReceiver.NotificationItem(appName, message, timestamp);
        NotificationReceiver.storeNotification(notificationItem);
    }

    @Override
    public void onNotificationRemoved(StatusBarNotification sbn) {
        // Handle notification removal if needed
    }
}