package com.lins.modulesystem.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;


public class NetWorkChangReceiver extends BroadcastReceiver {
    /**
     * 没有连接网络
     */
    private static final int NETWORK_NONE = -1;
    /**
     * 移动网络
     */
    private static final int NETWORK_MOBILE = 0;
    /**
     * 无线网络
     */
    private static final int NETWORK_WIFI = 1;

    @Override
    public void onReceive(Context context, Intent intent) {
        if (ConnectivityManager.CONNECTIVITY_ACTION.equals(intent.getAction())) {
            int netWorkState = getNetWorkState(context);
            if (NETWORK_NONE == netWorkState ){
                //网络断开
//                RxBus.$().post(RxBusTag.RXBUS_NETWORK, false);
//                RxBus.$().post(RxBusTag.RXBUS_NETWORK_ACTIVITY, false);
            }else if (NETWORK_MOBILE == netWorkState || NETWORK_WIFI == netWorkState){
                //网络连接
//                RxBus.$().post(RxBusTag.RXBUS_NETWORK, true);
//                RxBus.$().post(RxBusTag.RXBUS_NETWORK_ACTIVITY, true);
            }
        }
    }

    private int getNetWorkState(Context context) {
        // 得到连接管理器对象
        ConnectivityManager connectivityManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetworkInfo = connectivityManager
                .getActiveNetworkInfo();
        if (activeNetworkInfo != null && activeNetworkInfo.isConnected()) {

            if (activeNetworkInfo.getType() == (ConnectivityManager.TYPE_WIFI)) {
                return NETWORK_WIFI;
            } else if (activeNetworkInfo.getType() == (ConnectivityManager.TYPE_MOBILE)) {
                return NETWORK_MOBILE;
            }
        } else {
            return NETWORK_NONE;
        }
        return NETWORK_NONE;
    }
}