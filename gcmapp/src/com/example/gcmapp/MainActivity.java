package com.example.gcmapp;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.gcm.GoogleCloudMessaging;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Menu;
import android.widget.TextView;

public class MainActivity extends Activity {
private String registrationID="APA91bHnDj_OEW0F3AzuNvqUnMB9N4HDr341LF4XOdQt3M9i_owjmgFrgdD2S6GobFd92a5I16SztYYYFhO9pa6wGLJZxV7MeOP5mIXMw3BmQpNeBKCFuEdjlIJvd79vxKEqamkz8Q0UJnlpt5kYX-NyKF-yjIGdtQ";
	private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
	private static final String TAG = "Main Activity";

	 public static final String EXTRA_MESSAGE = "message";
	    public static final String PROPERTY_REG_ID = "registration_id";
	    private static final String PROPERTY_APP_VERSION = "1";

	    /**
	     * Substitute you own sender ID here. This is the project number you got
	     * from the API Console, as described in "Getting Started."
	     */
	    String SENDER_ID = "926771893499";
	    TextView mDisplay;
	    GoogleCloudMessaging gcm;
	    AtomicInteger msgId = new AtomicInteger();
	    SharedPreferences prefs;
	    Context context;

	   String regid;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		if (checkPlayServices()){
		            gcm = GoogleCloudMessaging.getInstance(this);
		            regid = getRegistrationId(context);

		            if (regid.isEmpty()) {
		                registerInBackground();
		            }
		        } else {
		            Log.i(TAG, "No valid Google Play Services APK found.");
		        }
		    }


private void registerInBackground(){
	new TrackStats().execute();
}
	private String getRegistrationId(Context context) {
	    final SharedPreferences prefs = getGCMPreferences(context);
	    String registrationId = prefs.getString(PROPERTY_REG_ID, "");
	    if (registrationId.isEmpty()) {
	        Log.i(TAG, "Registration not found.");
	        return "";
	    }
	    // Check if app was updated; if so, it must clear the registration ID
	    // since the existing regID is not guaranteed to work with the new
	    // app version.
	    int registeredVersion = prefs.getInt(PROPERTY_APP_VERSION, Integer.MIN_VALUE);
	    int currentVersion = getAppVersion(context);
	    if (registeredVersion != currentVersion) {
	        Log.i(TAG, "App version changed.");
	        return "";
	    }
	    return registrationId;
	}

	/**
	 * @return Application's {@code SharedPreferences}.
	 */
	private SharedPreferences getGCMPreferences(Context context) {
	    // This sample app persists the registration ID in shared preferences, but
	    // how you store the regID in your app is up to you.
	    return getSharedPreferences(MainActivity.class.getSimpleName(),
	            Context.MODE_PRIVATE);
	}
	
	private static int getAppVersion(Context context) {
	    try {
	        PackageInfo packageInfo = context.getPackageManager()
	                .getPackageInfo(context.getPackageName(), 0);
	        return packageInfo.versionCode;
	    } catch (NameNotFoundException e) {
	        // should never happen
	        throw new RuntimeException("Could not get package name: " + e);
	    }
	}

	protected void onResume(){
		super.onResume();
		checkPlayServices();
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	private void storeRegistrationId(Context context, String regId) {
	    final SharedPreferences prefs = getGCMPreferences(context);
	    int appVersion = getAppVersion(context);
	    Log.i(TAG, "Saving regId on app version " + appVersion);
	    SharedPreferences.Editor editor = prefs.edit();
	    editor.putString(PROPERTY_REG_ID, regId);
	    editor.putInt(PROPERTY_APP_VERSION, appVersion);
	    editor.commit();
	}
	
	/**
	 * Check the device to make sure it has the Google Play Services APK. If
	 * it doesn't, display a dialog that allows users to download the APK from
	 * the Google Play Store or enable it in the device's system settings.
	 */
	private boolean checkPlayServices() {
	    int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
	    if (resultCode != ConnectionResult.SUCCESS) {
	        if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
	            GooglePlayServicesUtil.getErrorDialog(resultCode, this,
	                    PLAY_SERVICES_RESOLUTION_REQUEST).show();
	        } else {
	            Log.i(TAG, "This device is not supported.");
	            finish();
	        }
	        return false;
	    }
	    return true;
	}
	 private class TrackStats extends AsyncTask<Void, Void,String> {

			
		 private String sendRegistrationIdToBackend(String devid,String gcmid){
			 
	
			 DefaultHttpClient client=new DefaultHttpClient();

				try {
				    HttpGet request = new HttpGet("http://t3.abdn.ac.uk:8080/t3v2/1/user/register/"+devid+"/"+gcmid);
				
				   HttpResponse resp= client.execute(request);
				   if(resp.getEntity()!=null){
					   Log.e("Entity",EntityUtils.toString(resp.getEntity()));
				   }
				  return "StatusCode: "+ resp.getStatusLine().getStatusCode();
			
				} catch (Exception ex) {
				   ex.printStackTrace();
				   return ex.getMessage();
				} finally {
				 // client.close();
				}
		
		 }
		 
		 
			@Override
			protected String doInBackground(Void... params) {
				String msg = "";
			    try {
			        if (gcm == null) {
			            gcm = GoogleCloudMessaging.getInstance(context);
			        }
			        regid = gcm.register(SENDER_ID);
			        msg = "Device registered, registration ID=" + regid;
			        Log.e("Registered",regid);
			        // You should send the registration ID to your server over HTTP,
			        // so it can use GCM/HTTP or CCS to send messages to your app.
			        // The request to your server should be authenticated if your app
			        // is using accounts.
			    	final TelephonyManager tm = (TelephonyManager) MainActivity.this.getBaseContext().getSystemService(Context.TELEPHONY_SERVICE);
					 String devid=tm.getDeviceId();	
			        sendRegistrationIdToBackend(devid,regid);

			        // For this demo: we don't need to send it because the device
			        // will send upstream messages to a server that echo back the
			        // message using the 'from' address in the message.

			        // Persist the regID - no need to register again.
			      //  storeRegistrationId(context, regid);
			    } catch (IOException ex) {
			        msg = "Error :" + ex.getMessage();
			        // If there is an error, don't just keep trying to register.
			        // Require the user to click a button again, or perform
			        // exponential back-off.
			return msg;
			}
			    return msg;
			}

		@Override
		protected void onPostExecute(String msg) {
		    mDisplay.append(msg + "\n");
		    storeRegistrationId(context,regid);
		    
		}
			 
		 }
	
	
	

}
