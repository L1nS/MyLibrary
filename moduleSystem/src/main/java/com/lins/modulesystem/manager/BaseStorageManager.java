
package com.lins.modulesystem.manager;

import android.os.Environment;

import com.orhanobut.logger.Logger;
import com.lins.modulesystem.base.BaseApp;
import com.lins.modulesystem.base.BaseConstant;

import java.io.File;

/**
 * 文件路径管理类
 */
public class BaseStorageManager {

    /**
     * 大容量存储的根目录
     *
     * @return
     */
    public static String getAppRootDir() {
        String path = Environment.getExternalStorageDirectory() + "/" + BaseConstant.DOWNLOAD_FOLDER;
        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
        }
        return path;
    }

    /**
     * 补丁目录
     *
     * @return
     */
    public static String getPatchDir() {
        String path = getExternalFilesDir("patch");
        return path;
    }

    /**
     * 删除补丁目录
     */
    public static void removePatchDir() {
        String path = getPatchDir();
        removeFile(path);
    }

    /**
     * carsh目录
     *
     * @return
     */
    public static String getCrashDir() {
//        String path = Environment.getExternalStorageDirectory() + "/" + BaseConstant.DOWNLOAD_FOLDER;
//        File file = new File(path);
//        if (!file.exists()) {
//            file.mkdirs();
//        }
        String path = getExternalFilesDir("crash");
        return path;
    }

    /**
     * 大容量存储的根目录的缓存目录
     *
     * @return
     */
    public static String getAppRootCacheDir() {
        return getAppRootDir() + "/cache";
    }


    /**
     * 大容量存储的/Android/data/package名的 根目录
     *
     * @return
     */
    public static String getExternalFilesDir(String dir) {
        String path = "";
        try {
            File file = BaseApp.getInstance().getExternalFilesDir(dir);
            if (file != null) path = file.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
        }
        return path;
    }


    /**
     * 获取缓存大小，包括qqty/cache
     *
     * @return size
     */
    public static long getCacheSize() {
        long size = 0;
        size += getFolderSize(getAppRootCacheDir());

        return size;
    }

    /**
     * 删除缓存数据（qqty/cache、数据库）
     */
    public static void removeCacheData() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                removeFile(getAppRootCacheDir());
//                DatabaseHelper.getHelper().removeAllData();
//		PreferencesUtil.removeAllData();
            }
        }).start();


    }

    /**
     * 删除配置数据
     */
    public static void removeSettingData() {
        new Thread(new Runnable() {
            @Override
            public void run() {
//                PreferencesUtil.removeDefaultData();
//				PreferencesUtil.removeConfigData();
//                DatabaseHelper.getHelper().removeAllData();

            }
        }).start();


    }


    /**
     * 获取文件夹大小
     *
     * @param path 路径
     * @return size
     */
    public static long getFolderSize(String path) {
        return getFolderSize(new File(path));
    }

    /**
     * 获取文件夹大小
     *
     * @param file File实例
     * @return size
     */
    public static long getFolderSize(File file) {

        long size = 0;
        try {
            File[] fileList = file.listFiles();
            if (fileList != null && fileList.length > 0) {
                for (int i = 0; i < fileList.length; i++) {
                    if (fileList[i].isDirectory()) {
                        size = size + getFolderSize(fileList[i]);

                    } else {
                        size = size + fileList[i].length();

                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return size;
    }

    /**
     * 删除文件
     *
     * @param path 文件路径
     * @return
     */
    public static boolean removeFile(String path) {
        return removeFile(new File(path));
    }

    /**
     * 删除文件
     *
     * @param file 文件
     * @return
     */
    public static boolean removeFile(File file) {
        File files[] = file.listFiles();
        if (files != null) {
            for (File f : files) {
                if (f.isDirectory()) { // 判断是否为文件夹
                    if (removeFile(f)) {
                        try {
                            boolean isSuccess = removeFileSafely(f);
                            Logger.d("删除文件夹 : " + f.getAbsolutePath() + " result = " + isSuccess);
                            if (!isSuccess) return false;
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {
                        return false;
                    }
                } else {
                    if (f.exists()) { // 判断是否存在
                        try {
                            boolean isSuccess = removeFileSafely(f);
                            Logger.d("删除文件 : " + f.getAbsolutePath() + " result = " + isSuccess);
                            if (!isSuccess) return false;
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
        return true;
    }

    /**
     * 安全删除文件.(先重命名再删除)
     * 当删除一个文件，再重新下载这个同名文件，保存到sdcard时出现error，部分手机出现
     * <p>
     * Caused by: libcore.io.ErrnoException: open failed: EBUSY (Device or resource busy)
     * at libcore.io.Posix.open(Native Method)
     * at libcore.io.BlockGuardOs.open(BlockGuardOs.Java:110)
     * at java.io.File.createNewFile(File.java:941)
     * 此问题在小米3，华为系列手机出现概率较大。
     * 文件创建失败的原因是，文件被删除后仍然被其他进程占用。
     * 进入adb shell，通过lsof命令查看占用该文件的进程。
     * 据说这是Android文件系统的bug，建议删除文件前先将该文件进行重命名：
     *
     * @param file
     * @return
     */
    public static boolean removeFileSafely(File file) {
        if (file != null) {
            String tmpPath = file.getParent() + File.separator + System.currentTimeMillis();
            File tmp = new File(tmpPath);
            file.renameTo(tmp);
            return tmp.delete();
        }
        return false;
    }

    /**
     * 获取友盟缓存文件目录
     */
    public static Boolean hasUmengcacheFile() {
        String path = Environment.getExternalStorageDirectory() + "/umeng_cache";
        File file = new File(path);
        if (file.exists()) return true;
        return false;
    }


}
