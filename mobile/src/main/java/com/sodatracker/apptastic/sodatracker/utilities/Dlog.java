package com.sodatracker.apptastic.sodatracker.utilities;

import android.util.Log;

import com.sodatracker.apptastic.sodatracker.BuildConfig;

/**
 * Created by mgarner on 7/22/2015.
 */
public class Dlog {

    public static final String CLASS_NAME = Dlog.class.getName();
    public static final boolean DEBUG_CLASS = false;
    public static final boolean DEBUG_METHOD_CALLS = false;

    public static void d(String tag, String message, boolean show) {
        if (show && BuildConfig.DEBUG_MODE) {
            Log.d(tag, message);
        }
    }

    public static void e(String tag, String message, boolean show) {
        if (show && BuildConfig.DEBUG_MODE) {
            Log.e(tag, message);
        }
    }

    public static void v(String tag, String message, boolean show) {
        if (show && BuildConfig.DEBUG_MODE) {
            Log.v(tag, message);
        }
    }

    public static void i(String tag, String message, boolean show) {
        if (show && BuildConfig.DEBUG_MODE) {
            Log.i(tag, message);
        }
    }

    public static void w(String tag, String message, boolean show) {
        if (show && BuildConfig.DEBUG_MODE) {
            Log.w(tag, message);
        }
    }
}
