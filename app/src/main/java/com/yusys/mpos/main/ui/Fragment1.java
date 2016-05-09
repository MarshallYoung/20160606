package com.yusys.mpos.main.ui;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.yusys.mpos.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 测试界面
 *
 * @author yuanshuai (marshall.yuan@foxmail.com)
 * @date 2016-04-06 17:26
 */
public class Fragment1 extends Fragment {

    private View fragmentView;
    @Bind(R.id.webview)
    WebView webView;
    private Handler handler;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (fragmentView == null) {// 缓存fragment,避免每次都执行onCreateView
            fragmentView = inflater.inflate(R.layout.fragment_1, null);
            ButterKnife.bind(this, fragmentView);// 注解绑定框架
            handler = new Handler();
        }
        return fragmentView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }

    private void initView() {
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);// 启用js
        // 限制在WebView中打开网页，而不用默认浏览器
        webView.setWebViewClient(new WebViewClient());
        // 如果不设置这个，JS代码中的按钮会显示，但是按下去却不弹出对话框
        // Sets the chrome handler. This is an implementation of WebChromeClient
        // for use in handling JavaScript dialogs, favicons, titles, and the
        // progress. This will replace the current handler.
        webView.setWebChromeClient(new WebChromeClient() {

            @Override
            public boolean onJsAlert(WebView view, String url, String message,
                                     JsResult result) {
                // TODO Auto-generated method stub
                return super.onJsAlert(view, url, message, result);
            }

        });
        webView.loadUrl("file:///android_asset/test.html");// 载入网页

//        webView.addJavascriptInterface(new Object() {
//            @JavascriptInterface
//            public void func1() {
//
//                webView.loadUrl("javascript:func1()");
//
//
//            }
//        }, "demo");
        webView.addJavascriptInterface(new WebCallInterface(getContext()), "android");

    }

    // web网页调用的方法类
    public class WebCallInterface

    {

        Context context;// 宿主

        WebCallInterface(Context con) {
            context = con;
        }

        // 17以上需要加注解
        @JavascriptInterface
        void test1(final String string) {
            handler.post(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(context, string, Toast.LENGTH_LONG).show();
                }
            });
        }

        @JavascriptInterface
        void test2(final String string) {
            handler.post(new Runnable() {
                @Override
                public void run() {
                    Log.e("====", string);
                    webView.loadUrl("javascript:func3('" + string + "')");
                }
            });
        }
    }

}
