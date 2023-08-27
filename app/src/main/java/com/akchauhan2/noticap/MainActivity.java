package com.akchauhan2.noticap;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.provider.Settings;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ListView taskListView;
    private ArrayAdapter<NotificationReceiver.NotificationItem> adapter;
    private List<NotificationReceiver.NotificationItem> notificationList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        taskListView = findViewById(R.id.taskListView);

        // Get the list of captured notifications from NotificationReceiver
        notificationList = new ArrayList<>(); // Initialize an empty list

        // Create an ArrayAdapter to display the notifications in the ListView
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, notificationList);

        // Set the ArrayAdapter as the ListView's adapter
        taskListView.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();

        // Register the NotificationListener
        ComponentName component = new ComponentName(this, NotificationListener.class);
        PackageManager pm = getPackageManager();
        pm.setComponentEnabledSetting(component, PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.DONT_KILL_APP);

        // Check if the user has granted access to the notification listener
        if (!isNotificationListenerEnabled()) {
            // Prompt the user to grant access
            Intent intent = new Intent(Settings.ACTION_NOTIFICATION_LISTENER_SETTINGS);
            startActivity(intent);
            Toast.makeText(this, "Please grant access to the notification listener", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        // Unregister the NotificationListener
        ComponentName component = new ComponentName(this, NotificationListener.class);
        PackageManager pm = getPackageManager();
        pm.setComponentEnabledSetting(component, PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP);
    }

    private boolean isNotificationListenerEnabled() {
        ComponentName component = new ComponentName(this, NotificationListener.class);
        String flat = Settings.Secure.getString(getContentResolver(), "enabled_notification_listeners");
        return flat != null && flat.contains(component.flattenToString());
    }
}