package com.lins.modulehome.test;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import com.hjq.toast.ToastUtils;
import com.lins.modulehome.R;
import com.lins.modulehome.R2;
import com.lins.modulehome.test.utils.BitmapDecodeUtil;
import com.lins.modulehome.test.utils.BitmapOptionsUtil;
import com.lins.modulehome.test.utils.BitmapPathUtil;
import com.lins.modulesystem.base.BaseActivity;
import com.lins.modulesystem.base.BaseConstant;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import butterknife.BindView;

public class TakePhotoActivity extends BaseActivity {

    @BindView(R2.id.id_btn_take_photo)
    Button idBtnTakePhoto;
    @BindView(R2.id.id_btn_choose_from_album)
    Button idBtnChooseFromAlbum;
    @BindView(R2.id.id_img)
    ImageView idImg;

    private static final int TAKE_PHOTO = 1;
    private static final int FROM_ALBUM = 2;
    private Uri imgUri;

    @Override
    public int initLayoutResID() {
        return R.layout.activity_take_photo;
    }

    @Override
    public void initData() {

    }

    @Override
    public void initView() {

    }

    @Override
    public void initListener() {
        idBtnTakePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                File outputImg = new File(getExternalCacheDir(), "output_img.jpg");
                try {
                    if (outputImg.exists()) {
                        outputImg.delete();
                    }
                    outputImg.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                if (Build.VERSION.SDK_INT >= 24) {
                    imgUri = FileProvider.getUriForFile(mContext, BaseConstant.FILE_PROVIDER, outputImg);
                } else {
                    imgUri = Uri.fromFile(outputImg);
                }

                Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                intent.putExtra(MediaStore.EXTRA_OUTPUT, imgUri);
                startActivityForResult(intent, TAKE_PHOTO);
            }
        });

        idBtnChooseFromAlbum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(mContext, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(TakePhotoActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                } else {
                    openAlbum();
                }
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
            Bitmap bmp = BitmapDecodeUtil.decodeBitmapFromFile(imagePath, 200,200);
            idImg.setImageBitmap(bmp);
        } else {
            ToastUtils.show( "获取照片失败");
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                    openAlbum();
                else
                    ToastUtils.show( "权限拒绝");
                break;
            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case TAKE_PHOTO:
                if (resultCode == RESULT_OK) {
                    try {
                        Bitmap bmp = BitmapFactory.decodeStream(getContentResolver().openInputStream(imgUri));
                        idImg.setImageBitmap(bmp);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
                break;
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