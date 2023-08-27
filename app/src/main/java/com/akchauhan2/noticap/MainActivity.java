package com.akchauhan2.noticap;

import android.os.Bundle;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.ArrayAdapter;

public class MainActivity extends AppCompatActivity {
    private String[] tasks = {
            "Task 1",
            "Task 2",
            "Task 3",
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView taskListView = findViewById(R.id.taskListView);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                tasks
        );

        taskListView.setAdapter(adapter);
    }
}

    private NotificationRecordingService notificationService{
@Override
protected void onStart() {
        super.onStart();
        notificationService = new NotificationRecordingService();
        startService(new Intent(this, NotificationRecordingService.class));
        }

@Override
protected void onStop() {
        super.onStop();
        stopService(new Intent(this, NotificationRecordingService.class));
        notificationService = null;
        }
        }