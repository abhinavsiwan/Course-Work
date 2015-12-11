package com.example.siwan.weatherforecast;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.ToggleButton;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

public class DetailsActivity extends AppCompatActivity {

    private String state;
    private String city;
    private String degree;
    private String unit;
    private String timeZone;
    private JSONObject daily;
    private JSONObject hourly;
    private JSONArray hour_data;

    private static final Map<String, String> mapIcon;
    static
    {
        mapIcon = new HashMap<String, String>();
        mapIcon.put("clear-day", "clear");
        mapIcon.put("clear-night", "clear_night");
        mapIcon.put("rain", "rain");
        mapIcon.put("snow", "snow");
        mapIcon.put("sleet", "sleet");
        mapIcon.put("wind", "wind");
        mapIcon.put("fog", "fog");
        mapIcon.put("cloudy", "cloudy");
        mapIcon.put("partly-cloudy-day", "cloud_day");
        mapIcon.put("partly-cloudy-night", "cloud_night");
        mapIcon.put("storm", "Storm");
    }
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        ((RelativeLayout) findViewById(R.id.next24Hours)).setVisibility(View.VISIBLE);
        ((RelativeLayout) findViewById(R.id.next7Days)).setVisibility(View.GONE);

        try {
            daily = new JSONObject(getIntent().getExtras().getString("daily"));
            hourly = new JSONObject(getIntent().getExtras().getString("hourly"));
            state = getIntent().getExtras().getString("state");
            city = getIntent().getExtras().getString("city");
            timeZone = getIntent().getExtras().getString("timezone");
            unit = getIntent().getExtras().getString("unit");    //gets the unit i.e F or C

            resultSet();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void resultSet() {
        setSummary();
        populate7DaysData();
        populate24HoursData(0, 24);
    }

    private void populate24HoursData(int start, int end) {
        //((TextView)findViewById(R.id.test)).setText(hourly.toString());
        try {
            TableLayout table = (TableLayout) findViewById(R.id.hourData);
            LayoutInflater inflater = LayoutInflater.from(getApplicationContext());
            JSONArray hour_data = hourly.getJSONArray("data");
            for (int i = start; i < end; i++) {
                View row= inflater.inflate(R.layout.hourly, null, false);
                TextView t1 = (TextView) row.findViewById(R.id.time_hour);
                TextView t2 = (TextView) row.findViewById(R.id.temp_hour);
                ImageView img1 = (ImageView) row.findViewById(R.id.img_hour);
                t1.setId(View.generateViewId());
                t1.setText(convertTime24hours(Integer.parseInt(((JSONObject) hour_data.get(i)).getString("time"))));
                //t1.setText(time);
                t2.setId(View.generateViewId());
                t2.setText("" + ((JSONObject) hour_data.get(i)).getInt("temperature"));
                //t2.setText("Temp");
                img1.setId(View.generateViewId());
                String icon = mapIcon.get(((JSONObject) hour_data.get(i)).getString("icon"));
                int id = getApplicationContext().getResources().getIdentifier(icon, "drawable", getApplicationContext().getPackageName());
                //int id = getApplicationContext().getResources().getIdentifier("clear", "drawable", getApplicationContext().getPackageName());
                img1.setImageResource(id);

                //Setting the Header for Temperature
                ((TextView) findViewById(R.id.hour_temp)).setText("Temp(" + unit + ")");
                if(i%2 == 0) {
                    row.setBackgroundColor(getResources().getColor(R.color.grey));
                }
                table.addView(row);
            }
            if (start == 0) {
                View row= inflater.inflate(R.layout.hourly_more, null, false);
                Button btn = (Button) row.findViewById(R.id.hour_morebtn);
                btn.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        TableRow row = (TableRow) findViewById(R.id.hourbtnview);
                        row.setVisibility(View.GONE);
                        populate24HoursData(24, 48);
                    }});
                row.setBackgroundColor(getResources().getColor(R.color.grey));
                table.addView(row);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String convertTime24hours(int sunriseTime) {
        Date date = new Date(sunriseTime*1000L); // *1000 is to convert seconds to milliseconds
        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm a"); // the format of your date
        sdf.setTimeZone(TimeZone.getTimeZone(timeZone));
        String formattedDate = sdf.format(date);
        return formattedDate;
    }

    private void populate7DaysData() {
        String[][] day = new String[7][3];

        try{
            for(int i=0;i<7;i++)
            {
                String minMaxTemp;
                JSONArray data1 = daily.getJSONArray("data");
                int minTemp = ((JSONObject)data1.get(i+1)).getInt("temperatureMin");
                int maxTemp = ((JSONObject)data1.get(i+1)).getInt("temperatureMax");
                minMaxTemp = "Min:"+ minTemp + unit + " | Max:"+ maxTemp + unit;

                String time = String.valueOf(convertTime(((JSONObject) data1.get(i+1)).getInt("time")));

                String icon = ((JSONObject)data1.get(i+1)).getString("icon");

                day[i][0] = time;
                day[i][1] = minMaxTemp;
                day[i][2] =icon;
            }
            showdata(day);
        } catch (Exception e) {
            //todo
        }

    }

    private String convertTime(int sunriseTime) {
        Date date = new Date(sunriseTime*1000L); // *1000 is to convert seconds to milliseconds
        SimpleDateFormat sdf = new SimpleDateFormat("EEEE, MMM dd"); // the format of your date
        sdf.setTimeZone(TimeZone.getTimeZone(timeZone));
        String formattedDate = sdf.format(date);
        return formattedDate;
    }

    private void showdata(String[][] day) {
        ((TextView) findViewById(R.id.details_time0)).setText(day[0][0]);
        ((TextView) findViewById(R.id.details_minmax0)).setText(day[0][1]);
        ((ImageView)findViewById(R.id.details_icon0)).setImageResource(findViewById(R.id.details_icon0).getResources().getIdentifier(mapIcon.get(day[0][2]), "drawable", findViewById(R.id.details_icon0).getContext().getPackageName()));


        ((TextView) findViewById(R.id.details_time1)).setText(day[1][0]);
        ((TextView) findViewById(R.id.details_minmax1)).setText(day[1][1]);
        ((ImageView)findViewById(R.id.details_icon1)).setImageResource(findViewById(R.id.details_icon1).getResources().getIdentifier(mapIcon.get(day[1][2]), "drawable", findViewById(R.id.details_icon1).getContext().getPackageName()));

        ((TextView) findViewById(R.id.details_time2)).setText(day[2][0]);
        ((TextView) findViewById(R.id.details_minmax2)).setText(day[2][1]);
        ((ImageView)findViewById(R.id.details_icon2)).setImageResource(findViewById(R.id.details_icon2).getResources().getIdentifier(mapIcon.get(day[2][2]), "drawable", findViewById(R.id.details_icon2).getContext().getPackageName()));

        ((TextView) findViewById(R.id.details_time3)).setText(day[3][0]);
        ((TextView) findViewById(R.id.details_minmax3)).setText(day[3][1]);
        ((ImageView)findViewById(R.id.details_icon3)).setImageResource(findViewById(R.id.details_icon3).getResources().getIdentifier(mapIcon.get(day[3][2]), "drawable", findViewById(R.id.details_icon3).getContext().getPackageName()));

        ((TextView) findViewById(R.id.details_time4)).setText(day[4][0]);
        ((TextView) findViewById(R.id.details_minmax4)).setText(day[4][1]);
        ((ImageView)findViewById(R.id.details_icon4)).setImageResource(findViewById(R.id.details_icon4).getResources().getIdentifier(mapIcon.get(day[4][2]), "drawable", findViewById(R.id.details_icon4).getContext().getPackageName()));

        ((TextView) findViewById(R.id.details_time5)).setText(day[5][0]);
        ((TextView) findViewById(R.id.details_minmax5)).setText(day[5][1]);
        ((ImageView)findViewById(R.id.details_icon5)).setImageResource(findViewById(R.id.details_icon5).getResources().getIdentifier(mapIcon.get(day[5][2]), "drawable", findViewById(R.id.details_icon5).getContext().getPackageName()));

        ((TextView) findViewById(R.id.details_time6)).setText(day[6][0]);
        ((TextView) findViewById(R.id.details_minmax6)).setText(day[6][1]);
        ((ImageView)findViewById(R.id.details_icon6)).setImageResource(findViewById(R.id.details_icon6).getResources().getIdentifier(mapIcon.get(day[6][2]), "drawable", findViewById(R.id.details_icon6).getContext().getPackageName()));
    }

    private void setSummary() {
        TextView summary = (TextView) findViewById(R.id.details_summary);
        try {
            String sum = "";
            sum += "More Deatils for "+city+", "+state;
            summary.setText(sum);
        } catch (Exception e) {
            summary.setText("N.A.");
        }
    }

    public void toggle24Hours(View view) {
        ToggleButton daybtn = (ToggleButton) findViewById(R.id.daybtn7);
        ToggleButton hourbtn = (ToggleButton) findViewById(R.id.hourbtn24);

        if(hourbtn.isChecked()) {
            ((RelativeLayout) findViewById(R.id.next24Hours)).setVisibility(View.VISIBLE);
            ((RelativeLayout) findViewById(R.id.next7Days)).setVisibility(View.GONE);
            hourbtn.setChecked(true);
            daybtn.setChecked(false);
        }
        else {
            daybtn.setChecked(true);
        }
    }

    public void toggle7Days(View view) {
        ToggleButton daybtn = (ToggleButton) findViewById(R.id.daybtn7);
        ToggleButton hourbtn = (ToggleButton) findViewById(R.id.hourbtn24);

        if(daybtn.isChecked()) {
            ((RelativeLayout) findViewById(R.id.next24Hours)).setVisibility(View.GONE);
            ((RelativeLayout) findViewById(R.id.next7Days)).setVisibility(View.VISIBLE);
            hourbtn.setChecked(false);
            daybtn.setChecked(true);
        }
        else {
            hourbtn.setChecked(true);
        }
    }
}
