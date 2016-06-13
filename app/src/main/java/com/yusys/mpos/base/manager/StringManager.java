package com.yusys.mpos.base.manager;

/**
 * @author yuanshuai (marshall.yuan@foxmail.com)
 * @since 2016-06-12 17:05
 */
public class StringManager {

    // 将大小以KB,MB,GB的格式显示
    public static String convertFileSize(long size) {
        long kb = 1024;
        long mb = kb * 1024;
        long gb = mb * 1024;

        if (size < 1024) {
            return String.format("%d B", size);
        } else if (size >= kb) {
            float f = (float) size / kb;
            return String.format("%.2f KB", f);
        } else if (size >= mb) {
            float f = (float) size / mb;
            return String.format("%.2f MB", f);
        } else {
            return String.format("%.2f GB", (float) size / gb);
        }
    }
}
