package sg.edu.rp.id19022187.pd_app4;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomAdapter extends ArrayAdapter {
    Context parent_context;
    int layout_id;
    ArrayList<BusArrival> busArr;

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

        // TODO: Question 6 - Write the codes so that the contact information will be shown correctly in the ListView
        TextView tvName = rowView.findViewById(R.id.name);
        TextView tvInfo = rowView.findViewById(R.id.contactInfo);


        BusArrival currentItem = busArr.get(position);
        tvName.setText(currentItem.getBusNum());
        if (currentItem.getPreferredMode() == 0){
            tvInfo.setText(currentItem.getMobileNumber());
        }
        else {
            tvInfo.setText(currentItem.getEmail());
        }

        return rowView;
    }
}