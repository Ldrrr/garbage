package com.garbage.service.impl;

import com.garbage.common.ServerResponse;
import com.garbage.dao.RecycleMapper;
import com.garbage.pojo.Recycle;
import com.garbage.service.IRecycleService;
import com.garbage.service.IUserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("iRecycle")
public class RecycleServiceImpl implements IRecycleService {

    @Resource
    RecycleMapper recycleMapper;

    @Override
    public ServerResponse addBin(Recycle bin) {

        String longitude=bin.getLongitude();
        String latitude=bin.getLatitude();
        Recycle binTmp = recycleMapper.findBin(longitude,latitude);

        if(binTmp!=null){


            return ServerResponse.createByErrorMsg("这垃圾桶已经存在了");

        }
        int cnt = recycleMapper.insert(bin);
        if(cnt>0){
            return  ServerResponse.createBySuccess(bin);
        }else{
            return  ServerResponse.createByErrorMsg("上传失败");
        }
    }

    @Override
    public ServerResponse getBin(String location) {
        location="%"+location+"%";
        List<Recycle> bins=recycleMapper.getNearBins(location);
        return ServerResponse.createBySuccess(bins);
    }

    @Override
    public ServerResponse uploadBin(Recycle bin) {
        String longitude=bin.getLongitude();
        String latitude=bin.getLatitude();
        if(bin==null||longitude==null||latitude==null){
            return ServerResponse.createByErrorMsg("垃圾桶经纬度错误");
        }

        Recycle binTmp = recycleMapper.findBin(longitude, latitude);
        if(binTmp==null){
            return ServerResponse.createByErrorMsg("找不到这垃圾桶");
        }

        bin.setId(binTmp.getId());
        int i = recycleMapper.updateByPrimaryKeySelective(bin);
        if(i>0){
            return ServerResponse.createBySuccess(bin);
        }else{
            return  ServerResponse.createByErrorMsg("修改失败");
        }
    }

    @Override
    public ServerResponse DelBin(Recycle bin) {
        String longitude=bin.getLongitude();
        String latitude=bin.getLatitude();
        if(bin==null||longitude==null||latitude==null){
            return ServerResponse.createByErrorMsg("垃圾桶经纬度错误");
        }
        Recycle binTmp = recycleMapper.findBin(longitude, latitude);
        if(binTmp==null){
            return ServerResponse.createByErrorMsg("找不到这垃圾桶");
        }
        int i = recycleMapper.deleteByPrimaryKey(binTmp.getId());
        if(i>0){
            return ServerResponse.createBySuccess(bin);
        }else {
           return ServerResponse.createByErrorMsg("删除垃圾桶出现错误，该垃圾桶本来就不存在");
        }
    }

    @Override
    public ServerResponse findBin(String jd, String wd) {
        Recycle bin = recycleMapper.findBin(jd, wd);
        if(bin==null){
            return ServerResponse.createByErrorMsg("找不到");
        }else{
            return ServerResponse.createBySuccess(bin);
        }
    }
}
