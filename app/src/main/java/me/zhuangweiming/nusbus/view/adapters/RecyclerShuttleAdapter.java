package me.zhuangweiming.nusbus.view.adapters;

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
import me.zhuangweiming.nusbus.model.Shuttle;

/**
 * Created by a607937 on 18/06/2015.
 */
public class RecyclerShuttleAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>
        /***/ {

    private List<Shuttle> shuttles;
    private WeakReference<OnItemClickListener> mCallBack;
    private Context context;

    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;

    // Provide a suitable constructor (depends on the kind of dataset)
    public RecyclerShuttleAdapter(Context context, List<Shuttle> shuttles, OnItemClickListener listener) {

        mCallBack = new WeakReference<OnItemClickListener>(listener);
        this.shuttles = shuttles;
        this.context = context;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder vh = null;
        if (viewType == TYPE_ITEM) {
            // create a new view
            View v = LayoutInflater.from(context)
                    .inflate(R.layout.shuttle, parent, false);
            // set the view's size, margins, paddings and layout parameters

            vh = new ViewHolder(v);

        } else if (viewType == TYPE_HEADER) {

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

            RecyclerShuttleAdapter.ViewHolder vh = (RecyclerShuttleAdapter.ViewHolder) viewHolder;
            //the current Forecast
            final Shuttle currentTrip = shuttles.get(position);
            //we generate the color

            vh.name.setText(currentTrip.getName());
            String suff = currentTrip.getArrivalTime().compareTo("-") != 0
                    && currentTrip.getArrivalTime().compareTo("Arr") != 0 ? " min" : "";
            vh.arrival.setText(currentTrip.getArrivalTime()+suff);
            suff = currentTrip.getNextArrivalTime().compareTo("-") != 0
                    && currentTrip.getNextArrivalTime().compareTo("Arr") != 0 ? "min" : "";
            vh.nextArrival.setText(currentTrip.getNextArrivalTime()+suff);

            //cast holder to VHItem and set data
        }
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return shuttles.size();
    }

    public void remove(int position) {

    }

    public void remove(Shuttle trip) {

    }

    public Shuttle getItemAt(int position)
    {
        return shuttles.get(position);
    }

    public void clear() {
        shuttles.clear();
    }

    public interface OnItemClickListener {
        void onItemClick(View v, int position, Shuttle trip);
    }


    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public class ViewHolder extends  RecyclerView.ViewHolder implements View.OnClickListener {
        // each data item is just a string in this case

        @BindView(R.id.shuttle_name)
        protected TextView name;

        @BindView(R.id.arrival_time)
        protected TextView arrival;

        @BindView(R.id.arrival_time_2)
        protected TextView nextArrival;


        public ViewHolder(View v) {
            super(v);

            ButterKnife.bind(this, v);

            v.setClickable(true);
        }

        @Override
        public void onClick(View v) {
            OnItemClickListener listener = null;
            if ((listener = mCallBack.get()) != null)
            {
                mCallBack.get().onItemClick(v, getAdapterPosition(), shuttles.get(getAdapterPosition()));
            }


        }
    }
}