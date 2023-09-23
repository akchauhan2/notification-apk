package com.akchauhan2.noticap;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;

import java.util.ArrayList;
import java.util.List;

public class NotificationReceiver extends NotificationListenerService {

    private static List<NotificationItem> notificationList = new ArrayList<>();
    private static NotificationListener notificationListener;
    // Define the storeNotification method here or call it from another class
    public static void storeNotification(NotificationItem notificationItem) {
        System.out.println(" Timestamp Received notification:");
        // Here, you can define the logic to store the notification
        // For example, you can store it in a database, write it to a file, or perform any other desired action

        // In this example, let's simply print the notification details for demonstration purposes
        String appName = notificationItem.getAppName();
        String message = notificationItem.getMessage();
        long timestamp = notificationItem.getTimestamp();

        System.out.println("Received notification:");
        System.out.println("App Name: " + appName);
        System.out.println("Message: " + message);
        System.out.println("Timestamp: " + timestamp);
    }

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
        // Handle the removed notification
        if (notificationListener != null) {
            NotificationItem notificationItem = new NotificationItem(sbn.getPackageName(), sbn.getNotification().tickerText.toString(), sbn.getPostTime());
            notificationList.remove(notificationItem);
            //notificationListener.onNotificationRemoved(notificationItem);
        }
    }

    public static void setNotificationListener(NotificationListener listener) {
        notificationListener = listener;
        System.out.println(" Timestamp listener:");
    }

    public static List<NotificationItem> getNotificationList() {
        System.out.print("Timestamp getNotificationList"+notificationList);
        return notificationList;
    }

    public static class NotificationItem {
        private String appName;
        private String message;
        private long timestamp;

        public NotificationItem(String appName, String message, long timestamp) {
            System.out.print("Timestamp_"+appName+"_"+message);

            this.appName = appName;
            this.message = message;
            this.timestamp = timestamp;
        }

        public String getAppName() {
            return appName;
        }

        public String getMessage() {
            return message;
        }

        public long getTimestamp() {
            return timestamp;
        }
    }
}
