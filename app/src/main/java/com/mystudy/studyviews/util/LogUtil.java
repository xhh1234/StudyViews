package com.mystudy.studyviews.util;

import android.util.Log;

public class LogUtil {
    private final static int LEVEL_VERBOSE=1;
    private final static int LEVEL_DEBUG=2;
    private final static int LEVEL_INFO=3;
    private final static int LEVEL_WARM=4;
    private final static int LEVEL_ERROR=5;
    private final static int LEVEL_NOTHING=6;
    private static  final int LEVEL=LEVEL_VERBOSE;

    public static void e(String tag,String msg){
        if (LEVEL<LEVEL_NOTHING){
            Log.e(tag, "v: "+msg);
        }
    }
}
