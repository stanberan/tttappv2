package uk.ac.abdn.t3.t3v2app;

import org.json.JSONArray;
import org.json.JSONObject;

import uk.ac.abdn.t3.t3v2app.fragments.CapabilityFragment;

import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.squareup.picasso.Picasso;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.app.ActionBar.LayoutParams;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.os.Build;
import android.provider.Settings;

public class OverviewActivity extends ActionBarActivity {
LinearLayout personalDataLayout;
LinearLayout companyLayout;


JSONArray personalData=null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_overview);
		personalDataLayout=(LinearLayout)findViewById(R.id.personalData_container);
		companyLayout=(LinearLayout)findViewById(R.id.companies_container);
		
		
		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new CapabilityFragment()).commit();
		}
		
		
		String devid=getIntent().getStringExtra("devid");
		String caller=getIntent().getStringExtra("caller");
		Log.e("Tag", devid+caller);
		getPersonalData(null);
		getCompanies();
		
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
	public void getCompanies(){
		
		String tag_json_arry = "json_array_req";
		String url = "http://t3.abdn.ac.uk:8080/t3v2/1/device/"+AppController.DEV_ID+"/companies";
		JsonArrayRequest req = new JsonArrayRequest(url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d("PDC", response.toString());   
                        populateCompanyView(response);
                       
                             
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        VolleyLog.d("PDC", "Error: " + error.getMessage());
                     
                    }
                });
 
// Adding request to request queue
AppController.getInstance().addToRequestQueue(req, tag_json_arry);
		
	}
		
	public void getPersonalData(View v){
		Log.e("PDC", "CLICKED");
		String tag_json_arry = "json_array_req";
		String url = "http://t3.abdn.ac.uk:8080/t3v2/1/device/"+AppController.DEV_ID+"/personaldata/all";
		     
		
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
	
	final OnClickListener personallistener = new OnClickListener() {
    
		public void onClick(final View v) {
     try{
			int id=v.getId();
         Dialogs.createPersonalDialog(personalData.getJSONObject(id).getString("description"),OverviewActivity.this);
     }
     catch(Exception e){
    	e.printStackTrace();
     }
        	
        }
	};
	
	final OnClickListener companyListener = new OnClickListener() {
	    
		public void onClick(final View v) {
     try{
			String uri=(String)v.getTag();
         Dialogs.getCompanyData(uri, OverviewActivity.this);
     }
     catch(Exception e){
    	e.printStackTrace();
     }
        	
        }
	};
	
    public void populateCompanyView(JSONArray response){
    //	"logo":"http://www.t3.abdn.ac.uk/image/carman.png","email":"mailto:enquiries@carmanufacturer.gov.uk","address":"Aberdeen,UK","tel":"tel:0000111","uri":"http://t3.abdn.ac.uk/t3v2/1/device/simbbox001/CarManufacturer"},{"logo":"http://t3.abdn.ac.uk/image/simbox.png","email":"mailto:simbbox@simbbox.co.uk","address":"MacRobert Building, University of Aberdeen, Aberdeen, UK","tel":"file:///var/lib/tomcat7/0770000000","uri":"http://t3.abdn.ac.uk/t3v2/1/device/simbbox001/Owner"},
    	int pixels =(int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 
                20, getResources().getDisplayMetrics());		
    	try{
    		for(int i=0; i<response.length();i++){
    		JSONObject respOb=response.getJSONObject(i);
    			ImageView im=new ImageView(this);
    			
		    	Picasso.with(OverviewActivity.this).load(respOb.getString("logo")).resize(50, 50).into(im);
    				im.setPadding(2, 2, 2, 2);
    				im.setId(i);
    				im.setTag(respOb.getString("uri"));
	
    				im.setOnClickListener(companyListener);
    			LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
    			params.setMargins(0, 0, pixels, 0);
    				im.setLayoutParams(params);
    				//im.setImageDrawable(getResources().getDrawable(R.drawable.ic_fingerprint_personal_data));
    				companyLayout.addView(im);
    			
    			}
    			
    			
    			
    		}
    		
    		catch(Exception e){
    			e.printStackTrace();
    		}
    	
    	
    }
	public void populatePDCView(JSONArray response){
		personalData=response;
		personalDataLayout.removeAllViews();
		
		int pixels =(int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 
                20, getResources().getDisplayMetrics());		
		try{
		for(int i=0; i<response.length();i++){
				ImageView im=new ImageView(this);
				im.setPadding(2, 2, 2, 2);
				im.setId(i);
				im.setOnClickListener(personallistener);
			LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
			params.setMargins(0, 0, pixels, 0);
				im.setLayoutParams(params);
				im.setImageDrawable(getResources().getDrawable(R.drawable.ic_fingerprint_personal_data));
				personalDataLayout.addView(im);
			
			}
			
			
			
		}
		
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
}
