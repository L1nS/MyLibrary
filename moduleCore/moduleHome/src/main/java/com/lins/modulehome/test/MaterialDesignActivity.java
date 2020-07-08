package com.lins.modulehome.test;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.lins.modulehome.R;
import com.lins.modulesystem.base.BaseActivity;

public class MaterialDesignActivity extends BaseActivity {

    private Toolbar idToolbar;
    private ImageView idImgTop;
    private TextView idTvContent;
    private CollapsingToolbarLayout idCollapsing;

    @Override
    public int initLayoutResID() {
        return R.layout.activity_material_design;
    }

    @Override
    public void initData() {

    }

    @Override
    public void initView() {
        idToolbar = findViewById(R.id.id_toolbar);
        idImgTop = findViewById(R.id.id_img_top);
        idTvContent = findViewById(R.id.id_tv_content);
        idCollapsing = findViewById(R.id.id_collapsing);

//        setSupportActionBar(idToolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        Glide.with(mContext)
                .load(BitmapFactory.decodeResource(getResources(), R.drawable.big_image))
                .into(idImgTop);
        idCollapsing.setTitle("这是个标题");
        idTvContent.setText(getContent());
    }

    @Override
    public void initListener() {
    }

    @Override
    public void initLoad() {

    }

    private String getContent() {
        String str = "content";
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 300; i++) {
            sb.append(str);
        }
        return sb.toString();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            default:
                break;
        }
        return true;
    }
}