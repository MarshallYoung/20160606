package com.yusys.mpos.main.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import com.yusys.mpos.R;
import com.yusys.mpos.main.adapter.FunctionAdapter;
import com.yusys.mpos.main.adapter.FunctionItem;
import com.yusys.mpos.merchant.ui.TakePhotoActivity;
import com.yusys.mpos.security.ui.SignActivity;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 功能
 *
 * @author yuanshuai (marshall.yuan@foxmail.com)
 * @since 2016-04-06 17:26
 */
public class FunctionFragment extends Fragment {

    private View fragmentView;
    @Bind(R.id.toolbar_title)
    TextView toolbar_title;
    @Bind(R.id.gv_function)
    GridView gv_function;// 功能列表

    Class[] classes = {};
    int[] images = {R.drawable.weather, R.drawable.notes, R.drawable.mail,
            R.drawable.calculator, R.drawable.shopping, R.drawable.video};
    String[] texts = {"拍照", "签名", "功能3", "功能4", "很长的一个字符串测试", "功能6"};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (fragmentView == null) {
            fragmentView = inflater.inflate(R.layout.fragment_function,
                    container, false);
            ButterKnife.bind(this, fragmentView);
            toolbar_title.setText("功能");
        }
        // 缓存Fragment,避免重新执行onCreateView
        ViewGroup parentView = (ViewGroup) fragmentView.getParent();
        if (parentView != null) {
            parentView.removeView(fragmentView);
        }
        return fragmentView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ArrayList itemList = new ArrayList();
        for (int i = 0; i < texts.length; i++) {
            FunctionItem item = new FunctionItem();
            item.image = images[i];
            item.text = texts[i];
            // TODO target没写呢
            itemList.add(item);
        }
        FunctionAdapter adapter = new FunctionAdapter(getContext(), itemList);
        gv_function.setAdapter(adapter);
        gv_function.setOnItemClickListener(itemClickListener);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }

    /**
     * 点击功能列表执行的方法
     */
    private AdapterView.OnItemClickListener itemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            switch (position) {
                case 0:// 拍照
                    Intent photoIntent = new Intent(getActivity(), TakePhotoActivity.class);
                    startActivity(photoIntent);
                    getActivity().overridePendingTransition(R.anim.slide_in_from_right, R.anim.slide_out_to_left);
                    break;
                case 1:// 签名
                    Intent signIntent = new Intent(getActivity(), SignActivity.class);
                    startActivity(signIntent);
                    getActivity().overridePendingTransition(R.anim.slide_in_from_right, R.anim.slide_out_to_left);
                    break;
            }
        }
    };
}