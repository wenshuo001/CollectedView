package com.example.collectedview.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.collectedview.R;
import com.example.collectedview.UI.SpinnerActivity;

import java.util.List;
import java.util.Map;

public class ListRiskAreaListsDemoAdapter  extends BaseAdapter {

    private SpinnerActivity mContext;
    //单行的布局
    private int mResource;
    //列表展现的数据
    private List<String> mData;

    /**
     * 构造方法
     * @param context
     * @param data 列表展现的数据
     * @param resource 单行的布局
     */
    public ListRiskAreaListsDemoAdapter(SpinnerActivity context, List<String> data,
                                        int resource){
        mContext = context;
        mData = data;
        mResource = resource;
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position){
        return position;
    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent){
        final ListRiskAreaListsDemoAdapter.ViewHolder holder;
        if(convertView == null){
            //使用自定义的list_items作为Layout
            convertView = LayoutInflater.from(mContext).inflate(mResource, parent, false);
            //使用减少findView的次数
            holder = new ListRiskAreaListsDemoAdapter.ViewHolder();
            holder.tvAreaItem =(TextView) convertView.findViewById(R.id.tvAreaItem);
            //设置标记
            convertView.setTag(holder);
        }else{
            holder = (ListRiskAreaListsDemoAdapter.ViewHolder) convertView.getTag();
        }
        holder.tvAreaItem.setText(mData.get(position));
        holder.tvAreaItem.setTextColor(mContext.getResources().getColor(R.color.black));
        if (checked == position) {
            //如果点击的项目正好是position这一项
            holder.tvAreaItem.setTextColor(mContext.getResources().getColor(R.color.homeworktext));
        }
        return convertView;
    }

    /**
     * ViewHolder类
     */
    static class ViewHolder {
        TextView tvAreaItem;//区域名
    }

    private int checked = -1;//初始选择为-1，position没有-1嘛，那就是谁都不选咯
    public void setChecked(int checked) {//设定一个选中的标志位，在activity中传入值。
        this.checked = checked;
    }

}