package uk.ac.abdn.t3.t3v2app;

import org.json.JSONArray;
import org.json.JSONObject;

import uk.ac.abdn.t3.t3v2app.fragments.CapabilityFragment;
import uk.ac.abdn.t3.t3v2app.fragments.DeviceDescriptionFragment;
import uk.ac.abdn.t3.t3v2app.fragments.PictorialFragment;

import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.squareup.picasso.Picasso;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.app.ActionBar.LayoutParams;
import android.app.ProgressDialog;
import android.graphics.Matrix;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ImageView.ScaleType;
import android.os.Build;
import android.provider.Settings;

public class OverviewActivity extends ActionBarActivity {
//LinearLayout personalDataLayout;
//LinearLayout companyLayout;
ImageView rotate;

JSONArray personalData=null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_overview);
		//personalDataLayout=(LinearLayout)findViewById(R.id.personalData_container);
		//companyLayout=(LinearLayout)findViewById(R.id.companies_container);
	
	//	rotate.setVisibility(View.GONE);
		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
			.add(R.id.description_frag, new DeviceDescriptionFragment()).commit();
			getSupportFragmentManager().beginTransaction()
					.add(R.id.cap_frag, new CapabilityFragment()).commit();
			getSupportFragmentManager().beginTransaction()
			.add(R.id.pic_frag, new PictorialFragment()).commit();
			
		}
		
		
	
		String devid=getIntent().getStringExtra("devid");
		String caller=getIntent().getStringExtra("caller");
		Log.e("Tag", devid+caller);
	
	
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.overview, menu);
		return true;
	}
	public void rotateImage(){
	

        Animation animation = AnimationUtils.loadAnimation(this, R.anim.rotate_around_center_point);
        rotate.startAnimation(animation);
	}
	public void hideImage(){
		rotate.setVisibility(View.GONE);
		rotate.clearAnimation();
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
	public void onResume(){
		super.onResume();
	}
	
	
}
