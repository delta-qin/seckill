package com.deltaqin.seckill.dao;

import com.deltaqin.seckill.dataobject.SeckillGood;
import com.deltaqin.seckill.dataobject.SeckillGoodExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SeckillGoodMapper {
    long countByExample(SeckillGoodExample example);

    int deleteByExample(SeckillGoodExample example);

    int deleteByPrimaryKey(Integer secId);

    int insert(SeckillGood record);

    int insertSelective(SeckillGood record);

    List<SeckillGood> selectByExample(SeckillGoodExample example);

    SeckillGood selectByPrimaryKey(Integer secId);

    int updateByExampleSelective(@Param("record") SeckillGood record, @Param("example") SeckillGoodExample example);

    int updateByExample(@Param("record") SeckillGood record, @Param("example") SeckillGoodExample example);

    int updateByPrimaryKeySelective(SeckillGood record);

    int updateByPrimaryKey(SeckillGood record);
}