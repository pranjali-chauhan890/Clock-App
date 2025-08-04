package com.v2v.clockapp1;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class StopwatchActivity extends AppCompatActivity {

    private TextView txtTime;
    private Button btnStart, btnStop, btnReset;

    private int seconds = 0;
    private boolean running = false;
    private Handler handler = new Handler();
    private Runnable runnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stopwatch);

        txtTime = findViewById(R.id.txtTimer);
        btnStart = findViewById(R.id.btnStart);
        btnStop = findViewById(R.id.btnStop);
        btnReset = findViewById(R.id.btnReset);

        btnStart.setOnClickListener(v -> startStopwatch());
        btnStop.setOnClickListener(v -> stopStopwatch());
        btnReset.setOnClickListener(v -> resetStopwatch());

        runStopwatch();

        // Bottom Navigation
        findViewById(R.id.nav_alarm).setOnClickListener(v -> {
            startActivity(new Intent(this, MainActivity.class));
            overridePendingTransition(0, 0);
            finish();
        });

        findViewById(R.id.nav_stopwatch).setOnClickListener(v -> {
            // Already on this screen
        });

        findViewById(R.id.nav_world_clock).setOnClickListener(v -> {
            startActivity(new Intent(this, WorldClockActivity.class));
            overridePendingTransition(0, 0);
            finish();
        });

        findViewById(R.id.nav_timer).setOnClickListener(v -> {
            startActivity(new Intent(this, TimerActivity.class));
            overridePendingTransition(0, 0);
            finish();
        });
    }

    private void runStopwatch() {
        runnable = new Runnable() {
            @Override
            public void run() {
                int hours = seconds / 3600;
                int minutes = (seconds % 3600) / 60;
                int secs = seconds % 60;

                String time = String.format("%02d:%02d:%02d", hours, minutes, secs);
                txtTime.setText(time);

                if (running) {
                    seconds++;
                }
                handler.postDelayed(this, 1000);
            }
        };
        handler.post(runnable);
    }

    private void startStopwatch() {
        Toast.makeText(this, "Started", Toast.LENGTH_SHORT).show();
        running = true;
    }

    private void stopStopwatch() {
        Toast.makeText(this, "Stopped", Toast.LENGTH_SHORT).show();
        running = false;
    }

    private void resetStopwatch() {
        Toast.makeText(this, "Reset", Toast.LENGTH_SHORT).show();
        running = false;
        seconds = 0;
        txtTime.setText("00:00:00");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(runnable);
    }
}