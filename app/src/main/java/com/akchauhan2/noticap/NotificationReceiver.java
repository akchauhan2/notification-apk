package com.akchauhan2.noticap;

import android.app.Notification;
import android.app.PendingIntent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NotificationReceiver extends NotificationListenerService {

    private static List<NotificationItem> notificationList = new ArrayList<>();
    private static NotificationListener notificationListener;

    @Override
    public void onNotificationPosted(StatusBarNotification sbn) {
        // Extract relevant notification information
        String appName = sbn.getPackageName();
        String message = sbn.getNotification().extras.getString(Notification.EXTRA_TEXT);
        long timestamp = sbn.getPostTime();

        // Extract notification date
        Date notificationDate = new Date(timestamp);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(notificationDate);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        // Create a new NotificationItem and pass it to the storeNotification() method
        NotificationItem notificationItem = new NotificationItem(appName, message, timestamp, year, month, day);
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
        int year = notificationItem.getYear();
        int month = notificationItem.getMonth();
        int day = notificationItem.getDay();

        System.out.println("Received notification:");
        System.out.println("App Name: " + appName);
        System.out.println("Message: " + message);
        System.out.println("Timestamp: " + timestamp);
        System.out.println("Date: " + year + "-" + month + "-" + day);

        notificationList.add(notificationItem);

        // Notify MainActivity about the new notification
        MainActivity.addNotification(appName, message, timestamp, year, month, day);
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
        private int year;
        private int month;
        private int day;

        public NotificationItem(String appName, String message, long timestamp, int year, int month, int day) {
            this.appName = appName;
            this.message = message;
            this.timestamp = timestamp;
            this.year = year;
            this.month = month;
            this.day = day;
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

        public int getYear() {
            return year;
        }

        public int getMonth() {
            return month;
        }

        public int getDay() {
            return day;
        }
    }
}