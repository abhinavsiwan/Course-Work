package com.example.siwan.weatherforecast;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.facebook.share.Sharer;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;
import com.hamweather.aeris.communication.AerisEngine;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

public class ResultActivity extends AppCompatActivity {

    private JSONObject result;
    private JSONObject daily;
    private JSONObject currently;
    private JSONObject hourly;
    private String state;
    private String city;
    private String degree;
    private String unit;
    private String timeZone;

    //For Facebook
    CallbackManager callbackManager;
    ShareDialog shareDialog;

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
        String rTAG = "ResultActivity";
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_result);

        //For Facebook
        callbackManager = CallbackManager.Factory.create();
        shareDialog = new ShareDialog(this);
        LoginManager.getInstance().logOut();
        //For Map
        AerisEngine.initWithKeys(this.getString(R.string.aeris_client_id), this.getString(R.string.aeris_client_secret), this);

        try {
            result = new JSONObject(getIntent().getExtras().getString("result"));
            currently = (JSONObject) result.get("currently");
            hourly = (JSONObject) result.get("hourly");
            daily = (JSONObject) result.get("daily");
            timeZone = result.getString("timezone");
            state = getIntent().getExtras().getString("state");
            city = getIntent().getExtras().getString("city");
            degree = getIntent().getExtras().getString("degree");   // gets the us or si value
            unit = getIntent().getExtras().getString("unit");    //gets the unit i.e F or C
            /*
            TextView txt = new TextView(this);
            txt.setText(timeZone);
            setContentView(txt);
            */
            resultSet();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void resultSet() {
        setSummaryImage();
        setSummary();
        setTemperature();
        setMinMax();
        setPrecipitation();
        setRain();
        setWindSpeed();
        setDewPoint();
        setHumidity();
        setVisibility();
        setSunriseTime();
        setSunsetTime();
    }

    private void setSunsetTime() {
        TextView sunset = (TextView) findViewById(R.id.sunset);
        try {
            String set;
            JSONArray data = daily.getJSONArray("data");
            set = convertTime(((JSONObject) data.get(0)).getInt("sunsetTime"));
            //set = ((JSONObject)data.get(0)).getString("sunsetTime");
            sunset.setText(set);
        } catch (Exception e) {
            sunset.setText("N.A.");
        }
    }

    private void setSunriseTime() {
        TextView sunrise = (TextView) findViewById(R.id.sunrise);
        try {
            String rise;
            JSONArray data = daily.getJSONArray("data");
            rise = convertTime(((JSONObject) data.get(0)).getInt("sunriseTime"));
            //rise = ((JSONObject)data.get(0)).getString("sunriseTime");
            sunrise.setText(rise);
        } catch (Exception e) {
            sunrise.setText("N.A.");
        }
    }

    private String convertTime(int sunriseTime) {
        Date date = new Date(sunriseTime*1000L); // *1000 is to convert seconds to milliseconds
        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm a"); // the format of your date
        sdf.setTimeZone(TimeZone.getTimeZone(timeZone));
        String formattedDate = sdf.format(date);
        return formattedDate;
    }

    private void setVisibility() {
        TextView visibility = (TextView) findViewById(R.id.visibility);
        try {
            String visi = "";
            if (degree.equals("si")) {
                visi = Math.round(currently.getDouble("visibility")*100.0)/100.0 +" kms";
            } else {
                visi = Math.round(currently.getDouble("visibility")*100.0)/100.0 +" mi";
            }
            visibility.setText(visi);

        } catch (Exception e) {
            visibility.setText("N.A.");
        }
    }

    private void setHumidity() {
        TextView humidity = (TextView) findViewById(R.id.humidity);
        try {
            String humi = Math.round(currently.getDouble("humidity")*100)+"%";
            humidity.setText(humi);
        } catch (Exception e) {
            humidity.setText("N.A.");
        }
    }

    private void setDewPoint() {
        TextView dewPoint = (TextView) findViewById(R.id.dewpoint);
        try {
            String dew_data = Math.round(currently.getDouble("dewPoint"))+unit;
            dewPoint.setText(dew_data);

        } catch(Exception e) {
            dewPoint.setText("N.A.");
        }
    }

    private void setWindSpeed() {
        TextView windSpeed = (TextView) findViewById(R.id.windspeed);

        try {
            String wSpeed = "";
            double wind_data = currently.getDouble("windSpeed");

            if(degree.equals("si")) {
                wSpeed = Math.round(wind_data*100.0)/100.0 + " mpsec";
            }
            else {
                wSpeed = Math.round(wind_data*100.0)/100.0 + " mph";
            }
            windSpeed.setText(wSpeed);
        } catch (Exception e) {
            windSpeed.setText("N.A.");
        }

    }

    private void setRain() {
        TextView precip_rain = (TextView) findViewById(R.id.rain);
        try {
            String pre_rain = "";
            double rain_data = currently.getDouble("precipProbability");
            precip_rain.setText((int) Math.round(rain_data*100) + "%");
        } catch (Exception e) {
            precip_rain.setText("N.A.");
        }

    }

    private void setPrecipitation() {
        TextView precipitatipn = (TextView) findViewById(R.id.precipitation);
        try {
            String precip = "";
            double precip_data = currently.getDouble("precipIntensity");

            if(degree.equals("si")) {
                precip_data /= 25.4;
            }

            //Mapping the precipitation values
            if(precip_data>=0 && precip_data<0.002)
                precip="None";
            if(precip_data>=0.002 && precip_data<0.017)
                precip="Very Light";
            if(precip_data>=0.017 && precip_data<0.1)
                precip="Light";
            if(precip_data>=0.1 && precip_data<0.4)
                precip="Moderate";
            if(precip_data>=0.4)
                precip="Heavy";

            precipitatipn.setText(precip);
        } catch (Exception e) {
            precipitatipn.setText("N.A.");
        }
    }

    private void setMinMax() {
        TextView minMax = (TextView) findViewById(R.id.resultMinMax);
        try {
            String minMaxTemp;
            JSONArray value = daily.getJSONArray("data");
            int minTemp = ((JSONObject)value.get(0)).getInt("temperatureMin");
            int maxTemp = ((JSONObject)value.get(0)).getInt("temperatureMax");
            minMaxTemp = "L:"+ minTemp + (char) 0x00B0 +" | H:"+ maxTemp + (char) 0x00B0;
            minMax.setText(minMaxTemp);
        } catch (Exception e) {
            minMax.setText("N.A.");
        }
    }

    private void setTemperature() {
        TextView temperature = (TextView) findViewById(R.id.temp);
        TextView temperatureUnit = (TextView) findViewById(R.id.unit_temp);
        try {
            int temp = currently.getInt("temperature");
            temperature.setText("" + temp);
            temperatureUnit.setText(unit);

        } catch (Exception e) {
            temperature.setText("N.A.");
            temperatureUnit.setText("N.A.");
        }
    }

    private void setSummary() {
        TextView summary = (TextView) findViewById(R.id.summary);
        try {
            String sum = currently.getString("summary");
            sum += " in "+city+", "+state;
            summary.setText(sum);
        } catch (Exception e) {
            summary.setText("N.A.");
        }
    }

    private void setSummaryImage() {
        try {
            ImageView img_summary = (ImageView) findViewById(R.id.img_summary);
            Context context = img_summary.getContext();
            String icon =mapIcon.get(currently.getString("icon"));
            int id = context.getResources().getIdentifier(icon, "drawable", context.getPackageName());
            img_summary.setImageResource(id);

        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public void FB_Post(View view) {
        if (ShareDialog.canShow(ShareLinkContent.class)) {
            try {
                String icon = geticonURl();
                //String url = "http://cs-server.usc.edu:45678/hw/hw8/images/";
                //String icon = url + mapIcon.get(currently.getString("icon")) + ".png";
                String desc = getDesc();
                // desc = currently.getString("summary") + ", " + currently.getInt("temperature") + unit;
                String title = gettitle();
                //String title = "Current Weather in " + city + ", " + state;
                ShareLinkContent linkContent = new ShareLinkContent.Builder()
                        .setContentTitle(title)
                        .setContentDescription(desc)
                        .setImageUrl(Uri.parse(icon))
                        .setContentUrl(Uri.parse("http://forecast.io"))
                        .build();
                shareDialog.registerCallback(callbackManager, new FacebookCallback<Sharer.Result>() {
                    @Override
                    public void onSuccess(Sharer.Result result) {
                        if(result.getPostId() == null) {
                            Toast.makeText(getApplicationContext(), "Post Cancelled", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getApplicationContext(), "Facebook Post Successful", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancel() {
                        Toast.makeText(getApplicationContext(), "Post Cancelled", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(FacebookException error) {
                        Toast.makeText(getApplicationContext(), "Post Failed", Toast.LENGTH_SHORT).show();
                    }
                });
                shareDialog.show(linkContent);
            } catch (Exception e) {
                //todo
            }
        }
    }

    private String gettitle() {
        try {
            String title = "Current Weather in " + city + ", " + state;
            return title;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private String getDesc() {
        try {
            String desc = currently.getString("summary") + ", " + currently.getInt("temperature") + unit;
            return  desc;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private String geticonURl() {
        try {
            String url = "http://cs-server.usc.edu:45678/hw/hw8/images/";
            String icon = url + mapIcon.get(currently.getString("icon")) + ".png";
            return icon;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    public void MoreDetails(View view) {
        Intent detailsActivity = new Intent(getApplicationContext(),DetailsActivity.class);
        detailsActivity.putExtra("city",city);
        detailsActivity.putExtra("state", state);
        detailsActivity.putExtra("daily",daily.toString());
        detailsActivity.putExtra("hourly", hourly.toString());
        detailsActivity.putExtra("timezone",timeZone);
        detailsActivity.putExtra("unit", unit);   //sends the unit i.e F or C
        startActivity(detailsActivity);
    }

    public void ViewMap(View view) {
        try {
            Intent MapActivity = new Intent(getApplicationContext(),MapActivity.class);
            MapActivity.putExtra("latitude", "" + result.getDouble("latitude"));
            MapActivity.putExtra("longitude", "" + result.getDouble("longitude"));
            startActivity(MapActivity);
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
