package com.deltaqin.seckill.dao;

import com.deltaqin.seckill.dataobject.Good;
import com.deltaqin.seckill.dataobject.GoodExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface GoodMapper {
    long countByExample(GoodExample example);

    int deleteByExample(GoodExample example);

    int deleteByPrimaryKey(Integer goodId);

    int insert(Good record);

    int insertSelective(Good record);

    List<Good> selectByExample(GoodExample example);

    Good selectByPrimaryKey(Integer goodId);

    int updateByExampleSelective(@Param("record") Good record, @Param("example") GoodExample example);

    int updateByExample(@Param("record") Good record, @Param("example") GoodExample example);

    int updateByPrimaryKeySelective(Good record);

    int updateByPrimaryKey(Good record);

    // 这里写了@Param之后就不用在mapper里面指定参数类型了
    void increaseSales(@Param("id") Integer goodsId, @Param("count") Integer count);
}
