package uk.ac.abdn.t3.t3v2app;

import org.json.JSONArray;
import org.json.JSONObject;

import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.squareup.picasso.Picasso;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class Dialogs {

	
	
	
	
	
	public static  void getCompanyData(String uri,final Context c){
		String tag_json_ob="tag_json_ob";
		String url="http://t3.abdn.ac.uk:8080/t3v2/1/device/"+AppController.DEV_ID+"/company";
		try{
		final ProgressDialog pDialog = new ProgressDialog(c);
		pDialog.setMessage("Loading...Personal Data");
		pDialog.show();     
		  JSONObject body=new JSONObject();
		  body.put("uri", uri);
		  
		JsonObjectRequest req = new JsonObjectRequest(Method.POST,url,body,
		                new Response.Listener<JSONObject>() {
		                    @Override
		                    public void onResponse(JSONObject response) {
		                    	  pDialog.hide();   
		                     getCompanyDialog(response,c);
		                    
		                    }
		                }, new Response.ErrorListener() {
		                    @Override
		                    public void onErrorResponse(VolleyError error) {
		                        VolleyLog.d("PDC", "Error: " + error.getMessage());
		                        pDialog.hide();
		                    }
		                });
		 
		// Adding request to request queue
		AppController.getInstance().addToRequestQueue(req, tag_json_ob);
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
		
		
		
	}
	
	public static void createPersonalDialog(String data, Context c){
		// 1. Instantiate an AlertDialog.Builder with its constructor
		AlertDialog.Builder builder = new AlertDialog.Builder(c);

		// 2. Chain together various setter methods to set the dialog characteristics
		builder.setMessage(data)
		       .setTitle("Personal Data");    //TODO hard coded personal data title

		// 3. Get the AlertDialog from create()
		AlertDialog dialog = builder.create();
		dialog.show();
	}
	
	private static void getCompanyDialog(JSONObject res,Context c){
		
		
		
		 try{
		 final Dialog dialog = new Dialog(c);
			dialog.setContentView(R.layout.company_layout);
			dialog.setTitle(res.getString("name"));
			dialog.setCanceledOnTouchOutside(true);
			

			TextView address = (TextView) dialog.findViewById(R.id.address);
			
			address.setText(res.getString("address"));
			TextView email=(TextView)dialog.findViewById(R.id.company_email);
			if(!res.getString("email").equals("null")){
			email.setText(res.getString("email"));
			email.setVisibility(View.VISIBLE);
			dialog.findViewById(R.id.label_email).setVisibility(View.VISIBLE);
			}
			TextView url=(TextView)dialog.findViewById(R.id.company_website);
			if(!res.getString("web").equals("null")){
			url.setText(res.getString("web"));
			url.setVisibility(View.VISIBLE);
			dialog.findViewById(R.id.label_website).setVisibility(View.VISIBLE);
			}
			TextView phone=(TextView)dialog.findViewById(R.id.company_phone);
			if(!res.getString("tel").equals("null")){
			phone.setText(res.getString("tel"));
			phone.setVisibility(View.VISIBLE);
			dialog.findViewById(R.id.label_phone).setVisibility(View.VISIBLE);
			}
			ImageView image = (ImageView) dialog.findViewById(R.id.image);
			Picasso.with(c).load(res.getString("logo")).into(image);
	
			
		 dialog.show();
		 }
		 catch(Exception e){
			 Toast.makeText(c, e.getMessage(), Toast.LENGTH_LONG).show();
		 }
		 
		 
		 
	 }
	
	
	
	
}
