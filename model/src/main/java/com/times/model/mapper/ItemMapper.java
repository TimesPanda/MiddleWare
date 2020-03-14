package com.times.model.mapper;

import com.times.model.entity.Item;
import org.apache.ibatis.annotations.Param;

public interface ItemMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(Item record);

    int insertSelective(Item record);

    Item selectByPrimaryKey(Integer id);
    int updateByPrimaryKeySelective(Item recod);
    int updateByPrimaryKey(Item record);
    Item selectByCode(@Param("code") String code);
}
