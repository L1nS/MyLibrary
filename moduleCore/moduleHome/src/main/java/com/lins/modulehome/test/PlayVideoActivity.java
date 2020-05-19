package com.lins.modulehome.test;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.VideoView;

import com.lins.modulehome.R;
import com.lins.modulehome.R2;
import com.lins.modulesystem.base.BaseActivity;

import java.io.File;

import butterknife.BindView;

public class PlayVideoActivity extends BaseActivity {


    @BindView(R2.id.id_btn_start)
    Button idBtnStart;
    @BindView(R2.id.id_btn_pause)
    Button idBtnPause;
    @BindView(R2.id.id_btn_replay)
    Button idBtnReplay;
    @BindView(R2.id.id_vv)
    VideoView idVv;

    @Override
    public int initLayoutResID() {
        return R.layout.activity_play_video;
    }

    @Override
    public void initData() {

    }

    @Override
    public void initView() {

    }

    @Override
    public void initListener() {
        idBtnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!idVv.isPlaying())
                    idVv.start();
            }
        });

        idBtnPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (idVv.isPlaying()) {
                    idVv.pause();
                }
            }
        });

        idBtnReplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (idVv.isPlaying()) {
                    idVv.resume();
                }
            }
        });
    }

    @Override
    public void initLoad() {
        if (ContextCompat.checkSelfPermission(mContext, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(PlayVideoActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        } else {
            initVideoView();
        }
    }

    private void initVideoView() {
        File file = new File(Environment.getExternalStorageDirectory(), "videodemo.mp4");
        idVv.setVideoPath(file.getPath());
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                    initVideoView();
                break;
            default:
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (idVv != null)
            idVv.suspend();
    }
}