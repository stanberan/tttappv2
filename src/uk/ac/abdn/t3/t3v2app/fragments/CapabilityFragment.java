package uk.ac.abdn.t3.t3v2app.fragments;

import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.android.gms.games.request.Requests;

import uk.ac.abdn.t3.t3v2app.AppController;
import uk.ac.abdn.t3.t3v2app.Helpers;
import uk.ac.abdn.t3.t3v2app.Loader;
import uk.ac.abdn.t3.t3v2app.OverviewActivity;
import uk.ac.abdn.t3.t3v2app.R;
import uk.ac.abdn.t3.t3v2app.adapters.HeadersAdapter;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Matrix;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.ListView;
import android.widget.TextView;

public class CapabilityFragment extends Fragment implements Loader {

ListView elv;
ImageView loader;
TextView output;
JSONArray children;
   LayoutInflater inflater;
		public CapabilityFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
		this.inflater=inflater;
		
			View rootView = inflater.inflate(R.layout.fragment_overview,
					container, false);
			
		elv=(ListView)rootView.findViewById(R.id.headers_list);
		loader=(ImageView)rootView.findViewById(R.id.headers_loader);
		output=(TextView) rootView.findViewById(R.id.headers_output);
		startLoad();
		output.setVisibility(View.GONE);
		
			getCapabilities();
			

			
			
			
			
			return rootView;
		}
		
		
		public void getCapabilities(){
			try{
		
			
				String getCapURL=AppController.HOST+"t3v2/1/device/"+AppController.DEV_ID+"/check/capabilities/"+AppController.UID;
				
			  
				  
				        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Method.GET,
				                getCapURL, null,
				                new Response.Listener<JSONObject>() {
				 
				                    @Override
				                    public void onResponse(JSONObject response) {
				                        Log.d("SUCCESS", response.toString());
				                        JSONObject sorted=response;
				                      
									
				                        if(response.has("currentHeaders")){
				                        	  try {
													children=response.getJSONArray("sorted");
													
				                       HeadersAdapter ad=new HeadersAdapter(sorted,inflater,getActivity());
				                     elv.setAdapter(ad);
				                     if(response.has("different")&& response.getBoolean("different")){
				                    	 ((OverviewActivity)getActivity()).addAccept();
				                     }
				                     
				                        	  }   
				                    	catch (JSONException e1) {
											// TODO Auto-generated catch block
											e1.printStackTrace();
										}
				                     elv.setOnItemClickListener(new OnItemClickListener() {

				                         @Override
				                         public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				                                 long arg3) {
				                             // TODO Auto-generated method stub
				                             try {
												Log.d("############","Item " + children.getJSONArray(arg2).toString(5) );
											} catch (JSONException e) {
												// TODO Auto-generated catch block
												e.printStackTrace();
											}
				                         }

				                     });
				                        }
				                        else{
				                        	output.setVisibility(View.VISIBLE);
				                        	output.setText("No capabilities have been detected...");
				                        }
				                     
				                       
				                      stopLoad();
				                    }
				                }, new Response.ErrorListener() {
				 
				                    @Override
				                    public void onErrorResponse(VolleyError error) {
				                        VolleyLog.d("ERROR", "Error: " + error.getMessage());
				                        // hide the progress dialog
				                        
				                   stopLoad();
				                    }
				                });
				        
				       jsonObjReq.setRetryPolicy(new DefaultRetryPolicy(
				                1000*60*1, 
				                DefaultRetryPolicy.DEFAULT_MAX_RETRIES, 
				                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT)); 
				 
				// Adding request to request queue
				AppController.getInstance().addToRequestQueue(jsonObjReq, "tag_json_obj");
				
		
			
			
			}
			catch(Exception e){
				e.printStackTrace();
			}
		}
		
		
		public boolean exist(JSONObject j,JSONArray array){
			try{
				String jstring=j.toString();
			for(int i=0; i<array.length();i++){
			
				String arraystring=array.getJSONObject(i).toString();
				if(jstring.equalsIgnoreCase(arraystring)){
					
					return true;
				}
				
			}
			
			return false;
			}catch(Exception e){e.printStackTrace(); return false;}
		}
			

		
			
		

		@Override
		public void startLoad() {
		loader.setVisibility(View.VISIBLE);
		Helpers.rotateImage(loader,getActivity());
			
		}

		@Override
		public void stopLoad() {
		loader.setVisibility(View.GONE);
		loader.clearAnimation();
			
		}
		
		
	
		

}

