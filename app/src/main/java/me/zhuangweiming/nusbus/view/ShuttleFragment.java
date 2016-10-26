package me.zhuangweiming.nusbus.view;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.zhuangweiming.nusbus.BusApplication;
import me.zhuangweiming.nusbus.R;
import me.zhuangweiming.nusbus.model.Shuttle;
import me.zhuangweiming.nusbus.services.LoadShuttleCallback;
import me.zhuangweiming.nusbus.services.ShuttleService;

/**
 * A simple {@link Fragment} subclass.
 */
public class ShuttleFragment extends Fragment implements LoadShuttleCallback {

    @Inject
    protected ShuttleService shuttleService;

    @BindView(R.id.shuttle_list_view) protected ListView shuttleListView;

    private List<Shuttle> shuttleList = new ArrayList<>();

    private String busStopName;

    public ShuttleFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((BusApplication) getActivity().getApplication()).getAppComponent().inject(this);
        busStopName = getActivity().getIntent().getStringExtra("busStopName");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View currentView = inflater.inflate(R.layout.fragment_shuttle, container, false);
        ButterKnife.bind(this, currentView);

        shuttleService.loadShuttles(this, busStopName);
        return currentView;
    }

    @Override
    public void onShuttleLoaded(List<Shuttle> shuttles) {
        this.shuttleList = shuttles;
        final ShuttleAdapter shuttleAdapter = new ShuttleAdapter(getActivity(), R.layout.shuttle, shuttleList);
        shuttleListView.setAdapter(shuttleAdapter);
    }

    @Override
    public void onShuttleLoadingError(Exception e) {

    }

}
