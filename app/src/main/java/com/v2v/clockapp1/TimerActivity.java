package com.v2v.clockapp1;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.NumberPicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class TimerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);

        setupBottomNavigation();

        FloatingActionButton fabAdd = findViewById(R.id.fabAdd);
        fabAdd.setOnClickListener(v -> showAddTimerDialog());
    }

    private void setupBottomNavigation() {
        findViewById(R.id.nav_alarm).setOnClickListener(v -> {
            startActivity(new Intent(TimerActivity.this, MainActivity.class));
            overridePendingTransition(0, 0);
            finish();
        });

        findViewById(R.id.nav_timer).setOnClickListener(v -> {
            // Already in TimerActivity
        });

        findViewById(R.id.nav_stopwatch).setOnClickListener(v -> {
            startActivity(new Intent(TimerActivity.this, StopwatchActivity.class));
            overridePendingTransition(0, 0);
            finish();
        });

        findViewById(R.id.nav_world_clock).setOnClickListener(v -> {
            startActivity(new Intent(TimerActivity.this, WorldClockActivity.class));
            overridePendingTransition(0, 0);
            finish();
        });
    }

    private void showAddTimerDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = LayoutInflater.from(this);
        View dialogView = inflater.inflate(R.layout.dialog_add_timer, null);

        NumberPicker hourPicker = dialogView.findViewById(R.id.hourPicker);
        NumberPicker minPicker = dialogView.findViewById(R.id.minPicker);
        NumberPicker secPicker = dialogView.findViewById(R.id.secPicker);

        hourPicker.setMinValue(0);
        hourPicker.setMaxValue(23);
        minPicker.setMinValue(0);
        minPicker.setMaxValue(59);
        secPicker.setMinValue(0);
        secPicker.setMaxValue(59);

        builder.setView(dialogView);
        builder.setTitle("Set Timer");

        builder.setPositiveButton("Start Timer", (dialog, which) -> {
            int hours = hourPicker.getValue();
            int minutes = minPicker.getValue();
            int seconds = secPicker.getValue();

            Toast.makeText(this, "Timer set: " + hours + "h " + minutes + "m " + seconds + "s", Toast.LENGTH_SHORT).show();
        });

        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());
        builder.create().show();
    }
}