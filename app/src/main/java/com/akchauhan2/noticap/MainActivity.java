package com.akchauhan2.noticap;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ListView listView;
    private ArrayAdapter<String> adapter;
    private NotificationReceiver receiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.listView);
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
        listView.setAdapter(adapter);

        receiver = new NotificationReceiver();
        NotificationReceiver.setNotificationListener(new NotificationListener() {
            @Override
            public void onNotificationPosted(NotificationReceiver.NotificationItem notification) {
                adapter.add(notification.getAppName() + ": " + notification.getMessage());
            }

            @Override
            public void onNotificationRemoved(NotificationReceiver.NotificationItem notification) {
                adapter.remove(notification.getAppName() + ": " + notification.getMessage());
            }
        });
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
}