package sg.edu.rp.id19022187.pd_app4;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ListView lvBusArr;
    ArrayList<BusArrival> busArrList;
    CustomAdapter busArrAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lvBusArr = findViewById(R.id.listViewBusArr);

        busArrList = new ArrayList<>();
        BusArrival item1 = new BusArrival("Candice", 92205555, "candice@gmail.com", 1);
        busArrList.add(item1);

        busArrAdapter = new CustomAdapter(this, R.layout.row, busArrList);
        lvBusArr.setAdapter(busArrAdapter);
    }
}