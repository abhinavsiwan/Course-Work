package kau.first.theshield;

import android.app.Activity;
import android.app.AlertDialog;

import android.content.DialogInterface;
import android.content.Intent;

import android.os.Bundle;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;




public class Helper extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main2);
		Button passcode = (Button)findViewById(R.id.button1);
		Button password = (Button)findViewById(R.id.button2);
		/*final ToggleButton tg = (ToggleButton)findViewById(R.id.tb1);
		final SMSReceiver k = new SMSReceiver();
		final IntentFilter filter = new IntentFilter();
		filter.addAction("android.provider.Telephony.SMS_RECEIVED");*/
		
		passcode.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent int1 = new Intent(Helper.this,Passcode.class);
				startActivity(int1);
				
			}
		});
		
		password.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent kau17 = new Intent(Helper.this,ResetPassword.class);
				startActivity(kau17);
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
	        	AlertDialog.Builder ad = new AlertDialog.Builder(Helper.this);
				ad.setCancelable(false);
				ad.setTitle("About this application");
				ad.setMessage("Friends!!Thanks for downloading this app.This is my first app in GooglePlay,Hope you like it.\nPlease give a rating and recommend your friends if u like it.\nPlease provide your Valuable Feedback and where to improve by Comments in Google Play or E-mail me at:\nkaushiks.feedback@gmail.com");
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
	        	Intent n = new Intent(Helper.this,Menuhelp.class);
	        	startActivity(n);
	        }
	            
	        default:
	            return super.onOptionsItemSelected(item);
	        }
	    }
	
	


}
