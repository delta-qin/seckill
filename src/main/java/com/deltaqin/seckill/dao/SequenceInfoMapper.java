package com.deltaqin.seckill.dao;

import com.deltaqin.seckill.dataobject.SequenceInfo;
import com.deltaqin.seckill.dataobject.SequenceInfoExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SequenceInfoMapper {
    long countByExample(SequenceInfoExample example);

    int deleteByExample(SequenceInfoExample example);

    int deleteByPrimaryKey(String seqName);

    int insert(SequenceInfo record);

    int insertSelective(SequenceInfo record);

    List<SequenceInfo> selectByExample(SequenceInfoExample example);

    SequenceInfo selectByPrimaryKey(String seqName);

    int updateByExampleSelective(@Param("record") SequenceInfo record, @Param("example") SequenceInfoExample example);

    int updateByExample(@Param("record") SequenceInfo record, @Param("example") SequenceInfoExample example);

    int updateByPrimaryKeySelective(SequenceInfo record);

    int updateByPrimaryKey(SequenceInfo record);
}