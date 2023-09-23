package com.akchauhan2.noticap;

import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;

import java.util.Calendar;
import java.util.Date;
public class NotificationListener extends NotificationListenerService {

    @Override
    public void onNotificationPosted(StatusBarNotification sbn) {
        // Extract relevant notification information
        String appName = sbn.getPackageName();
        String message = sbn.getNotification().extras.getString(android.app.Notification.EXTRA_TEXT);
        long timestamp = sbn.getPostTime();
        Date date = new Date(timestamp);

// Create a Calendar instance and set the date
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1; // Month starts from 0, so add 1
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        // Create a new NotificationItem with additional parameters
        NotificationReceiver.NotificationItem notificationItem = new NotificationReceiver.NotificationItem(appName, message, timestamp, year, month, day);

        // Pass the notificationItem to the storeNotification() method
        NotificationReceiver.storeNotification(notificationItem);
    }

    @Override
    public void onNotificationRemoved(StatusBarNotification sbn) {
        // Handle notification removal if needed
    }
}