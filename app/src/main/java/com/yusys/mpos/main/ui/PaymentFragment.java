package com.yusys.mpos.main.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yusys.mpos.R;
import com.yusys.mpos.creditcard.ui.RefundActivity;
import com.yusys.mpos.login.ui.CameraActivity;
import com.yusys.mpos.main.adapter.FunctionAdapter;
import com.yusys.mpos.main.adapter.FunctionItem;
import com.yusys.mpos.pay.ui.LandiActivity;
import com.yusys.mpos.security.ui.SignActivity;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 付款
 *
 * @author yuanshuai (marshall.yuan@foxmail.com)
 * @since 2016-04-06 17:26
 */
public class PaymentFragment extends Fragment {

    private View fragmentView;
    @Bind(R.id.toolbar_title)
    TextView toolbar_title;
    @Bind(R.id.toolbar_back)
    LinearLayout toolbar_back;
    @Bind(R.id.toolbar_right_button)
    Button toolbar_rightButton;
    @Bind(R.id.gv_function)
    GridView gv_function;// 功能列表

    int[] images = {R.drawable.icon_orange_agreement, R.drawable.icon_orange_agreement,
            R.drawable.icon_orange_agreement, R.drawable.icon_orange_agreement,
            R.drawable.icon_orange_agreement, R.drawable.icon_orange_agreement};
    String[] texts = {"拍照", "签名", "联迪蓝牙测试", "联迪", "百富", "功能6"};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (fragmentView == null) {
            fragmentView = inflater.inflate(R.layout.fragment_payment_test, container, false);
            ButterKnife.bind(this, fragmentView);
            initView();
        }
        // 缓存Fragment,避免重新执行onCreateView
        ViewGroup parentView = (ViewGroup) fragmentView.getParent();
        if (parentView != null) {
            parentView.removeView(fragmentView);
        }
        return fragmentView;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }

    void initView() {
        toolbar_title.setText("付款");
        toolbar_back.setVisibility(View.INVISIBLE);
        toolbar_rightButton.setVisibility(View.INVISIBLE);
        gv_function.setVisibility(View.INVISIBLE);
        ArrayList itemList = new ArrayList();
        for (int i = 0; i < texts.length; i++) {
            FunctionItem item = new FunctionItem();
            item.image = images[i];
            item.text = texts[i];
            itemList.add(item);
        }
        FunctionAdapter adapter = new FunctionAdapter(getActivity(), itemList);
        gv_function.setAdapter(adapter);
        gv_function.setOnItemClickListener(itemClickListener);
    }

    /**
     * 信用卡还款
     */
    @SuppressWarnings("unused")
    @OnClick(R.id.ll_credit_card_refund)
    void creditCardRefund(View view) {
        getActivity().startActivity(new Intent(getActivity(), RefundActivity.class));
    }

    /**
     * 测试
     */
    @SuppressWarnings("unused")
    @OnClick(R.id.btn_test)
    void test(View view) {
        if (gv_function.getVisibility() == View.INVISIBLE) {
            gv_function.setVisibility(View.VISIBLE);
        } else if (gv_function.getVisibility() == View.VISIBLE) {
            gv_function.setVisibility(View.INVISIBLE);
        }
    }

    /**
     * 点击功能列表执行的方法
     */
    private AdapterView.OnItemClickListener itemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            switch (position) {
                case 0:// 拍照
                    redirectTo(CameraActivity.class);
                    break;
                case 1:// 签名
                    redirectTo(SignActivity.class);
                    break;
                case 2:// 联迪蓝牙测试
                    break;
                case 3:// 联迪
                    redirectTo(LandiActivity.class);
                    break;
                case 4:// 百富
                    break;
            }
        }
    };

    /**
     * 跳转界面
     *
     * @param cls 目标界面
     */
    private void redirectTo(Class cls) {
        Intent intent = new Intent(getActivity(), cls);
        startActivity(intent);
        getActivity().overridePendingTransition(R.anim.slide_in_from_right, R.anim.slide_out_to_left);
    }
}