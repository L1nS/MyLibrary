package com.lins.modulehome.test.service;


import android.Manifest;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;

import com.lins.modulehome.R;
import com.lins.modulehome.R2;
import com.lins.modulesystem.base.BaseActivity;
import com.lins.modulesystem.utils.ToastUtil;

import butterknife.BindView;

public class ServiceActivity extends BaseActivity {

    @BindView(R2.id.id_btn_start_download)
    Button idBtnStartDownload;
    @BindView(R2.id.id_btn_pause_downlaod)
    Button idBtnPauseDownload;
    @BindView(R2.id.id_btn_cancel_download)
    Button idBtnCancelDownload;

    private MyService.DownloadBinder downloadBinder;
    private boolean isConnect = false;

    @Override
    public int initLayoutResID() {
        return R.layout.activity_service;
    }

    @Override
    public void initData() {
        Intent intent = new Intent(this, MyService.class);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            startForegroundService(intent);
        } else {
            startService(intent);
        }
        bindService(intent, serviceConnection, BIND_AUTO_CREATE);
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) !=
                PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(ServiceActivity.this, new String[]{
                    Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        }
    }

    @Override
    public void initView() {

    }

    @Override
    public void initListener() {
        idBtnStartDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://pkg.biligame.com/games/gzljReDive_2.4.6_1.2_20200410_013416_3e6fb.apk";
                downloadBinder.startDownload(url);
            }
        });
        idBtnPauseDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                downloadBinder.pauseDownload();
            }
        });
        idBtnCancelDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                downloadBinder.cancelDownload();
            }
        });
    }

    @Override
    public void initLoad() {

    }


    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            downloadBinder = (MyService.DownloadBinder) service;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults.length <= 0 || grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    ToastUtil.showToast(this, "拒绝权限将无法使用下载");
                    finishThis();
                }
                break;
            default:
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (isConnect) {
            unbindService(serviceConnection);
            isConnect = false;
        }
        Intent intent = new Intent(this, MyService.class);
        stopService(intent);
    }

}