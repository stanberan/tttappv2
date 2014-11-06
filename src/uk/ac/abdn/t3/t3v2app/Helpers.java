package uk.ac.abdn.t3.t3v2app;

import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.android.volley.Request;
import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;

public class Helpers {

	
	
	
	public static void requestGetJSON(String url,final JSONHandler j) {
		
		JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.GET, url, null,
			    new Response.Listener<JSONObject>() 
			    {
			        @Override
			        public void onResponse(JSONObject response) {   
			                        // display response     
			           try {
						j.parseJson(response);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			        }
			    }, 
			    new Response.ErrorListener() 
			    {
			         @Override
			         public void onErrorResponse(VolleyError error) {            
			            Log.d("Error.Response", error.getMessage());
			            try {
							j.parseJson(null);
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
			       }
			    }
			);
			 
		AppController.getInstance().addToRequestQueue(getRequest);
		
		
	}
public static void requestGet(final String url) {
	
		JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.GET, url, null,
			    new Response.Listener<JSONObject>() 
			    {
			        @Override
			        public void onResponse(JSONObject response) {   
			                        // display response     
			           try {
			        	   Log.e("Success", url+response.toString());
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			        }
			    }, 
			    new Response.ErrorListener() 
			    {
			         @Override
			         public void onErrorResponse(VolleyError error) {            
			            Log.e("Error.Response", error.getMessage());
			            error.printStackTrace();
			            try {
					
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
			       }
			    }
			);
			 
		AppController.getInstance().addToRequestQueue(getRequest);
		
		
	}
	
	
	
	
	public static void getImageRequest(final ImageView i, String url, Loader l){
		
		ImageRequest request = new ImageRequest(url,
			    new Response.Listener<Bitmap>() {		
			 
					@Override
					public void onResponse(Bitmap arg0) {
						i.setImageBitmap(arg0);						
					}}
			    , 0, 0, null,
			    new Response.ErrorListener() {
			        public void onErrorResponse(VolleyError error) {
			           i.setImageResource(R.drawable.ic_new);
			        }
			    });

		AppController.getInstance().addToRequestQueue(request);
		
	}
	
public static void rotateImage(ImageView i,Context c){
		

        Animation animation = AnimationUtils.loadAnimation(c, R.anim.rotate_around_center_point);
        i.startAnimation(animation);
	}
	
}
