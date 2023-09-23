package com.akchauhan2.noticap;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static ListView listView;
    private static ArrayAdapter<String> adapter;
    private static NotificationReceiver receiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.listView);
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
        listView.setAdapter(adapter);


        receiver = new NotificationReceiver();
        NotificationReceiver.setNotificationListener(new NotificationListener());

    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.clear();
        adapter.addAll(getNotificationStrings());
    }
    private String[] getNotificationStrings() {
        List<NotificationReceiver.NotificationItem> notificationList = NotificationReceiver.getNotificationList();

        System.out.println("notificationList: "+notificationList );
        // Add a sample notification
        String sampleAppName = "Sample App";
        String sampleMessage = "This is a sample notification";
        long sampleTimestamp = System.currentTimeMillis();
        NotificationReceiver.NotificationItem sampleNotification = new NotificationReceiver.NotificationItem(sampleAppName, sampleMessage, sampleTimestamp);
        notificationList.add(sampleNotification);

        System.out.println("Timestamp:56 " );
        String[] strings = new String[notificationList.size()];
        for (int i = 0; i < notificationList.size(); i++) {
            NotificationReceiver.NotificationItem notification = notificationList.get(i);
            strings[i] = notification.getAppName() + ": " + notification.getMessage();
        }
        return strings;
    }

    public static void addNotification(String appName, String message) {
        adapter.add(appName + ": " + message);
    }
}
