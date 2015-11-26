package com.example.siwan.weatherforecast;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    //public final static String EXTRA_MESSAGE = "com.example.siwan.weatherforecast.MESSAGE";
    private String[] states;
    private Spinner spinner;

    private RadioGroup radioTempGroup;
    private RadioButton radioTempButton;
    private Button btnSearch;

    private ImageView img;

    private EditText textStreet;
    private EditText textCity;

    private TextView ErrorStreet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Attach a click Listener for Drop-Down Menu - Spinner
        addListenerOnSpinner();

        //Attach a click Listener for Forecast.io
        addListenerOnImage();

    }

    //Click Listener for Drop-Down Menu - Spinner
    private void addListenerOnSpinner() {
        states = getResources().getStringArray(R.array.state_list);
        spinner = (Spinner) findViewById(R.id.StateSpinner);

        ArrayAdapter<String> dataAdadpter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,states);
        dataAdadpter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(dataAdadpter);

        //Check if this code is required
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //int item = spinner.getSelectedItemPosition();
                //String text = spinner.getSelectedItem().toString();
                //Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
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

    //Called when the user clicks the ABOUT button
    //Creates an Intent and send to DisplayAboutInfo activity
    public void sendInfo(View view) {
        Intent intent = new Intent(this,DisplayAboutInfo.class);
        //textStreet = (EditText) findViewById(R.id.etStreet);
        //String message = textStreet.getText().toString();
        //intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);
    }

    //Called when the user clicks the Clear Button
    public void clearForm(View view) {
        //clears the Street editText
        textStreet = (EditText) findViewById(R.id.etStreet);
        textStreet.setText("");

        //clears the City editeText
        textCity = (EditText) findViewById(R.id.etCity);
        textCity.setText("");

        //set the State Spinner to default
        addListenerOnSpinner();

        //set the Radio Button to default
        radioTempGroup = (RadioGroup) findViewById(R.id.radioTemp);
        radioTempGroup.check(R.id.rbFah);

        //Clears the Error TextView Box
        ErrorStreet = (TextView) findViewById(R.id.etError);
        ErrorStreet.setVisibility(View.INVISIBLE);
    }

    //Called when the user clicks the Search Button
    public void submitForm(View view) {
            String address = "";
            String city = "";
            String state = "";

            //variable to get the return value after validation
            boolean resultValidate;

            //get the value of radio button
            radioTempGroup = (RadioGroup) findViewById(R.id.radioTemp);
            //get selected button from radioGroup
            int selectedId =  radioTempGroup.getCheckedRadioButtonId();
            //find the radio button by returned id
            radioTempButton = (RadioButton) findViewById(selectedId);
            String degree = (String) radioTempButton.getText();

            //get the value of Street edittext
            textStreet = (EditText) findViewById(R.id.etStreet);
            address = String.valueOf(textStreet.getText()).trim();

            //get the value of city edittext
            textCity = (EditText) findViewById(R.id.etCity);
            city = String.valueOf(textCity.getText()).trim();

            //get the value of State spinner
            spinner=(Spinner) findViewById(R.id.StateSpinner);
            state = spinner.getSelectedItem().toString();
            int state_spin = (int) spinner.getSelectedItemId();

            //get the value of SEARCH button
            btnSearch = (Button) findViewById(R.id.btSearch);
            String submit = String.valueOf(btnSearch.getText());

            resultValidate = validate(address, city, state_spin);

            if(resultValidate) {
                ErrorStreet.setVisibility(View.INVISIBLE);
                Toast.makeText(getApplicationContext(), address, Toast.LENGTH_SHORT).show();
                Toast.makeText(MainActivity.this, city, Toast.LENGTH_SHORT).show();
                Toast.makeText(MainActivity.this, state, Toast.LENGTH_SHORT).show();
                Toast.makeText(MainActivity.this, degree, Toast.LENGTH_SHORT).show();
                Toast.makeText(MainActivity.this, submit, Toast.LENGTH_SHORT).show();
            }

    }

    //Called when validate function is called from SubmitForm
    public boolean validate(String address, String city, int state_spin) {
        boolean address_fill = false;
        boolean city_fill = false;
        boolean state_fill = false;

        String errorText ="Please Enter a ";
        if(!address.equals(""))
        {
            address_fill = true;
        }
        if(!city.equals(""))
        {
            city_fill = true;
        }
        if(state_spin !=0)
        {
            state_fill = true;
        }

        if(!address_fill)
        {
            errorText += "street address";
        }

        if(!city_fill)
        {
            if(address_fill) {
                errorText += "City";
            }
            else {
                errorText += "";
            }
        }

        if(!state_fill)
        {
            if(address_fill && city_fill)
            {
                errorText += "State";
            }
            else
            {
                errorText += "";
            }
        }
        if(!address_fill || !city_fill || !state_fill)
        {
            ErrorStreet = (TextView) findViewById(R.id.etError);
            ErrorStreet.setVisibility(View.VISIBLE);
            ErrorStreet.setText(errorText);
            return false;
        }
        if(address_fill && city_fill && state_fill)
        {
            return true;
        }
        return Boolean.parseBoolean(null);
    }
}
