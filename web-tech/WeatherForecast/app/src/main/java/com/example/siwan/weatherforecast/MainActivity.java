package com.example.siwan.weatherforecast;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;


public class MainActivity extends AppCompatActivity {

    public final static String EXTRA_MESSAGE = "com.example.siwan.weatherforecast.MESSAGE";

    private RadioGroup radioTempGroup;
    private RadioButton radioTempButton;
    private Button btnSearch;

    private ImageView img;

    private EditText textStreet;
    private EditText textCity;
    private Spinner spinnerState;

    private TextView error;
    String jsonResult="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textStreet = (EditText) findViewById(R.id.etStreet);
        textCity = (EditText) findViewById(R.id.etCity);
        spinnerState = (Spinner) findViewById(R.id.StateSpinner);
        radioTempGroup = (RadioGroup) findViewById(R.id.radioTemp);
        error = (TextView) findViewById(R.id.etError);
        btnSearch = (Button) findViewById(R.id.btSearch);

        //Attach a click Listener for Forecast.io
        addListenerOnImage();

    }

    //Click Listener for Forecast.io Image
    private void addListenerOnImage() {
        img = (ImageView) findViewById(R.id.ivImage);
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.addCategory(Intent.CATEGORY_BROWSABLE);
                intent.setData(Uri.parse("http://forecast.io/"));
                startActivity(intent);
            }
        });
    }

    //Creates an Intent and send to DisplayAboutInfo activity
    public void sendInfo(View view) {
        Intent intent = new Intent(this, DisplayAboutInfo.class);
        startActivity(intent);
    }

    //Clear the Form - Called when the user clicks the Clear Button
    public void clearForm(View view) {
        textStreet.setText("");
        textCity.setText("");
        spinnerState.setSelection(0);
        radioTempGroup.check(R.id.rbFah);
        error.setText("");
    }

    //Validation - Called when the user clicks the Search Button
    public void submitForm(View view) {
        String address = "";
        String city = "";
        String stat = "";
        String degree = "";
        //variable to get the return value after validation
        boolean resultValidate;

        degree = getDegree();
        /*
        int selectedId = radioTempGroup.getCheckedRadioButtonId();
        radioTempButton = (RadioButton) findViewById(selectedId);
        String deg = (String) radioTempButton.getText();
        if(deg.toString().equals("Fahrenheit")) {
            degree = "us";
        }
        if(deg.toString().equals("Celsius")) {
            degree = "si";
        }
        */
        address = String.valueOf(textStreet.getText()).trim();
        city = String.valueOf(textCity.getText()).trim();

        stat = spinnerState.getSelectedItem().toString();
        int state_spin = (int) spinnerState.getSelectedItemId();

        String submit = String.valueOf(btnSearch.getText());

        resultValidate = validate(address, city, state_spin);

        if (resultValidate) {
            error.setText("");
            /*
            Toast.makeText(getApplicationContext(), address, Toast.LENGTH_SHORT).show();
            Toast.makeText(MainActivity.this, city, Toast.LENGTH_SHORT).show();
            Toast.makeText(MainActivity.this, stat, Toast.LENGTH_SHORT).show();
            Toast.makeText(MainActivity.this, degree, Toast.LENGTH_SHORT).show();
            Toast.makeText(MainActivity.this, submit, Toast.LENGTH_SHORT).show();
            */

            // When user clicks the search button, calls AsyncTask.
            // Before attempting to fetch the URL, makes sure that there is a network connection.
            ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
            if(networkInfo != null && networkInfo.isConnected())
            {
                //fetch JSON data
                new GETJSONTask().execute(address,city,stat,degree,submit);
            }
            else
            {
                //display error
                error.setText("Network Connectivity not available !!!");
            }
        }

    }

    private String getDegreeUnit() {
        String degreeUnit = "";
        int selectedId = radioTempGroup.getCheckedRadioButtonId();
        radioTempButton = (RadioButton) findViewById(selectedId);
        String deg = (String) radioTempButton.getText();
        if(deg.toString().equals("Fahrenheit")) {
            degreeUnit = "\u2109";
        }
        if(deg.toString().equals("Celsius")) {
            degreeUnit = "\u2103";
        }

        return degreeUnit;
    }


    private String getDegree() {
        String degree = "";
        int selectedId = radioTempGroup.getCheckedRadioButtonId();
        radioTempButton = (RadioButton) findViewById(selectedId);
        String deg = (String) radioTempButton.getText();
        if(deg.toString().equals("Fahrenheit")) {
            degree = "us";
        }
        if(deg.toString().equals("Celsius")) {
            degree = "si";
        }

        return degree;
    }

    private class GETJSONTask extends AsyncTask<String, Void, String> {
        //private ProgressDialog progressDialog = new ProgressDialog(MainActivity.this);
        String url = "http://usc2015-env.elasticbeanstalk.com/?address=";
        String mTAG = "myAsyncTask";

        protected String doInBackground(String... urls) {
            Log.d(mTAG, "Background Work in Progress");
            try {
                url=url+ URLEncoder.encode(urls[0], "UTF-8");
                url=url+"&city=";
                url=url+URLEncoder.encode(urls[1], "UTF-8");
                url=url+"&state=";
                url=url+URLEncoder.encode(urls[2], "UTF-8");
                url=url+"&degree=";
                url=url+URLEncoder.encode(urls[3], "UTF-8");
                url=url+"&submit=";
                url=url+URLEncoder.encode(urls[4], "UTF-8");
                return downloadUrl(url);
            } catch (IOException e) {
                return "Unable to retrieve web page. URL may be invalid.";
            }
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            try {
                //JSONObject results = new JSONObject(result);
                Intent resultActivity = new Intent(getApplicationContext(),ResultActivity.class);
                resultActivity.putExtra("result", result);
                resultActivity.putExtra("state", spinnerState.getSelectedItem().toString());
                resultActivity.putExtra("city", textCity.getText().toString().trim());
                resultActivity.putExtra("degree", getDegree());
                resultActivity.putExtra("unit", getDegreeUnit());
                startActivity(resultActivity);
            } catch(Exception e) {
                e.printStackTrace();
            }
        }

        private String downloadUrl(String myurl) throws IOException {
            InputStream is = null;
            // Only display the first 500 characters of the retrieved
            // web page content.
            //int len = 500;
            try {
                URL url = new URL(myurl);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(10000 /* milliseconds */);
                conn.setConnectTimeout(15000 /* milliseconds */);
                conn.setRequestMethod("GET");
                conn.setDoInput(true);
                // Starts the query
                conn.connect();
                int response = conn.getResponseCode();
                Log.d(mTAG, "The response is: " + response);
                is = conn.getInputStream();

                // Convert the InputStream into a string
                String contentAsString = readIt(is);
                return contentAsString;

                // Makes sure that the InputStream is closed after the app is
                // finished using it.
            } finally {
                if (is != null) {
                    is.close();
                }
            }
        }
        public String readIt(InputStream stream) throws IOException, UnsupportedEncodingException {
            Reader reader = null;
            reader = new InputStreamReader(stream, "UTF-8");
            StringBuilder output = new StringBuilder();
            char[] buffer = new char[1000];
            for(;;) {
                int abh = reader.read(buffer, 0, buffer.length);
                if(abh < 0)
                    break;
                output.append(buffer, 0, abh);
            }
            return output.toString();
        }
    }

    //Called when validate function is called from SubmitForm
    public boolean validate(String address, String city, int state_spin) {
        error.setText("");
        if (address.isEmpty()) {
            error.setText("Please enter a Street Address");
            return false;
        }
        if (city.isEmpty()) {
            error.setText("Please enter a City");
            return false;
        }
        if (state_spin == 0) {
            error.setText("Please select a State");
            return false;
        }
        return true;
    }
}
