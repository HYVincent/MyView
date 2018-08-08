package com.jycs.myview;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import org.jetbrains.annotations.Nullable;

/**
 * 作者: jack(黄冲)
 * 邮箱: 907755845@qq.com
 * create on 2018/5/7 09:09
 */

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder>{

    private String[] mString;
    private OnItemClickListener mOnItemClickListener;

    public MyAdapter(String[] strings) {
        mString = strings;
    }

    @Override
    public MyAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyAdapter.MyViewHolder(View.inflate(parent.getContext(), R.layout.activity_main_item, null));
    }

    @Override
    public void onBindViewHolder(MyAdapter.MyViewHolder holder, final int position) {
        holder.itemView.setText(mString[position]);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnItemClickListener != null) mOnItemClickListener.onClick(v, position, mString[position]);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mString == null ? 0 : mString.length;
    }

    public void setOnItemClickListener(@Nullable OnItemClickListener listener) {
        mOnItemClickListener = listener;
    }

    interface OnItemClickListener{
        void onClick(View view, int position, String title);
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        Button itemView;

        public MyViewHolder(View itemView) {
            super(itemView);
            this.itemView = (Button) itemView;
        }
    }

}
