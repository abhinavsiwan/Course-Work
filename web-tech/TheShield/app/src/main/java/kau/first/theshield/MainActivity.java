package kau.first.theshield;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
	@Override
	protected void onStop() {
		finish();
		super.onStop();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		final EditText e1 = (EditText)findViewById(R.id.editText1);
		final EditText e2 = (EditText)findViewById(R.id.editText2);
		final EditText ema = (EditText)findViewById(R.id.e3);
		final TextView t2 = (TextView)findViewById(R.id.t2);
		final TextView t3 = (TextView)findViewById(R.id.textView2);
		e1.setText("");
		
		final Button b = (Button)findViewById(R.id.b1);
		SharedPreferences first = getApplicationContext().getSharedPreferences(
                "password", MODE_PRIVATE);
		 
		final String a = first.getString("pass","");
		if(a=="")
		{
			//Toast.makeText(getApplicationContext(), "Empty box", Toast.LENGTH_LONG).show();
		}
		
		else 
		{
			//Toast.makeText(getApplicationContext(), a, Toast.LENGTH_LONG).show();
			//e.setText(a);
			t2.setVisibility(View.GONE);
			t3.setVisibility(View.GONE);
			b.setText("Login");
			e2.setVisibility(View.GONE);
			ema.setVisibility(View.GONE);
		}


b.setOnClickListener(new View.OnClickListener() {
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		SharedPreferences pref = getApplicationContext().getSharedPreferences(
                "password", MODE_PRIVATE);
		String ma = ema.getText().toString().trim();
		String c = e1.getText().toString().trim();
		String d = e2.getText().toString().trim();
		String z = pref.getString("pass", "");
		int x =c.compareTo(z);
		int y =c.compareTo(d);
		if(a=="" && c!="")
		{
		if(ma.length()>7 )
		{
		 if(y==0)
		 {
			
		 
		 Editor editor = pref.edit();
		 String s = e1.getText().toString();
		 editor.putString("pass", s);
		 editor.putString("backupph", ma);
		 editor.commit();
		 Toast.makeText(getApplicationContext(), "You have successfully created password ", Toast.LENGTH_LONG).show();
		 Intent kau2 = new Intent(MainActivity.this,Helper.class);
			startActivity(kau2);
		 
			  }
			  else 
				  Toast.makeText(getApplicationContext(), "Your passwords doesnt match", Toast.LENGTH_LONG).show();
		}
		else
			Toast.makeText(getApplicationContext(), "Please Enter a valid Backup number", Toast.LENGTH_LONG).show();
		}
		 		
		else if(x==0)
		{
			Toast.makeText(getApplicationContext(), "Logged in successfully", Toast.LENGTH_LONG).show();
			Intent kau = new Intent(MainActivity.this,Helper.class);
			startActivity(kau);
		}
		else 
			Toast.makeText(getApplicationContext(), "Wrong Password",Toast.LENGTH_LONG).show();
		
	}
});

}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		MenuInflater mi = getMenuInflater();
		mi.inflate(R.layout.menu,menu);
		return true;
		
	}
	
		@Override
	    public boolean onOptionsItemSelected(MenuItem item)
	    {
	         
	        switch (item.getItemId())
	        {
	        case R.id.menu_about:
	        {
	        	AlertDialog.Builder ad = new AlertDialog.Builder(MainActivity.this);
				ad.setCancelable(false);
				ad.setTitle("About this application");
				ad.setMessage("Friends!!Thanks for downloading this app.This is my first app in GooglePlay,Hope you like it.\nPlease give a rating and recommend your friends if u like it.\nPlease provide your Valuable Feedback and where to improve by Comments in Google Play or E-mail me at:kaushiks.\nfeedback@gmail.com");
				ad.setIcon(R.drawable.about);
				 ad.setPositiveButton("OK", new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
						dialog.cancel();								
								
							}	
		 						
					});
				 AlertDialog alertDialog1 = ad.create();
				 alertDialog1.show();
	            return true;
	        }
	        case R.id.menu_help:
	        {
	        	Intent n = new Intent(MainActivity.this,Menuhelp.class);
	        	startActivity(n);
	        }
	            
	        default:
	            return super.onOptionsItemSelected(item);
	        }
	    }
}
