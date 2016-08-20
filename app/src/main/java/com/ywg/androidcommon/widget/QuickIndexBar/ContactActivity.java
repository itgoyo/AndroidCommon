package com.ywg.androidcommon.widget.QuickIndexBar;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;

import com.ywg.androidcommon.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ContactActivity extends AppCompatActivity {

    RecyclerView recyclerView;

    SideBar sidebar;

    TextView textDialog;

    private List<ConatactModel> data;
    ContactAdapter mContactAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        sidebar = (SideBar) findViewById(R.id.sidebar);
        textDialog = (TextView) findViewById(R.id.textDialog);

        sidebar.setTextView(textDialog);
        // 设置字母导航触摸监听
        sidebar.setOnTouchingLetterChangedListener(new SideBar.OnTouchingLetterChangedListener() {

            @Override
            public void onTouchingLetterChanged(String s) {
                // TODO Auto-generated method stub
                // 该字母首次出现的位置
                int position = mContactAdapter.getPositionForSelection(s.charAt(0));

                if (position != -1) {
                    recyclerView.getLayoutManager().scrollToPosition(position);
                }
            }
        });
        data = getData(getResources().getStringArray(R.array.listpersons));
        // 数据在放在adapter之前需要排序
        Collections.sort(data, new PinyinComparator());
        for (int i = 0; i < data.size(); i++) {
            Log.i("wxl", "Fpinyin=" + data.get(i).getFirstPinYin());
        }
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        mContactAdapter = new ContactAdapter(data);
        recyclerView.setAdapter(mContactAdapter);
    }

    private List<ConatactModel> getData(String[] data) {
        List<ConatactModel> listarray = new ArrayList<ConatactModel>();
        for (int i = 0; i < data.length; i++) {
            String pinyin = PinyinUtil.getPingYin(data[i]);
            String Fpinyin = pinyin.substring(0, 1).toUpperCase();

            ConatactModel person = new ConatactModel();
            person.setName(data[i]);
            person.setPinYin(pinyin);
            // 正则表达式，判断首字母是否是英文字母
            if (Fpinyin.matches("[A-Z]")) {
                person.setFirstPinYin(Fpinyin);
            } else {
                person.setFirstPinYin("#");
            }

            listarray.add(person);
        }

        return listarray;

    }  
   
}