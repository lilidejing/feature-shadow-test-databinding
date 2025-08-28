package com.kye.utils.crashlog;

import android.content.Context;
import android.os.Environment;

import com.kye.pda.utilcode.util.ProcessUtils;
import com.kye.pda.utils.time.DateUtil;
import com.kye.utils.FileUtils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Routing
 * Desc TODO
 * Source
 * Created by lt on 2019/8/8 15:18
 * Modify by lt on 2019/8/8 15:18
 * Version 1.0
 */
public class LogFillUtil {

    public static final int TYPE_BURY = 1;
    public static final int TYPE_PUSH = 2;
    public static final int TYPE_PHOTO_UPLOAD = 3;

    private static LogFillUtil sBuryFillUtil;
    private static ExecutorService sExecutor;
    /**
     * 用于格式化日期,作为日志文件名的一部分
     */
    private DateFormat mFormatter = null;
    private String mKyConfigName = "ky_config.txt";

    private LogFillUtil() {

        sExecutor = Executors.newSingleThreadExecutor();
        mFormatter = new SimpleDateFormat("yyyy-MM-dd");
    }


    public static LogFillUtil getInstance() {

        if (sBuryFillUtil == null) {

            synchronized (LogFillUtil.class) {

                if (sBuryFillUtil == null) {

                    sBuryFillUtil = new LogFillUtil();
                }

            }

        }
        return sBuryFillUtil;
    }

