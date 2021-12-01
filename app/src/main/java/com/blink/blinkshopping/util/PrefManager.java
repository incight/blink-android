package com.blink.blinkshopping.util;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by praveen on 06/03/2020
 */

public class PrefManager {
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Context _context;

    int PRIVATE_MODE = 0;

    private static final String PREF_NAME = "BlinkApp";

    private static final String IS_FIRST_TIME_LAUNCH = "IsFirstTimeLaunch";

    private static final String IS_OVERLAY_SHOW = "IsOverLayShow";

    public PrefManager(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public void setFirstTimeLaunch(boolean isFirstTime) {
        editor.putBoolean(IS_FIRST_TIME_LAUNCH, isFirstTime);
        editor.commit();
    }

    public boolean isFirstTimeLaunch() {
        return pref.getBoolean(IS_FIRST_TIME_LAUNCH, true);
    }


    public void setIsOverlayShow(boolean isFirstTime) {
        editor.putBoolean(IS_OVERLAY_SHOW, isFirstTime);
        editor.commit();
    }

    public boolean isOverLayShow() {
        return pref.getBoolean(IS_OVERLAY_SHOW, false);
    }


}
