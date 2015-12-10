package com.example.siwan.weatherforecast;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.google.android.gms.maps.model.LatLng;

import org.json.JSONObject;

public class MapActivity extends AppCompatActivity {

    private String state;
    private String city;
    private String degree;
    private String unit;
    private JSONObject hourly;
    double latitude;
    double longitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            latitude = Double.parseDouble(getIntent().getExtras().getString("latitude"));
            longitude = Double.parseDouble(getIntent().getExtras().getString("longitude"));
            setContentView(R.layout.activity_map);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public LatLng getLatLong() {
        return new LatLng(latitude, longitude);
    }
}
