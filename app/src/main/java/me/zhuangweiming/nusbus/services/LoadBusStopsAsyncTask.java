package me.zhuangweiming.nusbus.services;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.List;

import me.zhuangweiming.nusbus.model.BusStop;
import me.zhuangweiming.nusbus.utils.RestClient;

/**
 * Created by Thibault on 16/10/2016.
 */
public class LoadBusStopsAsyncTask extends AsyncTask<Long, Void, List<BusStop>> {

    public static final String TAG = "LoadBusStopAsyncTask";

    protected RestClient client;

    private WeakReference<BusStopLoadedCallback> mCallBack;
    private Context context;
    private Exception pendingException;
    private DataCache dataCache;

    public LoadBusStopsAsyncTask(RestClient client, Context context, BusStopLoadedCallback callBack, DataCache dt) {
        mCallBack = new WeakReference<BusStopLoadedCallback>(callBack);
        this.context = context;
        this.client = client;
        this.dataCache = dt;
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
    protected List<BusStop> doInBackground(Long... cityId) {

        List<BusStop> result = null;
        try {

            result = client.getBusStops();

        } catch (IOException e) {

            pendingException = e;
            Log.e(TAG, "error during task", e);
        }

        return result;
    }

    @Override
    protected void onPostExecute(List<BusStop> result) {

        BusStopLoadedCallback ref = mCallBack.get();
        if (ref == null) {
            return;
        }

        if (pendingException == null) {
            dataCache.storeBusStops(result);
            ref.onBusStopsLoaded(result);
        } else {
            ref.onBusStopLoadingError(pendingException);
        }
    }
}
