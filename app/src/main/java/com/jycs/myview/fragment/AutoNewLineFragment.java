package com.jycs.myview.fragment;

import android.view.View;
import android.widget.Toast;

import com.jycs.myview.R;
import com.jycs.myview.widget.AutoNewlineView;

/**
 * 作者: jack(黄冲)
 * 邮箱: 907755845@qq.com
 * create on 2018/5/7 09:53
 */

public class AutoNewLineFragment extends BaseFragment {


    @Override
    protected int getLayout() {
        return R.layout.fragment_auto_new_line;
    }

    @Override
    protected void init(View view) {
        AutoNewlineView autoNewLineView = (AutoNewlineView) view.findViewById(R.id.autoNewlineView);

        String[] strings = {"道姑牛B", "肖文祥最屌", "黎星大佬", "牛B", "吊", "个个都是人才", "吊炸天", "带我飞", "让我上车", "师傅带我", "大白菜带我", "菜哥威武", "教我kotln"};
        autoNewLineView.setAdapder(strings)
                .create();
    }
}
