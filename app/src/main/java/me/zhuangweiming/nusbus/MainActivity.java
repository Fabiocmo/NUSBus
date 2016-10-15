package me.zhuangweiming.nusbus;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import me.zhuangweiming.nusbus.model.BusStop;
import me.zhuangweiming.nusbus.utils.HttpsUtil;
import me.zhuangweiming.nusbus.view.BusStopAdapter;

public class MainActivity extends Activity {
    final String BUS_TIMEING_HOST = "https://nextbus.comfortdelgro.com.sg";
    final String BUS_STOP_API = "/eventservice.svc/BusStops";
    final String BUS_API = "/eventservice.svc/Shuttleservice?busstopname=";  // + busstopname

    private List<BusStop> busStopList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new GetBusStops().execute(BUS_TIMEING_HOST + BUS_STOP_API);
        final BusStopAdapter busStopAdapter = new BusStopAdapter(MainActivity.this, R.layout.bus_stop, busStopList);
        ListView listView = (ListView) findViewById(R.id.list_view);
        listView.setAdapter(busStopAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                BusStop busStop = busStopList.get(position);
                Toast.makeText(MainActivity.this, busStop.getName(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public class GetBusStops extends AsyncTask<String, Void, String> {
        protected String doInBackground(String... urls) {
            return HttpsUtil.sendHttpsRequest(urls[0]);
        }

        protected void onPostExecute(String result) {
            try {
                JSONObject jsonObject = new JSONObject(result);
                JSONObject busStopsObject = jsonObject.getJSONObject("BusStopsResult");
                JSONArray busStops = busStopsObject.getJSONArray("busstops");
                initBusStops(busStops);
            } catch (JSONException e) {
                Log.e("GetBusStops", e.getLocalizedMessage());
            }
        }
    }

    private void initBusStops(JSONArray busStops) throws JSONException {
        for (int i = 0; i < busStops.length(); i++) {
            JSONObject busStopObject = busStops.getJSONObject(i);
            String caption = busStopObject.getString("caption");
            String name = busStopObject.getString("name");
            double latitude = busStopObject.getDouble("latitude");
            double longitude = busStopObject.getDouble("longitude");
            BusStop busStop = new BusStop(caption, name, latitude, longitude);
            busStopList.add(busStop);
        }
    }

}
