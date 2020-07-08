package com.lins.modulehome.test;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.hjq.toast.ToastUtils;
import com.lins.modulehome.R;
import com.lins.modulehome.R2;
import com.lins.modulesystem.mvp.BaseMvpActivity;
import com.lins.modulesystem.mvp.BaseMvpPresenter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class ContactActivity extends BaseMvpActivity {

    @BindView(R2.id.id_lv)
    ListView idLv;

    private List<String> contactsList = new ArrayList<>();
    private ArrayAdapter adapter;

    @Override
    protected BaseMvpPresenter initPresenter() {
        return null;
    }

    @Override
    public int initLayoutResID() {
        return R.layout.activity_contact;
    }

    @Override
    public void initData() {
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, contactsList);
        idLv.setAdapter(adapter);

    }

    @Override
    public void initView() {

    }

    @Override
    public void initListener() {

    }

    @Override
    public void initLoad() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_CONTACTS}, 1);
        } else {
            readContacts();
        }
    }

    private void readContacts() {
        Cursor cursor =null;
        try {
            cursor = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,null,null,null);
            if(cursor!=null){
                while (cursor.moveToNext()){
                    String name = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                    String number =cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                    contactsList.add(name+"\n"+number);
                }
                adapter.notifyDataSetChanged();
            }

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(cursor!=null)
                cursor.close();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    readContacts();
                } else {
                    ToastUtils.show( "权限拒绝");
                }
                break;
            default:
                break;
        }
    }
}