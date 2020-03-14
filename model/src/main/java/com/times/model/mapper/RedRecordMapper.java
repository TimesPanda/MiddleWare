package com.times.model.mapper;

import com.times.model.entity.RedRecord;

public interface RedRecordMapper {
    int deleteByPrimaryKey(Integer id); //根据主键ID删除
    int insert(RedRecord record); //插入数据记录
    int insertSelective(RedRecord record); //插入数据记录
    RedRecord selectByPrimaryKey(Integer id);//根据主键ID查询记录
    int updateByPrimaryKeySelective(RedRecord record); //更新数据记录
    int updateByPrimaryKey(RedRecord record);  //更新数据记录
}
