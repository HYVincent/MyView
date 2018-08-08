package com.jycs.myview.fragment;

import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.jycs.myview.MainActivity;
import com.jycs.myview.R;
import com.jycs.myview.widget.MyBoView;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者: jack(黄冲)
 * 邮箱: 907755845@qq.com
 * create on 2018/5/7 22:13
 */

public class MyBoFragment extends BaseFragment {
    @Override
    protected int getLayout() {
        return R.layout.fragment_my_bo;
    }

    @Override
    protected void init(View view) {
        ((ViewGroup) view).addView(initLabellingLinearLayout());
    }

    private View initLabellingLinearLayout() {

        MyBoView.MyBoBean myBoBean = new MyBoView.MyBoBean();
        myBoBean.setLabelling(1);
        myBoBean.setTitleText("黄焖鸡");
        myBoBean.setTitleBackgroundColor(0xff0000ff);

        MyBoView.MyBoBean myBoBean1 = new MyBoView.MyBoBean();
        myBoBean1.setTitleText("盖浇饭");
        myBoBean1.setTitleBackgroundColor(0xffff00ff);

        MyBoView.MyBoBean myBoBean2 = new MyBoView.MyBoBean();
        myBoBean2.setTitleText("回锅肉");
        myBoBean2.setTitleBackgroundColor(0xffff0000);

        MyBoView.MyBoBean myBoBean3 = new MyBoView.MyBoBean();
        myBoBean3.setTitleText("小炒肉");
        myBoBean3.setTitleBackgroundColor(0xff00ff00);

        MyBoView.MyBoBean myBoBean4 = new MyBoView.MyBoBean();
        myBoBean4.setLabelling(13);
        myBoBean4.setTitleText("米线");
        myBoBean4.setTitleBackgroundColor(0xff00ffff);

        MyBoView.MyBoBean myBoBean5 = new MyBoView.MyBoBean();
        myBoBean5.setTitleText("麻辣香锅");
        myBoBean5.setTitleBackgroundColor(0xffff8800);

        List<MyBoView.MyBoBean> myBoBeen = new ArrayList<>();
        myBoBeen.add(myBoBean);
        myBoBeen.add(myBoBean1);
        myBoBeen.add(myBoBean2);
        myBoBeen.add(myBoBean3);
        myBoBeen.add(myBoBean4);
        myBoBeen.add(myBoBean5);


        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);
        MyBoView myBoView = new MyBoView(mActivity);
        myBoView.setOnMyBoClickListener(new MyBoView.OnMyBoClickListener() {
            @Override
            public void onClick(View parent, View childView, int parentPosition, int childPosition) {
                Toast.makeText(mActivity, "外层" + parentPosition + "=====" + "内层" + childPosition, Toast.LENGTH_SHORT).show();
            }
        });
        myBoView.setAdapder(myBoBeen);
        myBoView.setLayoutParams(layoutParams);
        return myBoView;
    }
}
