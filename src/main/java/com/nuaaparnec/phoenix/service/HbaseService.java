package com.nuaaparnec.phoenix.service;

import java.util.List;

import com.nuaaparnec.phoenix.model.PUser;
import com.nuaaparnec.phoenix.model.PUserExample;

public interface HbaseService {
    List<PUser> selectByExample(PUserExample example);

    void insert(PUser record);
}
