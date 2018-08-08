package com.jycs.myview.fragment;

import android.view.View;

import com.jycs.myview.R;
import com.jycs.myview.widget.RingView;

/**
 * 作者: jack(黄冲)
 * 邮箱: 907755845@qq.com
 * create on 2018/5/7 21:35
 */

public class RingFragment extends BaseFragment {
    @Override
    protected int getLayout() {
        return R.layout.fragment_ring;
    }

    @Override
    protected void init(View view) {

        ((RingView) view.findViewById(R.id.ringView)).setProgress(100);
    }
}
