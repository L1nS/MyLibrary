package com.lins.modulehome.test.bitmapEdit;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatSeekBar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.hjq.toast.ToastUtils;
import com.lins.modulehome.R;
import com.lins.modulehome.R2;
import com.lins.modulehome.test.utils.BitmapDecodeUtil;
import com.lins.modulehome.test.utils.BitmapPathUtil;
import com.lins.modulesystem.mvp.BaseMvpActivity;

import butterknife.BindView;

public class BitmapEditActivity extends BaseMvpActivity<BitmapEditView, BitmapPresenter>
        implements BitmapEditView {


    @BindView(R2.id.id_btn_choose_from_album)
    Button idBtnChooseFromAlbum;
    @BindView(R2.id.id_img)
    ImageView idImg;
    @BindView(R2.id.id_tv_hue)
    TextView idTvHue;
    @BindView(R2.id.id_sb_hue)
    AppCompatSeekBar idSbHue;
    @BindView(R2.id.id_tv_saturation)
    TextView idTvSaturation;
    @BindView(R2.id.id_sb_saturation)
    AppCompatSeekBar idSbSaturation;
    @BindView(R2.id.id_tv_lum)
    TextView idTvLum;
    @BindView(R2.id.id_sb_lum)
    AppCompatSeekBar idSbLum;
    @BindView(R2.id.id_btn_film_ori)
    Button idBtnFilmOri;
    @BindView(R2.id.id_btn_film_now)
    Button idBtnFilmNow;

    private static final int FROM_ALBUM = 2;
    private Bitmap originalBmp;//原始图片
    private Bitmap newBmp;//修改后的图片。

    @Override
    protected BitmapPresenter initPresenter() {
        return new BitmapPresenter();
    }

    @Override
    public int initLayoutResID() {
        return R.layout.activity_bitmap_edit;
    }

    @Override
    public void initData() {
    }

    @Override
    public void initView() {

    }

    @Override
    public void initListener() {
        idBtnChooseFromAlbum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(mContext, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(BitmapEditActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                } else {
                    openAlbum();
                }
            }
        });

        idSbHue.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                int p = progress - 180;
                idTvHue.setText("" + p);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                int p = seekBar.getProgress() - 180;
                mPresenter.setHue(originalBmp, p);
            }
        });

        idSbSaturation.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                double p = mPresenter.div(progress, 10, 1);
                idTvSaturation.setText("" + p);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                float p = (float) mPresenter.div(seekBar.getProgress(), 10, 1);
                mPresenter.setSaturation(originalBmp, p);
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        idSbLum.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                double p = mPresenter.div(progress, 10, 1);
                idTvLum.setText("" + p);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                float p = (float) mPresenter.div(seekBar.getProgress(), 10, 1);
                mPresenter.setLum(originalBmp, p);
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        idBtnFilmOri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.setBitmapFilm(originalBmp);
            }
        });
        idBtnFilmNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.setBitmapFilm(newBmp);
            }
        });
    }

    @Override
    public void initLoad() {

    }

    private void openAlbum() {
        //从相册中选择
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//        Intent intent = new Intent("android.intent.action.GET_CONTENT");
//        intent.setType("image/*");
        startActivityForResult(intent, FROM_ALBUM);
    }

    private void displayImage(String imagePath) {
        if (imagePath != null) {
            originalBmp = BitmapDecodeUtil.decodeBitmapFromFile(imagePath, 200, 200);
            idImg.setImageBitmap(originalBmp);
        } else {
            ToastUtils.show( "获取照片失败");
        }
    }

    @Override
    public void displayImageEidted(Bitmap bmp) {
        newBmp = bmp;
        idImg.setImageBitmap(bmp);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                    openAlbum();
                else
                    ToastUtils.show("权限拒绝");
                break;
            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case FROM_ALBUM:
                if (resultCode == RESULT_OK) {
                    String path = BitmapPathUtil.getRealPathFromUri(mContext, data.getData());
                    displayImage(path);
                }
                break;
            default:
                break;
        }
    }
}