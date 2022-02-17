package com.garbage.service.impl;

import com.garbage.common.ServerResponse;
import com.garbage.dao.GcountMapper;
import com.garbage.pojo.Gcount;
import com.garbage.pojo.User;
import com.garbage.service.IG_CountService;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import javax.annotation.Resource;

@Service("IG_Service")
public class G_ServiceImpl implements IG_CountService {
    @Resource
    GcountMapper gcountMapper;

    @Override
    public int CreateCounter(User user) {
        Gcount gcount=new Gcount();
        gcount.setUserId(user.getId());
        gcount.setPhone(user.getPhone());
        gcount.setHarm(0);
        gcount.setDry(0);
        gcount.setWet(0);
        gcount.setRecyclable(0);
        int cnt = gcountMapper.insert(gcount);
        if(cnt==1){
            return 1;
        }
        return 0;
    }

    @Override
    public ServerResponse addCounter(String phone, String[] type, int[] counts, int n) {
        return null;
    }

    @Override
    public ServerResponse addCounter(String phone, String type, int n) {

        Gcount gcount = gcountMapper.selectByPhone(phone);
        switch (type){
            case "recyclable": gcount.setRecyclable(gcount.getRecyclable()+n);break;
            case "wet": gcount.setWet(gcount.getWet()+n);break;
            case "harm": gcount.setHarm(gcount.getHarm()+n);break;
            case "dry": gcount.setDry(gcount.getDry()+n);break;
        }
        System.err.println(gcount.toString());
        int i = gcountMapper.updateByPrimaryKey(gcount);
        if(i==1){
            return  ServerResponse.createBySuccess(gcount);
        }
        return  ServerResponse.createByErrorMsg("更新用户的垃圾分类信息失败");

    }


}
