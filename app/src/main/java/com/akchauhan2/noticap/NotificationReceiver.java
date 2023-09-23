package com.akchauhan2.noticap;

import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;

import java.util.ArrayList;
import java.util.List;

public class NotificationReceiver extends NotificationListenerService {

    private static List<NotificationItem> notificationList = new ArrayList<>();
    private static NotificationListener notificationListener;

    @Override
    public void onNotificationPosted(StatusBarNotification sbn) {
        // Extract relevant notification information
        String appName = sbn.getPackageName();
        String message = sbn.getNotification().extras.getString(android.app.Notification.EXTRA_TEXT);
        long timestamp = sbn.getPostTime();

        // Create a new NotificationItem and pass it to the storeNotification() method
        NotificationItem notificationItem = new NotificationItem(appName, message, timestamp);
        storeNotification(notificationItem);
    }

    @Override
    public void onNotificationRemoved(StatusBarNotification sbn) {
        // Handle notification removal if needed
    }

    public static void storeNotification(NotificationItem notificationItem) {
        System.out.println("Timestamp: Received notification");
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

        notificationList.add(notificationItem);
    }

    public static List<NotificationItem> getNotificationList() {
        return notificationList;
    }

    public static void setNotificationListener(NotificationListener listener) {
        notificationListener = listener;
    }

    public static class NotificationItem {
        private String appName;
        private String message;
        private long timestamp;

        public NotificationItem(String appName, String message, long timestamp) {
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