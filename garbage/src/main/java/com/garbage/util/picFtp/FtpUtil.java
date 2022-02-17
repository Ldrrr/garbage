//package com.garbage.util;
//
//import com.garbage.util.PropertiesUtil;
//import org.apache.commons.net.ftp.FTPClient;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.IOException;
//import java.util.List;
//
//
//public class FtpUtil {
//
//    private static final Logger logger = LoggerFactory.getLogger(FtpUtil.class);
//
//    private static String ftpIp = PropertiesUtil.getProperty("ftp.server.ip");
//
//    private static String ftpUser = PropertiesUtil.getProperty("ftp.user");
//    private static String ftpPass = PropertiesUtil.getProperty("ftp.pass");
//
//    public FtpUtil(String ip, int port, String user, String pwd) {
//        this.ip = ip;
//        this.port = port;
//        this.user = user;
//        this.pwd = pwd;
//    }
//
//    public static boolean uploadFile(List<File> fileList) throws IOException {
//        FtpUtil ftpUtil = new FtpUtil(ftpIp, 21, ftpUser, ftpPass);
//        logger.info("开始连接ftp服务器");
//        boolean result = ftpUtil.uploadFile("/home/ftpuser/images", fileList);
//        logger.info("开始连接ftp服务器,结束上传,上传结果:{}");
//        return result;
//    }
//
//
//    private boolean uploadFile(String remotePath, List<File> fileList) throws IOException {
//        boolean uploaded = true;
//        FileInputStream fis = null;
//        //连接FTP服务器
//        if (connectServer(this.ip, this.port, this.user, this.pwd)) {
//            try {
//                ftpClient.changeWorkingDirectory(remotePath);
//                ftpClient.setBufferSize(1024);
//                ftpClient.setControlEncoding("UTF-8");
//                ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
//                ftpClient.enterLocalPassiveMode();
//                for (File fileItem : fileList) {
//                    fis = new FileInputStream(fileItem);
//                    ftpClient.storeFile(fileItem.getName(), fis);
//                }
//
//            } catch (IOException e) {
//                logger.error("上传文件异常", e);
//                uploaded = false;
//                e.printStackTrace();
//            } finally {
//                fis.close();
//                ftpClient.disconnect();
//            }
//        }
//        return uploaded;
//    }
//
//
//    private boolean connectServer(String ip, int port, String user, String pwd) {
//
//        boolean isSuccess = false;
//        ftpClient = new FTPClient();
//        try {
//            ftpClient.connect(ip);
//            isSuccess = ftpClient.login(user, pwd);
//        } catch (IOException e) {
//            logger.error("连接FTP服务器异常", e);
//        }
//        return isSuccess;
//    }
//
//
//    private String ip;
//    private int port;
//    private String user;
//    private String pwd;
//    private FTPClient ftpClient;
//
//    public String getIp() {
//        return ip;
//    }
//
//    public void setIp(String ip) {
//        this.ip = ip;
//    }
//
//    public int getPort() {
//        return port;
//    }
//
//    public void setPort(int port) {
//        this.port = port;
//    }
//
//    public String getUser() {
//        return user;
//    }
//
//    public void setUser(String user) {
//        this.user = user;
//    }
//
//    public String getPwd() {
//        return pwd;
//    }
//
//    public void setPwd(String pwd) {
//        this.pwd = pwd;
//    }
//
//    public FTPClient getFtpClient() {
//        return ftpClient;
//    }
//
//    public void setFtpClient(FTPClient ftpClient) {
//        this.ftpClient = ftpClient;
//    }
//}




package com.garbage.util.picFtp;


import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;

import java.io.IOException;
import java.io.InputStream;

public class FtpUtil {

    /**
     * Description: 向FTP服务器上传文件
     * @param host FTP服务器ip
     * @param port FTP服务器端口
     * @param username FTP登录账号
     * @param password FTP登录密码
     * @param basePath FTP服务器基础目录,/home/ftpuser/images
     * @param filePath FTP服务器文件存放路径。例如分日期存放：/2018/05/28。文件的路径为basePath+filePath
     * @param filename 上传到FTP服务器上的文件名
     * @param input 输入流
     * @return 成功返回true，否则返回false
     */
    public static boolean uploadFile(String host, int port, String username, String password, String basePath,
                                     String filePath, String filename, InputStream input) {
        boolean result = false;
        FTPClient ftp = new FTPClient();
        try {
            int reply;
            ftp.connect(host, port);// 连接FTP服务器
            // 如果采用默认端口，可以使用ftp.connect(host)的方式直接连接FTP服务器
            ftp.login(username, password);// 登录
            reply = ftp.getReplyCode();
            if (!FTPReply.isPositiveCompletion(reply)) {
                ftp.disconnect();
                return result;
            }
            //切换到上传目录
            if (!ftp.changeWorkingDirectory(basePath+filePath)) {
                //如果目录不存在创建目录
                String[] dirs = filePath.split("/");
                String tempPath = basePath;
                for (String dir : dirs) {
                    if (null == dir || "".equals(dir)) continue;
                    tempPath += "/" + dir;
                    if (!ftp.changeWorkingDirectory(tempPath)) {
                        if (!ftp.makeDirectory(tempPath)) {
                            return result;
                        } else {
                            ftp.changeWorkingDirectory(tempPath);
                        }
                    }
                }
            }
            //设置为被动模式
            ftp.enterLocalPassiveMode();
            //设置上传文件的类型为二进制类型
            ftp.setFileType(FTP.BINARY_FILE_TYPE);
            //上传文件
            if (!ftp.storeFile(filename, input)) {
                return result;
            }
            input.close();
            ftp.logout();
            result = true;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (ftp.isConnected()) {
                try {
                    ftp.disconnect();
                } catch (IOException ioe) {
                }
            }
        }
        return result;
    }
}
