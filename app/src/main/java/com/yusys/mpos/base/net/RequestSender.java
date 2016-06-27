package com.yusys.mpos.base.net;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.yusys.mpos.base.YXApplication;
import com.yusys.mpos.base.bean.YXRequest;
import com.yusys.mpos.login.bean.LoginRequest;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 网络请求发送者
 *
 * @author yuanshuai (marshall.yuan@foxmail.com)
 * @since 2016-06-16 20:54
 */
public class RequestSender {

    public static final String TESTAPI = "http://172.16.1.243:8080/gateway.do";
    public static final String API = "";
    public String TARGET = "";

    public static void sendRequest(final YXRequest request) {
        StringRequest sendRequest = new StringRequest(Request.Method.POST, TESTAPI, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            // 定义请求数据
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> hashMap = new HashMap<>();
                // 添加公共请求参数
                hashMap.put("chnl_id", "001");// 应用渠道
                hashMap.put("format", "JSON");// 格式
                hashMap.put("charset", "utf-8");// 编码格式
                hashMap.put("sign_type", "RSA");// 签名算法
                hashMap.put("sign", "sign");// 签名串
                SimpleDateFormat sdf = new SimpleDateFormat("yy-MM-dd HH:mm:ss");
                hashMap.put("timestamp", sdf.format(new Date()));// 时间戳
                hashMap.put("version", "1.0");// 接口版本
                hashMap.put("auth_token", "auth_token");
                String method = "";
                // 添加请求参数
                String biz_content = "";
                if (request instanceof LoginRequest) {// 登录请求
                    method = "web.pub.login";
                    biz_content = "{}";
                }
                hashMap.put("method", method);// 接口名称
                hashMap.put("biz_content", biz_content);
                return hashMap;
            }
        };
        YXApplication.getHttpQueue().add(sendRequest);
    }
}
