package com.wristband.gaoyusheng.base_lib_module.util;

import android.app.Application;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;

import org.apache.commons.lang3.StringUtils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.channels.FileChannel;
import java.text.SimpleDateFormat;
import java.util.Date;

import io.reactivex.Observable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;


/**
 * Created by gaoyusheng on 17/3/23.
 */

public class FileUtil {
    private Context context;

    private static FileUtil fileUtil;

    private static final String PATH = Environment.getExternalStorageDirectory().getPath() + "/hzgcrash/log/";
    private static final String FILE_NAME = "crash";
    //log文件的后缀名
    private static final String FILE_NAME_SUFFIX = ".text";
    private static final String LOG_FILE_NAME_SUFFIX = ".log";


    //日志保存到文件
    public static void saveLogToFile(String text) {
        File dir = new File(PATH);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        long current = System.currentTimeMillis();
        String time = new SimpleDateFormat("yyyy-MM-dd__HHmmss").format(new Date(current));
        String timeCreate = new SimpleDateFormat("yyyy-MM-dd").format(new Date(current));
        String needWriteMessage = time + ":" + "    " + text;
        File file = new File(PATH + timeCreate + FILE_NAME_SUFFIX);
        try {
            FileWriter filerWriter = new FileWriter(file, true);// 后面这个参数代表是不是要接上文件中原来的数据，不进行覆盖 94
            BufferedWriter bufWriter = new BufferedWriter(filerWriter);
            bufWriter.write(needWriteMessage);
            bufWriter.newLine();
            bufWriter.close();
            filerWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void createLogFileAndUpload(String message) {
        File dir = new File(PATH);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        long current = System.currentTimeMillis();
        String time = new SimpleDateFormat("yyyy-MM-dd__HHmmss").format(new Date(current));
        //以当前时间创建log文件
        File file = new File(PATH + FILE_NAME + time + LOG_FILE_NAME_SUFFIX);

        try {
            PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(file)));
            //导出发生异常的时间
            pw.println(time);

            //导出手机信息
            dumpPhoneInfo(pw);

            pw.println();
            //导出异常的调用栈信息
            pw.append(message);
//            ex.printStackTrace(pw);

            pw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        uploadCrashFile();
    }

    public static void createCrashFileAndUpload(Throwable ex) {
        File dir = new File(PATH);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        long current = System.currentTimeMillis();
        String time = new SimpleDateFormat("yyyy-MM-dd__HHmmss").format(new Date(current));
        //以当前时间创建log文件
        File file = new File(PATH + FILE_NAME + time + FILE_NAME_SUFFIX);

        try {
            PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(file)));
            //导出发生异常的时间
            pw.println(time);

            //导出手机信息
            dumpPhoneInfo(pw);

            pw.println();
            //导出异常的调用栈信息
            ex.printStackTrace(pw);

            pw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        uploadCrashFile();
    }

