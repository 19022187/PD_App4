package sg.edu.rp.id19022187.pd_app4;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class BusStopAdapter extends ArrayAdapter<BusStops> {
    Context parent_context;
    int layout_id;
    private ArrayList<BusStops> BusStops;

    public BusStopAdapter(Context context, int resource, ArrayList<BusStops> objects) {
        super(context, resource, objects);
        parent_context = context;
        layout_id = resource;
        BusStops = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) parent_context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rowView = inflater.inflate(R.layout.bsrow, parent, false);
        TextView BSName = rowView.findViewById(R.id.BusStopName);
        TextView BSCode = rowView.findViewById(R.id.BusStopCode);
        TextView RoadName = rowView.findViewById(R.id.RoadName);

        BusStops currentItem = BusStops.get(position);
        BSName.setText(currentItem.getDesc());
        BSCode.setText(currentItem.getBsCode());
        RoadName.setText(currentItem.getRdName());

        return rowView;
    }
}
