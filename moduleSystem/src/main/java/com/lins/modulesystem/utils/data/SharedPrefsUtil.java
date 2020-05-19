package com.lins.modulesystem.utils.data;

import android.content.Context;
import android.content.SharedPreferences;

import com.lins.modulesystem.base.BaseApp;
import com.lins.modulesystem.base.BaseConstant;


/**
 * Created by Admin on 2017/5/10.
 */

public class SharedPrefsUtil {
    private static SharedPrefsUtil sInstance;
    private static SharedPreferences sharedReadable;
    private static SharedPreferences.Editor sharedWritable;

    private SharedPrefsUtil() {
        sharedReadable = BaseApp.getInstance()
                .getSharedPreferences(BaseConstant.USER_SP, Context.MODE_PRIVATE);
        sharedWritable = sharedReadable.edit();
    }

    public static SharedPrefsUtil getInstance() {
        if (sInstance == null) {
            synchronized (SharedPrefsUtil.class) {
                if (sInstance == null) {
                    sInstance = new SharedPrefsUtil();
                }
            }
        }
        return sInstance;
    }

    public String getString(String key, String defValue) {
        return sharedReadable.getString(key, defValue);
    }

    public void putString(String key, String value) {
        sharedWritable.putString(key, value);
        sharedWritable.apply();
    }

    public void putInt(String key, int value) {
        sharedWritable.putInt(key, value);
        sharedWritable.apply();
    }

    public int getInt(String key, int def) {
        return sharedReadable.getInt(key, def);
    }

    public void putBoolean(String key, boolean value) {
        sharedWritable.putBoolean(key, value);
        sharedWritable.apply();
    }

    public boolean getBoolean(String key, boolean def) {
        return sharedReadable.getBoolean(key, def);
    }
}
