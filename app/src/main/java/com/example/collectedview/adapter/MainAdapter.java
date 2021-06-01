package com.example.collectedview.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.collectedview.R;

import java.util.List;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.MainViewHolder> {

    private Context mContext;
    private List<String> mList;

    public MainAdapter(Context context, List<String> list) {
        mContext = context;
        mList = list;
    }

    @Override
    public MainViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_main, parent, false);
        MainViewHolder holderA = new MainViewHolder(view);
        return holderA;
    }

    @Override
    public void onBindViewHolder(MainViewHolder holder,int position) {
        holder.titleViewType.setText(mList.get(position));
        holder.titleViewType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class MainViewHolder extends RecyclerView.ViewHolder {

        TextView titleViewType;

        public MainViewHolder(View itemView) {
            super(itemView);
            titleViewType = itemView.findViewById(R.id.titleViewType);
        }
    }

    public OnItemClickListener onItemClickListener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
}
