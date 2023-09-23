package com.akchauhan2.noticap;

import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;

import java.text.SimpleDateFormat;
import java.util.Date;

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