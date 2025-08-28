package com.kye.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.content.FileProvider;

import com.kye.pda.utilcode.util.Logger;
import com.kye.pda.utils.time.DateUtil;
import com.kye.utils.constant.Constants;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Routing
 * Desc 文件(File)的工具类
 * Source
 * Created by ly on 2018/10/29 17:14
 * Modify by ly on 2018/10/29 17:14
 * Version 1.0
 */
public final class FileUtils {

    private FileUtils() {
    }

    /**
     * 获取SD卡的根目录的文件夹
     *
     * @return
     */
    private static File getSDCardFolder() {

        File root = null;
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            root = Environment.getExternalStorageDirectory();
        } else {
            root = Environment.getDataDirectory();
        }

        return root;
    }

    /**
     * 创建PDA的根目录文件夹
     *
     * @return
     */
    private static File getPDARootFolder() {

        File root = new File(getSDCardFolder(), Constants.FileFolderName.PDA_ROOT_FILE_NAME);
        if (!root.exists()) {
            root.mkdirs();
        }
        return root;
    }

    /**
     * 获取PDA的根目录文件夹的绝对路径
     *
     * @return
     */
    public static String getPDARoot() {

        return getPDARootFolder().getAbsolutePath();
    }

    /**
     * 获取崩溃日志路径
     *
     * @return 获取崩溃日志路径
     */
    public static String getCrashPath() {

        String path = getPDARoot() + File.separator + Constants.FileFolderName.PDA_CRASH;
        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
        }
        return path;
    }

    /**
     * 获取普通日志路径
     *
     * @return 获取普通日志路径
     */
    public static String getNormalLogPath() {

        String path = getPDARoot() + File.separator + Constants.FileFolderName.PDA_NORMAL_LOG;
        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
        }
        return path;
    }

    /**
     * 获取埋点日志路径
     *
     * @return 获取普通日志路径
     */
    public static String getBuryLogPath() {

        String path = getPDARoot() + File.separator + Constants.FileFolderName.PDA_BURY_LOG;
        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
        }
        return path;
    }

    /**
     * 获取本地路径
     *
     * @return 获取普通日志路径
     */
    public static String getH5Path() {

        String path = getPDARoot() + File.separator + "h5";
        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
        }
        return path;
    }


    /**
     * 获取推送日志路径
     *
     * @return 获取推送通日志路径
     */
    public static String getPushLogPath() {

        String path = getPDARoot() + File.separator + Constants.FileFolderName.PDA_PUSH_LOG;
        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
        }
        return path;
    }

    public static String getHprofPath() {
        String path = getPDARoot() + File.separator + "hprof";
        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
        }
        return path + File.separator + "pdaHeap.hprof";
    }

    /**
     * 获取跨声文件夹
     */
    public static String getKuaShengXLogPath() {
        String path = getPDARoot() + File.separator + Constants.FileFolderName.PDA_KUA_SHENG_XLOG;
        File file = new File(path);
        if (!file.exists()) {
            boolean result = file.mkdirs();
            Logger.i(FileUtils.class.getName(), "result:" + result);
        }
        return path;
    }


    public static String getXLogPath(String logPath) {
        String path = getPDARoot() + File.separator + logPath;
        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
        }
        return path;
    }

    /**
     * 获取照片上传日志路径
     *
     * @return 获取照片上传日志路径
     */
    public static String getPhotoUploadLogPath() {

        String path = getPDARoot() + File.separator + Constants.FileFolderName.PDA_PHOTO_UPLOAD_LOG;
        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
        }
        return path;
    }

    /**
     * 获取配制文件路径
     *
     * @return
     */
    public static String getKyConfigPath() {

        String path = getPDARoot() + File.separator + Constants.FileFolderName.PDA_KY_CONFIG;
        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
        }
        return path;
    }

    /**
     * 日志上传根据服务器返回的部分选择
     *
     * @param partLogPath
     * @return
     */
    public static String getUploadLogPathByService(String partLogPath) {
        String path = getPDARoot() + partLogPath;
        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
        }
        return path;
    }

    /**
     * 获取缓存路劲
     *
     * @return 获取热更新补丁路径
     */
    public static String getKyCachePath() {

        String path = getPDARoot() + File.separator + Constants.FileFolderName.PDA_KY_CACHE;
        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
        }
        return path;
    }

    /**
     * 获取自定义相机的照片缓存路径
     *
     * @param context
     * @return
     */
    public static String getPicturePath(Context context) {
        if (context == null) {
            return null;
        }
        File file = context.getExternalCacheDir();
        if (file == null) {
            return null;
        }
        String fileName = String.format(Locale.getDefault(), "%s.jpg", String.valueOf(new Date().getTime()));
        File f = new File(file.getPath() + File.separator + "picture", fileName);
        return com.kye.pda.utilcode.util.FileUtils.createOrExistsFile(f) ? f.getPath() : null;
    }


    /**
     * 获取签名图片照片缓存路径
     *
     * @param context
     * @return
     */
    public static String getSignPicturePath(Context context) {
        if (context == null) {
            return null;
        }
        File file = context.getExternalCacheDir();
        if (file == null) {
            return null;
        }
        String fileName = String.format(Locale.getDefault(), "B%s.jpg", String.valueOf(new Date().getTime()));
        File f = new File(file.getPath() + File.separator + "sign", fileName);
        return com.kye.pda.utilcode.util.FileUtils.createOrExistsFile(f) ? f.getPath() : null;
    }


    /**
     * 获取自定义相机的照片缓存路径
     *
     * @param context
     * @return
     */
    public static String getOcrPicturePath(Context context) {
        if (context == null) {
            return null;
        }
        File file = context.getExternalCacheDir();
        if (file == null) {
            return null;
        }
        String fileName = String.format(Locale.getDefault(), "%s.jpg", String.valueOf(new Date().getTime()));
        File f = new File(file.getPath() + File.separator + "picture" + File.separator + "ocr", fileName);
        return com.kye.pda.utilcode.util.FileUtils.createOrExistsFile(f) ? f.getPath() : null;
    }

    /**
     * 获取较大文件的MD5
     *
     * @param file 文件路径
     * @return
     */
    public static String getMD54BigFile(File file) {
        FileInputStream fis = null;
        byte[] buffer = new byte[2048];
        int numRead = 0;
        MessageDigest md5;

        try {
            fis = new FileInputStream(file);
            md5 = MessageDigest.getInstance("MD5");
            while ((numRead = fis.read(buffer)) > 0) {
                md5.update(buffer, 0, numRead);
            }
            fis.close();
            BigInteger bi = new BigInteger(1, md5.digest());
            String hexStr = bi.toString(16);
            return hexStr;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            // 释放资源
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    fis = null;
                }
            }
        }
    }


    /**
     * 安装apk方法
     *
     * @param context context
     * @param apkPath apk路径
     */
    public static void installApk(Context context, String apkPath) {
//        copyAPK(context);
        File apkfile = new File(apkPath);
        if (!apkfile.exists()) {
            Toast.makeText(context, "安装包不存在", Toast.LENGTH_SHORT).show();
            return;
        }
        String cmd3 = "chmod 777 " + apkfile.getAbsolutePath();
        String cmd4 = "chmod 777 " + apkfile.getParent();
        try {
            Runtime.getRuntime().exec(cmd4);
            Runtime.getRuntime().exec(cmd3);
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("aa", e.toString());
        }
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        //设配Android7.0安装apk文件
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            //判读版本是否在7.0以上
            Uri apkUri = FileProvider.getUriForFile(context, context.getPackageName() + ".fileprovider", apkfile);
            //添加这一句表示对目标应用临时授权该Uri所代表的文件
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.setDataAndType(apkUri, "application/vnd.android.package-archive");
        } else {
            intent.setDataAndType(Uri.fromFile(apkfile), "application/vnd.android.package-archive");
        }
        context.startActivity(intent);
    }


    /**
     * 安装完整包apk
     *
     * @param
     */
    @Deprecated
    public static void installApk(Context context) {
        copyAPK(context);
        File apkfile = new File(getHostPathRoot(), "KUAYUE.apk");
        if (!apkfile.exists()) {
            Toast.makeText(context, "安装包不存在", Toast.LENGTH_SHORT).show();
            return;
        }
        String cmd3 = "chmod 777 " + apkfile.getAbsolutePath();
        String cmd4 = "chmod 777 " + apkfile.getParent();
        try {
            Runtime.getRuntime().exec(cmd4);
            Runtime.getRuntime().exec(cmd3);
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("aa", e.toString());
        }
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        //设配Android7.0安装apk文件
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            //判读版本是否在7.0以上
            Uri apkUri = FileProvider.getUriForFile(context, context.getPackageName() + ".fileprovider", apkfile);
            //添加这一句表示对目标应用临时授权该Uri所代表的文件
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.setDataAndType(apkUri, "application/vnd.android.package-archive");
        } else {
            intent.setDataAndType(Uri.fromFile(apkfile), "application/vnd.android.package-archive");
        }
        context.startActivity(intent);

    }

    /**
     * 安装完整包apk
     *
     * @param
     */
    public static void installApk(Context context, IInstallFinishListener listener) {
        copyAPK(context);
        File apkfile = new File(context.getFilesDir(), "KUAYUE.apk");
        if (!apkfile.exists()) {
            Toast.makeText(context, "安装包不存在", Toast.LENGTH_SHORT).show();
            return;
        }
        String cmd3 = "chmod 777 " + apkfile.getAbsolutePath();
        String cmd4 = "chmod 777 " + apkfile.getParent();
        try {
            Runtime.getRuntime().exec(cmd4);
            Runtime.getRuntime().exec(cmd3);
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("aa", e.toString());
        }
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        //设配Android7.0安装apk文件
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            //判读版本是否在7.0以上
            Uri apkUri = FileProvider.getUriForFile(context, context.getPackageName() + ".fileprovider", apkfile);
            //添加这一句表示对目标应用临时授权该Uri所代表的文件
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.setDataAndType(apkUri, "application/vnd.android.package-archive");
        } else {
            intent.setDataAndType(Uri.fromFile(apkfile), "application/vnd.android.package-archive");
        }
        context.startActivity(intent);
        if (listener != null) {
            listener.installFinish();
        }
    }

    public interface IInstallFinishListener {
        void installFinish();
    }

    /**
     * 获取指定应用的APK安装包路径
     */
    @RequiresApi(api = Build.VERSION_CODES.FROYO)
    public static String getInstallApkPath(Context context, String packageName) {
        return context.getPackageResourcePath(); // 试试这种方式
    }

    /**
     * 备份基准包到指定目录
     */
    @RequiresApi(api = Build.VERSION_CODES.FROYO)
    public static void copyAPK(Context context) {
        File sdCardFile = Environment.getExternalStorageDirectory();
        File apkFileDir = new File(sdCardFile, "/KYSD/apk/");
        if (!apkFileDir.exists()) {
            apkFileDir.mkdirs();
        }
        String baseApkPath = getInstallApkPath(context, context.getPackageName());
        if (!TextUtils.isEmpty(baseApkPath)) {
            File baseApkFile = new File(baseApkPath);
            File apkFile = new File(apkFileDir, "NEW_KYE.apk");
            if (apkFile.exists()) {
                if (apkFile.length() != baseApkFile.length()) {
                    copyFile(baseApkPath, apkFile.getAbsolutePath());
                }
            } else {
                copyFile(baseApkPath, apkFile.getAbsolutePath());
            }
        }
    }

    /**
     * 复制单个文件
     *
     * @param oldPath String 原文件路径
     * @param newPath String 复制后路径
     * @return boolean
     */
    public static void copyFile(String oldPath, String newPath) {
        InputStream inStream = null;
        FileOutputStream fs = null;
        try {
            int bytesum = 0;
            int byteread = 0;
            File oldfile = new File(oldPath);
            if (oldfile.exists()) { //文件存在时
                inStream = new FileInputStream(oldPath); //读入原文件
                fs = new FileOutputStream(newPath);
                byte[] buffer = new byte[1444];
                int length;
                while ((byteread = inStream.read(buffer)) != -1) {
                    bytesum += byteread; //字节数 文件大小
                    System.out.println(bytesum);
                    fs.write(buffer, 0, byteread);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (inStream != null) {
                try {
                    inStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fs != null) {
                try {
                    fs.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    /**
     * 删除最近已经上传的照片
     */
    public static void deleteLatelyPicture(Context context) {
        File cacheFile = context.getExternalCacheDir();
        if (cacheFile == null) {
            return;
        }
        File file = new File(cacheFile.getPath() + File.separator + "picture");
        File[] subFile = file.listFiles();
        if (subFile == null) {
            return;
        }
        for (int i = 0; i < subFile.length; i++) {
            File tempFile = subFile[i];
            String name = tempFile.getName();
            int startIndex = 0;
            if (name.contains("temp_")) {
                startIndex = 5;
            }
            int endIndex = name.length();
            int dot = name.lastIndexOf('.');
            if ((dot > -1) && (dot < (name.length() - 1))) {
                endIndex = dot;
            }
            String time = tempFile.getName().substring(startIndex, endIndex);
            try {
                long tempTime = Long.parseLong(time);
                long currentTime = new Date().getTime();
                long day = (currentTime - tempTime) / (24 * 60 * 60 * 1000);
                if (day > 30) {
                    tempFile.delete();
                }
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 定义获取插件的路径， added 2021.01.06, plugin作为apk文件只能放在data目录下
     */
    public static String getPluginPath(Context context) {
        return context.getFilesDir().getPath();
    }


    /**
     * 定义插件下载路径
     *
     * @param dirName 目录名称
     * @return 插件下载路径
     */
    public static String getPluginDownloadPath(@NonNull Context context, String dirName, boolean isHost) {
        File cacheFile = context.getExternalCacheDir();
        String pluginPath = cacheFile.getPath() + File.separator + Constants.FileFolderName.PDA_PLUGIN_DOWNLOAD + File.separator + dirName;
        if (isHost) {
            //如果是宿主
            pluginPath = getPDARoot() + File.separator + Constants.FileFolderName.PDA_PLUGIN_DOWNLOAD + File.separator + dirName;
        }
        File file = new File(pluginPath);
        if (!file.exists()) {
            file.mkdirs();
        }
        return pluginPath;
    }

    /**
     * 获取下载宿主或差分包路径
     *
     * @param dirName 目录名称
     * @return 宿主APP下载包路径
     */
    public static String getHostDownloadPath(String dirName) {
        String hostPath = getPDARoot() + File.separator + Constants.FileFolderName.PDA_HOST_APP + File.separator + dirName;
        File file = new File(hostPath);
        if (!file.exists()) {
            file.mkdirs();
        }
        return hostPath;
    }


    /**
     * 获取下载宿主或差分包路径
     *
     * @return 宿主APP下载包路径
     */
    public static String getHostPathRoot() {
        String hostPath = getPDARoot() + File.separator + Constants.FileFolderName.PDA_HOST_APP;
        File file = new File(hostPath);
        if (!file.exists()) {
            file.mkdirs();
        }
        return hostPath;
    }

    /**
     * 获取MD5值防止出现0的问题
     */
    public static String getFileMD5(File file) {
        if (file == null) {
            return null;
        }
        StringBuffer stringbuffer = null;
        FileInputStream in = null;
        try {
            char[] hexDigits = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
            in = new FileInputStream(file);
            FileChannel ch = in.getChannel();
            MappedByteBuffer byteBuffer = ch.map(FileChannel.MapMode.READ_ONLY, 0, file.length());
            MessageDigest messagedigest = MessageDigest.getInstance("MD5");
            messagedigest.update(byteBuffer);
            byte[] bytes = messagedigest.digest();
            int n = bytes.length;
            stringbuffer = new StringBuffer(2 * n);
            for (int l = 0; l < n; l++) {
                byte bt = bytes[l];
                char c0 = hexDigits[(bt & 0xf0) >> 4];
                char c1 = hexDigits[bt & 0xf];
                stringbuffer.append(c0);
                stringbuffer.append(c1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        if (stringbuffer != null) {
            return stringbuffer.toString();
        } else {
            return null;
        }
    }

    public static boolean safeDeleteFile(File file) {
        if (file == null) {
            return true;
        }

        if (file.exists()) {
            boolean deleted = file.delete();
            if (!deleted) {
                file.deleteOnExit();
            }
            return deleted;
        }
        return true;
    }


    /**
     * 6A巴枪删除超过本地存储的超过三个月的录音文件，释放手机内存
     */
    public static void clearCallRecordingFilesFor6A(long currentDate) {
        String path = FileUtils.getCallRecordingFilesDirFor6APDA();
        if (TextUtils.isEmpty(path)) return;
        File file = new File(path);
        if (!file.exists()) {
            return;
        }
        if (!file.isDirectory()) return;
        File[] files = file.listFiles();
        if (files != null) {
            for (File subFile : files) {
                String name = subFile.getName();
                if (TextUtils.isEmpty(name)) continue;
                String[] names = name.split("_");
                if (TextUtils.isEmpty(names[0])) continue;
                String dataYMD;
                if (name.startsWith("0_") || name.startsWith("1_")) {//自动录音生成的录音文件 如：0_13878042830_070121_08.47.34.3gpp
                    if (names.length != 4 || TextUtils.isEmpty(names[2]) || names[2].length() != 6)
                        continue;
                    String date = names[2].substring(0, 2);
                    if (TextUtils.isEmpty(date)) continue;
                    String month = names[2].substring(2, 4);
                    if (TextUtils.isEmpty(month)) continue;
                    String year = names[2].substring(4, 6);
                    if (TextUtils.isEmpty(year)) continue;
                    dataYMD = "20" + year + "-" + month + "-" + date;
                } else {//手动录音生成的文件 如：2021-04-01_09.27.201033015709.3gpp
                    dataYMD = names[0];
                }
                Date recodeDate = DateUtil.parse2Date(dataYMD + " 00:00:00");//录制日期 时间精度到天
                if (recodeDate != null && DateUtil.isMore90Days(currentDate, recodeDate.getTime())) {
                    safeDeleteFile(subFile);
                }
            }
        }
    }

    public static File[] getElectronicReceiptPaths() {
        File file = new File(getPDARoot() + File.separator + Constants.FileFolderName.PDA_ELECTRONIC_RECEIPT);
        if (file.exists() && file.isDirectory()) {
            return file.listFiles();
        }
        return null;
    }

    /**
     * 6C巴枪删除超过本地存储的超过三个月的录音文件，释放手机内存
     */
    public static void clearCallRecordingFilesFor6C(long currentDate) {
        String path = FileUtils.getCallRecordingFilesDirFor6CPDA();
        if (TextUtils.isEmpty(path)) return;
        File file = new File(path);
        if (!file.exists()) {
            return;
        }
        if (!file.isDirectory()) return;
        File[] files = file.listFiles();
        if (files == null) {
            return;
        }
        for (File subFile : files) {
            String name = subFile.getName();
            if (TextUtils.isEmpty(name)) continue;
            String[] names = name.split("_");
            if (TextUtils.isEmpty(names[0])) continue;
            if (names[0].length() != 8) continue;
            String year = names[0].substring(0, 4);//年
            String month = names[0].substring(4, 6);//月
            String date = names[0].substring(6);//日
            Date recodeDate = DateUtil.parse2Date(year + "-" + month + "-" + date + " 00:00:00");//录制日期 时间精度到天
            if (recodeDate != null && DateUtil.isMore90Days(currentDate, recodeDate.getTime())) {
                safeDeleteFile(subFile);
            }
        }
    }

    /**
     * 6B巴枪删除超过本地存储的超过三个月的录音文件，释放手机内存
     */
    public static void clearCallRecordingFilesFor6B(long currentDate) {
        String path = FileUtils.getCallRecordingFilesDirFor6BPDA();
        if (TextUtils.isEmpty(path)) return;
        File file = new File(path);
        if (!file.exists()) {
            return;
        }
        if (!file.isDirectory()) return;
        File[] files = file.listFiles();
        if (files == null) {
            return;
        }
        for (File subFile : files) {
            String name = subFile.getName();
            if (TextUtils.isEmpty(name)) continue;
            String[] names = name.split("_");
            if (names.length < 3 || TextUtils.isEmpty(names[2])) continue;
            try {
                String[] arr = names[2].split("\\.");
                long recordTime = Long.parseLong(arr[0]);
                if (DateUtil.isMore90Days(currentDate, recordTime)) {
                    safeDeleteFile(subFile);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 获取自定义相机的照片缓存路径
     *
     * @param context
     * @return
     */
    public static String getSignaturePicturePath(Context context) {
        if (context == null) {
            return null;
        }
        File file = context.getExternalCacheDir();
        if (file == null) {
            return null;
        }
        String fileName = String.format(Locale.getDefault(), "%s.png", String.valueOf(new Date().getTime()));
        File f = new File(file.getPath() + File.separator + "picture" + File.separator + "electronic_receipt", fileName);
        return com.kye.pda.utilcode.util.FileUtils.createOrExistsFile(f) ? f.getPath() : null;
    }


    /**
     * 6A巴枪获取通话录音文件存储目录
     *
     * @return 文件路径
     */
    private static String getCallRecordingFilesDirFor6APDA() {
        String dir = getSDCardFolder().getPath() + File.separator + Constants.FileFolderName.PHONE_RECORD;
        return dir;
    }

    private static String getCallRecordingFilesDirFor6CPDA() {
        String dir = getSDCardFolder().getPath() + File.separator + Constants.FileFolderName.PHONE_RECORD_6C;
        return dir;
    }

    private static String getCallRecordingFilesDirFor6BPDA() {
        String dir = getSDCardFolder().getPath() + File.separator + Constants.FileFolderName.PHONE_RECORD_6B;
        return dir;
    }

    /**
     * 创建MMKV的根目录文件夹路径
     *
     * @return
     */
    public static String getMMKVFolderPath() {

        String path = getPDARoot() + File.separator + Constants.FileFolderName.PDA_MMKV;
        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
        }
        return path;
    }

    //删除文件夹和文件夹里面的文件
    public static void deleteDirWithFile(String path) {
        File dir = new File(path);
        if (dir == null || !dir.exists() || !dir.isDirectory())
            return;
        // 遍历目录下的所有文件和文件夹
        File[] files = dir.listFiles();
        if (files == null) {
            return;
        }
        for (File file : files) {
            if (file.isFile())
                file.delete(); // 删除所有文件
            else if (file.isDirectory())
                deleteDirWithFile(path); // 递规的方式删除文件夹
        }
        dir.delete();// 删除目录本身
    }

    /**
     * 获取电子回单存储路径
     *
     * @return
     */
    public static String getElectronicReceiptPath() {
        String path = getPDARoot() + File.separator + Constants.FileFolderName.PDA_ELECTRONIC_RECEIPT;
        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
        }
        return path;
    }

    /**
     * 获取本地H5页面目录
     *
     * @return 目录
     */
    public static String getNewH5PathDir(Context context) {
        if (context == null) {
            return "";
        }
        String path = context.getFilesDir().getAbsolutePath() + File.separator + "h5";
        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
        }
        return path;
    }

    /**
     * 用于存储签到异常信息
     *
     * @param context
     * @param result
     * @return
     */
    public static String saveSignInfo(Context context, String result) {

        FileOutputStream fos = null;
        try {
            long timestamp = System.currentTimeMillis();
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
            String time = format.format(new Date());
            String fileName = "kye_sign_info" + time + "-" + timestamp + ".txt";
            if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
//                String path = FileUtils.getCrashPath();
                String signPath = FileUtils.getPDARoot() + File.separator + "sign_log";
                File dir = new File(signPath);
                if (!dir.exists()) {
                    dir.mkdirs();
                }
                String filePath = signPath + File.separator + fileName;
                fos = new FileOutputStream(filePath);
                fos.write(result.getBytes());
                //通知刷新
                Uri uri = Uri.fromFile(new File(filePath));
                Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri);
                context.sendBroadcast(intent);
            }
            return fileName;
        } catch (Exception e) {
        } finally {
            try {
                if (fos != null) {
                    fos.close();
                }
            } catch (IOException e) {
            }
        }
        return null;
    }

    /**
     * gps 定位状态记录，只用于测试
     *
     * @param newstr 写入的内容
     * @return
     * @throws IOException
     */
    public static boolean writeGpsCache(Context context, String newstr, String fileName) throws IOException {
        Boolean bool = false;
        String filein = newstr + "\r\n";//新写入的行，换行
        String temp = "";

        FileInputStream fis = null;
        InputStreamReader isr = null;
        BufferedReader br = null;
        FileOutputStream fos = null;
        PrintWriter pw = null;
        try {
            String filepath = getPDARoot() + File.separator + fileName;
            File file = new File(filepath);//文件路径(包括文件名称)
            if (!file.exists()) {
                file.createNewFile();
            }
            //将文件读入输入流
            fis = new FileInputStream(file);
            isr = new InputStreamReader(fis);
            br = new BufferedReader(isr);
            StringBuffer buffer = new StringBuffer();

            //文件原有内容
            for (int i = 0; (temp = br.readLine()) != null; i++) {
                buffer.append(temp);
                // 行与行之间的分隔符 相当于“\n”
                buffer = buffer.append(System.getProperty("line.separator"));
            }
            buffer.append(filein);

            fos = new FileOutputStream(file);
            pw = new PrintWriter(fos);
            pw.write(buffer.toString().toCharArray());
            pw.flush();
            bool = true;
            //通知刷新
            Uri uri = Uri.fromFile(new File(filepath));
            Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri);
            context.sendBroadcast(intent);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //不要忘记关闭
            if (pw != null) {
                pw.close();
            }
            if (fos != null) {
                fos.close();
            }
            if (br != null) {
                br.close();
            }
            if (isr != null) {
                isr.close();
            }
            if (fis != null) {
                fis.close();
            }
        }
        return bool;
    }

    /**
     * 写人文件
     *
     * @param context  上下文
     * @param fileName 文件名
     * @param data     数据
     */
    public static void writeToFile(Context context, String fileName, String data) {
        try {
            FileOutputStream fos = context.openFileOutput(fileName, Context.MODE_PRIVATE);
            fos.write(data.getBytes());
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * 读取文件
     *
     * @param context  上下文
     * @param fileName 文件名
     * @return 数据
     */
    public static String readFromFile(Context context, String fileName) {
        try {
            FileInputStream fis = context.openFileInput(fileName);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            br.close();
            return sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
