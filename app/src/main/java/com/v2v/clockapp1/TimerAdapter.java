package com.v2v.clockapp1;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
import java.util.Locale;
import java.util.WeakHashMap;

public class TimerAdapter extends RecyclerView.Adapter<TimerAdapter.TimerViewHolder> {

    private final List<TimerItem> timerList;
    private final Context context;
    private final WeakHashMap<TimerViewHolder, CountDownTimer> timerMap = new WeakHashMap<>();

    public TimerAdapter(List<TimerItem> timerList, Context context) {
        this.timerList = timerList;
        this.context = context;
    }

    @NonNull
    @Override
    public TimerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_timer, parent, false);
        return new TimerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TimerViewHolder holder, int position) {
        if (timerMap.containsKey(holder)) {
            timerMap.get(holder).cancel();
        }

        TimerItem timer = timerList.get(position);

        long totalMillis = (timer.getHours() * 3600L + timer.getMinutes() * 60L + timer.getSeconds()) * 1000L;

        CountDownTimer countDownTimer = new CountDownTimer(totalMillis, 1000) {
            public void onTick(long millisUntilFinished) {
                int hours = (int) (millisUntilFinished / (1000 * 60 * 60));
                int minutes = (int) ((millisUntilFinished / (1000 * 60)) % 60);
                int seconds = (int) ((millisUntilFinished / 1000) % 60);
                holder.txtTime.setText(String.format(Locale.getDefault(), "%02d:%02d:%02d", hours, minutes, seconds));
                holder.txtStatus.setText("Running...");
                holder.txtStatus.setTextColor(Color.GREEN);
            }

            public void onFinish() {
                holder.txtTime.setText("00:00:00");
                holder.txtStatus.setText("Time's Up!");
                holder.txtStatus.setTextColor(Color.RED);

                Context context = holder.itemView.getContext();
                Intent intent = new Intent(context, TimerFinishedActivity.class);
                context.startActivity(intent);
            }
        };

        countDownTimer.start();
        timerMap.put(holder, countDownTimer);
    }

    @Override
    public int getItemCount() {
        return timerList.size();
    }

    public static class TimerViewHolder extends RecyclerView.ViewHolder {
        TextView txtTime, txtStatus;

        public TimerViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTime = itemView.findViewById(R.id.txtTime);
            txtStatus = itemView.findViewById(R.id.txtStatus); // ✅ ADD THIS
        }
    }
}