package eu.trentorise.smartcampus.ifame.model;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

public class WebcamAspectRatioImageView extends ImageView {

	public WebcamAspectRatioImageView(Context context) {
		super(context);
	}

	public WebcamAspectRatioImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public WebcamAspectRatioImageView(Context context, AttributeSet attrs,
			int defStyle) {
		super(context, attrs, defStyle);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int width = widthMeasureSpec;
		int height = heightMeasureSpec;
		if (getDrawable() != null) {
			width = MeasureSpec.getSize(widthMeasureSpec);
			height = width * getDrawable().getIntrinsicHeight()
					/ getDrawable().getIntrinsicWidth();

		}
		setMeasuredDimension(width, height);
	}
}