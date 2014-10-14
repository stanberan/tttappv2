package uk.ac.abdn.t3.t3v2app;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.AsyncTask;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.gcm.GoogleCloudMessaging;
 
public class AppController extends Application {
 public static String DEV_ID="simbbox003";
	private String registrationID="APA91bHnDj_OEW0F3AzuNvqUnMB9N4HDr341LF4XOdQt3M9i_owjmgFrgdD2S6GobFd92a5I16SztYYYFhO9pa6wGLJZxV7MeOP5mIXMw3BmQpNeBKCFuEdjlIJvd79vxKEqamkz8Q0UJnlpt5kYX-NyKF-yjIGdtQ";
	private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
	public static String TTT_NS="http://t3.abdn.ac.uk/ontologies/t3.owl#";
	//capabilities types
	public static String PDC_TYPE=TTT_NS+"PersonalDataCollection";
	public static String PDG_TYPE=TTT_NS+"PersonalDataGeneration";
	public static String PDU=TTT_NS+"PersonalDataUsage";
	public static String BIL_TYPE=TTT_NS+"BillingCap";
	public static String PDS_TYPE=TTT_NS+"PersonalDataSharing";
	
	public static String HOST="http://crowddata.abdn.ac.uk:8080/";
	
	 public static final String EXTRA_MESSAGE = "message";
	 public static String UID;
	    public static final String PROPERTY_REG_ID = "registration_id";
	    private static final String PROPERTY_APP_VERSION = "10";
	    

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
	
	
	
    public static final String TAG = AppController.class
            .getSimpleName();
 
    private RequestQueue mRequestQueue;
 
    private static AppController mInstance;
 
    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        //get user id
        final TelephonyManager tm = (TelephonyManager) getBaseContext().getSystemService(Context.TELEPHONY_SERVICE);
		 UID=tm.getDeviceId();	
        
        if (checkPlayServices()){
            gcm = GoogleCloudMessaging.getInstance(this);
            regid = getRegistrationId(this);

         
                registerInBackground();
            
        } else {
            Log.i(TAG, "No valid Google Play Services APK found.");
        }
    }
        
        
        
        
  
 
    public static synchronized AppController getInstance() {
  
        return mInstance;
    	
    }
 
    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(getApplicationContext());
           
        }
 
        return mRequestQueue;
    }
 
 //
 
    public <T> void addToRequestQueue(Request<T> req, String tag) {
        // set the default tag if tag is empty
        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        getRequestQueue().add(req);
    }
 
    public <T> void addToRequestQueue(Request<T> req) {
        req.setTag(TAG);
        getRequestQueue().add(req);
    }
 
    public void cancelPendingRequests(Object tag) {
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(tag);
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
    	        //    GooglePlayServicesUtil.getErrorDialog(resultCode, this,
    	      //              PLAY_SERVICES_RESOLUTION_REQUEST).show();
    	        } else {
    	            Log.i(TAG, "This device is not supported.");
    	        //    finish();
    	        }
    	        return false;
    	    }
    	    return true;
    	}
    	 private class TrackStats extends AsyncTask<Void, Void,String> {

    			
    		 private String sendRegistrationIdToBackend(String devid,String gcmid){
    			 
    	
    			 DefaultHttpClient client=new DefaultHttpClient();

    				try {
    				    HttpGet request = new HttpGet("http://t3.abdn.ac.uk:8080/t3v2/1/user/register/"+UID+"/"+gcmid);
    				
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
    			    	
    			        sendRegistrationIdToBackend(UID,regid);

    			        // For this demo: we don't need to send it because the device
    			        // will send upstream messages to a server that echo back the
    			        // message using the 'from' address in the message.

    			        // Persist the regID - no need to register again.
    			      //  storeRegistrationId(context, regid);
    			    } catch (IOException ex) {
    			       Log.e("ERROR", "Error :" + ex.getMessage());
    			        // If there is an error, don't just keep trying to register.
    			        // Require the user to click a button again, or perform
    			        // exponential back-off.
    			return msg;
    			}
    			    return msg;
    			}

    		@Override
    		protected void onPostExecute(String msg) {
    		 //   mDisplay.append(msg + "\n");
    		    storeRegistrationId(AppController.this,regid);
    		    
    		}
    			 
    		 }
    
    	
}