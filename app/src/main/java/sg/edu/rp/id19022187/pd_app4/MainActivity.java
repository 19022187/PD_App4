package sg.edu.rp.id19022187.pd_app4;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity {

    private AsyncHttpClient client;
    ListView lvBusArr;
    ArrayList<BusArrival> busArrList;
    CustomAdapter busArrAdapter;
    SimpleDateFormat df, df2;
    String Time1, Time2, Time3, CurT, BScode;
    Date B1, B2, B3, CT;
    Integer IB1, IB2, IB3, ICT;
    TextView BusStopName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lvBusArr = findViewById(R.id.listViewBusArr);
        BusStopName = findViewById(R.id.BSName);
        client = new AsyncHttpClient();
        busArrList = new ArrayList<>();

        df = new SimpleDateFormat("YYYY-MM-DD'T'hh:mm:ss");
        df2 = new SimpleDateFormat("mm");
        TimeZone tz = TimeZone.getTimeZone("SGT");
        df.setTimeZone(tz);
        BScode = "83139";

        //Get Bus Arrival
        String url = "http://datamall2.mytransport.sg/ltaodataservice/BusArrivalv2?BusStopCode="+BScode;
        client.addHeader("AccountKey", "17ERkNCJTC6ts24AKPf4JA==");
        client.addHeader("accept", "application/json");
        client.get(url, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    JSONArray jsonArr = response.getJSONArray("Services");

                    for (int i = 0; i < jsonArr.length(); i++) {
                        JSONObject jsonObj = jsonArr.getJSONObject(i);

                        String BusNo = jsonObj.getString("ServiceNo");
                        JSONObject Bus1 = jsonObj.getJSONObject("NextBus");
                        JSONObject Bus2 = jsonObj.getJSONObject("NextBus2");
                        JSONObject Bus3 = jsonObj.getJSONObject("NextBus3");
                        Time1 = Bus1.getString("EstimatedArrival");
                        Time2 = Bus2.getString("EstimatedArrival");
                        Time3 = Bus3.getString("EstimatedArrival");

                        String currentTime = df.format(Calendar.getInstance().getTime());
                        try {
                            //Getting the current time
                            CT = df.parse(currentTime);
                            CurT = df2.format(CT);
                            ICT = Integer.parseInt(CurT);

                            //Timing for first bus
                            B1 = df.parse(Time1);
                            Time1 = df.format(B1);
                            B1 = df.parse(Time1);
                            Time1 = df2.format(B1);
                            IB1 = Integer.parseInt(Time1);
                            if (IB1 < ICT) {
                                IB1 = IB1 + 60;
                                Time1 = String.valueOf(IB1 - ICT);
                            } else if (IB1 - ICT <= 1 && IB1 - ICT >= -2) {
                                Time1 = "Arr";
                            } else {
                                Time1 = String.valueOf(IB1 - ICT);
                            }

                            //Timing for Second bus
                            B2 = df.parse(Time2);
                            Time2 = df.format(B2);
                            B2 = df.parse(Time2);
                            Time2 = df2.format(B2);
                            IB2 = Integer.parseInt(Time2);
                            if (IB2 < ICT) {
                                IB2 = IB2 + 60;
                                Time2 = String.valueOf(IB2 - ICT);
                            } else if (IB2 - ICT <= 1 && IB2 - ICT >= -2) {
                                Time2 = "Arr";
                            } else {
                                Time2 = String.valueOf(IB2 - ICT);
                            }

                            //Timing for Third bus
                            B3 = df.parse(Time3);
                            Time3 = df.format(B3);
                            B3 = df.parse(Time3);
                            Time3 = df2.format(B3);
                            IB3 = Integer.parseInt(Time3);
                            if (IB3 < ICT) {
                                IB3 = IB3 + 60;
                                Time3 = String.valueOf(IB3 - ICT);
                            } else if (IB3 - ICT <= 1 && IB3 - ICT >= -2) {
                                Time3 = "Arr";
                            } else {
                                Time3 = String.valueOf(IB3 - ICT);
                            }

                            BusArrival contact = new BusArrival(BusNo, Time1, Time2, Time3);
                            busArrList.add(contact);

                            BusStopName.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    Intent i = new Intent(getApplicationContext(), BusStopDetails.class);
                                    startActivityForResult(i, 123);
                                }
                            });

                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                busArrAdapter = new CustomAdapter(getApplicationContext(), R.layout.row, busArrList);
                lvBusArr.setAdapter(busArrAdapter);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Only handle when 2nd activity closed normally
        //  and data contains something
        if(resultCode == RESULT_OK){
            if (requestCode == 123) {
                if (data != null) {
                    busArrList.clear();
                    BScode = data.getStringExtra("BScode");
                    BusStopName.setText(BScode);
                    //Get Bus Arrival
                    String url = "http://datamall2.mytransport.sg/ltaodataservice/BusArrivalv2?BusStopCode=" + BScode;
                    Log.i("url", url);
                    client.addHeader("AccountKey", "17ERkNCJTC6ts24AKPf4JA==");
                    client.addHeader("accept", "application/json");
                    client.get(url, new JsonHttpResponseHandler() {
                        @Override
                        public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                            try {
                                JSONArray jsonArr = response.getJSONArray("Services");

                                for (int i = 0; i < jsonArr.length(); i++) {
                                    JSONObject jsonObj = jsonArr.getJSONObject(i);

                                    String BusNo = jsonObj.getString("ServiceNo");
                                    JSONObject Bus1 = jsonObj.getJSONObject("NextBus");
                                    JSONObject Bus2 = jsonObj.getJSONObject("NextBus2");
                                    JSONObject Bus3 = jsonObj.getJSONObject("NextBus3");
                                    Time1 = Bus1.getString("EstimatedArrival");
                                    Time2 = Bus2.getString("EstimatedArrival");
                                    Time3 = Bus3.getString("EstimatedArrival");

                                    String currentTime = df.format(Calendar.getInstance().getTime());
                                    try {
                                        //Getting the current time
                                        CT = df.parse(currentTime);
                                        CurT = df2.format(CT);
                                        ICT = Integer.parseInt(CurT);

                                        //Timing for first bus
                                        B1 = df.parse(Time1);
                                        Time1 = df.format(B1);
                                        B1 = df.parse(Time1);
                                        Time1 = df2.format(B1);
                                        IB1 = Integer.parseInt(Time1);
                                        if (IB1 < ICT) {
                                            IB1 = IB1 + 60;
                                            Time1 = String.valueOf(IB1 - ICT);
                                        } else if (IB1 - ICT <= 1 && IB1 - ICT >= -2) {
                                            Time1 = "Arr";
                                        } else {
                                            Time1 = String.valueOf(IB1 - ICT);
                                        }

                                        //Timing for Second bus
                                        B2 = df.parse(Time2);
                                        Time2 = df.format(B2);
                                        B2 = df.parse(Time2);
                                        Time2 = df2.format(B2);
                                        IB2 = Integer.parseInt(Time2);
                                        if (IB2 < ICT) {
                                            IB2 = IB2 + 60;
                                            Time2 = String.valueOf(IB2 - ICT);
                                        } else if (IB2 - ICT <= 1 && IB2 - ICT >= -2) {
                                            Time2 = "Arr";
                                        } else {
                                            Time2 = String.valueOf(IB2 - ICT);
                                        }

                                        //Timing for Third bus
                                        B3 = df.parse(Time3);
                                        Time3 = df.format(B3);
                                        B3 = df.parse(Time3);
                                        Time3 = df2.format(B3);
                                        IB3 = Integer.parseInt(Time3);
                                        if (IB3 < ICT) {
                                            IB3 = IB3 + 60;
                                            Time3 = String.valueOf(IB3 - ICT);
                                        } else if (IB3 - ICT <= 1 && IB3 - ICT >= -2) {
                                            Time3 = "Arr";
                                        } else {
                                            Time3 = String.valueOf(IB3 - ICT);
                                        }

                                        BusArrival contact = new BusArrival(BusNo, Time1, Time2, Time3);
                                        busArrList.add(contact);

                                    } catch (ParseException e) {
                                        e.printStackTrace();
                                    }
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            busArrAdapter = new CustomAdapter(getApplicationContext(), R.layout.row, busArrList);
                            lvBusArr.setAdapter(busArrAdapter);
                        }
                    });
                }
            }
        }
    }
}
