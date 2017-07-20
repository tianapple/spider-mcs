package com.upotv.mcs.dict.service.impl;

import com.upotv.mcs.dict.dao.DictDao;
import com.upotv.mcs.dict.entity.McsCode;
import com.upotv.mcs.dict.service.DictService;
import jdk.nashorn.internal.objects.annotations.Constructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by wow on 2017/6/28.
 */
@Service
public class DictServiceImpl implements DictService {

    private static final Logger LOGGER = LoggerFactory.getLogger(DictService.class);

    private static HashMap<String, CopyOnWriteArrayList<McsCode>> typecodes = new HashMap<String, CopyOnWriteArrayList<McsCode>>();

    @Autowired
    private DictDao dictDao;

    @PostConstruct
    private void initDict() {
        LOGGER.debug("初始化字典表");
        List<McsCode> dictList = dictDao.getAllDict();
        for (McsCode mcsCode : dictList) {
            if (!typecodes.containsKey(mcsCode.getCodeType())) {
                typecodes.put(mcsCode.getCodeType(), new CopyOnWriteArrayList<McsCode>());
            }
            CopyOnWriteArrayList<McsCode> lists = typecodes.get(mcsCode.getCodeType());
            lists.add(mcsCode);
        }
    }

    /*
     * 获得type下的所有List
	 */
    public List<McsCode> getDict(String typecode, boolean cache) {
        List<McsCode> list = new CopyOnWriteArrayList<McsCode>();
        if (cache) {
            List<McsCode> cacheList = typecodes.get(typecode);
            if(cacheList != null){
                list = cacheList;
            }
        } else {
            list = dictDao.getDictByType(typecode);
        }
        return list;
    }
}
