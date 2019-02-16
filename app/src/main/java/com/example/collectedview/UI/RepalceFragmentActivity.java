package com.example.collectedview.UI;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.collectedview.R;
import com.example.collectedview.fragment.ListFenZuFragment;

public class RepalceFragmentActivity extends AppCompatActivity {

    Fragment lastFragment;
    Button list_fz;
    ListFenZuFragment listFenZuFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repalce_fragment);
        list_fz=(Button)findViewById(R.id.list_fz);
        list_fz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listFenZuFragment=new ListFenZuFragment();
                lastFragment = listFenZuFragment;
                changeFragment(lastFragment, null);
                list_fz.setVisibility(View.GONE);
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        list_fz.setVisibility(View.VISIBLE);
    }

    private void changeFragment(Fragment from, Fragment to) {
        if (to != null) {
            lastFragment = to;
        }
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction mTrans = manager.beginTransaction();
        if (!from.isAdded()) {
            //
            mTrans.add(R.id.replace, from).commitAllowingStateLoss();
        }
        if (to == null) {
            return;
        }
        if (from == to) {
            return;
        } else {
            if (!to.isAdded()) {
                mTrans.hide(from).add(R.id.replace, to)
                        .commitAllowingStateLoss();
            } else {
                mTrans.hide(from).show(to).commitAllowingStateLoss();
            }
        }
    }


}
