package me.zhuangweiming.nusbus.services;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by Thibault on 24/10/2016.
 */

public class AlarmReceiver extends BroadcastReceiver {

    // Triggered by the Alarm periodically (starts the service to run task)
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent i = new Intent(context, BusDownloadIntentService.class);
        context.startService(i);
    }
}

