package com.akchauhan2.noticap;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;

public class NotificationListener extends NotificationListenerService {
    @Override
    public void onNotificationPosted(StatusBarNotification sbn) {
        // Extract relevant notification information
        String appName = sbn.getPackageName();
        String message = sbn.getNotification().extras.getString(android.app.Notification.EXTRA_TEXT);

        // Add the received notification to the adapter in MainActivity
        MainActivity.addNotification(appName, message);
    }

    @Override
    public void onNotificationRemoved(StatusBarNotification sbn) {
        System.out.println("Timestamp: re,move" );
        // Handle notification removal if needed
    }
}