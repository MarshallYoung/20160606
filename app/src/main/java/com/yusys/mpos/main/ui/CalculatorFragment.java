package com.yusys.mpos.main.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.yusys.mpos.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 计算器
 *
 * @author yuanshuai (marshall.yuan@foxmail.com)
 * @date 2016-04-06 17:26
 */
public class CalculatorFragment extends Fragment {

    private View fragmentView;
    @Bind(R.id.toolbar_title)
    TextView toolbar_title;
    @Bind(R.id.calculator_result)
    TextView tv_result;// 结果
    double amount;// 金额

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (fragmentView == null) {
            fragmentView = inflater.inflate(R.layout.fragment_calculator,
                    container, false);
            ButterKnife.bind(this, fragmentView);
            toolbar_title.setText("收款");
            amount = 0;
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

    // 数字按钮的执行方法
    @SuppressWarnings("unused")
    @OnClick({R.id.calculator_0, R.id.calculator_1, R.id.calculator_2, R.id.calculator_3,
            R.id.calculator_4, R.id.calculator_5, R.id.calculator_6,
            R.id.calculator_7, R.id.calculator_8, R.id.calculator_9})
    void onClick1(View view) {
        String tvString = tv_result.getText().toString().trim();
        if (tvString.length() < 12) {// 最大金额千万级
            String text = ((Button) view).getText().toString();
            int value = Integer.valueOf(text);
            amount = amount * 10 + value * 0.01;
            String result = String.format("%.2f", amount);
            tv_result.setText(result);
        }
    }

    // 清除
    @SuppressWarnings("unused")
    @OnClick(R.id.calculator_c)
    void onClick3(View view) {
        tv_result.setText("0.00");
        amount = 0;
    }

    // 后退
    @SuppressWarnings("unused")
    @OnClick(R.id.calculator_back)
    void onClick4(View view) {
        String string = String.format("%.3f", amount / 10);
        String result = string.substring(0, string.length() - 1);// 去掉最后一位
        amount = Double.parseDouble(result);
        tv_result.setText(result);
    }

    // 刷卡
    @SuppressWarnings("unused")
    @OnClick(R.id.calculator_cash)
    void onClick2(View view) {
        Toast.makeText(getContext(), "刷卡", Toast.LENGTH_SHORT).show();
    }

}