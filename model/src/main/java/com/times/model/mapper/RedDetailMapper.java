package com.times.model.mapper;

import com.times.model.entity.RedDetail;

public interface RedDetailMapper {
    int deleteByPrimaryKey(Integer id); //根据主键ID删除
    int insert(RedDetail record); //插入数据记录
    int insertSelective(RedDetail record); //插入数据记录
    RedDetail selectByPrimaryKey(Integer id);//根据主键ID查询记录
    int updateByPrimaryKeySelective(RedDetail record); //更新数据记录
    int updateByPrimaryKey(RedDetail record);  //更新数据记录
}
