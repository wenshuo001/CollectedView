package com.example.collectedview.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.example.collectedview.R;
import com.example.collectedview.adapter.ExpandableItemAdapter;

import java.util.ArrayList;

/**
 * Creator :Wen
 * DataTime: 2019/1/19
 * Description:
 */
public class ListFenZuFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_listfenzu,container,false);
    }
    RecyclerView rl_view;
    ExpandableItemAdapter adapter;
    ArrayList<MultiItemEntity> list;
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        rl_view= (RecyclerView)getView().findViewById(R.id.rl_view);

    }
}
