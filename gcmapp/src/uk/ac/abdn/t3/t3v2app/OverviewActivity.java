package uk.ac.abdn.t3.t3v2app;

import org.json.JSONArray;

import uk.ac.abdn.t3.t3v2app.fragments.CapabilityFragment;

import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;
import android.provider.Settings;

public class OverviewActivity extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_overview);

		
		
		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new CapabilityFragment()).commit();
		}
		
		
		String devid=getIntent().getStringExtra("devid");
		String caller=getIntent().getStringExtra("caller");
		Log.e("Tag", devid+caller);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.overview, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	public void onResume(){
		super.onResume();
	}
	
	public void getPersonalData(View v){
		Log.e("PDC", "CLICKED");
		String tag_json_arry = "json_array_req";
		 String devid="simbbox001";
		String url = "http://t3.abdn.ac.uk:8080/t3v2/1/device/"+devid+"/personaldata/all";
		     
		
	//	StringRequest stringRequest = new StringRequest(Method.POST,
	 //           url, this, this) ;
		
		
		final ProgressDialog pDialog = new ProgressDialog(this);
		pDialog.setMessage("Loading...Personal Data");
		pDialog.show();     
		         
		JsonArrayRequest req = new JsonArrayRequest(url,
		                new Response.Listener<JSONArray>() {
		                    @Override
		                    public void onResponse(JSONArray response) {
		                        Log.d("PDC", response.toString());   
		                        populatePDCView(response);
		                       
		                        pDialog.hide();             
		                    }
		                }, new Response.ErrorListener() {
		                    @Override
		                    public void onErrorResponse(VolleyError error) {
		                        VolleyLog.d("PDC", "Error: " + error.getMessage());
		                        pDialog.hide();
		                    }
		                });
		 
		// Adding request to request queue
		AppController.getInstance().addToRequestQueue(req, tag_json_arry);
	}

	public void populatePDCView(JSONArray response){
		
		
	}
}
