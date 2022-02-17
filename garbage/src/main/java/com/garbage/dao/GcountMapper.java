package com.garbage.dao;

import com.garbage.pojo.Gcount;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface GcountMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Gcount record);

    int insertSelective(Gcount record);

    Gcount selectByPrimaryKey(Gcount record);

    int updateByPrimaryKeySelective(Gcount record);

    int updateByPrimaryKey(Gcount record);

    Gcount selectByPhone(String phone);
}