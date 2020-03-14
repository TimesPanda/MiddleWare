package com.times.model.mapper;

import com.times.model.entity.RedRobRecord;

public interface RedRobRecordMapper {
    int deleteByPrimaryKey(Integer id); //根据主键ID删除
    int insert(RedRobRecord record); //插入数据记录
    int insertSelective(RedRobRecord record); //插入数据记录
    RedRobRecord selectByPrimaryKey(Integer id);//根据主键ID查询记录
    int updateByPrimaryKeySelective(RedRobRecord record); //更新数据记录
    int updateByPrimaryKey(RedRobRecord record);  //更新数据记录
}
