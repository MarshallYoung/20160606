package com.yusys.mpos.login.ui;

import android.content.Intent;
import android.graphics.PixelFormat;
import android.hardware.Camera;
import android.hardware.Camera.PictureCallback;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;

import com.yusys.mpos.R;
import com.yusys.mpos.base.manager.AppManager;
import com.yusys.mpos.base.ui.BaseActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 照相机界面
 *
 * @author yuanshuai (marshall.yuan@foxmail.com)
 * @since 2016-05-31 16:48
 */
public class CameraActivity extends BaseActivity {

    @Bind(R.id.btn_cancel)
    Button btn_cancel;// 取消
    @Bind(R.id.btn_confirm)
    Button btn_confirm;// 确认
    @Bind(R.id.btn_take_photo)
    Button btn_take_photo;// 拍照

    @Bind(R.id.sv_photo)
    SurfaceView sv_photo;
    private Camera camera;
    private Camera.Parameters parameters;
    Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
        ButterKnife.bind(this);
        sv_photo.getHolder().setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        sv_photo.getHolder().setFixedSize(160, 160);// 设置Surface分辨率
        sv_photo.getHolder().setKeepScreenOn(true);// 屏幕常亮
        sv_photo.getHolder().addCallback(new SurfaceCallback());//为SurfaceView的句柄添加一个回调函数
        btn_cancel.setVisibility(View.INVISIBLE);
        btn_confirm.setVisibility(View.INVISIBLE);
    }

    /**
     * 取消
     */
    @SuppressWarnings("unused")
    @OnClick(R.id.btn_cancel)
    void cancel(View view) {
        btn_cancel.setVisibility(View.INVISIBLE);
        btn_confirm.setVisibility(View.INVISIBLE);
        btn_take_photo.setVisibility(View.VISIBLE);
        if (camera != null) {
            camera.startPreview();
        }
    }

    /**
     * 确认
     */
    @SuppressWarnings("unused")
    @OnClick(R.id.btn_confirm)
    void confirm(View view) {
        if (bundle != null) {
            Intent intent = new Intent();
            intent.putExtra("photo", bundle);
            setResult(RESULT_OK, intent);
            finish();
        }
    }

    /**
     * 拍照
     */
    @SuppressWarnings("unused")
    @OnClick(R.id.btn_take_photo)
    void takePhoto(View view) {
        if (camera != null) {
            camera.takePicture(null, null, new MyPictureCallback());
        }
    }

    private final class MyPictureCallback implements PictureCallback {
        @Override
        public void onPictureTaken(byte[] data, Camera camera) {
            bundle = new Bundle();
            bundle.putByteArray("bytes", data);
            btn_cancel.setVisibility(View.VISIBLE);
            btn_confirm.setVisibility(View.VISIBLE);
            btn_take_photo.setVisibility(View.INVISIBLE);
        }
    }


    private final class SurfaceCallback implements Callback {

        // 拍照状态变化时调用该方法
        @Override
        public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            parameters = camera.getParameters(); // 获取各项参数
            parameters.setPictureFormat(PixelFormat.JPEG); // 设置图片格式
            parameters.setPreviewSize(width, height); // 设置预览大小
            parameters.setPreviewFrameRate(5);  //设置每秒显示4帧
            parameters.setPictureSize(width, height); // 设置保存的图片尺寸
            parameters.setJpegQuality(80); // 设置照片质量
        }

        // 开始拍照时调用该方法
        @Override
        public void surfaceCreated(SurfaceHolder holder) {
            try {
                camera = Camera.open(); // 打开摄像头
                camera.setPreviewDisplay(holder); // 设置用于显示拍照影像的SurfaceHolder对象
                camera.startPreview(); // 开始预览
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        // 停止拍照时调用该方法
        @Override
        public void surfaceDestroyed(SurfaceHolder holder) {
            if (camera != null) {
                camera.release(); // 释放照相机
                camera = null;
            }
        }
    }
}
