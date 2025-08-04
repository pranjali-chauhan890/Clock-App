package com.v2v.clockapp1;

import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;


public class AlarmRingActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_alarm_ring);

        Button stopButton = findViewById(R.id.stopAlarmBtn);
        stopButton.setOnClickListener(view->{
            if(AlarmReceiver.mediaPlayer != null && AlarmReceiver.mediaPlayer.isPlaying()){
                AlarmReceiver.mediaPlayer.stop();
                AlarmReceiver.mediaPlayer.release();
                AlarmReceiver.mediaPlayer = null;
            }
            finish();
        });
    }
}
