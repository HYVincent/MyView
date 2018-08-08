package com.jycs.myview.fragment;

import android.util.Log;
import android.view.View;

import com.jycs.myview.R;
import com.jycs.myview.widget.DeviceChartView;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * 作者: jack(黄冲)
 * 邮箱: 907755845@qq.com
 * create on 2018/5/11 17:22
 */

public class ChartFragment extends BaseFragment {
    @Override
    protected int getLayout() {
        return R.layout.fragment_chart;
    }

    @Override
    protected void init(View view) {

        DeviceChartView chartView = (DeviceChartView) view.findViewById(R.id.deviceChartView);
        chartView.setParam(DeviceChartView.StateOrder.day, 0, 300, "30");

        List<String> list = new ArrayList<>();
        Random random = new Random();
        for (int i = 0; i < 2000; i++) {
            list.add(String.valueOf(random.nextInt(500)));
        }
        chartView.setData(list);

        long l = 1377708139616927744L;

        Log.e("TAG", new StringBuffer(Long.toHexString(l)).reverse().toString().toUpperCase());
    }

}
