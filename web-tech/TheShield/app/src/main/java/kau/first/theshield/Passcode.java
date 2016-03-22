package kau.first.theshield;





import android.app.Activity;
import android.app.AlertDialog;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Passcode extends Activity   {
	
	@Override
	protected void onStop() {
		finish();
		super.onStop();
	}


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.passcode);
		//Button back = (Button)findViewById(R.id.b4);
		Button smode = (Button)findViewById(R.id.b3);
		Button wimode = (Button)findViewById(R.id.b1);
		Button gmode = (Button)findViewById(R.id.b2);
		final SharedPreferences pass = getApplicationContext().getSharedPreferences(
                "passcode", MODE_PRIVATE);
		
		final TextView smt = (TextView)findViewById(R.id.smt);
		smt.setText(pass.getString("sound",""));
		final TextView wit = (TextView)findViewById(R.id.textView4);
		wit.setText(pass.getString("wifi", ""));
		final TextView gt = (TextView)findViewById(R.id.textView2);
		gt.setText(pass.getString("location", ""));
		
		/*back.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent n = new Intent(getApplicationContext(),MainActivity.class);
				startActivity(n);
			}
		});*/
		smode.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				AlertDialog.Builder ad = new AlertDialog.Builder(Passcode.this);
				ad.setCancelable(false);
				ad.setTitle("Passcode for Silent mode-Off");
				ad.setMessage("Enter the Passcode:");
				LayoutInflater inflater1=(LayoutInflater)getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				final View v1 = inflater1.inflate(R.layout.popout, null);
				ad.setView(v1);	
				final EditText et = (EditText)v1.findViewById(R.id.et1);
				et.setHint("Enter the Passcode");
				 ad.setPositiveButton("Save", new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface dialog, int which) {
								
										String s = et.getText().toString().trim();
										smt.setText(s);
										Toast.makeText(Passcode.this, s, Toast.LENGTH_SHORT).show();
										 Editor editor = pass.edit();
										 editor.putString("sound", s);
										 editor.commit();
										dialog.cancel();
										
										
									}	
				 
								
							});
							ad.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface dialog, int which) {
					
									Toast.makeText(Passcode.this, "Cancel", Toast.LENGTH_SHORT).show();
									dialog.cancel();
									
								}
							}); 
				AlertDialog alertDialog = ad.create();
				alertDialog.show(); 
			}
		});
		wimode.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				AlertDialog.Builder ad2 = new AlertDialog.Builder(Passcode.this);
				ad2.setCancelable(false);
				ad2.setTitle("Passcode for Wi-Fi On");
				ad2.setMessage("Enter the Passcode:");
				LayoutInflater inflater1=(LayoutInflater)getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				final View v1 = inflater1.inflate(R.layout.popout, null);
				ad2.setView(v1);	
				final EditText et = (EditText)v1.findViewById(R.id.et1);
				et.setHint("Enter the Passcode");
				 ad2.setPositiveButton("Save", new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface dialog, int which) {
								
										String w = et.getText().toString().trim();
										wit.setText(w);
										Editor editor = pass.edit();
										 editor.putString("wifi", w);
										 editor.commit();
										Toast.makeText(Passcode.this, w, Toast.LENGTH_SHORT).show();
										dialog.cancel();
									}	
				 
								
							});
							ad2.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface dialog, int which) {
					
									Toast.makeText(Passcode.this, "Cancel", Toast.LENGTH_SHORT).show();
									dialog.cancel();
								}
							}); 
				AlertDialog alertDialog2 = ad2.create();
				alertDialog2.show(); 
			}
		});
		gmode.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				AlertDialog.Builder ad3 = new AlertDialog.Builder(Passcode.this);
				ad3.setCancelable(false);
				ad3.setTitle("Passcode for GPS location");
				ad3.setMessage("Enter the Passcode:");
				LayoutInflater inflater1=(LayoutInflater)getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				final View v1 = inflater1.inflate(R.layout.popout, null);
				ad3.setView(v1);	
				final EditText et = (EditText)v1.findViewById(R.id.et1);
				et.setHint("Enter the Passcode");
				 ad3.setPositiveButton("Save", new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface dialog, int which) {
								
										String l = et.getText().toString().trim();
										gt.setText(l);
										Toast.makeText(Passcode.this, l, Toast.LENGTH_SHORT).show();
										Editor editor = pass.edit();
										 editor.putString("location", l);
										 editor.commit();
										dialog.cancel();
									}	
				 
								
							});
							ad3.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface dialog, int which) {
					
									Toast.makeText(Passcode.this, "Cancel", Toast.LENGTH_SHORT).show();
									dialog.cancel();
								}
							}); 
				AlertDialog alertDialog3 = ad3.create();
				alertDialog3.show(); 
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
	        	AlertDialog.Builder ad = new AlertDialog.Builder(Passcode.this);
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
	        	Intent n = new Intent(Passcode.this,Menuhelp.class);
	        	startActivity(n);
	        }
	            
	        default:
	            return super.onOptionsItemSelected(item);
	        }
	    }
}
