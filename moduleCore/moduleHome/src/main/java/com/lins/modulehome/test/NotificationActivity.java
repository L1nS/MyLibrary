package com.lins.modulehome.test;

import androidx.core.app.NotificationCompat;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.view.View;
import android.widget.Button;

import com.hjq.toast.ToastUtils;
import com.lins.modulehome.R;
import com.lins.modulehome.R2;
import com.lins.modulesystem.mvp.BaseMvpActivity;
import com.lins.modulesystem.mvp.BaseMvpPresenter;

import butterknife.BindView;

public class NotificationActivity extends BaseMvpActivity {

    @BindView(R2.id.id_btn_send_notice)
    Button idBtnSendNotice;


    @Override
    protected BaseMvpPresenter initPresenter() {
        return null;
    }

    @Override
    public int initLayoutResID() {
        return R.layout.activity_notification;
    }

    @Override
    public void initData() {

    }

    @Override
    public void initView() {

    }

    @Override
    public void initListener() {
        idBtnSendNotice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ToastUtils.show("消息通知");
                NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                //高版本需要渠道
                if(Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O){
                    //只在Android O之上需要渠道
                    NotificationChannel notificationChannel = new NotificationChannel("default","channelname",NotificationManager.IMPORTANCE_HIGH);
                    //如果这里用IMPORTANCE_NOENE就需要在系统的设置里面开启渠道，通知才能正常弹出
                    manager.createNotificationChannel(notificationChannel);
                }

                //通知点击跳转
                Intent intent = new Intent(mContext,ContactActivity.class);
                PendingIntent pi =PendingIntent.getActivity(mContext,0,intent,0);

//                在ContactActivity中加入下面两行可以取消通知。
//                NotificationManager manager= (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
//                manager.cancel(1);

                Notification notification = new NotificationCompat.Builder(NotificationActivity.this, "default")
                        .setContentTitle("消息通知")
                        //显示长文字
//                        .setStyle(new NotificationCompat.BigTextStyle().bigText("消息通知内容消息通知内容消息通知内容消息通知内容消息通知内容消息通知内容"))
                        //显示图片
//                        .setStyle(new NotificationCompat.BigPictureStyle().bigPicture(BitmapFactory.decodeResource(getResources(),R.drawable.big_image)))
                        .setContentText("消息通知")
                        .setWhen(System.currentTimeMillis())
                        .setSmallIcon(R.mipmap.ic_launcher_round)
                        .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher))
                        .setContentIntent(pi)
                        .setAutoCancel(true)//点击后取消通知显示
//                        .setSound()//铃声
//                        .setLights()//LED灯
                        .setDefaults(NotificationCompat.DEFAULT_ALL)
                        .setPriority(NotificationCompat.PRIORITY_LOW)
                        .build();
                manager.notify(1, notification);
            }
        });
    }

    @Override
    public void initLoad() {

    }
}