package uk.ac.abdn.t3.t3v2app.adapters;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import uk.ac.abdn.t3.t3v2app.R;
import uk.ac.abdn.t3.t3v2app.adapters.HeadersAdapter.ViewHolder;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class DetailsAdapter extends BaseAdapter {
JSONArray children;
	
	
	LayoutInflater inflater;
	Context c;
	public static String TTT_NS = "http://t3.abdn.ac.uk/ontologies/t3.owl#";



	public DetailsAdapter(JSONArray ar, LayoutInflater inflater, Context c){
		this.inflater=inflater;
		children=ar;
		this.c=c;
		
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return children.length();
	}



	@Override
	public Object getItem(int position) {
		try {
			return children.getJSONObject(position);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public long getItemId(int position) {
	return position;
	}

	 @Override
     public View getView(int position, View convertView, ViewGroup parent) {
         ViewHolder holder = null;
         int type = getItemViewType(position);
         System.out.println("getView " + position + " " + convertView + " type = " + type);
         if (convertView == null) {
             holder = new ViewHolder();
            
              
                	 convertView = inflater.inflate(R.layout.details_cap, null);
                     holder.count=(TextView)convertView.findViewById(R.id.details_ca);

              
             
             convertView.setTag(holder);
         } else {
             holder = (ViewHolder)convertView.getTag();
         }
         try{
   
       
        	 boolean isnew=children.getJSONObject(position).has("new");
        	 if(isnew){
        	 convertView.setBackgroundColor(c.getResources().getColor(R.color.silver));
        	 }
        	 holder.count.setText(children.getJSONObject(position).toString(3));
     
         }catch(Exception e){e.printStackTrace();}
    	   
         return convertView;
     }
	 static class ViewHolder{
		 public TextView count;
	 }

 }

