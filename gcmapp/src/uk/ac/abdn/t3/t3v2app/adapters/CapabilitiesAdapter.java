package uk.ac.abdn.t3.t3v2app.adapters;

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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.BaseExpandableListAdapter;

public class CapabilitiesAdapter extends BaseExpandableListAdapter {
	JSONObject capabilities;
	LayoutInflater inflater;
	Context c;
	String PDG="generationarray";
	String PDU="usagearray";
	String PDC="collectionarray";
	String BIL="billingarray";
	String PDS="sharingarray";
	
	public CapabilitiesAdapter(JSONObject capabilities,LayoutInflater inflater,Context c){
		this.capabilities=capabilities;
		this.inflater=inflater;
		this.c=c;
	}


	@Override
	public Object getChild(int groupPosition, int childPosition) {
	 try{
		 JSONArray children=getChildren(groupPosition);
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
		// TODO Auto-generated method stub
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
			return 1;
		}
		else if(groupType.equals(PDC)){
			return 2;
		}
		else if(groupType.equals(PDU)){
			return 3;
		}
		else if(groupType.equals(PDS)){
			return 4;
		}
		else if(groupType.equals(BIL)){
			return 5;
		}
		return -1;
		
	
 }
 public int getChildTypeCount(){
	 return capabilities.length();
 }
 
 public int getChildType(int groupPosition,int childPosition){
	 
		String groupType=getGroup(groupPosition).toString();
		if(groupType.equals(PDG)){
			return 1;
		}
		else if(groupType.equals(PDC)){
			return 2;
		}
		else if(groupType.equals(PDU)){
			return 3;
		}
		else if(groupType.equals(PDS)){
			return 4;
		}
		else if(groupType.equals(BIL)){
			return 5;
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
	
		String groupType=getGroup(groupPosition).toString();
		if(groupType.equals(PDG)){
			return getPDGView(groupPosition,isExpanded,convertView,parent);
		}
		else if(groupType.equals(PDC)){
			return getPDCView(groupPosition,isExpanded,convertView,parent);
		}
		else if(groupType.equals(PDU)){
			return getPDUView(groupPosition,isExpanded,convertView,parent);
		}
		else if(groupType.equals(PDS)){
			return getPDSView(groupPosition,isExpanded,convertView,parent);
		}
		else if(groupType.equals(BIL)){
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
		
		PDGViewHolder holder=(PDGViewHolder)row.getTag();
		
		if(holder ==null){
			holder=new PDGViewHolder(row);
			row.setTag(holder);
		}
		
		try{
		JSONObject object=(JSONObject)getChild(groupPosition,0);
		
		holder.count.setText(getChildrenCount(groupPosition));
		holder.comp_uri=object.getString("generatedBy");
		holder.desc.setText(object.getString("type").substring(AppController.TTT_NS.length()));
		Picasso.with(c).load(object.getString("logo")).resize(45, 45).into(holder.logo);
		
		
		holder.logo.setOnClickListener(new View.OnClickListener(){

			@Override
			public void onClick(View v) {
			 PDGViewHolder h =(PDGViewHolder) v.getTag();
			 Dialogs.getCompanyData(h.comp_uri,c);	
			}
			
		});
		
		
		
		
		
		
		if(hasNew(getChildren(groupPosition))){
			holder.newcap.setVisibility(View.VISIBLE);
		}
		else{
			holder.newcap.setVisibility(View.GONE);
		}
		
		return  row;
		
		}
		catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	public View getPDGChildView(int groupPosition, int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent){
		View row=convertView;
		
		if(row==null){
			row=inflater.inflate(R.layout.row_capability_pdg, parent,false);
		}
		
		PDGRowViewHolder holder=(PDGRowViewHolder)row.getTag();
		
		if(holder ==null){
			holder=new PDGRowViewHolder(row);
			row.setTag(holder);
		}
		
		try{
		JSONObject object=(JSONObject)getChild(groupPosition,childPosition);
		
		holder.desc.setText(object.getString("data_desc"));
		
		if(object.has("new")){
			holder.newCap.setVisibility(View.VISIBLE);
		}
		else{
			holder.newCap.setVisibility(View.GONE);
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
		return null;
	}
	public View getPDUView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent){
		return null;
	}
	public View getPDSView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent){
		return null;
	}
	public View getBILView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent){
		return null;
	}
	
	


}
