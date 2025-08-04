package com.v2v.clockapp1;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class TimerFinishedReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent i = new Intent(context, TimerFinishedActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(i);
    }
}