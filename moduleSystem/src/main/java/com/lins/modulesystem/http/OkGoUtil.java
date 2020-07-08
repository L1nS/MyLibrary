package com.lins.modulesystem.http;

import android.app.Application;
import android.text.TextUtils;

import com.hjq.toast.ToastUtils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheEntity;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.cookie.CookieJarImpl;
import com.lzy.okgo.cookie.store.DBCookieStore;
import com.lzy.okgo.exception.HttpException;
import com.lzy.okgo.exception.StorageException;
import com.lzy.okgo.https.HttpsUtils;
import com.lzy.okgo.interceptor.HttpLoggingInterceptor;
import com.lzy.okgo.model.HttpHeaders;
import com.lzy.okgo.model.HttpParams;
import com.lins.modulesystem.http.exception.TokenException;
import com.lins.modulesystem.manager.LoginManager;
import com.lins.modulesystem.rx.RxBus;
import com.lins.modulesystem.rx.RxBusTag;
import com.lins.modulesystem.utils.MyDateUtils;

import java.io.UnsupportedEncodingException;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

import okhttp3.OkHttpClient;

public class OkGoUtil {

    public static void initOkGo(Application app) {
        //----------------------------------------------------------------------------------------//

        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        //log相关
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor("OkGo");
        loggingInterceptor.setPrintLevel(HttpLoggingInterceptor.Level.BODY);        //log打印级别，决定了log显示的详细程度
        loggingInterceptor.setColorLevel(Level.INFO);                               //log颜色级别，决定了log在控制台显示的颜色
        builder.addInterceptor(loggingInterceptor);                                 //添加OkGo默认debug日志

        //超时时间设置，默认60秒
        builder.readTimeout(OkGo.DEFAULT_MILLISECONDS, TimeUnit.MILLISECONDS);      //全局的读取超时时间
        builder.writeTimeout(OkGo.DEFAULT_MILLISECONDS, TimeUnit.MILLISECONDS);     //全局的写入超时时间
        builder.connectTimeout(OkGo.DEFAULT_MILLISECONDS, TimeUnit.MILLISECONDS);   //全局的连接超时时间

        //自动管理cookie（或者叫session的保持），以下几种任选其一就行
        //builder.cookieJar(new CookieJarImpl(new SPCookieStore(this)));            //使用sp保持cookie，如果cookie不过期，则一直有效
        builder.cookieJar(new CookieJarImpl(new DBCookieStore(app.getApplicationContext())));              //使用数据库保持cookie，如果cookie不过期，则一直有效
        //builder.cookieJar(new CookieJarImpl(new MemoryCookieStore()));            //使用内存保持cookie，app退出后，cookie消失


        //https相关设置，以下几种方案根据需要自己设置
        //方法一：信任所有证书,不安全有风险
        HttpsUtils.SSLParams sslParams1 = HttpsUtils.getSslSocketFactory();
        builder.sslSocketFactory(sslParams1.sSLSocketFactory, sslParams1.trustManager);

        // 其他统一的配置
        // 详细说明看GitHub文档：https://github.com/jeasonlzy/
        OkGo.getInstance().init(app)                           //必须调用初始化
                .setOkHttpClient(builder.build())               //建议设置OkHttpClient，不设置会使用默认的
                .setCacheMode(CacheMode.NO_CACHE)               //全局统一缓存模式，默认不使用缓存，可以不传
                .setCacheTime(CacheEntity.CACHE_NEVER_EXPIRE)   //全局统一缓存时间，默认永不过期，可以不传
                .setRetryCount(1);                      //全局统一超时重连次数，默认为三次，那么最差的情况会请求4次(一次原始请求，三次重连请求)，不需要可以设置为0
//                .addCommonHeaders(getHttpHeaders());                   //全局公共头
//                .addCommonParams(params);                       //全局公共参数
    }

    /**
     * 公共头
     *
     * @return
     */
    public static HttpHeaders getHttpHeaders() {
        HttpHeaders headers = new HttpHeaders();
//        headers.put(Constants.HTTP_HEADER_TOKEN, MyApp.user.getToken());    //header不支持中文，不允许有特殊字符
//        headers.put(Constants.HTTP_HEADER_ID, MyApp.user.getUid());
        return headers;
    }

    /**
     * @return
     */
    public static HttpParams getHttpParams(Map<String, String> treeMap) {
        HttpParams params = new HttpParams();
        HashMap<String, String> linkedHashMap = new LinkedHashMap<>();
        Iterator iter = treeMap.entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry entry = (Map.Entry) iter.next();
            String key = (String) entry.getKey();
            String val = (String) entry.getValue();
            params.put(key, val);
            linkedHashMap.put(key, val);
        }
        params.put("_t", MyDateUtils.getCurTimeLong());
        linkedHashMap.put("_t", String.valueOf(MyDateUtils.getCurTimeLong()));
        params.put("_sign", StringToMD5(linkedHashMap));
        return params;
    }

    public static String StringToMD5(HashMap<String, String> linkedHashMap) {

        String str = "";
        Iterator iter = linkedHashMap.entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry entry = (Map.Entry) iter.next();
            String key = (String) entry.getKey();
            String val = (String) entry.getValue();
            str += key + val;
        }
//        str += Constants.ThisAppKey;
        MessageDigest messageDigest = null;

        try {
            messageDigest = MessageDigest.getInstance("MD5");

            messageDigest.reset();

            messageDigest.update(str.getBytes("UTF-8"));
        } catch (NoSuchAlgorithmException e) {
            System.out.println("NoSuchAlgorithmException caught!");
            System.exit(-1);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        byte[] byteArray = messageDigest.digest();

        StringBuffer md5StrBuff = new StringBuffer();

        for (int i = 0; i < byteArray.length; i++) {
            if (Integer.toHexString(0xFF & byteArray[i]).length() == 1)
                md5StrBuff.append("0").append(Integer.toHexString(0xFF & byteArray[i]));
            else
                md5StrBuff.append(Integer.toHexString(0xFF & byteArray[i]));
        }
        return md5StrBuff.toString().toLowerCase().substring(8, 24);
    }

    public static void exception(Throwable exception) {
        String errorMsg = "";
        if (exception != null) exception.printStackTrace();
        if (exception instanceof UnknownHostException || exception instanceof ConnectException) {
            errorMsg = "网络连接失败，请连接网络";
            System.out.println(errorMsg);
        } else if (exception instanceof SocketTimeoutException) {
            errorMsg = "网络请求超时";
            System.out.println(errorMsg);
        } else if (exception instanceof HttpException) {
            errorMsg = "服务器响应失败";
            System.out.println("服务器响应码404和500了");
        } else if (exception instanceof StorageException) {
            errorMsg = "sd卡不存在或者没有权限";
            System.out.println(errorMsg);
        } else if (exception instanceof TokenException) {//Token过期
            LoginManager.getInstance().clearUserInfo();
            RxBus.$().post(RxBusTag.RXBUS_GOTO_LOGIN);//跳转登录页面
            errorMsg = exception.getMessage();
        } else if (exception instanceof IllegalStateException) {
            //这个异常类型就是你自己抛的，当然你也可以抛其他类型，或者自定义类型，无所谓，只要是异常就行
            //这里获取的message就是你前面抛出来的数据，至于你想怎么解析都行
            errorMsg = exception.getMessage();
            System.out.println(errorMsg);
        }
        if (!TextUtils.isEmpty(errorMsg))
            ToastUtils.show(errorMsg);
    }
}
