package com.deltaqin.seckill.dao;

import com.deltaqin.seckill.dataobject.UserPassword;
import com.deltaqin.seckill.dataobject.UserPasswordExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface UserPasswordMapper {
    long countByExample(UserPasswordExample example);

    int deleteByExample(UserPasswordExample example);

    int deleteByPrimaryKey(Integer passId);

    int insert(UserPassword record);

    int insertSelective(UserPassword record);

    List<UserPassword> selectByExample(UserPasswordExample example);

    UserPassword selectByPrimaryKey(Integer passId);

    int updateByExampleSelective(@Param("record") UserPassword record, @Param("example") UserPasswordExample example);

    int updateByExample(@Param("record") UserPassword record, @Param("example") UserPasswordExample example);

    int updateByPrimaryKeySelective(UserPassword record);

    int updateByPrimaryKey(UserPassword record);

    UserPassword selectByUserId(Integer id);
}
