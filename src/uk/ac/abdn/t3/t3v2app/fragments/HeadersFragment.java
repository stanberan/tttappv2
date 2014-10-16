package uk.ac.abdn.t3.t3v2app.fragments;

import uk.ac.abdn.t3.t3v2app.R;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;



public class HeadersFragment extends Fragment {
LayoutInflater inflater;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
	this.inflater=inflater;
		View rootView = inflater.inflate(R.layout.fragment_overview,
				container, false);
//	elv=(ExpandableListView)	rootView.findViewById(R.id.capabilities_list);
		
		//getCapabilities();
		

		
		
		
		
		return rootView;
	}
}
