package sg.edu.rp.id19022187.pd_app4;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomAdapter extends ArrayAdapter<BusArrival> {
    Context parent_context;
    int layout_id;
    private ArrayList<BusArrival> busArr;

    public CustomAdapter(Context context, int resource, ArrayList<BusArrival> objects) {
        super(context, resource, objects);
        parent_context = context;
        layout_id = resource;
        busArr = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) parent_context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rowView = inflater.inflate(layout_id, parent, false);

        TextView tvBus = rowView.findViewById(R.id.bus);
        TextView tvTime1 = rowView.findViewById(R.id.time1);
        TextView tvTime2 = rowView.findViewById(R.id.time2);
        TextView tvTime3 = rowView.findViewById(R.id.time3);


        BusArrival currentItem = busArr.get(position);
        tvBus.setText(currentItem.getBusNum());

        tvTime1.setText(currentItem.getTime1());
        tvTime2.setText(currentItem.getTime2());
        tvTime3.setText(currentItem.getTime3());

        return rowView;
    }
}