package me.zhuangweiming.nusbus.view;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.zhuangweiming.nusbus.BusApplication;
import me.zhuangweiming.nusbus.R;
import me.zhuangweiming.nusbus.model.BusStop;
import me.zhuangweiming.nusbus.services.BusStopLoadedCallback;
import me.zhuangweiming.nusbus.services.BusStopService;
import me.zhuangweiming.nusbus.view.adapters.BusStopAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class BusStopFragment extends Fragment implements BusStopLoadedCallback{

    @Inject
    protected BusStopService busStopService;

    @BindView(R.id.bus_stop_list_view) protected ListView busStopListView;

    private List<BusStop> busStopList = new ArrayList<>();

    public BusStopFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((BusApplication) getActivity().getApplication()).getAppComponent().inject(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View currentView = inflater.inflate(R.layout.fragment_bus_stop, container, false);

        ButterKnife.bind(this, currentView);

        busStopService.loadBusStops(this);

        return currentView;
    }


    @Override
    public void onBusStopsLoaded(List<BusStop> busStops) {
        this.busStopList =  busStops;
        final BusStopAdapter busStopAdapter = new BusStopAdapter(getActivity(), R.layout.bus_stop, busStopList);
        busStopListView.setAdapter(busStopAdapter);

        busStopListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                BusStop busStop = busStopList.get(position);
                Intent intent = new Intent(getActivity().getApplicationContext(), ShuttleActivity.class);
                intent.putExtra("busStopName", busStop.getName());
                startActivity(intent);
//                Toast.makeText(getActivity(), busStop.getName(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onBusStopLoadingError(Exception exception) {
        Log.d("pb", "error");
    }
}
