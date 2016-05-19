package com.yusys.mpos.merchant.ui;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.yusys.mpos.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 拍照
 *
 * @author yuanshuai (marshall.yuan@foxmail.com)
 * @since 2016-05-18 17:41
 */
public class TakePhotoActivity extends Activity {

    /**
     * 用来区分返回数据类型
     */
    interface requestCode {
        int TAKE_PHOTO = 1;// 拍照
    }

    @Bind(R.id.iv_photo)
    ImageView iv_photo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_photo);
        ButterKnife.bind(this);
        initView();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == TakePhotoActivity.requestCode.TAKE_PHOTO) {
            if (resultCode == RESULT_OK) {
                Bitmap bm = (Bitmap) data.getExtras().get("data");
                iv_photo.setImageBitmap(bm);//想图像显示在ImageView视图上，private ImageView img;
            }
        }
    }

    void initView() {

    }

    /**
     * 拍照
     */
    @SuppressWarnings("unused")
    @OnClick(R.id.btn_take_photo)
    void takePhoto(View view) {
        startActivityForResult(new Intent("android.media.action.IMAGE_CAPTURE"), requestCode.TAKE_PHOTO);
    }
}