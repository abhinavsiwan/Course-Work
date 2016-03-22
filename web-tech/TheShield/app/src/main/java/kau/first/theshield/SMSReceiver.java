package kau.first.theshield;





import java.io.IOException;
import java.util.List;
import java.util.Locale;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.AudioManager;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;


public class SMSReceiver extends BroadcastReceiver
{
 	 private static final String SMS_RECEIVED = "android.provider.Telephony.SMS_RECEIVED";
     private static final String TAG = "SMSBroadcastReceiver";
     String msgbody ="";
     AudioManager am;
     int k = 0,lov=0,bms=0;
     String lat1,lon1;
     
   
    
  

     @Override
     public void onReceive(final Context context, Intent intent) {
          Log.i(TAG, "Intent recieved: " + intent.getAction());
          final SharedPreferences mobile = context.getSharedPreferences(
                  "passcode", Context.MODE_PRIVATE);
          SharedPreferences pass = context.getSharedPreferences(
                  "password", Context.MODE_PRIVATE);
         
          
     		SharedPreferences get = context.getSharedPreferences("passcode", context.MODE_PRIVATE);
     		String s = get.getString("sound","");
     		String w = get.getString("wifi", "");
     		String l = get.getString("location","");
     		String password = pass.getString("pass", "");
     		String Backup = pass.getString("backupph", "");
     		final String phno;
     		

             if (intent.getAction().equals(SMS_RECEIVED)) {
                 Bundle bundle = intent.getExtras();
                 if (bundle != null) {
                     Object[] pdus = (Object[])bundle.get("pdus");
                     final SmsMessage[] messages = new SmsMessage[pdus.length];
                     for (int i = 0; i < pdus.length; i++) {
                         messages[i] = SmsMessage.createFromPdu((byte[])pdus[i]);
                     }
                     if (messages.length > -1) {
                         Log.i(TAG, "Message recieved: " + messages[0].getMessageBody());
                         Log.i(TAG, "Message recieved: " + messages[0].getOriginatingAddress());
                          phno = messages[0].getOriginatingAddress();
                         Editor editor = mobile.edit();
						 editor.putString("phno", phno);
						 editor.commit();
                         
                         //Toast.makeText(context, "Hai kaushik u got a message", Toast.LENGTH_LONG).show();
                         msgbody = messages[0].getMessageBody();
                     }
                  
                 }
                 int so = msgbody.compareTo(s);
                 if(so==0 && s!="")
                 {
                	
                	AudioManager am =(AudioManager)context.getSystemService(Context.AUDIO_SERVICE);
                	
                	am.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
                	
                	 Log.d("Kaushik","Message contains sound");
                 }
                 Log.d("kaushik", msgbody);
                 Log.d("kaushik", Backup);
                 int z = msgbody.compareTo("BackupPassword");
					if(z==0)
					{
						if(lov==0)
						{
							Log.d("kaushik","It entered Backup");
						String abc = "Your password is : "+"\""+ password+"\"" ; 
						SmsManager.getDefault().sendTextMessage(Backup, null, abc, null, null);
						Log.d("kaushik","Message sent");
						lov++;
						}
						
					}
					int yx = msgbody.compareTo("BackupKeywords");
					if(yx==0)
					{
						if(bms==0)
						{
							Log.d("kaushik","It entered Backup Passcode");
						String abc = "Your passcodes are : "+"\""+"\n"+ "Sound mode on:"+"\""+s+"\n" +"Wi-fi mode on:"+"\""+w+"\""+"\n"+"Current location:"+"\""+l +"\""; 
						SmsManager.getDefault().sendTextMessage(Backup, null, abc, null, null);
						Log.d("kaushik","Message sent");
						bms++;
						}
						
					}
					int wi = msgbody.compareTo(w);
                 if(wi==0 && w!="")
                 {
                	 try
                	 {
                	 WifiManager wifiManager = (WifiManager)context.getSystemService(Context.WIFI_SERVICE);
                	 wifiManager.setWifiEnabled(true);
                	 Log.d("Kaushik","Message contains WiFI-On");
                	 }
                	 catch(Exception ioe)
                	 {
                		 Toast.makeText(context,"Sorry this feature is not supported in this device", Toast.LENGTH_LONG).show();
                		 Log.d("kaushik",ioe.toString());
                	 }
                 }
                 int lom = msgbody.compareTo(l);
                 if(lom==0 && l!="")
                 {
                	 //int io=0;
                	 Log.d("kaushik","It entered if statment1");
                	 LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
                	 Log.d("kaushik","It entered if statment2");
                	 LocationListener locationListener = new LocationListener() {
     					public void onLocationChanged(Location location) {
     						if(k==0)
     						{
     							Log.d("kaushik","It entered if statment3");
     						// Called when a new location is found by the network location provider.
     						//SystemClock.sleep(60000);
     						String lat = Double.toString(location.getLatitude());
     						String lon = Double.toString(location.getLongitude());
     						//tv.setText("Your Location is:" + lat + "--" + lon);
     						Log.d("kaushik","It got latitude and latitude");
     						String address = abc(lat,lon);
     						String finaladdress = "Latitude:"+ lat +" Longitude:"+ lon + address;
     						//String finaladdress = "Latitude:"+ lat +" Longitude:"+ lon;
     						Log.d("kaushik",finaladdress);
     						String mobileno = mobile.getString("phno",null);
     						k++;
     						
     						SmsManager.getDefault().sendTextMessage(mobileno, null, finaladdress, null, null);
     						
     						}
     						
     					}
     					

     					private String abc(String lat, String lon) {
     						Geocoder geocoder = new Geocoder(context , Locale.ENGLISH);
     	               		String ret = "";
     	               		try {
     	               			List<Address> addresses = geocoder.getFromLocation(Double.parseDouble(lat), Double.parseDouble(lon), 1);
     	               			if(addresses != null) {
     	               				Address returnedAddress = addresses.get(0);
     	               				StringBuilder strReturnedAddress = new StringBuilder("Address:\n");
     	               				for(int i=0; i<returnedAddress.getMaxAddressLineIndex(); i++) {
     	               					strReturnedAddress.append(returnedAddress.getAddressLine(i)).append("\n");
     	               				}
     	               				ret = strReturnedAddress.toString();
     	               			}
     	               			else{
     	               				ret = "No Address returned!";
     	               			}
     	               		} catch (IOException e) {
     	               			// TODO Auto-generated catch block
     	               			e.printStackTrace();
     	               			ret = "Can't get Address!";
     	               		}
     	               		return ret;
						}

						public void onStatusChanged(String provider, int status, Bundle extras) {}
     					public void onProviderEnabled(String provider) {}
     					public void onProviderDisabled(String provider) {}
     				};
                	 Log.d("Kaushik","Message contains Location");
                	 locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener); 
                	 if(k==0)
                     {
                    	Log.d("kaushik","Secondary location");
                    	Criteria criteria = new Criteria();
                    	String bestProvider = locationManager.getBestProvider(criteria, false);
                    	Location location = locationManager.getLastKnownLocation(bestProvider);
                    	LocationListener loc_listener = new LocationListener() {

                    		public void onLocationChanged(Location l) {}

                    		public void onProviderEnabled(String p) {}

                    		public void onProviderDisabled(String p) {}

                    		public void onStatusChanged(String p, int status, Bundle extras) {}
                    	};
                    	locationManager.requestLocationUpdates(bestProvider, 0, 0, loc_listener);
                    	location = locationManager.getLastKnownLocation(bestProvider);
                    	locationManager.requestLocationUpdates(bestProvider, 0, 0, loc_listener);
                    	location = locationManager.getLastKnownLocation(bestProvider);
                    	try {
                    			lat1 = Double.toString(location.getLatitude());
                    			lon1 = Double.toString(location.getLongitude());
                    			
     						Geocoder geocoder = new Geocoder(context , Locale.ENGLISH);
     	               		String ret = "";
     	               		try {
     	               			List<Address> addresses = geocoder.getFromLocation(Double.parseDouble(lat1), Double.parseDouble(lon1), 1);
     	               			if(addresses != null) {
     	               				Address returnedAddress = addresses.get(0);
     	               				StringBuilder strReturnedAddress = new StringBuilder("Address:\n");
     	               				for(int i=0; i<returnedAddress.getMaxAddressLineIndex(); i++) {
     	               					strReturnedAddress.append(returnedAddress.getAddressLine(i)).append("\n");
     	               				}
     	               				ret = strReturnedAddress.toString();
     	               			}
     	               			else{
     	               				ret = "No Address returned!";
     	               			}
     	               		} catch (IOException e) {
     	               			// TODO Auto-generated catch block
     	               			e.printStackTrace();
     	               			ret = "Can't get Address!";
     	               		}
     	               	String finaladdress1 = "This may not be accurate as it is ur Last known address:"+"Latitude:"+ lat1 +" Longitude:"+ lon1 + ret;
     	               String mobileno1 = mobile.getString("phno",null);
     	               Log.d("kaushik","address sent");
     	               SmsManager.getDefault().sendTextMessage(mobileno1, null, finaladdress1, null, null);
						
                    	} catch (NullPointerException e) {
                    		lat1 = "";
                    		lon1 = "";
                    		 String mobileno2 = mobile.getString("phno",null);
                    		  SmsManager.getDefault().sendTextMessage(mobileno2, null, "Sorry cannot get your address", null, null);
                    	
                    	} //
                    	
                    	
                     }
                	 
                 }
                
             
             }
                 
             }
             
            
          
    

    
     
 }