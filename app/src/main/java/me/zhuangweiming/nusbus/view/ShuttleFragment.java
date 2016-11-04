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

    @BindView(R.id.shuttle_list) protected RecyclerView shuttleListView;

    private List<Shuttle> shuttleList = new ArrayList<>();

    private BusStop busStop;

    public ShuttleFragment() {
        // Required empty public constructor
    }

    public static ShuttleFragment newInstance(BusStop stop)
    {
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
    }

    @Override
    public void onShuttleLoadingError(Exception e) {

    }

    @Override
    public void onItemClick(View v, int position, Shuttle trip) {

    }

    private void applyFilter(List<Shuttle> shuttles) {
        boolean swaped = true;
        while (swaped) {
            swaped = false;
            for (int i = 0; i < shuttles.size() - 1; i++) {
                int j = i + 1;
                String current = shuttles.get(i).getArrivalTime();
                String next = shuttles.get(i + 1).getArrivalTime();
                if (current.equals("-")) {
                    if (!next.equals("-")) {
                        swap(i, j, shuttles);
                        swaped = true;
                    }
                } else {
                    if (!next.equals("-") && Integer.valueOf(current) > Integer.valueOf(next)) {
                        swap(i, j, shuttles);
                        swaped = true;
                    }
                }
            }
        }
    }

    private void swap(int i, int j, List<Shuttle> shuttles) {
        Shuttle temp = shuttles.get(i);
        shuttles.set(i, shuttles.get(j));
        shuttles.set(j, temp);
    }

}
