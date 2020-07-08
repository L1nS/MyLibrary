package com.lins.modulehome.test.service;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Binder;
import android.os.Build;
import android.os.Environment;
import android.os.IBinder;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import com.hjq.toast.ToastUtils;
import com.lins.modulehome.R;
import com.lins.modulehome.test.ContactActivity;

import java.io.File;


public class MyService extends Service {
    private static final String TAG = "MyService";

    private String channelId = "1";
    private String channelName = "其他消息";

    private DownloadBinder mBinder = new DownloadBinder();
    private NotificationManager manager;
    private NotificationCompat.Builder builder;

    private DownloadTask downloadTask;

    private String downloadUrl;

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    class DownloadBinder extends Binder {

        public void startDownload(String url) {
            if (downloadTask == null) {
                downloadUrl = url;
                downloadTask = new DownloadTask(listener);
                downloadTask.execute(downloadUrl);
                initNotification();
                startForeground(1, builder.build());
                ToastUtils.show( "Downloading...");

            }
            Log.d(TAG, "startDownload: ");
        }

        public void pauseDownload() {
            if (downloadTask != null) {
                downloadTask.pauseDownload();
            }
        }

        public void cancelDownload() {
            if (downloadTask != null) {
                Log.d(TAG,"Canceled");
                ToastUtils.show("canceled");
                downloadTask.cancelDownload();
            } else {
                if (downloadUrl != null) {
                    Log.d(TAG,"Deleted");
                    //取消下载时需将文件删除，并关闭通知.
                    String fileName = downloadUrl.substring(downloadUrl.lastIndexOf("/"));
                    String directory = Environment.getExternalStoragePublicDirectory(Environment.
                            DIRECTORY_DOWNLOADS).getPath();
                    File file = new File(directory + fileName);
                    if (file.exists()) {
                        file.delete();
                    }
                    manager.cancel(1);
                    stopForeground(true);
                    ToastUtils.show( "delete");
                }
            }
        }

    }

    private DownloadListener listener = new DownloadListener() {
        @Override
        public void onProgress(int progress) {
            setNotificationContent("Downloading...", progress);
        }

        @Override
        public void onSuccess() {
            downloadTask = null;
            //下载成功时将前台服务通知关闭，并创建一个下载成功的通知。
            stopForeground(true);
            setNotificationContent("Download Success", -1);
            ToastUtils.show( "Download Success");
        }

        @Override
        public void onFailed() {
            downloadTask = null;
            stopForeground(true);
            setNotificationContent("Download Failed", -1);
            ToastUtils.show( "Download Failed");
        }

        @Override
        public void onPaused() {
            downloadTask = null;
            setNotificationContent("Download paused", -1);
            ToastUtils.show("Download Paused");
        }

        @Override
        public void onCanceled() {
            downloadTask = null;
            stopForeground(true);
            ToastUtils.show( "Downlaod Canceled");
        }
    };

    private void initNotification() {
        manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        //高版本需要渠道
        if (Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            //只在Android O之上需要渠道
            NotificationChannel notificationChannel = new NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_LOW);
            //如果这里用IMPORTANCE_NOENE就需要在系统的设置里面开启渠道，通知才能正常弹出
            notificationChannel.setSound(null, null);
            notificationChannel.setImportance(NotificationManager.IMPORTANCE_LOW);
            manager.createNotificationChannel(notificationChannel);
            builder = new NotificationCompat.Builder(getApplicationContext(), channelId);
        } else {
            builder = new NotificationCompat.Builder(getApplicationContext());
        }
        Intent intent = new Intent(getApplicationContext(), ContactActivity.class);
        PendingIntent pi = PendingIntent.getActivity(getApplicationContext(), 0, intent, 0);
        builder.setContentTitle("Downloading...");
        builder.setSmallIcon(R.mipmap.ic_launcher_round);
        builder.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher));
        builder.setContentIntent(pi);
        builder.setContentText("0%");
        builder.setProgress(100, 0, false);
    }

    private void setNotificationContent(String title, int progress) {
        builder.setContentTitle(title);
        if (progress > 0) {
            builder.setContentText(progress + "%");
            builder.setProgress(100, progress, false);
        }
        manager.notify(1, builder.build());
    }
}