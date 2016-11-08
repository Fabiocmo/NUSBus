package me.zhuangweiming.nusbus.view.adapters;

/**
 * Created by weiming on 4/11/16.
 */

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.lang.ref.WeakReference;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.zhuangweiming.nusbus.R;
import me.zhuangweiming.nusbus.model.BusStop;

public class RecyclerBusStopAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>
        /***/ {

    private List<BusStop> busStops;
    private WeakReference<OnItemClickListener> mCallBack;
    private Context context;

    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;

    // Provide a suitable constructor (depends on the kind of dataset)
    public RecyclerBusStopAdapter(Context context, List<BusStop> busStops, OnItemClickListener listener) {
        mCallBack = new WeakReference<>(listener);
        this.busStops = busStops;
        this.context = context;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder vh = null;
        if (viewType == TYPE_ITEM) {
            View v = LayoutInflater.from(context).inflate(R.layout.bus_stop, parent, false);
            vh = new ViewHolder(v);
        }
        return vh;
    }

    @Override
    public int getItemViewType(int position) {
        return TYPE_ITEM;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, final int position) {

        if (viewHolder instanceof ViewHolder) {

            RecyclerBusStopAdapter.ViewHolder vh = (RecyclerBusStopAdapter.ViewHolder) viewHolder;
            final BusStop currentBusStop = busStops.get(position);
            vh.name.setText(currentBusStop.getName());
//            vh.arrival.setText(currentTrip.getArrivalTime());
        }
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return busStops.size();
    }

    public void remove(int position) {

    }

    public void remove(BusStop busStop) {

    }

    public BusStop getItemAt(int position)
    {
        return busStops.get(position);
    }

    public void clear() {
        busStops.clear();
    }

    public interface OnItemClickListener {
        void onItemClick(View v, int position, BusStop busstop);
    }


    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public class ViewHolder extends  RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.bus_stop_caption)
        protected TextView name;
//
//        @BindView(R.id.next_bus_arrival_time)
//        protected TextView arrival;


        public ViewHolder(View v) {
            super(v);

            ButterKnife.bind(this, v);

            v.setClickable(true);
        }

        @Override
        public void onClick(View v) {
            OnItemClickListener listener = null;
            if ((listener = mCallBack.get()) != null) {
                mCallBack.get().onItemClick(v, getAdapterPosition(), busStops.get(getAdapterPosition()));
            }
        }
    }
}