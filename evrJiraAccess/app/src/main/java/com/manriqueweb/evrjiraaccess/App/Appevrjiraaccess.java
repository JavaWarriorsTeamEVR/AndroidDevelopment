package com.manriqueweb.evrjiraaccess.App;

import android.app.Application;
import android.util.Log;

import com.manriqueweb.evrjiraaccess.Utils.Constants;

import static com.manriqueweb.evrjiraaccess.Utils.Constants.isDebbud;

/**
 * Created by omar on 7/05/17.
 */

public class Appevrjiraaccess extends Application {
    private static final String LOG_TAG = Constants.STR_LOG_TAG.concat("Appevrjiraaccess");
    private static final boolean IS_DEBBUG = isDebbud;

    public void onCreate () {
        super.onCreate();
        if(IS_DEBBUG) Log.d(LOG_TAG, "onCreate");

    }

}
