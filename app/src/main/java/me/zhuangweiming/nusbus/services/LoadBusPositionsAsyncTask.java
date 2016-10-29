package me.zhuangweiming.nusbus.services;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import me.zhuangweiming.nusbus.model.Bus;
import me.zhuangweiming.nusbus.utils.RestClient;

/**
 * Created by Thibault on 24/10/2016.
 */

public class LoadBusPositionsAsyncTask extends AsyncTask<Long, Void, List<Bus>> {

    public static final String TAG = "LoadBusStopAsyncTask";

    protected RestClient client;

    private WeakReference<BusPositionsLoadedCallback> mCallBack;
    private Context context;
    private Exception pendingException;

    public LoadBusPositionsAsyncTask(RestClient client, Context context, BusPositionsLoadedCallback callBack) {
        mCallBack = new WeakReference<>(callBack);
        this.context = context;
        this.client = client;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected List<Bus> doInBackground(Long... cityId) {

        List<Bus> result = null;
        try {

            HashMap<String, Bus> busMap = new HashMap<>();
            result = client.getBusPositions();

            for(Bus bus : result)
            {
                busMap.put(bus.getVehicleSerial(), bus);
            }

            result = new ArrayList<>(busMap.values());

        } catch (IOException e) {

            pendingException = e;
            Log.e(TAG, "error during task", e);
        }

        return result;
    }

    @Override
    protected void onPostExecute(List<Bus> result) {

        BusPositionsLoadedCallback ref = mCallBack.get();
        if (ref == null) {
            return;
        }

        if (pendingException == null) {
            ref.onBusPositionsLoaded(result);
        } else {
            ref.onBusPositionsLoadingError(pendingException);
        }
    }
}
