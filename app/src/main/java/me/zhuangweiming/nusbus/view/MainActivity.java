package me.zhuangweiming.nusbus.view;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import me.zhuangweiming.nusbus.BusApplication;
import me.zhuangweiming.nusbus.R;
import me.zhuangweiming.nusbus.model.Bus;
import me.zhuangweiming.nusbus.model.BusStop;
import me.zhuangweiming.nusbus.services.BusPositionsLoadedCallback;
import me.zhuangweiming.nusbus.services.BusStopLoadedCallback;
import me.zhuangweiming.nusbus.services.BusStopService;

public class MainActivity extends Activity implements BusStopLoadedCallback, BusPositionsLoadedCallback {

    @Inject
    BusStopService busStopService;

    private List<BusStop> busStopList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ((BusApplication) getApplication()).getAppComponent().inject(this);

        busStopService.loadBusStops(this);
    }

    @Override
    public void onBusStopsLoaded(List<BusStop> busStops) {
        this.busStopList =  busStops;
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

    @Override
    public void onBusStopLoadingError(Exception exception) {
        Log.d("pb", "error");
    }

    @Override
    public void onBusPositionsLoaded(List<Bus> busses) {
        Log.d("busses", ""+busses.size());
    }

    @Override
    public void onBusPositionsLoadingError(Exception exception) {

    }
}

