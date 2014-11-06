package uk.ac.abdn.t3.t3v2app;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import uk.ac.abdn.t3.t3v2app.R;
import uk.ac.abdn.t3.t3v2app.R.layout;
import uk.ac.abdn.t3.t3v2app.R.menu;
import uk.ac.abdn.t3.t3v2app.fragments.DeviceDescriptionFragment;

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
import android.support.v7.app.ActionBarActivity;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Menu;
import android.widget.TextView;

public class MainActivity extends ActionBarActivity {
private String registrationID="APA91bHnDj_OEW0F3AzuNvqUnMB9N4HDr341LF4XOdQt3M9i_owjmgFrgdD2S6GobFd92a5I16SztYYYFhO9pa6wGLJZxV7MeOP5mIXMw3BmQpNeBKCFuEdjlIJvd79vxKEqamkz8Q0UJnlpt5kYX-NyKF-yjIGdtQ";
	
JSONObject j;
TextView output;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		//personalDataLayout=(LinearLayout)findViewById(R.id.personalData_container);
		//companyLayout=(LinearLayout)findViewById(R.id.companies_container);
	
	//	rotate.setVisibility(View.GONE);
		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
			.add(R.id.scan_tag_frag_holder, new ScanATagFragment()).commit();
		}
		
		

	}


	/**
	 * @return Application's {@code SharedPreferences}.
	 */
	

	protected void onResume(){
		super.onResume();
		
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	
	/**
	 * Check the device to make sure it has the Google Play Services APK. If
	 * it doesn't, display a dialog that allows users to download the APK from
	 * the Google Play Store or enable it in the device's system settings.
	 */
	
			 
		 }
	
	
	


