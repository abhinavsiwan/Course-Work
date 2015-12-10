package com.example.siwan.weatherforecast;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONObject;

public class FacebookActivity extends AppCompatActivity {

    private String state;
    private String city;
    private String degree;
    private String unit;
    private JSONObject currently;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_facebook);

        try {
            currently = new JSONObject(getIntent().getExtras().getString("currently"));
            state = getIntent().getExtras().getString("state");
            city = getIntent().getExtras().getString("city");
            degree = getIntent().getExtras().getString("degree");   // gets the us or si value
            unit = getIntent().getExtras().getString("unit");    //gets the unit i.e F or C
            /*
            String val = state + "\n" + city + "\n" + degree + "\n" + unit;
            TextView txt = new TextView(this);
            txt.setText(val);
            setContentView(summary);
            */

            TextView summary = (TextView) findViewById(R.id.fb_summary);
            summary.setText(currently.toString());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
