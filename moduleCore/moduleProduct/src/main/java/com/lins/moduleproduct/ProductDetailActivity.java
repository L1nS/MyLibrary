package com.lins.moduleproduct;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.hjq.toast.ToastUtils;
import com.lins.modulesystem.base.BaseConstant;
import com.lins.modulesystem.mvp.BaseMvpActivity;
import com.lins.modulesystem.mvp.BaseMvpPresenter;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import butterknife.BindView;

@Route(path = BaseConstant.ACTIVITY_URL_MODULE_PRODUCT_PRODUCT_DETAIL)
public class ProductDetailActivity extends BaseMvpActivity {

    @BindView(R2.id.id_tv)
    TextView idTv;
    @BindView(R2.id.id_btn_send_local_broadcast)
    Button idBtnSendLocalBroadCast;
    @BindView(R2.id.id_et)
    EditText idEt;


    //    @Autowired()
    String title;

    private LocalBroadcastManager localBroadcastManager;
    private LocalReceiver localReceiver;

    @Override
    protected BaseMvpPresenter initPresenter() {
        return null;
    }

    @Override
    public int initLayoutResID() {
        return R.layout.activity_product_detail;
    }

    @Override
    public void initData() {
        title = getIntent().getStringExtra("title");

        localBroadcastManager = LocalBroadcastManager.getInstance(this);

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.lins.moduleproduct.LOCAL_BROADCAST");
        localReceiver = new LocalReceiver();
        localBroadcastManager.registerReceiver(localReceiver, intentFilter);

    }

    @Override
    public void initView() {

    }

    @Override
    public void initListener() {
        idBtnSendLocalBroadCast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent("com.lins.moduleproduct.LOCAL_BROADCAST");
                localBroadcastManager.sendBroadcast(intent);
            }
        });
    }

    @Override
    public void initLoad() {
        idTv.setText(title);
        String save = load();
        if(!TextUtils.isEmpty(save)){
            idEt.setText(save);
            idEt.setSelection(save.length());
            ToastUtils.show("文本复原成功");
        }
    }

    class LocalReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            ToastUtils.show("本地通知");
        }
    }

    @Override
    protected void onDestroy() {
        String inputText = idEt.getText().toString();
        save(inputText);
        super.onDestroy();
        localBroadcastManager.unregisterReceiver(localReceiver);
    }

    private void save(String input) {
        if (TextUtils.isEmpty(input))
            return;
        FileOutputStream out = null;
        BufferedWriter writer = null;
        try {
            out = openFileOutput("data123", Context.MODE_PRIVATE);
            writer = new BufferedWriter(new OutputStreamWriter(out));
            writer.write(input);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (writer != null) {
                    writer.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    private String load() {
        FileInputStream in = null;
        BufferedReader reader = null;
        StringBuilder content = new StringBuilder();
        try {
            in = openFileInput("data123");
            reader = new BufferedReader(new InputStreamReader(in));
            String line = "";
            while ((line = reader.readLine()) != null) {
                content.append(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return content.toString();
    }
}