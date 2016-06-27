package com.yusys.mpos.main.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;

import com.yusys.mpos.R;
import com.yusys.mpos.base.manager.AppManager;

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

    private Class classes[] = {GatheringFragment.class, PaymentFragment.class, MeFragment.class};
    private int selectors[] = {R.drawable.selector_tab_gathering,
            R.drawable.selector_tab_payment, R.drawable.selector_tab_me};
    private String titles[] = {"收款", "付款", "我"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        AppManager.getInstance().addActivity(this);
        inflater = LayoutInflater.from(this);
        initView();
        tabHost.setCurrentTab(0);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
        AppManager.getInstance().finishActivity(this);
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
        ImageView imageView = (ImageView) root.findViewById(R.id.iv_tab_item);
        imageView.setImageResource(selectors[index]);
        TextView textView = (TextView) root.findViewById(R.id.tv_tab_item);
        textView.setText(titles[index]);
        return root;
    }

    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
        overridePendingTransition(R.anim.slide_in_from_right, R.anim.slide_out_to_left);
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_from_left, R.anim.slide_out_to_right);
    }
}