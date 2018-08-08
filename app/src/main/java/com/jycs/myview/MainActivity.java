package com.jycs.myview;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSmoothScroller;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;

import com.jycs.myview.fragment.AutoNewLineFragment;
import com.jycs.myview.fragment.ChartFragment;
import com.jycs.myview.fragment.ClockFragment;
import com.jycs.myview.fragment.DemoFragment;
import com.jycs.myview.fragment.LyricsFragment;
import com.jycs.myview.fragment.MagnifierFragment;
import com.jycs.myview.fragment.MyBoFragment;
import com.jycs.myview.fragment.RingFragment;
import com.jycs.myview.fragment.RippleFragment;
import com.jycs.myview.widget.AutoNewlineView;
import com.jycs.myview.widget.MyRecyclerView;

import java.util.List;

/**
 * 作者: jack(黄冲)
 * 邮箱: 907755845@qq.com
 * create on 2018/5/6 09:13
 */

public class MainActivity extends AppCompatActivity{

    private Class[] mClasses = {RippleFragment.class, MagnifierFragment.class, ClockFragment.class,
            AutoNewLineFragment.class, RingFragment.class, LyricsFragment.class, MyBoFragment.class,
            ChartFragment.class, DemoFragment.class};
    private MyRecyclerView mRecyclerView;
    private MyAdapter mMyAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initConfigure();

        setContentView(R.layout.activity_main);
        initRV();

        replaceFragment(0);
    }


    private void initConfigure() {
        WindowManager manager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        Display defaultDisplay = manager.getDefaultDisplay();
        DisplayMetrics screenSize = new DisplayMetrics();
        defaultDisplay.getMetrics(screenSize);

        CodeVal.sScreenWidth = screenSize.widthPixels;
        CodeVal.sScreenHeight = screenSize.heightPixels;
        WindowsInfo.sScreenWidth = screenSize.widthPixels;
        WindowsInfo.sScreenHeight = screenSize.heightPixels;
        WindowsInfo.sAutoScaleX = WindowsInfo.sScreenWidth * 1.0f / WindowsInfo.UI_WIDTH;
        WindowsInfo.sAutoScaleY = WindowsInfo.sScreenHeight * 1.0f / WindowsInfo.UI_HEIGHT;
    }

    private void initRV() {
        mRecyclerView = (MyRecyclerView) findViewById(R.id.rv);
        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        mRecyclerView.setLayoutManager(manager);

        String[] strings = {"水波纹", "放大镜", "时钟", "自动换行", "圆环", "歌词效果", "点菜", "图表", "demo"};
        mMyAdapter = new MyAdapter(strings);
        mMyAdapter.setOnItemClickListener(mOnItemClickListener);
        mRecyclerView.setAdapter(mMyAdapter);
    }

    private MyAdapter.OnItemClickListener mOnItemClickListener = new MyAdapter.OnItemClickListener() {
        @Override
        public void onClick(View view, int position, String title) {
            int childCount = mMyAdapter.getItemCount();
            int[] ints = new int[2];
            view.getLocationOnScreen(ints);
            int index = ints[0] > CodeVal.sScreenWidth / 2 ? position + 1 : position - 1;
            index = index > childCount ? childCount : index < 0 ? 0 : index;
            mRecyclerView.smoothScrollToPosition(index);
            replaceFragment(position);
        }
    };

    private void replaceFragment(int position){
        FragmentManager manager = getSupportFragmentManager();
        try {
            Fragment fragment = (Fragment) mClasses[position].newInstance();
            manager.beginTransaction().replace(R.id.frameLayout, fragment).show(fragment).commitNowAllowingStateLoss();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
