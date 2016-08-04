package com.ywg.androidcommon.sample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.ywg.androidcommon.R;
import com.ywg.androidcommon.widget.CircularMusicProgressBar;

public class CircularMusicProgressBarActivity extends AppCompatActivity {


    CircularMusicProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_circular_music_progress_bar);

        progressBar = (CircularMusicProgressBar) findViewById(R.id.album_art);

        // set progress to 40%
        progressBar.setValue(40);

    }
}
