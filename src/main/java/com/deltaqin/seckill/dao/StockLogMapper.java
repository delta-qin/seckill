package com.deltaqin.seckill.dao;

import com.deltaqin.seckill.dataobject.StockLog;
import com.deltaqin.seckill.dataobject.StockLogExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface StockLogMapper {
    long countByExample(StockLogExample example);

    int deleteByExample(StockLogExample example);

    int deleteByPrimaryKey(Integer stockLogId);

    int insert(StockLog record);

    int insertSelective(StockLog record);

    List<StockLog> selectByExample(StockLogExample example);

    StockLog selectByPrimaryKey(Integer stockLogId);

    int updateByExampleSelective(@Param("record") StockLog record, @Param("example") StockLogExample example);

    int updateByExample(@Param("record") StockLog record, @Param("example") StockLogExample example);

    int updateByPrimaryKeySelective(StockLog record);

    int updateByPrimaryKey(StockLog record);
}