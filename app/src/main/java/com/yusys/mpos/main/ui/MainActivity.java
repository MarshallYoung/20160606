package com.yusys.mpos.main.ui;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;

import com.yusys.mpos.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 主界面
 *
 * @author yuanshuai (marshall.yuan@foxmail.com)
 * @since 2016-04-06 17:16
 */
public class MainActivity extends FragmentActivity {

    @Bind(android.R.id.tabhost)
    FragmentTabHost tabHost;
    private LayoutInflater inflater;

    private Class classes[] = {CalculatorFragment.class, FunctionFragment.class, MeFragment.class};
    private int selectors[] = {R.drawable.selector_tab_2,
            R.drawable.selector_tab_3, R.drawable.selector_tab_4};
    private String titles[] = {"收款", "功能", "我"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        inflater = LayoutInflater.from(this);
        initView();
        tabHost.setCurrentTab(0);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }

    private void initView() {
        tabHost.setup(this, getSupportFragmentManager(), R.id.framecontent);
        int count = classes.length;
        for (int i = 0; i < count; i++) {
            //为每一个Tab按钮设置图标、文字和内容
            TabSpec tabSpec = tabHost.newTabSpec(titles[i]).setIndicator(getTabItemView(i));
            //将Tab按钮添加进Tab选项卡中
            tabHost.addTab(tabSpec, classes[i], null);
        }
    }

    /**
     * 给Tab按钮设置图标和文字
     */
    private View getTabItemView(int index) {
        View root = inflater.inflate(R.layout.item_tab, null);
        ImageView imageView = (ImageView) root.findViewById(R.id.imageview);
        imageView.setImageResource(selectors[index]);
        TextView textView = (TextView) root.findViewById(R.id.textview);
        textView.setText(titles[index]);
        return root;
    }
}