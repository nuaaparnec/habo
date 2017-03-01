package com.nuaaparnec.phoenix.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.nuaaparnec.phoenix.model.PUser;
import com.nuaaparnec.phoenix.model.PUserExample;

public interface PUserMapper {
    int countByExample(PUserExample example);

    int deleteByExample(PUserExample example);

    int insert(PUser record);

    int insertSelective(PUser record);

    List<PUser> selectByExample(PUserExample example);

    int updateByExampleSelective(@Param("record") PUser record, @Param("example") PUserExample example);

    int updateByExample(@Param("record") PUser record, @Param("example") PUserExample example);
}