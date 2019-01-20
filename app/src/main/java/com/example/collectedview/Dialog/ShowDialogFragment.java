package com.example.collectedview.Dialog;


import android.app.AlertDialog;
import android.app.Dialog;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;

import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.example.collectedview.R;
import com.example.collectedview.Utils.ScreenUtils;
import com.example.collectedview.fragment.ItemFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Creator :Wen
 * DataTime: 2019/1/19
 * Description:
 */
public class ShowDialogFragment extends DialogFragment implements View.OnClickListener{

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //设置背景透明
        //getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        View view= LayoutInflater.from(getActivity()).inflate(R.layout.dialog_show, null);
        layout=  view.findViewById(R.id.tablayout);
        pager=  view.findViewById(R.id.viewpager);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        Window window = getDialog().getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        params.gravity = Gravity.CENTER_VERTICAL;
        params.width = (int) (ScreenUtils.getScreenWidth()*0.94);
        params.height=(int) (ScreenUtils.getScreenHeight()*0.65);
        window.setAttributes(params);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        window.getDecorView().setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    dismiss();
                }
                return true;
            }
        });

    }




    TabLayout layout;
    ViewPager pager;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        List <Fragment>fragmentList = new ArrayList<>();
        List<String> list_Title = new ArrayList<>();
        fragmentList.add(new ItemFragment());
        fragmentList.add(new ItemFragment());
        list_Title.add("one");
        list_Title.add("two");
        layout.addTab(layout.newTab().setText("one"));
        layout.addTab(layout.newTab().setText("two"));
        pager.setAdapter(new MyPagerAdapter(getChildFragmentManager(),getActivity(),fragmentList,list_Title));
        layout.setupWithViewPager(pager);
    }



    @Override
    public void onClick(View v) {
        switch (v.getId()){

        }
    }

    public class MyPagerAdapter extends FragmentPagerAdapter {
        private Context context;
        private List<Fragment> fragmentList;
        private List<String> list_Title;

        public MyPagerAdapter(FragmentManager fm, Context context, List<Fragment> fragmentList, List<String> list_Title) {
            super(fm);
            this.context = context;
            this.fragmentList = fragmentList;
            this.list_Title = list_Title;
        }

        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        @Override
        public int getCount() {
            return list_Title.size();
        }

        /**
         * //此方法用来显示tab上的名字
         *
         * @param position
         * @return
         */
        @Override
        public CharSequence getPageTitle(int position) {
            return list_Title.get(position);
        }
    }
}
