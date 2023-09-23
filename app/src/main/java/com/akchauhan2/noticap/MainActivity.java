package com.akchauhan2.noticap;

import androidx.appcompat.app.AppCompatActivity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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

            public void onNotificationPosted(NotificationReceiver.NotificationItem notification) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String timestamp = sdf.format(new Date(notification.getTimestamp()));

                adapter.add(notification.getAppName() + ": " + notification.getMessage() + " (" + timestamp + ")");
            }

            public void onNotificationRemoved(NotificationReceiver.NotificationItem notification) {
                adapter.remove(notification.getAppName() + ": " + notification.getMessage());
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                showNotificationDetailsPopup(position);
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
        List<String> notificationStrings = new ArrayList<>();

        for (NotificationReceiver.NotificationItem notification : notificationList) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String timestamp = sdf.format(new Date(notification.getTimestamp()));

            notificationStrings.add(notification.getAppName() + ": " + notification.getMessage() + " (" + timestamp + ")");
        }

        return notificationStrings.toArray(new String[0]);
    }
    private void showNotificationDetailsPopup(int position) {
        NotificationReceiver.NotificationItem notification = NotificationReceiver.getNotificationList().get(position);

        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.notification_details_dialog, null);

        TextView appNameTextView = dialogView.findViewById(R.id.appNameTextView);
        TextView messageTextView = dialogView.findViewById(R.id.messageTextView);
        TextView timestampTextView = dialogView.findViewById(R.id.timestampTextView);

        appNameTextView.setText(notification.getAppName());
        messageTextView.setText(notification.getMessage());

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String timestamp = sdf.format(new Date(notification.getTimestamp()));
        timestampTextView.setText(timestamp);

        builder.setView(dialogView)
                .setTitle("Notification Details")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}
