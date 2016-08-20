package com.ywg.androidcommon.sample;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;

import com.ywg.androidcommon.R;
import com.ywg.androidcommon.widget.BlurImageView.BlurImageView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class BlurImageViewActivity extends AppCompatActivity {

    @BindView(R.id.fast_blur_btn)
    Button fastBlurBtn;

    @BindView(R.id.full_blur_image_view)
    BlurImageView blurImageView;

    @BindView(R.id.image_indicator)
    TextView imageIndicator;

    boolean alreadyLoad = false;

    int[] mediumSmRes = {
            R.drawable.medium_sm_1,
            R.drawable.medium_sm_2,
            R.drawable.medium_sm_3,
            R.drawable.medium_sm_4
    };

    int[] mediumNmRes = {
            R.drawable.medium_nm_1,
            R.drawable.medium_nm_2,
            R.drawable.medium_nm_3,
            R.drawable.medium_nm_4
    };

    String[] mediumSmUrl = {
            "https://cdn-images-1.medium.com/freeze/max/60/1*J0q0NNg1j9q3yV_ZVt-saw.jpeg?q=20",
            "https://cdn-images-1.medium.com/freeze/max/60/1*HZcGmW8TuTeMJ8rEibGoyQ.jpeg?q=20",
            "https://cdn-images-1.medium.com/freeze/max/60/1*WYkY4km0sPBCRAw5B_3J2g.jpeg?q=20",
            "https://cdn-images-1.medium.com/freeze/max/30/1*Ez9M6IDiA_Frjz8TBiyzlA.jpeg?q=20"
    };

    String[] mediumNmUrl = {
            "https://cdn-images-1.medium.com/max/2000/1*J0q0NNg1j9q3yV_ZVt-saw.jpeg",
            "https://cdn-images-1.medium.com/max/2000/1*HZcGmW8TuTeMJ8rEibGoyQ.jpeg",
            "https://cdn-images-1.medium.com/max/2000/1*WYkY4km0sPBCRAw5B_3J2g.jpeg",
            "https://cdn-images-1.medium.com/max/2000/1*Ez9M6IDiA_Frjz8TBiyzlA.jpeg"
    };

    int[] blurImageViewProgressBgColor = {
            Color.BLACK,
            Color.BLACK,
            Color.parseColor("#E29C45"),
            Color.parseColor("#E29C45"),
    };

    int[] blurImageViewProgressClor = {
            Color.WHITE,
            Color.parseColor("#789262"),
            Color.parseColor("#7BCFA6"),
            Color.parseColor("#519A73"),
    };


    int currentIndex = 0;
    int getResIndex() {
        if (currentIndex > 3) {
            currentIndex = currentIndex - 4;
        }
        return currentIndex;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blur_image_view);

        ButterKnife.bind(this);

    }

    @OnClick(R.id.fast_blur_btn)
    void doFastBlur() {
        if (!alreadyLoad) {
            customizeBlurImageView();

            int blurFactor = BlurImageView.DEFAULT_BLUR_FACTOR;
            blurImageView.setBlurFactor(blurFactor);
            blurImageView.setFullImageByUrl(mediumSmUrl[getResIndex()], mediumNmUrl[getResIndex()]);

            alreadyLoad = true;
            fastBlurBtn.setText("Click and Clear current image");
            imageIndicator.setText((getResIndex() + 1) + "/" + mediumNmUrl.length);
        } else {
            blurImageView.clear();

            currentIndex++;
            alreadyLoad = false;
            fastBlurBtn.setText("Click to load new Image");
        }
    }

    private void customizeBlurImageView() {
        blurImageView.setProgressBarBgColor(blurImageViewProgressBgColor[getResIndex()]);
        blurImageView.setProgressBarColor(blurImageViewProgressClor[getResIndex()]);
    }

    @Override
    protected void onDestroy() {
        blurImageView.cancelImageRequestForSafty();
        super.onDestroy();
    }

}
