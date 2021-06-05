package sg.edu.rp.id19022187.pd_app4;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.PermissionChecker;
import androidx.fragment.app.FragmentManager;

import android.Manifest;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class BusStopDetails extends AppCompatActivity {

    private AsyncHttpClient client;
    TextView busStopName, busStopCode, roadName;
    ListView lvBusStops;
    ArrayList<BusStops> busStopsArrList;
    BusStopAdapter busStopsArrAdapter;
    private GoogleMap map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bus_stop_details);

        busStopName = findViewById(R.id.BusStopName);
        busStopCode = findViewById(R.id.BusStopCode);
        roadName = findViewById(R.id.RoadName);
        lvBusStops = findViewById(R.id.BusStopCodes);
        busStopsArrList = new ArrayList<>();
        client = new AsyncHttpClient();

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        FragmentManager fragm = getSupportFragmentManager();
        SupportMapFragment mapfrag = (SupportMapFragment) fragm.findFragmentById(R.id.map);

        mapfrag.getMapAsync(new OnMapReadyCallback(){
            @Override
            public void onMapReady(GoogleMap googleMap) {
                map = googleMap;
            }
        });

        if (map != null){
            map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        }

        mapfrag.getMapAsync(new OnMapReadyCallback(){
            @Override
            public void onMapReady(GoogleMap googleMap) {
                map = googleMap;

                UiSettings ui2 = map.getUiSettings();
                ui2.setZoomControlsEnabled(true);

                int permissionCheck = ContextCompat.checkSelfPermission(BusStopDetails.this,
                        android.Manifest.permission.ACCESS_FINE_LOCATION);

                if (permissionCheck == PermissionChecker.PERMISSION_GRANTED) {

                } else {
                    Log.e("GMap - Permission", "GPS access has not been granted");
                    ActivityCompat.requestPermissions(BusStopDetails.this,
                            new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 0);
                }
            }
        });

        //Get BusStop Code
        client.addHeader("AccountKey", "17ERkNCJTC6ts24AKPf4JA==");
        client.addHeader("accept", "application/json");
        client.get("http://datamall2.mytransport.sg/ltaodataservice/BusStops", new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    JSONArray jsonArr = response.getJSONArray("value");
                    for (int i = 0; i < jsonArr.length(); i++) {
                        JSONObject jsonObj = jsonArr.getJSONObject(i);
                        String BSName = jsonObj.getString("Description");
                        String BSCode = jsonObj.getString("BusStopCode");
                        String RdName = jsonObj.getString("RoadName");
                        Double Lat = jsonObj.getDouble("Latitude");
                        Double Long = jsonObj.getDouble("Longitude");

                        BusStops busstop = new BusStops(BSCode, RdName, BSName, Lat, Long);
                        busStopsArrList.add(busstop);

                        final LatLng BusStopLoc = new LatLng(Lat, Long);

                        final Marker bsmarkers = map.addMarker(new
                                MarkerOptions()
                                .position(BusStopLoc)
                                .title(BSName)
                                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));

                        final LatLng SGLoc = new LatLng(1.29362677394379, 103.83283853530904);
                        map.moveCamera(CameraUpdateFactory.newLatLngZoom(SGLoc,15));
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                busStopsArrAdapter = new BusStopAdapter(getApplicationContext(), R.layout.bsrow, busStopsArrList);
                lvBusStops.setAdapter(busStopsArrAdapter);
            }
        });

        lvBusStops.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int pos, long l) {
                BusStops BScode = busStopsArrList.get(pos);
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                String code = BScode.getBsCode();
                i.putExtra("BScode", code);
                setResult(RESULT_OK, i);
                finish();
            }
        });
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}