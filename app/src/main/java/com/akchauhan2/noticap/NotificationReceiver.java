package com.akchauhan2.noticap;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;

import java.util.ArrayList;
import java.util.List;

public class NotificationReceiver extends NotificationListenerService {

    @Override
    public void onNotificationPosted(StatusBarNotification sbn) {
        // Handle the posted notification
    }

    @Override
    public void onNotificationRemoved(StatusBarNotification sbn) {
        // Handle the removed notification
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

        // Getter methods for the notification item's properties

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

    public static class Companion {
        private static List<NotificationItem> notificationList = new ArrayList<>();
        private static NotificationListener notificationListener;

        public static void setNotificationListener(NotificationListener listener) {
            notificationListener = listener;
        }

        public static void storeNotification(NotificationItem notificationItem) {
            // Add the notificationItem to the notificationList
            notificationList.add(notificationItem);

            // Notify the listener (NotificationListenerService) about the new notification
            if (notificationListener != null) {
                notificationListener.onNotificationPosted(null); // Pass null as StatusBarNotification object
            }
        }

        public static List<NotificationItem> getNotificationList() {
            return notificationList;
        }
    }
}