package me.zhuangweiming.nusbus.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import me.zhuangweiming.nusbus.R;
import me.zhuangweiming.nusbus.model.Shuttle;


/**
 * Created by weiming on 26/10/16.
 */

public class ShuttleAdapter extends ArrayAdapter<Shuttle> {
    private int resourceId;

    public ShuttleAdapter(Context context, int textViewResourceId, List<Shuttle> objects) {
        super(context, textViewResourceId, objects);
        this.resourceId = textViewResourceId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Shuttle shuttle = getItem(position);
        View view;
        ViewHodler viewHodler;
        if (convertView == null) {
            view = LayoutInflater.from(getContext()).inflate(resourceId, null);
            viewHodler = new ViewHodler();
            viewHodler.shuttleName = (TextView) view.findViewById(R.id.shuttle_name);
            viewHodler.arrivalTime = (TextView) view.findViewById(R.id.arrival_time);
            view.setTag(viewHodler);
        } else {
            view = convertView;
            viewHodler = (ViewHodler) view.getTag();
        }
        viewHodler.shuttleName.setText(shuttle.getName());
        viewHodler.arrivalTime.setText(shuttle.getArrivalTime());
        return view;
    }

    class ViewHodler {
        TextView shuttleName;
        TextView arrivalTime;
    }
}
