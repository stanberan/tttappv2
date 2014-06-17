package uk.ac.abdn.t3.t3v2app.adapters;


import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import uk.ac.abdn.t3.t3v2app.R;

public class PDGRowViewHolder {

	TextView desc;
	ImageView newCap;
	 
	  PDGRowViewHolder(View row) {
	    this.desc=(TextView)row.findViewById(R.id.text_pdg_description);
	    this.newCap=(ImageView)row.findViewById(R.id.image_new);

	  }
	}

