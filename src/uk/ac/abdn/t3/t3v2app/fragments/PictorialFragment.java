package uk.ac.abdn.t3.t3v2app.fragments;

import org.json.JSONArray;
import org.json.JSONObject;

import uk.ac.abdn.t3.t3v2app.AppController;
import uk.ac.abdn.t3.t3v2app.Dialogs;
import uk.ac.abdn.t3.t3v2app.Helpers;
import uk.ac.abdn.t3.t3v2app.OverviewActivity;
import uk.ac.abdn.t3.t3v2app.R;
import android.app.ProgressDialog;
import android.app.ActionBar.LayoutParams;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.squareup.picasso.Picasso;

public class PictorialFragment extends Fragment {

	
	LayoutInflater inflater;
LinearLayout personalDataLayout;
JSONArray personalData;
LinearLayout companyLayout;
ImageView loader;
	
boolean loadedP=false;
boolean loadedC=false;
	
	

@Override
public View onCreateView(LayoutInflater inflater, ViewGroup container,
		Bundle savedInstanceState) {
this.inflater=inflater;

	View rootView = inflater.inflate(R.layout.fragment_pictorial,
			container, false);
	
personalDataLayout=(LinearLayout)rootView.findViewById(R.id.pd_layout);
loader=(ImageView)rootView.findViewById(R.id.headers_loader);
companyLayout=(LinearLayout) rootView.findViewById(R.id.cp_layout);
loader.setVisibility(View.GONE);
//startLoad();
//
getCompanies();
getPersonalData();
	
	
	return rootView;
}

	
	
	
	
public void getCompanies(){
	companyLayout.removeAllViews();
		String tag_json_arry = "json_array_req";
		String url = AppController.HOST+"t3v2/1/device/"+AppController.DEV_ID+"/companies";
		JsonArrayRequest req = new JsonArrayRequest(url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d("PDC", response.toString());   
                        loadedC=true;
                        populateCompanyView(response);
                       
                             
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    stopLoad();
                        VolleyLog.d("PDC", "Error: " + error.getMessage());
                     
                    }
                });
 
// Adding request to request queue
AppController.getInstance().addToRequestQueue(req, tag_json_arry);
		
	}
		
	public void getPersonalData(){
		personalDataLayout.removeAllViews();
		Log.e("Personal", "Removed Before Load");
		String tag_json_arry = "json_array_req";
		String url = AppController.HOST+"t3v2/1/device/"+AppController.DEV_ID+"/personaldata/all";
		     
	
		JsonArrayRequest req = new JsonArrayRequest(url,
		                new Response.Listener<JSONArray>() {
		                    @Override
		                    public void onResponse(JSONArray response) {
		                        Log.d("PDC", response.toString());   
		                        populatePDCView(response);
		                        loadedP=true;        
		                    }
		                }, new Response.ErrorListener() {
		                    @Override
		                    public void onErrorResponse(VolleyError error) {
		                        VolleyLog.d("PDC", "Error: " + error.getMessage());
		                      stopLoad();
		                    }
		                });
		 
		// Adding request to request queue
		AppController.getInstance().addToRequestQueue(req, tag_json_arry);
	}
	
	final OnClickListener personallistener = new OnClickListener() {
    
		public void onClick(final View v) {
     try{
			int id=v.getId();
         Dialogs.createPersonalDialog(personalData.getJSONObject(id).getString("description"),getActivity());
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
         Dialogs.getCompanyData(uri, getActivity());
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
    			ImageView im=new ImageView(getActivity());
    			
		    	Picasso.with(getActivity()).load(respOb.getString("logo")).resize(50, 50).into(im);
    				im.setPadding(2, 2, 2, 2);
    				im.setMinimumHeight(100);
    				im.setMinimumWidth(100);
    				im.setId(i);
    				im.setTag(respOb.getString("uri"));
	
    				im.setOnClickListener(companyListener);
    			LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
    			params.setMargins(0, 0, pixels, 0);
    				im.setLayoutParams(params);
    				//im.setImageDrawable(getResources().getDrawable(R.drawable.ic_fingerprint_personal_data));
    				companyLayout.addView(im);
    				
    				if(loadedC && loadedP){
    					stopLoad();
    				}
    			}
    			
    			
    			
    		}
    		
    		catch(Exception e){
    			e.printStackTrace();
    		}
    	
    	
    }
	public void populatePDCView(JSONArray response){
		personalData=response;
		
		
		int pixels =(int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 
                20, getResources().getDisplayMetrics());		
		try{
		for(int i=0; i<response.length();i++){
				ImageView im=new ImageView(getActivity());
				im.setPadding(2, 2, 2, 2);
				im.setId(i);
				im.setMinimumHeight(100);
				im.setMinimumWidth(100);
				im.setOnClickListener(personallistener);
			LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
			params.setMargins(0, 0, pixels, 0);
				im.setLayoutParams(params);
				im.setImageDrawable(getResources().getDrawable(R.drawable.ic_fingerprint_personal_data));
				personalDataLayout.addView(im);
				
				if(loadedC && loadedP){
					stopLoad();
				}
			
			}
			
			
			
		}
		
		catch(Exception e){
			e.printStackTrace();
		}
	}
	

	public void startLoad() {
	loader.setVisibility(View.VISIBLE);
	Helpers.rotateImage(loader,getActivity());
		
	}

	
	public void stopLoad() {
	loader.setVisibility(View.GONE);
	loader.clearAnimation();
		
	}
}
