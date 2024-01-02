package com.wristband.gaoyusheng.base_lib_module.util;

import android.util.Log;

/**
 * @author zengzebin
 * @date 2017/3/8
 */

public class Logs {

    private static final String TAG = "Logs";

    public static final boolean LOGGER = false;

    public static void d(String text) {
        if (LOGGER) {
            Log.d("Logs", "-->" + text);
        }
    }

    public static void v(String tag, String msg) {
        if (LOGGER) {
            Log.v(TAG, tag + "-->" + msg);
        }
    }

    public static void d(String tag, String msg) {
        if (LOGGER) {
            Log.d(TAG, tag + "-->" + msg);
        }
    }

    public static void i(String tag, String msg) {
        if (LOGGER) {
            Log.i(TAG, tag + "-->" + msg);
        }
    }

    public static void w(String tag, String msg) {
        if (LOGGER) {
            Log.v(TAG, tag + "-->" + msg);
        }
    }

    public static void e(String tag, String msg) {
        if (LOGGER) {
            Log.e(TAG, tag + "-->" + msg);
        }
    }

    public static void e(String tag, String msg, Throwable tr) {
        if (LOGGER) {
            Log.e(TAG, tag + "-->" + msg);
        }
    }
}
