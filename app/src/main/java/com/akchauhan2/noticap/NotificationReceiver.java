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
 
    @Override
    public void onNotificationPosted( StatusBarNotification sbn) {
        // Handle the posted notification
        if (notificationListener != null) {
            NotificationItem notificationItem = new NotificationItem(sbn.getPackageName(), sbn.getNotification().tickerText.toString(), sbn.getPostTime());
            notificationList.add(notificationItem);
            notificationListener.onNotificationPosted(notificationItem);
        }
    }

    @Override
    public void onNotificationRemoved(StatusBarNotification sbn) {
        // Handle the removed notification
        if (notificationListener != null) {
            NotificationItem notificationItem = new NotificationItem(sbn.getPackageName(), sbn.getNotification().tickerText.toString(), sbn.getPostTime());
            notificationList.remove(notificationItem);
            notificationListener.onNotificationRemoved(notificationItem);
        }
    }

    public static void setNotificationListener(NotificationListener listener) {
        notificationListener = listener;
    }

    public static List<NotificationItem> getNotificationList() {
        return notificationList;
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