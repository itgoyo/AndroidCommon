package com.ywg.androidcommon.sample.TouchImageView;

import android.graphics.PointF;
import android.graphics.RectF;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.ywg.androidcommon.R;
import com.ywg.androidcommon.widget.TouchImageView;

import java.text.DecimalFormat;


public class SingleTouchImageViewActivity extends AppCompatActivity {
	
	private TouchImageView image;
	private TextView scrollPositionTextView;
	private TextView zoomedRectTextView;
	private TextView currentZoomTextView;
	private DecimalFormat df;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_single_touchimageview);
		//
		// DecimalFormat rounds to 2 decimal places.
		//
		df = new DecimalFormat("#.##");
		scrollPositionTextView = (TextView) findViewById(R.id.scroll_position);
		zoomedRectTextView = (TextView) findViewById(R.id.zoomed_rect);
		currentZoomTextView = (TextView) findViewById(R.id.current_zoom);
		image = (TouchImageView) findViewById(R.id.img);
		
		//
		// Set the OnTouchImageViewListener which updates edit texts
		// with zoom and scroll diagnostics.
		//
		image.setOnTouchImageViewListener(new TouchImageView.OnTouchImageViewListener() {
			
			@Override
			public void onMove() {
				PointF point = image.getScrollPosition();
				RectF rect = image.getZoomedRect();
				float currentZoom = image.getCurrentZoom();
				boolean isZoomed = image.isZoomed();
				scrollPositionTextView.setText("x: " + df.format(point.x) + " y: " + df.format(point.y));
				zoomedRectTextView.setText("left: " + df.format(rect.left) + " top: " + df.format(rect.top)
						+ "\nright: " + df.format(rect.right) + " bottom: " + df.format(rect.bottom));
				currentZoomTextView.setText("getCurrentZoom(): " + currentZoom + " isZoomed(): " + isZoomed);
			}
		});
	}
}
