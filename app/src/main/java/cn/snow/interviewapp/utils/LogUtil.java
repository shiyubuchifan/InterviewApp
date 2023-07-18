package cn.snow.interviewapp.utils;

import android.util.Log;

public class LogUtil {

    private static final boolean isDebug = true;
    private static final String TAG = "Snow Log";


    public static void d(String d) {
        if (isDebug) {
            Log.d(TAG, d);
        }
    }

    public static void d(String tag,String d) {
        if (isDebug) {
            Log.d(tag, d);
        }
    }

    public static void i(String i) {
        if (isDebug) {
            Log.i(TAG, i);
        }
    }

    public static void w(String w) {
        if (isDebug) {
            Log.w(TAG, w);
        }
    }

    public static void e(String e) {
        if (isDebug) {
            Log.e(TAG, e);
        }
    }
}
