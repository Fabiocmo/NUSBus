package me.zhuangweiming.nusbus.view;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
import me.zhuangweiming.nusbus.view.adapters.RecyclerShuttleAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class ShuttleFragment extends Fragment implements LoadShuttleCallback, RecyclerShuttleAdapter.OnItemClickListener {

    @Inject
    protected ShuttleService shuttleService;

    @BindView(R.id.shuttle_list) protected RecyclerView shuttleListView;

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
        final RecyclerShuttleAdapter shuttleAdapter = new RecyclerShuttleAdapter(getActivity(), shuttleList, this);
        shuttleListView.setLayoutManager(new LinearLayoutManager(getActivity()));
        shuttleListView.setAdapter(shuttleAdapter);
        shuttleAdapter.notifyDataSetChanged();
    }

    @Override
    public void onShuttleLoadingError(Exception e) {

    }

    @Override
    public void onItemClick(View v, int position, Shuttle trip) {

    }
}
