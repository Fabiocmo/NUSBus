package me.zhuangweiming.nusbus.services;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.List;

import me.zhuangweiming.nusbus.model.Shuttle;
import me.zhuangweiming.nusbus.utils.RestClient;

/**
 * Created by weiming on 26/10/16.
 */

public class LoadShuttlesAsyncTask extends AsyncTask<String, Void, List<Shuttle>> {
    public static final String TAG = "LoadShuttlesAsyncTask";

    protected RestClient client;
    private Context context;
    private WeakReference<LoadShuttleCallback> mCallBack;
    private Exception pendingException;

    public LoadShuttlesAsyncTask(RestClient restClient, Context context, LoadShuttleCallback callback) {
        mCallBack = new WeakReference<LoadShuttleCallback>(callback);
        this.client = restClient;
        this.context = context;
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
    protected List<Shuttle> doInBackground(String... busStopName) {
        List<Shuttle> result = null;
        try {
            result = client.getShuttles(busStopName[0]);
        } catch (IOException e) {
            pendingException = e;
            Log.e(TAG, "error during task", e);
        }

        return result;
    }

    @Override
    protected void onPostExecute(List<Shuttle> result) {
        LoadShuttleCallback ref = mCallBack.get();
        if (ref == null) {
            return;
        }

        if (pendingException == null) {
            ref.onShuttleLoaded(result);
        } else {
            ref.onShuttleLoadingError(pendingException);
        }
    }
}
