package uk.ac.abdn.t3.t3v2app;

import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;

public class Rotate {

	
	
	
	public void anim(){
		
	//	ImageView img = (ImageView)findViewById(R.id.ImageView01);
		RotateAnimation rotateAnimation = new RotateAnimation(30, 90,
		    Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
		
		
	//	rotationAnimation.setDuration(Animation.INFINITE); 
	//	ImageView myRotatingImage = (ImageView) 
	//	mRoot.findViewById(R.id.my_rotating_image);
		//myRotatingImage.clearAnimation();
		
		
	}
}
