package com.deltaqin.seckill.dao;

import com.deltaqin.seckill.dataobject.ItemStock;
import com.deltaqin.seckill.dataobject.ItemStockExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ItemStockMapper {
    long countByExample(ItemStockExample example);

    int deleteByExample(ItemStockExample example);

    int deleteByPrimaryKey(Integer stockId);

    int insert(ItemStock record);

    int insertSelective(ItemStock record);

    List<ItemStock> selectByExample(ItemStockExample example);

    ItemStock selectByPrimaryKey(Integer stockId);

    int updateByExampleSelective(@Param("record") ItemStock record, @Param("example") ItemStockExample example);

    int updateByExample(@Param("record") ItemStock record, @Param("example") ItemStockExample example);

    int updateByPrimaryKeySelective(ItemStock record);

    int updateByPrimaryKey(ItemStock record);

    ItemStock selectByGoodsId(Integer itemId);

    int decreaseStock(Integer id, Integer count);
}
