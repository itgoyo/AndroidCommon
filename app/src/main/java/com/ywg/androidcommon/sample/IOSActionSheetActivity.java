package com.ywg.androidcommon.sample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.ywg.androidcommon.R;
import com.ywg.androidcommon.widget.IOSActionSheet;


public class IOSActionSheetActivity extends AppCompatActivity {

    private Button mBtnPop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ios_action_sheet);
        mBtnPop = (Button) findViewById(R.id.btn_pop);

        mBtnPop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                IOSActionSheet.createBuilder(IOSActionSheetActivity.this, getSupportFragmentManager())
                        .setCancelButtonTitle("Cancel")
                        .setOtherButtonTitles("Item1", "Item2", "Item3", "Item4")
                        .setCancelableOnTouchOutside(true)
                        .setListener(new IOSActionSheet.ActionSheetListener() {

                            @Override
                            public void onDismiss(IOSActionSheet actionSheet, boolean isCancel) {

                            }

                            @Override
                            public void onOtherButtonClick(IOSActionSheet actionSheet, int index) {
                                switch(index) {
                                    case 0:
                                    case 1:
                                    case 2:
                                    case 3:
                                        Toast.makeText(IOSActionSheetActivity.this, "Item " + (index + 1) + " Clicked", Toast.LENGTH_SHORT).show();
                                    break;
                                }
                            }
                        })
                        .show();
            }
        });
    }


}
