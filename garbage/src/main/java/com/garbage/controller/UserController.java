package com.garbage.controller;


import com.garbage.common.Const;
import com.garbage.common.ServerResponse;
import com.garbage.dao.UserMapper;
import com.garbage.pojo.Gcount;
import com.garbage.pojo.Recycle;
import com.garbage.pojo.Sms;
import com.garbage.pojo.User;
import com.garbage.service.IFileService;
import com.garbage.service.IRecycleService;
import com.garbage.service.IUserService;
import com.garbage.service.impl.UserServiceImpl;
import com.garbage.util.FTPUtil;
import com.garbage.util.IDUtils;
import com.garbage.util.PhoneUtil;
import com.garbage.util.PropertiesUtil;
import com.garbage.util.picFtp.FtpUtil;
import com.garbage.util.smsUtil.GetCodeUtil;
import com.garbage.util.smsUtil.GetCodeUtil;
import com.garbage.util.smsUtil.SmsUtil;
import com.google.common.collect.Maps;
import com.sun.org.apache.bcel.internal.classfile.Code;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.type.SimpleTypeRegistry;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedMap;

@Controller
@RequestMapping("/user/")
public class UserController {

    @Resource
    private IUserService iUserService;

    @Resource
    private UserMapper userMapper;




    @RequestMapping(value = "login.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse login(HttpSession session, String phoneNumber, String password) {
        if(!phoneNumber.matches("^1[35-9]\\d{9}$")){
            return ServerResponse.createByErrorMsg("请输入正确的手机号！");
        }
        ServerResponse serverResponse;
        try {
            serverResponse = iUserService.login(phoneNumber, password);
        } catch (Exception e) {
            return ServerResponse.createByErrorMsg("登录失败");
        }
        User user = (User) serverResponse.getData();

        if (user != null) {
            session.setAttribute(Const.ID, user.getId());
            return serverResponse;
        } else {
            return ServerResponse.createByErrorMsg("用户名或者密码错误");
        }
    }

    HashMap<String,String> CodeMap=new HashMap<>();

    @RequestMapping("send_code.do")
    @ResponseBody
    public ServerResponse SendCode(String phone){
        if(!phone.matches("^1[35-9]\\d{9}$")){
            return ServerResponse.createByErrorMsg("请输入正确的手机号！");
        }

        String code = GetCodeUtil.getCode(4);
        Sms sms=new Sms(phone,code,5);
        SmsUtil.Send(sms);
        CodeMap.put(phone,code);
        return ServerResponse.createBySuccessMsg("发送成功");
    }


    @RequestMapping("register.do")
    @ResponseBody
    public ServerResponse register(User user,String msgCode){

        if(!user.getPhone().matches("^1[35-9]\\d{9}$")){
            return ServerResponse.createByErrorMsg("请输入正确的手机号！");
        }
        String s = CodeMap.get(user.getPhone());
        if(s.equals(msgCode)){
            return iUserService.register(user);
        } else{
            return ServerResponse.createByErrorMsg("验证码错误！");
        }

    }




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

    @RequestMapping(value = "change_pic", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse changepic(String phone,@RequestParam("pic") MultipartFile uploadFile){



        try {

            //1、给上传的图片生成新的文件名
            //1.获取原始的文件名
            String oldName = uploadFile.getOriginalFilename();
            String newName = IDUtils.genImageName();
            newName = newName + oldName.substring(oldName.lastIndexOf("."));
            String filePath = "";

            //2、把前端输入信息，包括图片的url保存到数据库
            User user = userMapper.selectByPhone(phone);
            if(user==null){
                return  ServerResponse.createByErrorMsg("这个手机没有注册");
            }



            //3、把图片上传到图片服务器
            //3.1获取上传的io流
            InputStream input = uploadFile.getInputStream();
            boolean result = FtpUtil.uploadFile(host, port, userName, passWord, basePath, filePath, newName, input);
            if (result) {
                String pic= "116.62.21.180" +"/" + newName;
                user.setImg(pic);
                System.err.println(phone);
                userMapper.changePic(phone,pic);
                return ServerResponse.createBySuccess(user);
            }else{
                return ServerResponse.createByErrorMsg("头像失败");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ServerResponse.createByErrorMsg("修改头像失败");

    }





    @RequestMapping(value = "getmsgcode.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse getMsgcode(String phoneNumber) {
        if (PhoneUtil.getVerificationCode(phoneNumber) != null) {
            return ServerResponse.createBySuccessMsg("发送成功");
        }
        return ServerResponse.createByErrorMsg("发送失败");
    }

    @RequestMapping(value = "loginresetpassword.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse loginResetPassword(String phone,String newpassword) {
        return iUserService.resetPass(phone,newpassword);

    }



    @RequestMapping(value = "updatemsg.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<User> updateUserInfo(User user) {

        System.err.println(user.toString()+"___________");
        return iUserService.updateUserInfo(user);
    }


    @RequestMapping(value = "calculategarbage.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<Gcount> calculateGarbage(String type, String phone) {
        return  iUserService.calculateGarbage(type,phone);
    }


    @RequestMapping("get_detail")
    @ResponseBody
    public ServerResponse GetDetail(String phone){
        return  iUserService.getDetail(phone);
    }

    @RequestMapping("get_info")
    @ResponseBody
    public ServerResponse GetInfo(String phone){
        return  iUserService.getInfo(phone);
    }
}















