package com.yusys.mpos.base.manager;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;

import java.io.ByteArrayInputStream;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.Iterator;
import java.util.List;

/**
 * apk管理器
 *
 * @author yuanshuai (marshall.yuan@foxmail.com)
 * @since 2016-06-16 11:48
 */
public class APKManager {

    /**
     * 调试模式,true-不验证合法性,false-验证合法性
     */
    public static boolean DEBUG = false;

    /**
     * 得到签名信息
     */
    public static String getSign(Context context) {
        PackageManager pm = context.getPackageManager();
        List<PackageInfo> apps = pm.getInstalledPackages(PackageManager.GET_SIGNATURES);
        Iterator<PackageInfo> iter = apps.iterator();
        while (iter.hasNext()) {
            PackageInfo packageinfo = iter.next();
            String packageName = packageinfo.packageName;
            if (packageName.equals(context.getPackageName())) {
                LogManager.e("==签名信息==", packageinfo.signatures[0].toCharsString());
                LogManager.e("==签名长度==", String.valueOf(packageinfo.signatures[0].toCharsString().length()));
                return packageinfo.signatures[0].toCharsString();
            }
        }
        return null;
    }

    public static void getSingInfo(Context context) {
        try {
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), PackageManager.GET_SIGNATURES);
            Signature[] signs = packageInfo.signatures;
            Signature sign = signs[0];
            String signString = sign.toString();
            LogManager.e("==包信息签名==", signString);
            LogManager.e("==包信息签名==", sign.toCharsString());
            parseSignature(sign.toByteArray());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void parseSignature(byte[] signature) {
        try {
            CertificateFactory certFactory = CertificateFactory.getInstance("X.509");
            X509Certificate cert = (X509Certificate) certFactory.generateCertificate(new ByteArrayInputStream(signature));
            String pubKey = cert.getPublicKey().toString();
            String signNumber = cert.getSerialNumber().toString();
            LogManager.e("==signName:==", cert.getSigAlgName());
            LogManager.e("==pubKey:==", pubKey);
            LogManager.e("==signNumber==:", signNumber);
            LogManager.e("==subjectDN:==", cert.getSubjectDN().toString());
        } catch (CertificateException e) {
            e.printStackTrace();
        }
    }
}