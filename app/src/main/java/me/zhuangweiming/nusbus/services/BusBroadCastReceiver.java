package me.zhuangweiming.nusbus.services;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import java.lang.ref.WeakReference;
import java.util.List;

import me.zhuangweiming.nusbus.model.Bus;

/**
 * Created by Thibault on 24/10/2016.
 */

public class BusBroadCastReceiver extends BroadcastReceiver {

    private WeakReference<BusPositionsLoadedCallback> callBack;

    public BusBroadCastReceiver(BusPositionsLoadedCallback callback) {
        super();
        this.callBack = new WeakReference<>(callback);
    }

    @Override
    public void onReceive(Context context, Intent intent) {

        List<Bus> busList = intent.getParcelableArrayListExtra(BusDownloadIntentService.BUS_LIST_KEY);

        callBack.get().onBusPositionsLoaded(busList);
    }

}
