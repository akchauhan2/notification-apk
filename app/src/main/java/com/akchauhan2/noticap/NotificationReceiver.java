package com.akchauhan2.noticap;

import android.app.Notification;
import android.content.Intent;
import android.os.IBinder;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class NotificationReceiver extends NotificationListenerService {

    private static List<NotificationItem> notificationList = new ArrayList<>();
    private static NotificationListener notificationListener;

    public static void storeNotification(NotificationItem notificationItem) {
        // Store the notification item
        notificationList.add(notificationItem);

        if (notificationListener != null) {
            notificationListener.onNotificationPosted(notificationItem);
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return super.onBind(intent);
    }

    @Override
    public void onNotificationPosted(StatusBarNotification sbn) {
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

        NotificationItem notificationItem = new NotificationItem(packageName, message, timestamp);
        storeNotification(notificationItem);
    }

    @Override
    public void onNotificationRemoved(StatusBarNotification sbn) {
        if (notificationListener != null) {
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

            NotificationItem notificationItem = new NotificationItem(packageName, message, timestamp);
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
        private String packageName;
        private String message;
        private long timestamp;

        public NotificationItem(String packageName, String message, long timestamp) {
            this.packageName = packageName;
            this.message = message;
            this.timestamp = timestamp;
        }

        public String getPackageName() {
            return packageName;
        }

        public String getMessage() {
            return message;
        }

        public long getTimestamp() {
            return timestamp;
        }
    }
}