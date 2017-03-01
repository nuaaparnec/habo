package com.nuaaparnec.phoenix.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nuaaparnec.phoenix.mapper.PUserMapper;
import com.nuaaparnec.phoenix.model.PUser;
import com.nuaaparnec.phoenix.model.PUserExample;
import com.nuaaparnec.phoenix.service.HbaseService;

@Service
public class HbaseServiceImpl implements HbaseService {

    @Autowired
    private PUserMapper pUserMapper;

    @Override
    public List<PUser> selectByExample(PUserExample example) {
        
        example.createCriteria().andIdEqualTo(17);
        List<PUser> selectByExample = pUserMapper.selectByExample(example);

        return selectByExample;

    }

    @Override
    public void insert(PUser record) {
        pUserMapper.insert(record);
    }

}
