package uk.ac.abdn.t3.t3v2app.adapters;


import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import uk.ac.abdn.t3.t3v2app.R;

public class PDGViewHolder {

	TextView desc;
	ImageView logo;
	ImageView newcap;
	TextView count;
	String comp_uri;
	  PDGViewHolder(View row) {
	    this.desc=(TextView)row.findViewById(R.id.text_type);
	    this.logo=(ImageView)row.findViewById(R.id.image_pdg_logo);
	    this.newcap=(ImageView)row.findViewById(R.id.image_new);
	    this.count=(TextView)row.findViewById(R.id.text_pdg_count);
	  }
	}

