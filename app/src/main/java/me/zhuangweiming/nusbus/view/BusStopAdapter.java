package me.zhuangweiming.nusbus.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import me.zhuangweiming.nusbus.R;
import me.zhuangweiming.nusbus.model.BusStop;

/**
 * Created by weiming on 15/10/16.
 */
public class BusStopAdapter extends ArrayAdapter<BusStop> {
    private int resourceId;

    public BusStopAdapter(Context context, int textViewResourceId, List<BusStop> objects) {
        super(context, textViewResourceId, objects);
        resourceId = textViewResourceId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        BusStop busStop = getItem(position);
        View view;
        ViewHolder viewHolder;
        if (convertView == null) {
            view = LayoutInflater.from(getContext()).inflate(resourceId, null);
            viewHolder = new ViewHolder();
            viewHolder.busStopCaption = (TextView) view.findViewById(R.id.bus_stop_caption);
            view.setTag(viewHolder);
        } else {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.busStopCaption.setText(busStop.getCaption());
        return view;
    }

    class ViewHolder {
        TextView busStopCaption;
    }
}

