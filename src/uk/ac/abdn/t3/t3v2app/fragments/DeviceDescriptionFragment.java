package uk.ac.abdn.t3.t3v2app.fragments;

import org.json.JSONObject;

import com.squareup.picasso.Picasso;

import uk.ac.abdn.t3.t3v2app.AppController;
import uk.ac.abdn.t3.t3v2app.Dialogs;
import uk.ac.abdn.t3.t3v2app.Helpers;
import uk.ac.abdn.t3.t3v2app.JSONHandler;
import uk.ac.abdn.t3.t3v2app.Loader;
import uk.ac.abdn.t3.t3v2app.R;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class DeviceDescriptionFragment extends Fragment implements JSONHandler {
String getDescURL="http://t3.abdn.ac.uk:8080/t3v2/1/device/"+AppController.DEV_ID+"/description";
JSONObject result=null;
	LinearLayout desc_lay;
	ImageView image;
	TextView description;
	ImageView loader;

	   LayoutInflater inflater;
			public DeviceDescriptionFragment() {
			}

			@Override
			public View onCreateView(LayoutInflater inflater, ViewGroup container,
					Bundle savedInstanceState) {
			this.inflater=inflater;
			
				View rootView = inflater.inflate(R.layout.fragment_devicedescription,
						container, false);
			desc_lay=(LinearLayout)rootView.findViewById(R.id.desc_lay)	;
			image=(ImageView)rootView.findViewById(R.id.description_image);
			loader=(ImageView)rootView.findViewById(R.id.headers_loader);
			description=(TextView) rootView.findViewById(R.id.description_text);
			
			image.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View arg0) {
					Log.e("MAG", "CLICKED");
					Dialogs.getDescriptionDialog(result, getActivity());
					
				}
				
			});
			
			
			startLoad();
			
			Helpers.requestGetJSON(getDescURL, this);
			//load description
				

				
				
				
				
				return rootView;
			}

	
			public void startLoad() {
			loader.setVisibility(View.VISIBLE);
			Helpers.rotateImage(loader,getActivity());
				
			}

	
			public void stopLoad() {
			loader.setVisibility(View.GONE);
			loader.clearAnimation();
				
			}

			@Override
			public void parseJson(JSONObject j) throws Exception {
				if(j!=null){
				description.setText(j.getString("deviceDescription"));
				Picasso.with(getActivity()).load(j.getString("logo")).fit().placeholder(R.drawable.ic_new).into(image);
				// TODO Auto-generated method stub
				result=j;
				}
				else{
					description.setText("Response is null");
				}
				stopLoad();
			}
	
}
