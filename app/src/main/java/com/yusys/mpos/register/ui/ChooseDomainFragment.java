package com.yusys.mpos.register.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.Toast;

import com.yusys.mpos.R;
import com.yusys.mpos.base.ui.BaseFragment;
import com.yusys.mpos.register.adapter.DomainAdapter;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 选择从事行业界面
 * parent是RegisterActivity,注册流程第6步
 *
 * @author yuanshuai (marshall.yuan@foxmail.com)
 * @since 2016-05-26 09:43
 */
public class ChooseDomainFragment extends BaseFragment {

    private View fragmentView;
    private RegisterActivity parentActivity;
    @Bind(R.id.lv_domains)
    ListView lv_domains;

    private DomainAdapter adapter;
    private ArrayList<String> domainList;
    private boolean isChecked = false;// 是否选择行业

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (fragmentView == null) {
            fragmentView = inflater.inflate(R.layout.fragment_choose_domain, container, false);
            ButterKnife.bind(this, fragmentView);
            parentActivity = (RegisterActivity) getActivity();
            domainList = new ArrayList();
            domainList.add("便利店/超市");
            domainList.add("化妆品商店");
            domainList.add("美容美发美甲");
            domainList.add("鲜花水果");
            domainList.add("餐饮");
            domainList.add("医药保健品");
            domainList.add("儿童玩具");
            domainList.add("KTV/娱乐场所");
            domainList.add("医院/诊所");
            domainList.add("物流");
            adapter = new DomainAdapter(this, domainList);
            lv_domains.setAdapter(adapter);
            lv_domains.setOnItemClickListener(listener);
        }
        // 缓存Fragment,避免重新执行onCreateView
        ViewGroup parentView = (ViewGroup) fragmentView.getParent();
        if (parentView != null) {
            parentView.removeView(fragmentView);
        }
        return fragmentView;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onReveal() {
        super.onReveal();
        parentActivity.toolbar_title.setText("选择从事行业界面");
        parentActivity.toolbar_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                parentActivity.showFragment(parentActivity.fragments.get(4));
            }
        });
    }

    /**
     * 下一步
     */
    @SuppressWarnings("unused")
    @OnClick(R.id.btn_next_step)
    void nextStep(View view) {
        if (isChecked) {
            parentActivity.showFragment(parentActivity.fragments.get(6));
        } else {
            Toast.makeText(getActivity(), "请选择从事行业", Toast.LENGTH_SHORT).show();
        }
    }

    private AdapterView.OnItemClickListener listener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            for (int i = 0; i < lv_domains.getChildCount(); i++) {
                ((CheckBox) lv_domains.getChildAt(i).findViewById(R.id.cb_domain)).setChecked(false);
            }
            CheckBox cb = (CheckBox) lv_domains.getChildAt(position - lv_domains.getFirstVisiblePosition()).findViewById(R.id.cb_domain);
            cb.setChecked(true);
            isChecked = true;
        }
    };
}