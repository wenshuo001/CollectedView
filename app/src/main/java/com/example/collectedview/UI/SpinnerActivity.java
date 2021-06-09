package com.example.collectedview.UI;

import android.annotation.SuppressLint;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.collectedview.Dialog.Solve7PopupWindow;
import com.example.collectedview.JavaView.ExpandLinearLayout;
import com.example.collectedview.R;
import com.example.collectedview.adapter.ListRiskAreaListsDemoAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class SpinnerActivity extends AppCompatActivity {
    RelativeLayout ll;
    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spinner);
          ll=(RelativeLayout) findViewById(R.id.rl_spinner);
        textView =(TextView) findViewById(R.id.textView);
          ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showRiskAreaPopupWindow();
            }
        });
        textView.setTextColor(getResources().getColor(R.color.homeworktext));

        findViewById(R.id.ell).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean toggle = ((ExpandLinearLayout)findViewById(R.id.ell)).toggle();
                //tv_tip.text = if (toggle) "收起" else "展开"

            }
        });
    }

    //PopupWindow菜单详细内容显示
    Solve7PopupWindow mPopWindow;
    List<String> riskAreaList;
    ListRiskAreaListsDemoAdapter listRiskAreaListsDemoAdapter;
    TextView oldTxtView;
    View contentView;
    private void showRiskAreaPopupWindow() {
        //设置contentView
        if(contentView==null){
            contentView = LayoutInflater.from(this).inflate(R.layout.newpages_activity_risk_area_popup_demo, null);
            mPopWindow = new Solve7PopupWindow(contentView, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            mPopWindow.setContentView(contentView);
            ListView lvRiskArea = (ListView)contentView.findViewById(R.id.lvRiskArea);
            riskAreaList= new ArrayList<>();
            riskAreaList.add("全部年级");
            riskAreaList.add("一年级");
            riskAreaList.add("二年级");
            riskAreaList.add("三年级");
            listRiskAreaListsDemoAdapter = new ListRiskAreaListsDemoAdapter( this, riskAreaList, R.layout.newpages_risk_area_popup_list_item_demo);
            lvRiskArea.setAdapter(listRiskAreaListsDemoAdapter);
            if (oldTxtView!=null){
                oldTxtView.setTextColor(getResources().getColor(R.color.homeworktext));
            }
            lvRiskArea.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    textView.setText(riskAreaList.get(position));
                    listRiskAreaListsDemoAdapter.setChecked(position);//传入现在选择的position 现在用到了
                    listRiskAreaListsDemoAdapter.notifyDataSetInvalidated();//重绘
                    mPopWindow.dismiss();
                }
            });
            //解决5.0以下版本点击外部不消失问题
            mPopWindow.setOutsideTouchable(true);
            mPopWindow.setFocusable(true);
            mPopWindow.setBackgroundDrawable(new BitmapDrawable());
            //显示方式
            mPopWindow.showAsDropDown(textView);
        }else {
            mPopWindow.showAsDropDown(textView);
        }


    }
}
