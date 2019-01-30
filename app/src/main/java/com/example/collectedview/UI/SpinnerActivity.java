package com.example.collectedview.UI;

import android.annotation.SuppressLint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.LinearLayout;

import com.example.collectedview.R;

@SuppressLint("WrongViewCast")
public class SpinnerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spinner);
        LinearLayout ll= (LinearLayout)findViewById(R.id.ll);

    }
}
