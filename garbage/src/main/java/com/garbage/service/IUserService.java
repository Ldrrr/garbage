package com.garbage.service;


import com.garbage.common.ServerResponse;
import com.garbage.pojo.Gcount;
import com.garbage.pojo.User;

public interface IUserService {

    ServerResponse login(String phoneNumber, String password) throws Exception;

    ServerResponse qqLogin(String qqId) throws Exception;

    ServerResponse register(User user);

    ServerResponse updateUserInfo(User user);

    ServerResponse loginResetPassword(int userId, String password);

    ServerResponse forgetResetPassword(String msgCode, String phoneNumber, String password);

    ServerResponse<User> getUserInfo(Integer userId);

    ServerResponse<User> checkName(String userName);

    ServerResponse qqRegister(String qqId, String phone, String msgCode, String img, String name);

    ServerResponse resetPass(String phone,String newpassword);


    ServerResponse<Gcount> calculateGarbage(String type, String phone);

    ServerResponse insertUser(User user);


    ServerResponse getDetail(String phone);

    ServerResponse getInfo(String phone);
}