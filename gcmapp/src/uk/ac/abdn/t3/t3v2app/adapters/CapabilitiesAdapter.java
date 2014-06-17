package uk.ac.abdn.t3.t3v2app.adapters;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.android.gms.wallet.e;
import com.squareup.picasso.Picasso;

import uk.ac.abdn.t3.t3v2app.AppController;
import uk.ac.abdn.t3.t3v2app.Dialogs;
import uk.ac.abdn.t3.t3v2app.R;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CapabilitiesAdapter extends BaseExpandableListAdapter {
	JSONObject capabilities;
	static LayoutInflater inflater;
	Context c;
	String PDG="generationarray";
	String PDU="usagearray";
	String PDC="collectionarray";
	String BIL="billingarray";
	String PDS="sharingarray";
	
	int PDG_TYPE=1;
	int PDU_TYPE=2;
	int PDS_TYPE=3;
	int PDC_TYPE=4;
	int BIL_TYPE=5;

	static HashMap<Integer,String> uris=new HashMap<Integer,String>();
	public CapabilitiesAdapter(JSONObject capabilities,LayoutInflater inflater,Context c){
		this.capabilities=capabilities;
		this.inflater=inflater;
		this.c=c;
	}


	@Override
	public Object getChild(int groupPosition, int childPosition) {
	 try{
		 JSONArray children=getChildren(groupPosition);
		 Log.e("CHILD", children.toString());
		 return (children.get(childPosition));
	 }catch(Exception e){
		 e.printStackTrace();
	 }
		return null;
	}


	@Override
	public long getChildId(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return (groupPosition *1024 + childPosition);
	}


	@Override
	public View getChildView(int groupPosition, int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {

		int viewType=getGroupType(groupPosition);
		if(viewType==PDG_TYPE){
			return getPDGRowView(groupPosition,childPosition,isLastChild,convertView,parent);
		}
		else if(viewType==PDC_TYPE){
			return getPDCRowView(groupPosition,childPosition,isLastChild,convertView,parent);
		}
		else if(viewType==PDU_TYPE){
			return getPDURowView(groupPosition,childPosition,isLastChild,convertView,parent);
		}
		else if(viewType==PDS_TYPE){
			return getPDSRowView(groupPosition,childPosition,isLastChild,convertView,parent);
		}
		else if(viewType==BIL_TYPE){
			return getBILRowView(groupPosition,childPosition,isLastChild,convertView,parent);
		}
		
		return null;
	
	}


	@Override
	public int getChildrenCount(int groupPosition) {
		try{
			JSONArray children=getChildren(groupPosition);
		return children.length();
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return 0;
	}
	public JSONArray getChildren(int groupPosition) throws JSONException{
		String key=getGroup(groupPosition).toString();
		return(capabilities.getJSONArray(key));
	}


	@Override
	public Object getGroup(int groupPosition) {		
		@SuppressWarnings("unchecked")
		Iterator<e> i=capabilities.keys();
		while(groupPosition > 0){
			i.next(); 
			groupPosition--;
		}
		return(i.next());
		
	}
 public int getGroupType(int groupPosition){
 
		String groupType=getGroup(groupPosition).toString();
		if(groupType.equals(PDG)){
			return PDG_TYPE;
		}
		else if(groupType.equals(PDC)){
			return PDC_TYPE;
		}
		else if(groupType.equals(PDU)){
			return PDU_TYPE;
		}
		else if(groupType.equals(PDS)){
			return PDS_TYPE;
		}
		else if(groupType.equals(BIL)){
			return BIL_TYPE;
		}
		return -1;
		
	
 }
 public int getChildTypeCount(){
	 return capabilities.length();
 }
 
 public int getChildType(int groupPosition,int childPosition){
	 
	 String groupType=getGroup(groupPosition).toString();
		if(groupType.equals(PDG)){
			return PDG_TYPE;
		}
		else if(groupType.equals(PDC)){
			return PDC_TYPE;
		}
		else if(groupType.equals(PDU)){
			return PDU_TYPE;
		}
		else if(groupType.equals(PDS)){
			return PDS_TYPE;
		}
		else if(groupType.equals(BIL)){
			return BIL_TYPE;
		}
		return -1;
		
	
}
public int getGroupTypeCount(){
	 return capabilities.length();
}




	@Override
	public int getGroupCount() {
		// TODO Auto-generated method stub
		return capabilities.length();
	}


	@Override
	public long getGroupId(int groupPosition) {
	return(groupPosition);
	}

	
	


	@Override
	public boolean hasStableIds() {
		// TODO Auto-generated method stub
		return true;
	}


	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return false;
	}
	
	
	@Override
	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {
	
		int viewType=getGroupType(groupPosition);
		if(viewType==PDG_TYPE){
			return getPDGView(groupPosition,isExpanded,convertView,parent);
		}
		else if(viewType==PDC_TYPE){
			return getPDCView(groupPosition,isExpanded,convertView,parent);
		}
		else if(viewType==PDU_TYPE){
			return getPDUView(groupPosition,isExpanded,convertView,parent);
		}
		else if(viewType==PDS_TYPE){
			return getPDSView(groupPosition,isExpanded,convertView,parent);
		}
		else if(viewType==BIL_TYPE){
			return getBILView(groupPosition,isExpanded,convertView,parent);
		}
		return null;
	
	
	}
	public boolean hasNew(JSONArray ar){
		try{
		for(int i=0;i<ar.length();i++){
			if(ar.getJSONObject(i).has("new")){
				return true;
			}
		}
		return false;
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return false;
	}
	
	public View getPDGView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent){
		View row=convertView;
		
		if(row==null){
			row=inflater.inflate(R.layout.header_capability_pdg, parent,false);
		}
		
	
		
		try{
			JSONArray ar=getChildren(groupPosition);
			if(ar.length()==0){
				return null;
			}
		JSONObject object=(JSONObject)getChild(groupPosition,0);
		Log.e("ADAPTER", object.toString());
	//	holder.uri=object.getString("generatedBy");
		TextView count=(TextView)row.findViewById(R.id.text_pdg_count);
		count.setText(""+ar.length());
		TextView desc=(TextView)row.findViewById(R.id.text_type);
		desc.setText(object.getString("type").substring(AppController.TTT_NS.length()));
		ImageView logo=(ImageView)row.findViewById(R.id.image_pdg_logo);
		Picasso.with(c).load(object.getString("generatedBy_logo")).resize(200, 200).into(logo);
		uris.put(logo.getId(), object.getString("generatedBy"));
		logo.setOnClickListener(new View.OnClickListener(){

			@Override
			public void onClick(View v) {
		
			 Dialogs.getCompanyData(uris.get(v),c);	
			}
			
		});
		
		
		
		return  row;
		
		}
		catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	public View getPDGRowView(int groupPosition, int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent){
		View row=convertView;
		Log.e("CHILD", "INSIDE GET CHILD VIEW");
		//if(row==null){
			row=inflater.inflate(R.layout.row_capability_pdg, parent,false);
		//}
		
		TextView desc=(TextView)row.findViewById(R.id.text_pdg_description);
		ImageView newcap=(ImageView)row.findViewById(R.id.image_new_pdg);
		
		try{
		JSONObject object=(JSONObject)getChild(groupPosition,childPosition);
		Log.e("GETCHILD", object.toString());
		
		desc.setText(object.getString("data_desc"));
		
		if(object.has("new")){
		newcap.setVisibility(View.VISIBLE);
		}
		else{
		newcap.setVisibility(View.GONE);
		}
		
		return  row;
		
		}
		catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	public View getPDCView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent){
		
		View row=convertView;
		
		if(row==null){
			row=inflater.inflate(R.layout.header_capability_pdc, parent,false);
		}
		
	
		
		try{
			JSONArray ar=getChildren(groupPosition);
			if(ar.length()==0){
				return null;
			}
		JSONObject object=(JSONObject)getChild(groupPosition,0);
		Log.e("ADAPTER", object.toString());
	//	holder.uri=object.getString("generatedBy");
		TextView count=(TextView)row.findViewById(R.id.text_pdc_count);
		count.setText(getChildren(groupPosition).length()+"");
		TextView desc=(TextView)row.findViewById(R.id.text_type);
		desc.setText(object.getString("type").substring(AppController.TTT_NS.length()));
		ImageView logo=(ImageView)row.findViewById(R.id.image_pdc_logo);
		Picasso.with(c).load(object.getString("consumer_logo")).resize(200, 200).into(logo);
		ImageView newCap=(ImageView)row.findViewById(R.id.image_new_pdc_header);
		
		if(!object.has("new")){
			newCap.setVisibility(View.GONE);
		}
		else{
			newCap.setVisibility(View.VISIBLE);
		}
		
		
		uris.put(logo.getId(), object.getString("consumer_uri"));
		logo.setOnClickListener(new View.OnClickListener(){

			@Override
			public void onClick(View v) {
		
			 Dialogs.getCompanyData(uris.get(v),c);	
			}
			
		});
		
		
		
		return  row;
		
		}
		catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	public View getPDUView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent){
View row=convertView;
		
		if(row==null){
			row=inflater.inflate(R.layout.header_capability_pdg, parent,false);
		}
		
	
		
		try{
			JSONArray ar=getChildren(groupPosition);
			if(ar.length()==0){
				return null;
			}
		JSONObject object=(JSONObject)getChild(groupPosition,0);
		Log.e("ADAPTER", object.toString());
	//	holder.uri=object.getString("generatedBy");
		TextView count=(TextView)row.findViewById(R.id.text_pdg_count);
		count.setText(getChildren(groupPosition).length());
		TextView desc=(TextView)row.findViewById(R.id.text_type);
		desc.setText(object.getString("type").substring(AppController.TTT_NS.length()));
		ImageView logo=(ImageView)row.findViewById(R.id.image_pdg_logo);
		Picasso.with(c).load(object.getString("generatedBy_logo")).resize(200, 200).into(logo);
		ImageView newCap=(ImageView)row.findViewById(R.id.image_new_pdg);
		uris.put(logo.getId(), object.getString("uri"));
		logo.setOnClickListener(new View.OnClickListener(){

			@Override
			public void onClick(View v) {
		
			 Dialogs.getCompanyData(uris.get(v),c);	
			}
			
		});
		
		
		
		return  row;
		
		}
		catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	public View getPDSView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent){
View row=convertView;
		
		if(row==null){
			row=inflater.inflate(R.layout.header_capability_pdg, parent,false);
		}
		
	
		
		try{
			JSONArray ar=getChildren(groupPosition);
			if(ar.length()==0){
				return null;
			}
		JSONObject object=(JSONObject)getChild(groupPosition,0);
		Log.e("ADAPTER", object.toString());
	//	holder.uri=object.getString("generatedBy");
		TextView count=(TextView)row.findViewById(R.id.text_pdg_count);
		count.setText(getChildren(groupPosition).length());
		TextView desc=(TextView)row.findViewById(R.id.text_type);
		desc.setText(object.getString("type").substring(AppController.TTT_NS.length()));
		ImageView logo=(ImageView)row.findViewById(R.id.image_pdg_logo);
		Picasso.with(c).load(object.getString("generatedBy_logo")).resize(200, 200).into(logo);
		ImageView newCap=(ImageView)row.findViewById(R.id.image_new_pdg);
		uris.put(logo.getId(), object.getString("uri"));
		logo.setOnClickListener(new View.OnClickListener(){

			@Override
			public void onClick(View v) {
		
			 Dialogs.getCompanyData(uris.get(v),c);	
			}
			
		});
		
		
		
		return  row;
		
		}
		catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	public View getBILView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent){
View row=convertView;
		
		if(row==null){
			row=inflater.inflate(R.layout.header_capability_pdg, parent,false);
		}
		
	
		
		try{
			JSONArray ar=getChildren(groupPosition);
			if(ar.length()==0){
				return null;
			}
		JSONObject object=(JSONObject)getChild(groupPosition,0);
		Log.e("ADAPTER", object.toString());
	//	holder.uri=object.getString("generatedBy");
		TextView count=(TextView)row.findViewById(R.id.text_pdg_count);
		count.setText(getChildren(groupPosition).length());
		TextView desc=(TextView)row.findViewById(R.id.text_type);
		desc.setText(object.getString("type").substring(AppController.TTT_NS.length()));
		ImageView logo=(ImageView)row.findViewById(R.id.image_pdg_logo);
		Picasso.with(c).load(object.getString("generatedBy_logo")).resize(200, 200).into(logo);
		ImageView newCap=(ImageView)row.findViewById(R.id.image_new_pdg);
		uris.put(logo.getId(), object.getString("uri"));
		logo.setOnClickListener(new View.OnClickListener(){

			@Override
			public void onClick(View v) {
		
			 Dialogs.getCompanyData(uris.get(v),c);	
			}
			
		});
		
		
		
		return  row;
		
		}
		catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	public View getPDCRowView(int groupPosition, int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent){
		View row=convertView;
		Log.e("CHILD", "INSIDE GET CHILD VIEW");
		//if(row==null){
			row=inflater.inflate(R.layout.row_capability_pdg, parent,false);
		//}
		
		TextView desc=(TextView)row.findViewById(R.id.text_pdg_description);
		ImageView newcap=(ImageView)row.findViewById(R.id.image_new_pdg);
		
		try{
		JSONObject object=(JSONObject)getChild(groupPosition,childPosition);
		Log.e("GETCHILD", object.toString());
		
		desc.setText(object.getString("data_desc"));
		
		if(object.has("new")){
		newcap.setVisibility(View.VISIBLE);
		}
		else{
		newcap.setVisibility(View.GONE);
		}
		
		return  row;
		
		}
		catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	public View getPDURowView(int groupPosition, int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent){
		View row=convertView;
		Log.e("CHILD", "INSIDE GET CHILD VIEW");
		//if(row==null){
			row=inflater.inflate(R.layout.row_capability_pdg, parent,false);
		//}
		
		TextView desc=(TextView)row.findViewById(R.id.text_pdg_description);
		ImageView newcap=(ImageView)row.findViewById(R.id.image_new_pdg);
		
		try{
		JSONObject object=(JSONObject)getChild(groupPosition,childPosition);
		Log.e("GETCHILD", object.toString());
		
		desc.setText(object.getString("data_desc"));
		
		if(object.has("new")){
		newcap.setVisibility(View.VISIBLE);
		}
		else{
		newcap.setVisibility(View.GONE);
		}
		
		return  row;
		
		}
		catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	public View getPDSRowView(int groupPosition, int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent){
		View row=convertView;
		Log.e("CHILD", "INSIDE GET CHILD VIEW");
		//if(row==null){
			row=inflater.inflate(R.layout.row_capability_pdg, parent,false);
		//}
		
		TextView desc=(TextView)row.findViewById(R.id.text_pdg_description);
		ImageView newcap=(ImageView)row.findViewById(R.id.image_new_pdg);
		
		try{
		JSONObject object=(JSONObject)getChild(groupPosition,childPosition);
		Log.e("GETCHILD", object.toString());
		
		desc.setText(object.getString("data_desc"));
		
		if(object.has("new")){
		newcap.setVisibility(View.VISIBLE);
		}
		else{
		newcap.setVisibility(View.GONE);
		}
		
		return  row;
		
		}
		catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	public View getBILRowView(int groupPosition, int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent){
		View row=convertView;
		Log.e("CHILD", "INSIDE GET CHILD VIEW");
		//if(row==null){
			row=inflater.inflate(R.layout.row_capability_pdg, parent,false);
		//}
		
		TextView desc=(TextView)row.findViewById(R.id.text_pdg_description);
		ImageView newcap=(ImageView)row.findViewById(R.id.image_new_pdg);
		
		try{
		JSONObject object=(JSONObject)getChild(groupPosition,childPosition);
		Log.e("GETCHILD", object.toString());
		
		desc.setText(object.getString("data_desc"));
		
		if(object.has("new")){
		newcap.setVisibility(View.VISIBLE);
		}
		else{
		newcap.setVisibility(View.GONE);
		}
		
		return  row;
		
		}
		catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	


}
