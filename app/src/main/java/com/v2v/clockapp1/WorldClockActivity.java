package com.v2v.clockapp1;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import java.util.Arrays;
import java.util.HashSet;
import java.util.TimeZone;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class WorldClockActivity extends AppCompatActivity {

    private RecyclerView cityRecyclerView;
    private FloatingActionButton fabAdd;
    private CityAdapter adapter;
    private ArrayList<String> cityList = new ArrayList<>();
    private SharedPreferences sharedPreferences;
    private final String PREF_NAME = "world_clock_cities";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_world_clock);

        findViewById(R.id.nav_alarm).setOnClickListener(v -> {
            startActivity(new Intent(WorldClockActivity.this, MainActivity.class));
            overridePendingTransition(0, 0);
            finish();
        });

        // Navigate to World Clock (same screen)
        findViewById(R.id.nav_world_clock).setOnClickListener(v -> {
            // Optional: You can skip reloading the same activity
        });

        // Navigate to Stopwatch
        findViewById(R.id.nav_stopwatch).setOnClickListener(v -> {
            startActivity(new Intent(WorldClockActivity.this, StopwatchActivity.class));
            overridePendingTransition(0, 0);
            finish();
        });

        // Navigate to Timer
        findViewById(R.id.nav_timer).setOnClickListener(v -> {
            startActivity(new Intent(WorldClockActivity.this, TimerActivity.class));
            overridePendingTransition(0, 0);
            finish();
        });

        cityRecyclerView = findViewById(R.id.cityRecyclerView);
        fabAdd = findViewById(R.id.fabAdd);

        sharedPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        cityList.addAll(sharedPreferences.getStringSet("cities", new HashSet<>()));

        adapter = new CityAdapter(this, cityList);
        cityRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        cityRecyclerView.setAdapter(adapter);

        fabAdd.setOnClickListener(view -> openAddCityDialog());
    }

    private void openAddCityDialog() {
        final String[] availableCities = TimeZone.getAvailableIDs();
        Arrays.sort(availableCities);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Select a City");

        builder.setItems(availableCities, (dialog, which) -> {
            String selectedCity = availableCities[which];

            if (!cityList.contains(selectedCity)) {
                cityList.add(selectedCity);
                saveCities();
                adapter.notifyDataSetChanged();
            } else {
                Toast.makeText(this, "City already added", Toast.LENGTH_SHORT).show();
            }
        });

        builder.show();
    }

    private void saveCities() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putStringSet("cities", new HashSet<>(cityList));
        editor.apply();
    }
}