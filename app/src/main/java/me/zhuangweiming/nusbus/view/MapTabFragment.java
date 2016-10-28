package me.zhuangweiming.nusbus.view;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import me.zhuangweiming.nusbus.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class MapTabFragment extends Fragment {


    public MapTabFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_map_tab, container, false);
    }

}
