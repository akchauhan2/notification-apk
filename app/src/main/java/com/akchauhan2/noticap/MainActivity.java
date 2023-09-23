package com.akchauhan2.noticap;

import androidx.appcompat.app.AppCompatActivity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.provider.Settings;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ListView listView;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.listView);
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
        listView.setAdapter(adapter);

        // Check if the app has notification access permission
        if (!isNotificationAccessGranted()) {
            // If not, open the notification access settings for the user to grant permission
            openNotificationAccessSettings();
            Toast.makeText(this, "Please grant notification access to the app", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.clear();
        adapter.addAll(getNotificationStrings());
    }

    private String[] getNotificationStrings() {
        List<NotificationReceiver.NotificationItem> notificationList = NotificationReceiver.getNotificationList();

        String[] strings = new String[notificationList.size()];
        for (int i = 0; i < notificationList.size(); i++) {
            NotificationReceiver.NotificationItem notification = notificationList.get(i);
            strings[i] = notification.getAppName() + ": " + notification.getMessage();
        }
        return strings;
    }

    private boolean isNotificationAccessGranted() {
        ComponentName cn = new ComponentName(this, NotificationListener.class);
        String flat = Settings.Secure.getString(getContentResolver(), "enabled_notification_listeners");
        return flat != null && flat.contains(cn.flattenToString());
    }

    private void openNotificationAccessSettings() {
        Intent intent = new Intent(Settings.ACTION_NOTIFICATION_LISTENER_SETTINGS);
        startActivity(intent);
    }
}