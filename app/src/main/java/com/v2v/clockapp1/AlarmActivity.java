package com.v2v.clockapp1;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Calendar;

public class AlarmActivity extends AppCompatActivity {

    TextView selectedTimeText;
    Button pickTimeButton, setAlarmButton;
    int selectedHour, selectedMinute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);

        selectedTimeText = findViewById(R.id.selectedTimeText);
        pickTimeButton = findViewById(R.id.pickTimeButton);
        setAlarmButton = findViewById(R.id.setAlarmButton);

        pickTimeButton.setOnClickListener(view -> {
            Calendar calendar = Calendar.getInstance();
            int hour = calendar.get(Calendar.HOUR_OF_DAY);
            int minute = calendar.get(Calendar.MINUTE);

            TimePickerDialog timePickerDialog = new TimePickerDialog(
                    AlarmActivity.this,
                    (TimePicker timePicker, int h, int m) -> {
                        selectedHour = h;
                        selectedMinute = m;
                        selectedTimeText.setText(String.format("Selected Time: %02d:%02d", h, m));
                    },
                    hour, minute, true
            );
            timePickerDialog.show();
        });

        setAlarmButton.setOnClickListener(view -> {
            Calendar c = Calendar.getInstance();
            c.set(Calendar.HOUR_OF_DAY, selectedHour);
            c.set(Calendar.MINUTE, selectedMinute);
            c.set(Calendar.SECOND, 0);

            if (c.before(Calendar.getInstance())) {
                c.add(Calendar.DATE, 1); // set for next day if time is in past
            }

            AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                if (!alarmManager.canScheduleExactAlarms()) {
                    // Show a dialog to tell the user to allow "Schedule exact alarms" in system settings
                    Intent intent = new Intent(Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM);
                    startActivity(intent);
                    return;
              }
            }
            Intent intent = new Intent(AlarmActivity.this, AlarmReceiver.class);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_IMMUTABLE);

            alarmManager.setExact(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), pendingIntent);

            Toast.makeText(this, "Alarm set for " + selectedHour + ":" + String.format("%02d", selectedMinute), Toast.LENGTH_SHORT).show();
        });
    }
}
