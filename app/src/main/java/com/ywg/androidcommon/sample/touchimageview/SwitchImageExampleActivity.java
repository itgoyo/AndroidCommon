package com.ywg.androidcommon.sample.TouchImageView;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;

import com.ywg.androidcommon.R;
import com.ywg.androidcommon.widget.TouchImageView;

public class SwitchImageExampleActivity extends AppCompatActivity {
	
	private TouchImageView image;
	private static int[] images = { R.drawable.nature_1, R.drawable.nature_2, R.drawable.nature_3, R.drawable.nature_4 };
	int index;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_switch_image_example);
		image = (TouchImageView) findViewById(R.id.img);
		index = 0;
		//
		// Set first image
		//
		setCurrentImage();
		//
		// Set next image with each button click
		//
		image.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				setCurrentImage();
			}
		});
	}
	
	private void setCurrentImage() {
		image.setImageResource(images[index]);
		index = (++index % images.length);
	}

}
