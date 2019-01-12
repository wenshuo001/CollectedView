package com.example.collectedview.Dialog;


import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.collectedview.R;
import com.example.collectedview.Utils.AnimationUtils;

/**
 * Creator :Wen
 * DataTime: 2019/1/7
 * Description:
 */
public class CenterDialogFragment extends DialogFragment {

    public CenterDialogFragment() {
    }

    private boolean isAnimation = false;
    private String mTitle;
    private String mCancel;
    private String[] items;
    private View mRootView;
    private OnClickListener mListener;

    public void setListener(OnClickListener listener) {
        mListener = listener;
    }

    @Override
    public void onStart() {
        super.onStart();
        Window window = getDialog().getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        params.gravity = Gravity.CENTER_VERTICAL;
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
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

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        initData();
        mRootView = inflater.inflate(R.layout.dialogfragment_layout, container, false);
        AnimationUtils.slideToUp(mRootView);
        return mRootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


    }

    private void initData() {
        mTitle = getArguments().getString("title");
        items = getArguments().getStringArray("items");
        mCancel = getArguments().getString("cancel");

    }

    @Override
    public void dismiss() {
        if (isAnimation) {
            return;
        }
        isAnimation = true;
        AnimationUtils.slideToDown(mRootView, new AnimationUtils.AnimationListener() {
            @Override
            public void onFinish() {
                isAnimation = false;
                CenterDialogFragment.super.dismiss();
            }
        });
    }



    public interface OnClickListener {
        void click(int position);
    }
}
