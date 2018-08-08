package com.jycs.myview.fragment;

import android.view.View;

import com.jycs.myview.R;
import com.jycs.myview.widget.ChartBean;
import com.jycs.myview.widget.ChartView;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者: jack(黄冲)
 * 邮箱: 907755845@qq.com
 * create on 2018/7/31 14:51
 */

public class DemoFragment extends BaseFragment{


    @Override
    protected int getLayout() {
        return R.layout.fragment_demo;
    }

    @Override
    protected void init(View view) {
        ChartView chartView = ((ChartView) view.findViewById(R.id.chartView));

        List<ChartBean> chartBeans = new ArrayList<>();

        ChartBean chartBean = new ChartBean();
        chartBean.setStartTime("1111-11-11 01:00:00");
        chartBean.setEndTime("1111-11-11 03:00:00");
        chartBean.setSleepStatus(1);

        ChartBean chartBean1 = new ChartBean();
        chartBean1.setStartTime("1111-11-11 04:00:00");
        chartBean1.setEndTime("1111-11-11 06:30:00");
        chartBean1.setSleepStatus(1);

        ChartBean chartBean2 = new ChartBean();
        chartBean2.setStartTime("1111-11-11 07:00:00");
        chartBean2.setEndTime("1111-11-11 11:30:00");
        chartBean2.setSleepStatus(0);

        chartBeans.add(chartBean);
        chartBeans.add(chartBean1);
        chartBeans.add(chartBean2);
        chartView.setData(chartBeans);

        int sleepHour = chartView.getSleepHour();
        int sleepMin = chartView.getSleepMin();
        int deepSleepHour = chartView.getDeepSleepHour();
        int deepSleepMin = chartView.getDeepSleepMin();
    }
}
