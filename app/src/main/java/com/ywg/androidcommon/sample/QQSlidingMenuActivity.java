package com.ywg.androidcommon.sample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.ywg.androidcommon.R;
import com.ywg.androidcommon.widget.QQSlidingMenu;

public class QQSlidingMenuActivity extends AppCompatActivity {
    private QQSlidingMenu mLeftMenuLayout;
    private Button btnLeft;
    private Button btnRight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qqsliding_menu);

        mLeftMenuLayout = (QQSlidingMenu) findViewById(R.id.leftMenuLayout);
        btnLeft = (Button) findViewById(R.id.btTestClickLeft);
        btnRight = (Button) findViewById(R.id.btTestClickRight);

        btnLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mLeftMenuLayout.closeMenu();
            }
        });
        btnRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mLeftMenuLayout.openMenu();
            }
        });

        mLeftMenuLayout.setMenuChangedListener(new QQSlidingMenu.MenuChangedListener() {
            @Override
            public void onChanged(boolean isOpen) {
                Toast.makeText(QQSlidingMenuActivity.this, "抽屉是否打开： " + isOpen, Toast.LENGTH_SHORT).show();
            }
        });

    }
}
