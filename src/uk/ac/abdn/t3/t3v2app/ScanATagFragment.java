package uk.ac.abdn.t3.t3v2app;



import java.util.ArrayList;
import java.util.List;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.telephony.TelephonyManager;
import android.text.SpannableString;
import android.text.method.LinkMovementMethod;
import android.text.util.Linkify;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class ScanATagFragment extends Fragment{
	
	private static ListView catalogue;
	private static ProgressBar progress=null;
	private static TextView progress_catalogue;
	private static String uid;
	private static boolean isNFC=false;
	public static boolean isRetrievingCat=false;
	private boolean first=true;
	//private SharedPreferences prefs;
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		 
			View result=inflater.inflate(R.layout.catalogue_list_header,container,false);		
		//	progress=(ProgressBar)result.findViewById(R.id.progressBar1);	
		//	catalogue=(ListView)result.findViewById(R.id.catalogue_list_view);
			//View header = View.inflate(getActivity(), R.layout.catalogue_list_header, null);
			//catalogue.addHeaderView(header, null, false);
			
		//	progress_catalogue=(TextView)result.findViewById(R.id.catalogue_progress);
			//progress_catalogue.setVisibility(View.GONE);
           
			return result;
	
			}
	
	public void onActivityCreated(Bundle savedInstanceState) { 
	super.onActivityCreated(savedInstanceState);
	
	setHasOptionsMenu(true);
	//setCatalogue();
	}
	
	
	
	
	public void showEula(){
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
				getActivity());
String raw=getResources().getText(R.string.terms_conditions).toString();

		final SpannableString s = 
	               new SpannableString(raw);
	  Linkify.addLinks(s, Linkify.WEB_URLS);
		
			// set title
			alertDialogBuilder.setTitle("Terms & Conditions");

			// set dialog message
			alertDialogBuilder
				.setMessage(s)
				.setCancelable(false)
				.setPositiveButton("I Agree to terms and conditions",new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog,int id) {
			//			  prefs.edit().putBoolean("EULA_ACCEPTED", true).commit();
					}
				  }).setNegativeButton("Cancel",new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog,int id) {
							// if this button is clicked, just close
							// the dialog box and do nothing
							dialog.cancel();
							getActivity().finish();
						}
					})
				;

				// create alert dialog
				AlertDialog alertDialog = alertDialogBuilder.create();
				
			
				// show it
				alertDialog.show();
				((TextView)alertDialog.findViewById(android.R.id.message)).setMovementMethod(LinkMovementMethod.getInstance());
			}
	 
}

