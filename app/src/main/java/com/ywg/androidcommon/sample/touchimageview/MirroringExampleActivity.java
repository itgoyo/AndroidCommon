package com.ywg.androidcommon.sample.TouchImageView;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.ywg.androidcommon.R;
import com.ywg.androidcommon.widget.TouchImageView;

public class MirroringExampleActivity extends AppCompatActivity {
	
	TouchImageView topImage;
	TouchImageView bottomImage;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mirroring_example);
		topImage = (TouchImageView) findViewById(R.id.topImage);
		bottomImage = (TouchImageView) findViewById(R.id.bottomImage);
		
		//
		// Each image has an OnTouchImageViewListener which uses its own TouchImageView
		// to set the other TIV with the same zoom variables.
		//
		topImage.setOnTouchImageViewListener(new TouchImageView.OnTouchImageViewListener() {
			
			@Override
			public void onMove() {
				bottomImage.setZoom(topImage);
			}
		});
		
		bottomImage.setOnTouchImageViewListener(new TouchImageView.OnTouchImageViewListener() {
			
			@Override
			public void onMove() {
				topImage.setZoom(bottomImage);
			}
		});
	}
}
