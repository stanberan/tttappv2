package uk.ac.abdn.t3.t3v2app.fragments;

import org.json.JSONObject;

import com.squareup.picasso.Picasso;

import uk.ac.abdn.t3.t3v2app.AppController;
import uk.ac.abdn.t3.t3v2app.Dialogs;
import uk.ac.abdn.t3.t3v2app.Helpers;
import uk.ac.abdn.t3.t3v2app.JSONHandler;
import uk.ac.abdn.t3.t3v2app.OverviewActivity;
import uk.ac.abdn.t3.t3v2app.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class AcceptDeclineFragment extends Fragment implements JSONHandler{
	String acceptURL="http://t3.abdn.ac.uk:8080/t3v2/1/user/accept/capabilities/"+AppController.UID+"/"+AppController.DEV_ID;
LayoutInflater inflater;
    TextView accept;
	TextView decline;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
	this.inflater=inflater;
	//
		View rootView = inflater.inflate(R.layout.acceptcancel_view,
				container, false);
accept=(TextView)rootView.findViewById(R.id.accept_button);
decline=(TextView)rootView.findViewById(R.id.cancel_button);
	accept.setOnClickListener(new OnClickListener(){

		@Override
		public void onClick(View arg0) {
			accept();
			
		}
		
	});
	
	decline.setOnClickListener(new OnClickListener(){

		@Override
		public void onClick(View arg0) {
		decline();
			
		}
		
	});
	return rootView;
	}
	
	public void accept(){
	Helpers.requestGetJSON(acceptURL, this);
	Dialogs.showDialog("Your newly accepted capabilities have been saved.", getActivity(),"Accept Response");	
	}
	public void decline(){
		Helpers.requestGetJSON(acceptURL, this);
		Dialogs.showDialog("Thank you, the appropriate service agents have been informed about your disagreement.", getActivity(),"Decline Response");
		
	}
	

	@Override
	public void parseJson(JSONObject j) throws Exception {
		 ((OverviewActivity)getActivity()).removeAccept();
	}
	

}
