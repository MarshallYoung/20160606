package com.yusys.mpos.login.ui;

import android.app.Activity;
import android.hardware.Camera;
import android.hardware.Camera.PictureCallback;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;

import com.yusys.mpos.R;
import com.yusys.mpos.base.manager.LogManager;
import com.yusys.mpos.base.manager.StringManager;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 照相机界面
 *
 * @author yuanshuai (marshall.yuan@foxmail.com)
 * @since 2016-05-31 16:48
 */
public class CameraActivity extends Activity {

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
    //    Bundle bundle;// 用来存放图片
    byte[] bytes;

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
        if (bytes != null) {

//            Bundle bundle = new Bundle();
//            bundle.putByteArray("bytes", bytes);
//            Intent intent = new Intent();
//            intent.putExtra("photo", bundle);
//            setResult(RESULT_OK, intent);
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
            camera.takePicture(null, null, jpegCallBack);
        }
    }

    // 拍照瞬间调用
    private final Camera.ShutterCallback shutterCallback = new Camera.ShutterCallback() {
        @Override
        public void onShutter() {
            LogManager.e("==拍照界面==", "shutter");
        }
    };

    // 获得没有压缩过的图片数据
    private final PictureCallback rawCallBack = new PictureCallback() {
        @Override
        public void onPictureTaken(byte[] data, Camera camera) {
            LogManager.e("==拍照界面==", "raw");
        }
    };

    // 返回jpeg图片数据
    private final PictureCallback jpegCallBack = new PictureCallback() {
        @Override
        public void onPictureTaken(byte[] data, Camera camera) {
            try {
                LogManager.e("==拍照界面==", "图片大小是:" + StringManager.convertFileSize(data.length));
                bytes = data;
//                bundle = new Bundle();
//                bundle.putByteArray("bytes", data);
                btn_cancel.setVisibility(View.VISIBLE);
                btn_confirm.setVisibility(View.VISIBLE);
                btn_take_photo.setVisibility(View.INVISIBLE);
            } catch (Exception exception) {
                LogManager.e("==拍照界面==", exception.toString());
            }
        }
    };


    private final class SurfaceCallback implements Callback {

        // 拍照状态变化时调用该方法
        @Override
        public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
//            parameters = camera.getParameters(); // 获取各项参数
//            parameters.setPictureFormat(PixelFormat.JPEG); // 设置图片格式
//            //这里面的参数只能是几个特定的参数,否则会报错.(176*144,320*240,352*288,480*360,640*480)
//            parameters.setPreviewSize(640, 480); // 设置预览大小
//            parameters.setPreviewFrameRate(5);  //设置每秒显示4帧
//            parameters.setPictureSize(640, 480); // 设置保存的图片尺寸
//            parameters.setJpegQuality(80); // 设置照片质量
//            camera.setParameters(parameters);
        }

        // 开始拍照时调用该方法
        @Override
        public void surfaceCreated(SurfaceHolder holder) {
            camera = Camera.open(); // 打开摄像头
            try {
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
                camera.stopPreview();//停止预览
                camera.release(); // 释放照相机
                camera = null;
            }
        }
    }
}
