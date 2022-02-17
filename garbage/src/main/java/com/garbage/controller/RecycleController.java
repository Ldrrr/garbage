package com.garbage.controller;

import com.garbage.common.ServerResponse;
import com.garbage.dao.RecycleMapper;
import com.garbage.pojo.Recycle;
import com.garbage.service.IRecycleService;
import com.garbage.util.IDUtils;
import com.garbage.util.picFtp.FtpUtil;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.InputStream;

@Controller
@RequestMapping("/recycle/")
public class RecycleController {

    @Value("116.62.21.180")
    private String host;
    // 端口
    @Value("21")
    private int port;
    // ftp用户名
    @Value("${FTP.USERNAME}")
    private String userName;
    // ftp用户密码
    @Value("${FTP.PASSWORD}")
    private String passWord;
    // 文件在服务器端保存的主目录
    @Value("${FTP.BASEPATH}")
    private String basePath;
    // 访问图片时的基础url
    @Value("${IMAGE.BASE.URL}")
    private String baseUrl;


    @Resource
    private IRecycleService recycleService;

    @Resource
    private RecycleMapper recycleMapper;
    @RequestMapping(value = "upload_bin.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse uploadAshbin(Recycle bin, @RequestParam("pic") MultipartFile uploadFile){



        try {

            //1、给上传的图片生成新的文件名
            //1.获取原始的文件名
            String oldName = uploadFile.getOriginalFilename();
            String newName = IDUtils.genImageName();
            newName = newName + oldName.substring(oldName.lastIndexOf("."));
            String filePath = "";





            //3、把图片上传到图片服务器
            //3.1获取上传的io流
            InputStream input = uploadFile.getInputStream();
            boolean result = FtpUtil.uploadFile(host, port, userName, passWord, basePath, filePath, newName, input);
            if (result) {
                String pic= "116.62.21.180" +"/" + newName;

                bin.setPicture(pic);
              return   recycleService.addBin(bin);

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ServerResponse.createByErrorMsg("上传垃圾桶失败");

    }


    //查附近垃圾桶
    @RequestMapping(value = "get_bin.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse GetBin(String location){
        return   recycleService.getBin(location);
    }

    //查特定的经纬度垃圾桶
    @RequestMapping(value = "find_bin.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse GetBin(@RequestParam("longitude") String jd,@RequestParam("latitude") String wd){
        return   recycleService.findBin(jd,wd);
    }


    //修改垃圾桶
    @RequestMapping(value = "change_bin.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse Change_Bin(Recycle bin,@RequestParam("pic") MultipartFile uploadFile){

        if(bin==null){
            return ServerResponse.createByErrorMsg("垃圾桶参数错误");
        }
        if(uploadFile!=null){
            try {

                //1、给上传的图片生成新的文件名
                //1.获取原始的文件名
                String oldName = uploadFile.getOriginalFilename();
                String newName = IDUtils.genImageName();
                newName = newName + oldName.substring(oldName.lastIndexOf("."));
                String filePath = "";



                //3、把图片上传到图片服务器
                //3.1获取上传的io流
                InputStream input = uploadFile.getInputStream();
                boolean result = FtpUtil.uploadFile(host, port, userName, passWord, basePath, filePath, newName, input);
                if (result) {
                    String pic= "116.62.21.180" +"/" + newName;

                    bin.setPicture(pic);


                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        return recycleService.uploadBin(bin);
    }

    //删除垃圾桶
    @RequestMapping(value = "del_bin.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse DelBin(@RequestParam("longitude") String jd,@RequestParam("latitude") String wd){
        Recycle bin=new Recycle();
        bin.setLongitude(jd);
        bin.setLatitude(wd);
        
        return   recycleService.DelBin(bin);
    }

}
