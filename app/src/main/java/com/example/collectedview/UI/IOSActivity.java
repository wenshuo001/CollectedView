package com.example.collectedview.UI;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.collectedview.R;
import com.example.collectedview.Utils.StatusBarUtil;

public class IOSActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wifi);
        StatusBarUtil.setColor(this,0xff409EFF,0);
    }
}