    /**
     * 保存信息到埋点文件中
     */
    public void saveText2File(final String text, final int type) {
        final String normalText = DateUtil.formatComplemetDate(new Date()) + text + "\n";
        Future<Boolean> submit = sExecutor.submit(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                String time = mFormatter.format(new Date());
                String fileName = time + ".txt";
                if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                    String path = "";
                    switch (type) {
                        case TYPE_BURY:
                            path = FileUtils.getBuryLogPath();
                            break;
                        case TYPE_PUSH:
                            path = FileUtils.getPushLogPath();
                            break;
                        case TYPE_PHOTO_UPLOAD:
                            path = FileUtils.getPhotoUploadLogPath();
                            break;
                        default:
                            return false;
                    }
                    File dir = new File(path);
                    if (!dir.exists()) {
                        dir.mkdirs();
                    }
                    String filePath = path + File.separator + fileName;
                    BufferedWriter bw = null;
                    try {
                        bw = new BufferedWriter(new FileWriter(filePath, true));
                        bw.write(normalText);
                        return true;
                    } catch (IOException e) {
                        e.printStackTrace();
                        return false;
                    } finally {
                        try {
                            if (bw != null) {
                                bw.close();
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
                return false;
            }
        });
    }

    /**
     * 保存信息到埋点文件中
     *
     * @return
     */
    public void saveText2File(Context context, final String text, final int type) {
        String processName = "";
        if (context != null) {
            processName = ProcessUtils.getCurrentProcessName();
        }
        String threadName = Thread.currentThread().getName();
        final String normalText = DateUtil.formatComplemetDate(new Date()) + " 当前进程名：" + processName + " 当前线程：" + threadName + " " + text + "\n";
        Future<Boolean> submit = sExecutor.submit(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                String time = mFormatter.format(new Date());
                String fileName = time + ".txt";
                if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                    String path = "";
                    switch (type) {
                        case TYPE_BURY:
                            path = FileUtils.getBuryLogPath();
                            break;
                        case TYPE_PUSH:
                            path = FileUtils.getPushLogPath();
                            break;
                        case TYPE_PHOTO_UPLOAD:
                            path = FileUtils.getPhotoUploadLogPath();
                            break;
                        default:
                            return false;
                    }
                    File dir = new File(path);
                    if (!dir.exists()) {
                        dir.mkdirs();
                    }
                    String filePath = path + File.separator + fileName;
                    BufferedWriter bw = null;
                    try {
                        bw = new BufferedWriter(new FileWriter(filePath, true));
                        bw.write(normalText);
                        return true;
                    } catch (IOException e) {
                        e.printStackTrace();
                        return false;
                    } finally {
                        try {
                            if (bw != null) {
                                bw.close();
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
                return false;

            }
        });
    }

    /**
     * 保存配制文件
     *
     * @param jsonText json文件
     * @return
     */
    public void saveConfigFile(final String jsonText) {
        Future<Boolean> submit = sExecutor.submit(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                    String path = FileUtils.getKyConfigPath();
                    String filePath = path + File.separator + mKyConfigName;
                    BufferedWriter bw = null;
                    try {
                        bw = new BufferedWriter(new FileWriter(filePath));
                        bw.write(jsonText);
                        return true;
                    } catch (IOException e) {
                        e.printStackTrace();
                        return false;
                    } finally {
                        try {
                            if (bw != null) {
                                bw.close();
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
                return false;

            }
        });
    }

    /**
     * 获取ky配制文件
     *
     * @return
     */
    public String getConfigFile() {
        Future<String> submit = sExecutor.submit(new Callable<String>() {
            @Override
            public String call() throws Exception {
                if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                    String path = FileUtils.getKyConfigPath();
                    String filePath = path + File.separator + mKyConfigName;
                    BufferedReader br = null;
                    try {
                        br = new BufferedReader(new FileReader(filePath));
                        return br.readLine();
                    } catch (IOException e) {
                        e.printStackTrace();
                        return "";
                    } finally {
                        try {
                            if (br != null) {
                                br.close();
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
                return "";
            }
        });
        try {
            return submit.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 获取域名文件
     *
     * @return
     */
    public String getFileReader(String filePath) {
        Future<String> submit = sExecutor.submit(new Callable<String>() {
            @Override
            public String call() throws Exception {
                if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                    BufferedReader br = null;
                    try {
                        br = new BufferedReader(new FileReader(filePath));
                        StringBuilder stringBuilder = new StringBuilder();
                        String line;
                        while ((line = br.readLine()) != null) {
                            stringBuilder.append(line);
                        }
                        return stringBuilder.toString();
                    } catch (IOException e) {
                        e.printStackTrace();
                        return "";
                    } finally {
                        try {
                            if (br != null) {
                                br.close();
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
                return "";
            }
        });
        try {
            return submit.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 删除历史久远的日志
     *
     * @return
     */
    public void deleteLatelyFile() {
        Future<Boolean> submit = sExecutor.submit(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                File file1 = new File(FileUtils.getBuryLogPath());
                File[] subFile1 = file1.listFiles();
                File file2 = new File(FileUtils.getPushLogPath());
                File[] subFile2 = file2.listFiles();
                File file3 = new File(FileUtils.getPhotoUploadLogPath());
                File[] subFile3 = file3.listFiles();
                ArrayList<File> files = new ArrayList<>();
                if (subFile1 != null) {
                    for (File file : subFile1) {
                        files.add(file);
                    }
                }
                if (subFile2 != null) {
                    for (File file : subFile2) {
                        files.add(file);
                    }
                }
                if (subFile3 != null) {
                    for (File file : subFile3) {
                        files.add(file);
                    }
                }
                if (files != null) {
                    for (int i = 0; i < files.size(); i++) {
                        File tempFile = files.get(i);
                        String name = tempFile.getName();
                        int startIndex = 0;
                        int endIndex = name.length();
                        int dot = name.lastIndexOf('.');
                        if ((dot > -1) && (dot < (name.length() - 1))) {
                            endIndex = dot;
                        }
                        String time = tempFile.getName().substring(startIndex, endIndex);
                        try {
                            long tempTime = mFormatter.parse(time).getTime();
                            long currentTime = System.currentTimeMillis();
                            long day = (currentTime - tempTime) / (24 * 60 * 60 * 1000);
                            if (day > 30) {
                                tempFile.delete();
                            }
                        } catch (NumberFormatException e) {
                            e.printStackTrace();
                        }
                    }
                }
                return true;
            }
        });
    }


}
