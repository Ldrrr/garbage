package com.garbage.dao;

import com.garbage.pojo.Recycle;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface RecycleMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Recycle record);

    int insertSelective(Recycle record);

    Recycle selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Recycle record);

    int updateByPrimaryKey(Recycle record);

    List<Recycle> getNearBins(String location);

    Recycle findBin(@Param("longitude") String longitude , @Param("latitude") String latitude);
}