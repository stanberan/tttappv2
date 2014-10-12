package uk.ac.abdn.t3.t3v2app;

import uk.ac.abdn.t3.t3v2app.R;
import uk.ac.abdn.t3.t3v2app.R.id;
import uk.ac.abdn.t3.t3v2app.R.layout;
import uk.ac.abdn.t3.t3v2app.R.menu;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.telephony.TelephonyManager;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.nfc.FormatException;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.Ndef;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import android.os.Build;

public class NfcActivity extends ActionBarActivity {
String urlAction="";
static String uid;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_nfc);
		
		
		handleIntent(getIntent());
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.nfc, menu);
		return true;
	}
	@SuppressLint("NewApi")
	public void handleIntent(Intent i){
		String action = i.getAction();
		if (NfcAdapter.ACTION_NDEF_DISCOVERED.equals(action)) {
		
			String scheme = i.getScheme();
			if (scheme.equals("http")) {
				Tag tag = i.getParcelableExtra(NfcAdapter.EXTRA_TAG);
				Ndef ndef = Ndef.get(tag);
				if (ndef == null) {
					// not supported;
					Log.d("Tag:Ndef", "Not supported");
				}
				NdefMessage ndefMessage = ndef.getCachedNdefMessage();
				byte[] rawMessage = ndefMessage.toByteArray();
				byte[] idTag = tag.getId();
				NdefRecord ndefrecord = ndefMessage.getRecords()[0];
			
				String type = new String(ndefrecord.getType());
				byte[] payloadurl = ndefMessage.getRecords()[0].getPayload();
				String urltemp = "http://";
				if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.JELLY_BEAN) {
					try {
						NdefMessage nestedMessage = new NdefMessage(payloadurl);
						byte[] nestedPayload = nestedMessage.getRecords()[0]
								.getPayload();
						for (int s = 1; s < nestedPayload.length; s++) {
							urltemp += (char) nestedPayload[s];
						}
						urlAction=urltemp;
					} catch (FormatException e) {
						Toast.makeText(getApplicationContext(),
								"No wrapper around NDEF Tag!",
								Toast.LENGTH_LONG).show();
					}

				}
				else{
				urlAction=ndefrecord.toUri().toString();
				if(urlAction.contains("http://t3.abdn.ac.uk/t3v2/1/device/")){
					Log.e("NFC", "FOUND intent starting....");
				Intent s=new Intent(this,OverviewActivity.class);
				Log.e("found", "Starting activity to get capailities.");
				s.putExtra("caller","nfc");
				
				String devid=urlAction.substring(35);	
				s.putExtra("devid", devid);
				startActivity(s);
				}
				}
			}}
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}


}
