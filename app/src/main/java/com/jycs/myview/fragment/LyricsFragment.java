package com.jycs.myview.fragment;

import android.view.Gravity;
import android.view.View;

import com.jycs.myview.R;
import com.jycs.myview.widget.MyTextView;

/**
 * 作者: jack(黄冲)
 * 邮箱: 907755845@qq.com
 * create on 2018/5/7 21:42
 * 歌词效果
 */

public class LyricsFragment extends BaseFragment{
    @Override
    protected int getLayout() {
        return R.layout.fragment_lyrics;
    }

    @Override
    protected void init(View view) {

        MyTextView myTextView = (MyTextView) view.findViewById(R.id.tv);
        myTextView.setTextSize(40);
        myTextView.setTextColor(0xff000000);
        myTextView.setGravity(Gravity.CENTER);
        myTextView.setText("水电费第三方的双丰");
    }
}
