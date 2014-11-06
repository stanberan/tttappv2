package uk.ac.abdn.t3.t3v2app.adapters;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.squareup.picasso.Picasso;

import uk.ac.abdn.t3.t3v2app.R;
import uk.ac.abdn.t3.t3v2app.ViewHolder;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class HeadersAdapter extends BaseAdapter {
	
	JSONArray headers;
	JSONArray children;
	
	
	LayoutInflater inflater;
	Context c;
	public static String TTT_NS = "http://t3.abdn.ac.uk/ontologies/t3.owl#";
	public static String PDC_TYPE = TTT_NS + "PersonalDataCollection";
	public static String PDG_TYPE = TTT_NS + "PersonalDataGeneration";
	public static String PDU_TYPE = TTT_NS + "PersonalDataUsage";
	public static String BIL_TYPE = TTT_NS + "BillingCap";
	public static String PDS_TYPE = TTT_NS + "PersonalDataSharing";

	final int PDC = 1;
	final int PDG = 2;
	final int PDU = 3;
	final int BIL = 4;
	final int PDS = 5;

	public HeadersAdapter(JSONObject cap, LayoutInflater inflater, Context c){
		this.inflater=inflater;
		try {
			headers=cap.getJSONArray("currentHeaders");
		
		children=cap.getJSONArray("sorted");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.c=c;
		
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return headers.length();
	}

	public int getViewTypeCount() {
		return 6;
		
	}

	public int getItemViewType(int position) {
		JSONObject j;
		try {
			j = headers.getJSONObject(position);

			String type = j.getString("type");

			if (type.equals(PDC_TYPE)) {
				return PDC;
			} else if (type.equals(PDG_TYPE)) {
				return PDG;
			} else if (type.equals(PDU_TYPE)) {
				return PDU;
			} else if (type.equals(BIL_TYPE)) {
				return BIL;
			} else if (type.equals(PDS_TYPE)) {
				return PDS;
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return -1;
		}
		return -1;
	}

	@Override
	public Object getItem(int position) {
		try {
			return headers.getJSONObject(position);
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
             switch (type) {
                 case PDU:
                     convertView = inflater.inflate(R.layout.header_capability_pdu, null);
                   holder.count=(TextView)convertView.findViewById(R.id.text_pdu_count);
                   holder.company=(ImageView)convertView.findViewById(R.id.image_pdu_logo);
                   holder.newCap=(ImageView)convertView.findViewById(R.id.image_new_pdu);
                     break;
                 case PDG:
                	 convertView = inflater.inflate(R.layout.header_capability_pdg,null);
                     holder.count=(TextView)convertView.findViewById(R.id.text_pdg_count);
                     holder.company=(ImageView)convertView.findViewById(R.id.image_pdg_logo);
                     holder.newCap=(ImageView)convertView.findViewById(R.id.image_new_pdg);
                     break;
                 case PDS:
                	 convertView = inflater.inflate(R.layout.header_capability_pds,null);
                     holder.count=(TextView)convertView.findViewById(R.id.text_pds_count);
                     holder.company=(ImageView)convertView.findViewById(R.id.image_pds_logo);
                     holder.newCap=(ImageView)convertView.findViewById(R.id.image_new_pds);
                     break;
                 case PDC:
                	 convertView = inflater.inflate(R.layout.header_capability_pdc, null);
                     holder.count=(TextView)convertView.findViewById(R.id.text_pdc_count);
                     holder.company=(ImageView)convertView.findViewById(R.id.image_pdc_logo);
                     holder.newCap=(ImageView)convertView.findViewById(R.id.image_new_pdc);
                     break;
                 case BIL:
                	 convertView = inflater.inflate(R.layout.header_capability_bil, null);
                     holder.count=(TextView)convertView.findViewById(R.id.text_bil_count);
                     holder.company=(ImageView)convertView.findViewById(R.id.image_bil_logo);
                     holder.newCap=(ImageView)convertView.findViewById(R.id.image_new_bil);
                     break;
             }
             convertView.setTag(holder);
         } else {
             holder = (ViewHolder)convertView.getTag();
         }
         try{
        	 int length=children.getJSONArray(position).length();
        	 String logo=headers.getJSONObject(position).getString("company_logo");
        	 boolean isnew=headers.getJSONObject(position).has("new");
        	 Log.e("Check values", ""+length+" "+logo+"isnew"+isnew);
        holder.count.setText(String.valueOf(length));
        Picasso.with(c).load(logo).placeholder(R.drawable.ic_new).into(holder.company);
       if(headers.getJSONObject(position).has("new")){
    	holder.newCap.setVisibility(View.VISIBLE);   
       }
       else{
    	   holder.newCap.setVisibility(View.INVISIBLE);
       }
         }catch(Exception e){e.printStackTrace();}
    	   
         return convertView;
     }
	 static class ViewHolder{
		 public  ImageView company;
		 public  ImageView newCap;
		 public TextView count;
	 }

 }


