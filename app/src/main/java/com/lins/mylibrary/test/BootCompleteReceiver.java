package com.lins.mylibrary.test;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.lins.modulesystem.utils.ToastUtil;

public class BootCompleteReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context,"MyLibrary启动成功",Toast.LENGTH_LONG);
    }
}