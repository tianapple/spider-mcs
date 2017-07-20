package com.upotv.mcs.dict.service;

import com.upotv.mcs.dict.entity.McsCode;

import java.util.List;

/**
 * Created by wow on 2017/6/28.
 */
public interface DictService {
    public List<McsCode> getDict(String typecode, boolean cache);

}
