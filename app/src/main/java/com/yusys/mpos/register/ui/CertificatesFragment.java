package com.yusys.mpos.register.ui;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;

import com.yusys.mpos.R;
import com.yusys.mpos.base.ui.BaseFragment;
import com.yusys.mpos.login.ui.CameraActivity;
import com.yusys.mpos.register.adapter.CertificateAdapter;
import com.yusys.mpos.register.adapter.CertificateItem;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 证件照片界面
 * parent是RegisterActivity,注册流程第4步
 *
 * @author yuanshuai (marshall.yuan@foxmail.com)
 * @since 2016-05-26 10:56
 */
public class CertificatesFragment extends BaseFragment {

    private View fragmentView;
    private RegisterActivity parentActivity;
    @Bind(R.id.gv_certificates)
    GridView gv_certificates;// 证件列表

    CertificateAdapter adapter;
    private ArrayList itemList;
    private int[] images = {R.drawable.putin1, R.drawable.idcard_back, R.drawable.putin3, R.drawable.credit_card};
    private String[] texts = {"身份证正面", "身份证反面", "个人在门店照", "信用卡正面照"};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (fragmentView == null) {
            fragmentView = inflater.inflate(R.layout.fragment_certificates, container, false);
            ButterKnife.bind(this, fragmentView);
            parentActivity = (RegisterActivity) getActivity();
            itemList = new ArrayList();
            for (int i = 0; i < texts.length; i++) {
                CertificateItem item = new CertificateItem();
                item.image = images[i];
                item.text = texts[i];
                itemList.add(item);
            }
            adapter = new CertificateAdapter(this, itemList);
            gv_certificates.setAdapter(adapter);
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
        parentActivity.toolbar_title.setText("证件照片");
        parentActivity.toolbar_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                parentActivity.showFragment(parentActivity.fragments.get(2));
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            Bundle bundle = data.getExtras();
            byte[] bytes = bundle.getByteArray("bytes");
            Bitmap bm = byteToBitmap(bytes);
            ImageView imageView = (ImageView) gv_certificates.getChildAt(requestCode)
                    .findViewById(R.id.iv_certificate_photo);
            imageView.setImageBitmap(bm);
        }
    }

    /**
     * 点击每个照片的添加按钮,拍照
     *
     * @param position 点击的按钮所在的item下标
     */
    public void takePhoto(int position) {
        startActivityForResult(new Intent(getActivity(), CameraActivity.class), position);
    }

    /**
     * 下一步
     */
    @SuppressWarnings("unused")
    @OnClick(R.id.btn_next_step)
    void nextStep(View view) {
        parentActivity.showFragment(parentActivity.fragments.get(4));
    }

    /**
     * 把图片byte流编程bitmap
     */
    private Bitmap byteToBitmap(byte[] data) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        Bitmap b = BitmapFactory.decodeByteArray(data, 0, data.length, options);
        int i = 0;
        while (true) {
            if ((options.outWidth >> i <= 1000)
                    && (options.outHeight >> i <= 1000)) {
                options.inSampleSize = (int) Math.pow(2.0D, i);
                options.inJustDecodeBounds = false;
                b = BitmapFactory.decodeByteArray(data, 0, data.length, options);
                break;
            }
            i += 1;
        }
        return b;
    }
}