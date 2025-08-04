package com.v2v.clockapp1;

import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Locale;
import java.util.TimeZone;

public class CityAdapter extends RecyclerView.Adapter<CityAdapter.CityViewHolder> {

    private Context context;
    private ArrayList<String> cities;

    public CityAdapter(Context context, ArrayList<String> cities) {
        this.context = context;
        this.cities = cities;
    }

    @NonNull
    @Override
    public CityViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.city_item, parent, false);
        return new CityViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CityViewHolder holder, int position) {
        String cityId = cities.get(position);
        holder.cityName.setText(cityId);

        TimeZone tz = TimeZone.getTimeZone(cityId);

        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss a", Locale.getDefault());
                sdf.setTimeZone(tz);
                holder.cityTime.setText(sdf.format(new Date()));

                holder.itemView.postDelayed(this, 1000);
            }
        });

        holder.itemView.setOnLongClickListener(v -> {
            int currentPosition = holder.getAdapterPosition();  // Get the actual position

            if (currentPosition != RecyclerView.NO_POSITION) {
                String cityIdToRemove = cities.get(currentPosition);

                new AlertDialog.Builder(context)
                        .setTitle("Remove City")
                        .setMessage("Remove " + cityIdToRemove + " from World Clock?")
                        .setPositiveButton("Yes", (dialog, which) -> {
                            cities.remove(currentPosition);
                            notifyDataSetChanged();

                            SharedPreferences sharedPreferences = context.getSharedPreferences("world_clock_cities", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putStringSet("cities", new HashSet<>(cities));
                            editor.apply();
                        })
                        .setNegativeButton("Cancel", null)
                        .show();
            }
            return true;
        });
    }

    @Override
    public int getItemCount() {
        return cities.size();
    }

    public static class CityViewHolder extends RecyclerView.ViewHolder {
        TextView cityName, cityTime;

        public CityViewHolder(@NonNull View itemView) {
            super(itemView);
            cityName = itemView.findViewById(R.id.cityName);
            cityTime = itemView.findViewById(R.id.cityTime);
        }
    }
}
