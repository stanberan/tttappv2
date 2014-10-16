package uk.ac.abdn.t3.t3v2app.fragments;

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
import uk.ac.abdn.t3.t3v2app.R;
import uk.ac.abdn.t3.t3v2app.adapters.CapabilitiesAdapter;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Matrix;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.ListView;
import android.widget.TextView;

public class CapabilityFragment extends Fragment implements Loader {

ListView elv;
ImageView loader;
TextView output;

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
		
		
			getCapabilities();
			

			
			
			
			
			return rootView;
		}
		
		
		public void getCapabilities(){
			try{
		Intent i=	getActivity().getIntent();
			
				String getCapURL=AppController.HOST+"t3v2/1/device/"+AppController.DEV_ID+"/check/capabilities/"+AppController.UID;
				
			  
				  
				        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Method.GET,
				                getCapURL, null,
				                new Response.Listener<JSONObject>() {
				 
				                    @Override
				                    public void onResponse(JSONObject response) {
				                        Log.d("SUCCESS", response.toString());
				                        JSONObject sorted=sortCapabilities(response);
				                       // CapabilitiesAdapter ad=new CapabilitiesAdapter(sorted,inflater,getActivity());
				                     //.setAdapter(ad);
				                        try {
											output.setText(response.toString(5));
										} catch (JSONException e) {
											// TODO Auto-generated catch block
											e.printStackTrace();
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
			

		public JSONObject sortCapabilities(JSONObject allcapabilities){
		try{
			Log.e("CapFrag", "Capabiliities"+allcapabilities.toString());
			JSONArray newcap=new JSONArray();
			if(allcapabilities.has("newcapabilities")){
				newcap=allcapabilities.getJSONArray("newcapabilities");
			}
			JSONArray jsonArray=allcapabilities.getJSONArray("currentCapabilities");
			
			JSONObject sorted=new JSONObject();
			JSONArray sharing=new JSONArray();
			JSONArray collection=new JSONArray();
			JSONArray usage=new JSONArray();
			JSONArray generation=new JSONArray();
			JSONArray billing=new JSONArray();
			
			
			
			for(int i=0; i<jsonArray.length();i++){
				JSONObject capabilityjson=jsonArray.getJSONObject(i);
				Log.e("CAPCURRENT", capabilityjson.toString() );
				String type=capabilityjson.getString("type");
				Log.e("TYPE", type);
				Log.e("ALL",AppController.PDG_TYPE);
				if(exist(capabilityjson,newcap)){
					Log.e("EXIST", "CapExist");
					capabilityjson.put("new", true);
				}
				
				if(type.equals(AppController.BIL_TYPE)){
					billing.put(capabilityjson);
				}
				else if(type.equals(AppController.PDC_TYPE)){
					collection.put(capabilityjson);
				}
				else if(type.equals(AppController.PDG_TYPE)){
					generation.put(capabilityjson);
				}
				else if(type.equals(AppController.PDU)){
					usage.put(capabilityjson);
				}
				else if(type.equals(AppController.PDS_TYPE)){
					sharing.put(capabilityjson);
				}
	
				
			}
			if(sharing.length()>0){
		//	sorted.put("sharingarray",sharing);
			}
			if(collection.length()>0){
	//		sorted.put("collectionarray",collection);
			}
			if(usage.length()>0){
			//sorted.put("usagearray", usage);
			}
			if(generation.length()>0){
			sorted.put("generationarray", generation);
			}
			if(billing.length()>0){
		//	sorted.put("billingarray", billing);
			}
			Log.e("OVERVIEW", sorted.toString());
			return sorted;

		}
		catch(Exception e){
			e.printStackTrace();
			JSONObject error=new JSONObject();
			try {
				error.put("error", e.getMessage());
			} catch (JSONException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			return error;
		}
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