    public static void uploadCrashFile() {
        //这里可以通过网络上传异常信息到服务器，便于开发人员分析日志从而解决bug
        Observable.just(true).subscribeOn(Schedulers.io())
                .doOnNext(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) {
                        if (isCanPingFtpServer()) {
                            uploadExceptionToServer();
                        }
                    }
                }).subscribe(new Consumer<Boolean>() {
            @Override
            public void accept(Boolean aBoolean) {

            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) {

            }
        });
    }

    private static boolean isCanPingFtpServer() {
        Process p = null;//m_strForNetAddress是输入的网址或者Ip地址
        try {
            p = Runtime.getRuntime().exec("ping -c 1 " + "10.105.19.22");
        } catch (IOException e) {
            e.printStackTrace();
        }
        int status = 1; //status 只能获取是否成功，无法获取更多的信息
        try {
            status = p.waitFor();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (status == 0) {
            return true;
        } else {
            return false;
        }
    }

    private static void uploadExceptionToServer() {
        try {
            File dir = new File(PATH);
            if (dir.exists()) {
                for (File file : dir.listFiles()) {
                    boolean result = FtpUtil.ftpUpload("10.253.9.93", "82", "user", "1qaz!QAZ", PATH, file.getName());
                    if (result) {
                        file.delete();
                    }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private static void dumpPhoneInfo(PrintWriter pw) throws PackageManager.NameNotFoundException {
        //应用的版本名称和版本号
        PackageManager pm = getFileUtil().context.getPackageManager();
        PackageInfo pi = pm.getPackageInfo(getFileUtil().context.getPackageName(), PackageManager.GET_ACTIVITIES);
        pw.print("App Version: ");
        pw.print(pi.versionName);
        pw.print('_');
        pw.println(pi.versionCode);

        //android版本号
        pw.print("OS Version: ");
        pw.print(Build.VERSION.RELEASE);
        pw.print("_");
        pw.println(Build.VERSION.SDK_INT);

        //手机制造商
        pw.print("Vendor: ");
        pw.println(Build.MANUFACTURER);

        //手机型号
        pw.print("Model: ");
        pw.println(Build.MODEL);

        //cpu架构
        pw.print("CPU ABI: ");
        pw.println(Build.CPU_ABI);
    }

    private static FileUtil getFileUtil() {
        if (fileUtil == null) {
            fileUtil = new FileUtil();
        }
        return fileUtil;
    }

    public static void initFileUtil(Application application) {
        getFileUtil().context = application;
    }

    public static String getFileString(String path) {
        return getFileUtil().readSDFile(Environment.getExternalStorageDirectory()
                .getAbsolutePath() + File.separator + path);
    }

    public static String readFileAndCreateIfNotExist(String folderName, String fileName, String defaultStr) {
        String res = StringUtils.EMPTY;
        try {
            File root = new File(Environment.getExternalStorageDirectory(), folderName);
            if (!root.exists()) {
                root.mkdirs();
            }
            File file = new File(root, fileName);
            if (file.exists()) {
                FileInputStream fis = new FileInputStream(file);

                int length = fis.available();

                byte[] buffer = new byte[length];
                fis.read(buffer);
                res = new String(buffer, "UTF-8");

                fis.close();
            } else {
                res = defaultStr;
                FileWriter writer = new FileWriter(file);
                writer.append(defaultStr);
                writer.flush();
                writer.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return res;
    }

    public static boolean saveFile(String content, String path, String fileName) {
        try {
            File root = new File(Environment.getExternalStorageDirectory(), path);
            if (!root.exists()) {
                if (!root.mkdirs()) {
                    return false;
                }
            }
            File file = new File(root, fileName);
            if (file.exists()) {
                return false;
            } else {
                FileWriter writer = new FileWriter(file);
                writer.append(content);
                writer.flush();
                writer.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static boolean copyFile(String sourcePath, String destPath) {
        File sourceFile = new File(Environment.getExternalStorageDirectory() + "/" + sourcePath);
        if (!sourceFile.exists()) {
            return false;
        }
        File desFile = new File(Environment.getExternalStorageDirectory() + "/" + destPath);
        return copyFile(sourceFile, desFile);
    }

    public static boolean deleteFile(String path) {
        File file = new File(Environment.getExternalStorageDirectory() + "/" + path);
        return file.delete();
    }

    public static boolean copyFile(File sourceFile, File destFile) {
        try {
            if (!destFile.getParentFile().exists()) {
                destFile.getParentFile().mkdirs();
            }

            if (!destFile.exists()) {
                destFile.createNewFile();
            }

            FileChannel source = null;
            FileChannel destination = null;

            try {
                source = new FileInputStream(sourceFile).getChannel();
                destination = new FileOutputStream(destFile).getChannel();
                destination.transferFrom(source, 0, source.size());
            } finally {
                if (source != null) {
                    source.close();
                }
                if (destination != null) {
                    destination.close();
                }
            }
        } catch (IOException ex) {
            return false;
        }
        return true;
    }

    private String readSDFile(String fileName) {
        String res = StringUtils.EMPTY;
        try {
            File file = new File(fileName);

            FileInputStream fis = new FileInputStream(file);

            int length = fis.available();

            byte[] buffer = new byte[length];
            fis.read(buffer);
            res = new String(buffer, "UTF-8");

            fis.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return res;
    }
}
