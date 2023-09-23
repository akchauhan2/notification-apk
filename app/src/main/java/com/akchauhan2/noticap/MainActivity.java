package com.akchauhan2.noticap;

import androidx.appcompat.app.AppCompatActivity;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private ListView listView;
    private ArrayAdapter<String> adapter;
    private static Map<String, List<NotificationReceiver.NotificationItem>> notificationMap = new HashMap<>();
    private static NotificationListener NotificationListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.listView);
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
        listView.setAdapter(adapter);

        // Check if the app has notification access permission
        if (!isNotificationServiceEnabled()) {
            // Request the user to grant notification access
            requestNotificationAccess();
        }

        // Create a notification channel for Android 8.0 and above
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createNotificationChannel();
        }

        // Set the notification listener for receiving notifications
        NotificationReceiver.setNotificationListener(new NotificationListener() {
            @Override
            public void onNotificationReceived(String appName, String message, long timestamp, int year, int month, int day) {
                // Handle the received notification
                String notificationText = appName + ": " + message;
                adapter.add(notificationText);
                adapter.notifyDataSetChanged();

                // Group notifications by date
                String dateKey = year + "-" + month + "-" + day;
                List<NotificationReceiver.NotificationItem> notificationList = notificationMap.get(dateKey);
                if (notificationList == null) {
                    notificationList = new ArrayList<>();
                    notificationMap.put(dateKey, notificationList);
                }
                NotificationReceiver.NotificationItem notificationItem = new NotificationReceiver.NotificationItem(appName, message, timestamp, year, month, day);
                notificationList.add(notificationItem);
            }
        });
    }

    // Check if the app has notification access permission
    private boolean isNotificationServiceEnabled() {
        String packageName = getPackageName();
        String flat = Settings.Secure.getString(getContentResolver(), "enabled_notification_listeners");
        if (flat != null) {
            return flat.contains(packageName);
        }
        return false;
    }

    // Request the user to grant notification access
    private void requestNotificationAccess() {
        Intent intent = new Intent("android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS");
        startActivity(intent);
        Toast.makeText(this, "Please grant notification access for the app", Toast.LENGTH_LONG).show();
    }

    // Create a notification channel for Android 8.0 and above
    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.channel_name);
            String description = getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(getString(R.string.channel_id), name, importance);
            channel.setDescription(description);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    public static void addNotification(String appName, String message, long timestamp, int year, int month, int day) {
        // Notify MainActivity about the new notification
        if (notificationListener != null) {
            notificationListener.onNotificationReceived(appName, message, timestamp, year, month, day);
        }
    }

    public static void setNotificationListener(NotificationListener listener) {
        notificationListener = listener;
    }
}