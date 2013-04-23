package eu.trentorise.smartcampus.ifame.fragment;

import java.io.InputStream;
import java.net.URL;

import android.app.Fragment;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import eu.trentorise.smartcampus.ifame.R;
import eu.trentorise.smartcampus.storage.model.Resource;

public class IFretta_Details extends Fragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater
				.inflate(R.layout.webcam_fragment, container, false);
		
		/*
		Drawable drawable = LoadImageFromWebOperations("http://www.operauni.tn.it/upload/cms/456_x/mensa-povo1.jpg");
		ImageView img_view = (ImageView) view.findViewById(R.id.imageViewID);
		
		img_view.setImageDrawable(drawable);
*/
		return view; 
	}
	
	/*public static Drawable LoadImageFromWebOperations(String url) {
	    try {
	        InputStream is = (InputStream) new URL(url).getContent();
	        Drawable d = Drawable.createFromStream(is, "src name");
	        return d;
	    } catch (Exception e) {
	        return null;
	    }
	} */
}
