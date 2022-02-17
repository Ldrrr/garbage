package com.garbage.service.impl;


import com.garbage.common.Const;
import com.garbage.common.ServerResponse;
import com.garbage.dao.GcountMapper;
import com.garbage.dao.UserMapper;
import com.garbage.pojo.Gcount;
import com.garbage.pojo.User;
import com.garbage.service.IUserService;
import com.garbage.util.MD5Util;
import com.garbage.util.PhoneUtil;
import com.garbage.vo.UserDetail;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;


@Service("iUserService")
public class UserServiceImpl implements IUserService {

    @Resource
    private UserMapper userMapper;


    @Resource
    private GcountMapper gcountMapper;

    @Resource
    private G_ServiceImpl gCountService;

    @Override
    public ServerResponse login(String phoneNumber, String password) throws Exception {

        String MD5PassWord = MD5Util.MD5EncodeUtf8(password);
        User user = userMapper.selectUser(phoneNumber, MD5PassWord);
        if(user==null){
            return ServerResponse.createByErrorMsg("该用户未注册");
        }
        //return addToken(user);
        user.setPassword("");
        return ServerResponse.createBySuccess(user);
    }

    @Override
    public ServerResponse qqLogin(String qqId) throws Exception {
        return null;
    }


    public boolean checkValid(User user)
    {
        if(StringUtils.isBlank(user.getPhone()) || StringUtils.isBlank(user.getPassword()) || StringUtils.isBlank(user.getPhone()))
        {
            return false;
        }
        return true;
    }

    @Override
    public ServerResponse register(User user)
    {
        ServerResponse serverResponse = checkVaild(user.getPhone(), Const.PHONE);
        if(!serverResponse.isSuccess())
        {
            return serverResponse;
        }
        if(!checkValid(user))
        {
            ServerResponse.createByErrorMsg("信息不完全");
        }

//        if(!PhoneUtil.judgeCodeIsTrue(msgCode, user.getPhone()))
//        {
//            return ServerResponse.createByErrorMsg("注册失败, 验证码不正确");
//        }
        User insertUser = new User();
        insertUser.setPassword(MD5Util.MD5EncodeUtf8(user.getPassword()));
        insertUser.setName("用户" + user.getPhone());
        insertUser.setPhone(user.getPhone());
        insertUser.setImg("116.62.21.180/"+"default.jpg");
        int count = userMapper.insertSelective(insertUser);
        if(count > 0)
        {
            gCountService.CreateCounter(insertUser);
            return ServerResponse.createBySuccessMsg("注册成功");
        }
        return ServerResponse.createByErrorMsg("注册失败");

    }



    public ServerResponse checkVaild(String str, String type)
    {
        if(StringUtils.equals(type, Const.PHONE))
        {
            if(userMapper.selectPhoneCount(str) > 0)
            {
                return ServerResponse.createByErrorMsg("手机号码已存在");
            }
        }else {
            return ServerResponse.createByErrorMsg("参数错误");
        }
        return ServerResponse.createBySuccessMsg("信息无重复, 可用");
    }

    @Override
    public ServerResponse updateUserInfo(User updateUser)
    {

        System.out.println(updateUser);
        updateUser.setCreateTime(new Date());
        if(userMapper.updateByPrimaryKeySelective(updateUser) > 0)
        {
            System.err.println(updateUser.toString());
            return ServerResponse.createBySuccessMsg("更新信息成功");
        }
        return ServerResponse.createByErrorMsg("更新失败");
    }

    @Override
    public ServerResponse loginResetPassword(int id, String password)
    {
        User updateUser = new User();
        updateUser.setId(id);
        updateUser.setPassword(MD5Util.MD5EncodeUtf8(password));
        if(userMapper.updateByPrimaryKeySelective(updateUser) > 0)
        {
            return ServerResponse.createBySuccessMsg("重置成功, 请重新登录");
        }

        return ServerResponse.createBySuccessMsg("重置失败");
    }

    @Override
    public ServerResponse forgetResetPassword(String msgCode, String phoneNumber, String password)
    {
        if (!PhoneUtil.judgeCodeIsTrue(msgCode, phoneNumber)) {
            return ServerResponse.createByErrorMsg("验证码错误");
        }
        password = MD5Util.MD5EncodeUtf8(password);
        if(userMapper.updateByPhone(phoneNumber, password) > 0)
        {
            return ServerResponse.createBySuccessMsg("修改成功");
        }
        return ServerResponse.createByErrorMsg("修改失败");
    }

    @Override
    public ServerResponse<User> getUserInfo(Integer userId) {
        return null;
    }


    @Override
    public ServerResponse<User> checkName(String userName) {
        if(userMapper.selectNameCount(userName) > 0)
        {
            return ServerResponse.createByErrorMsg("昵称重复");
        }
        return ServerResponse.createBySuccessMsg("昵称可用");
    }

    @Override
    public ServerResponse qqRegister(String qqId, String phone, String msgCode, String img, String name) {
        return null;
    }


    @Override
    public ServerResponse insertUser(User user) {
        userMapper.insert(user);
        return ServerResponse.createBySuccessMsg("ok");
    }

    @Override
    public ServerResponse getDetail(String phone) {
        User user = userMapper.selectByPhone(phone);
        if(user==null){
            return ServerResponse.createByErrorMsg("查无此人");
        }
        Gcount gcount = gcountMapper.selectByPhone(phone);
        user.setPhone(phone);
        user.setPassword("");
        System.err.println("__=+"+user.toString());
        UserDetail userDetaill=new UserDetail(user,gcount);
        return  ServerResponse.createBySuccess(userDetaill);

    }

    @Override
    public ServerResponse getInfo(String phone) {
        User user = userMapper.selectByPhone(phone);
        if(user==null){
            return ServerResponse.createByErrorMsg("查无此人");
        }
        user.setPassword("");

        return ServerResponse.createBySuccess(user);
    }


    public  ServerResponse resetPass(String phone,String newpassword) {
        String newpass = MD5Util.MD5EncodeUtf8(newpassword);
        int i = userMapper.updateByPhone(phone, newpass);
        if(i>0){
            return ServerResponse.createBySuccessMsg("重置成功, 请重新登录");
        }else {
            return ServerResponse.createByErrorMsg("修改失败");
        }
    }

    @Override
    public ServerResponse<Gcount> calculateGarbage(String type, String phone) {
        return null;
    }


}
