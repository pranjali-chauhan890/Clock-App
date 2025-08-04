package com.v2v.clockapp1;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.TimePicker;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;


import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.nav_alarm).setOnClickListener(v -> {

        });

        // Navigate to World Clock (same screen)
        findViewById(R.id.nav_world_clock).setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, WorldClockActivity.class));
            overridePendingTransition(0, 0);
            finish();
        });

        // Navigate to Stopwatch
        findViewById(R.id.nav_stopwatch).setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, StopwatchActivity.class));
            overridePendingTransition(0, 0);
            finish();
        });

        // Navigate to Timer
        findViewById(R.id.nav_timer).setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, TimerActivity.class));
            overridePendingTransition(0, 0);
            finish();
        });

        findViewById(R.id.fab).setOnClickListener(view -> {
            Calendar calendar = Calendar.getInstance();
            int hour = calendar.get(Calendar.HOUR_OF_DAY);
            int minute = calendar.get(Calendar.MINUTE);

            TimePickerDialog timePickerDialog = new TimePickerDialog(
                    MainActivity.this,
                    (TimePicker view1, int selectedHour, int selectedMinute) -> {
                        calendar.set(Calendar.HOUR_OF_DAY, selectedHour);
                        calendar.set(Calendar.MINUTE, selectedMinute);
                        calendar.set(Calendar.SECOND, 0);

                        setAlarm(calendar);
                    },
                    hour, minute, false
            );
            timePickerDialog.show();
        });
    }

    @SuppressLint("ScheduleExactAlarm")
    private void setAlarm(Calendar calendar) {
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.S) {
            if (!alarmManager.canScheduleExactAlarms()) {
                Toast.makeText(this, "Please allow exact alarm permission in settings", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(android.provider.Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM);
                intent.setData(Uri.parse("package:" + getPackageName()));
                startActivity(intent);
                return;
            }
        }
        Intent intent = new Intent(this, AlarmReceiver.class);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                this,
                0,
                intent,
                PendingIntent.FLAG_IMMUTABLE // Required for Android 12+
        );

        alarmManager.setExact(
                AlarmManager.RTC_WAKEUP,
                calendar.getTimeInMillis(),
                pendingIntent
        );

        Toast.makeText(this, "Alarm set for " +
                        calendar.get(Calendar.HOUR_OF_DAY) + ":" + String.format("%02d", calendar.get(Calendar.MINUTE)),
                Toast.LENGTH_LONG).show();
    }
}