package me.zhuangweiming.nusbus.view;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
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
import me.zhuangweiming.nusbus.model.BusStop;
import me.zhuangweiming.nusbus.model.Shuttle;
import me.zhuangweiming.nusbus.services.LoadShuttleCallback;
import me.zhuangweiming.nusbus.services.ShuttleService;
import me.zhuangweiming.nusbus.view.adapters.RecyclerShuttleAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class ShuttleFragment extends Fragment implements LoadShuttleCallback, RecyclerShuttleAdapter.OnItemClickListener {

    private static final String STOP_KEY = "stop_key";

    @Inject
    protected ShuttleService shuttleService;

    @BindView(R.id.shuttle_list)
    protected RecyclerView shuttleListView;

    private List<Shuttle> shuttleList = new ArrayList<>();

    private BusStop busStop;

    @BindView(R.id.swipe_refresh_layout)
    protected SwipeRefreshLayout swipeRefreshLayout;

    public ShuttleFragment() {
        // Required empty public constructor
    }

    public static ShuttleFragment newInstance(BusStop stop) {
        ShuttleFragment sh = new ShuttleFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(STOP_KEY, stop);
        sh.setArguments(bundle);

        return sh;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((BusApplication) getActivity().getApplication()).getAppComponent().inject(this);
        busStop = getArguments().getParcelable(STOP_KEY);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View currentView = inflater.inflate(R.layout.fragment_shuttle, container, false);
        ButterKnife.bind(this, currentView);

        shuttleService.loadShuttles(this, busStop.getName());
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                shuttleService.loadShuttles(ShuttleFragment.this, busStop.getName());
            }
        });

        return currentView;
    }

    @Override
    public void onShuttleLoaded(List<Shuttle> shuttles) {
        this.shuttleList = shuttles;
        applyFilter(shuttleList);
        final RecyclerShuttleAdapter shuttleAdapter = new RecyclerShuttleAdapter(getActivity(), shuttleList, this);
        shuttleListView.setLayoutManager(new LinearLayoutManager(getActivity()));
        shuttleListView.setAdapter(shuttleAdapter);
        shuttleAdapter.notifyDataSetChanged();
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onShuttleLoadingError(Exception e) {

    }

    @Override
    public void onItemClick(View v, int position, Shuttle trip) {

    }

    private void applyFilter(List<Shuttle> shuttles) {

        List<List<Shuttle>> buckets = new ArrayList<>();

        for(int i=0; i<52; i++) {
            buckets.add(new ArrayList<Shuttle>());
        }

        for(Shuttle sh:shuttles) {
            int arriving = -1;
            if(sh.getArrivalTime().equals("Arr")) {
                arriving = 0;
            } else if(sh.getArrivalTime().equals("-")) {
                arriving = 50;
            } else {
                arriving = Integer.parseInt(sh.getArrivalTime());
            }

            buckets.get(arriving).add(sh);
        }
        shuttles.clear();
        for(List<Shuttle> sh:buckets) {
            shuttles.addAll(sh);
        }
    }
}
