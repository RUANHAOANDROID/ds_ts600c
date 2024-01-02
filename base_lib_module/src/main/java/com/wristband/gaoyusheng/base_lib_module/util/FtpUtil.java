package com.wristband.gaoyusheng.base_lib_module.util;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;

import java.io.FileInputStream;
import java.io.IOException;

/**
 * Created by gaoyusheng on 17/5/9.
 */

public class FtpUtil {
    private static final String PREFIX_REMOVE_FOLDER = "app/exception/";
    public static String remoteFolder = "start";

    /**
     * @param url          IP地址
     * @param port         端口号
     * @param username     用户名
     * @param password     密码
     * @param fileNamePath 上传文件目录
     * @param fileName     上传文件名
     * @return
     */
    public static Boolean ftpUpload(String url, String port, String username, String password, String fileNamePath, String fileName) {
        FTPClient ftpClient = new FTPClient();
        FileInputStream fis = null;
        try {
            ftpClient.connect(url, Integer.parseInt(port));
            boolean loginResult = ftpClient.login(username, password);
            int returnCode = ftpClient.getReplyCode();
            if (loginResult && FTPReply.isPositiveCompletion(returnCode)) {// 如果登录成功
                ftpClient.makeDirectory(PREFIX_REMOVE_FOLDER + remoteFolder);
                // 设置上传目录
                ftpClient.changeWorkingDirectory(PREFIX_REMOVE_FOLDER + remoteFolder);
                ftpClient.setBufferSize(1024);
                ftpClient.setControlEncoding("UTF-8");
                ftpClient.enterLocalPassiveMode();
                fis = new FileInputStream(fileNamePath + fileName);
                ftpClient.storeFile(fileName, fis);

                return true;
            } else {// 如果登录失败
                return false;
            }

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } finally {
            //IOUtils.closeQuietly(fis);
            try {
                ftpClient.disconnect();
            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException("关闭FTP连接发生异常！", e);
            }
        }
    }
}
