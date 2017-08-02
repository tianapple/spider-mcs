package com.upotv.mcs.dict.controller;

import com.github.pagehelper.Page;
import com.upotv.mcs.core.ResultData;
import com.upotv.mcs.dict.entity.McsCode;
import com.upotv.mcs.dict.entity.McsCodeSelectVo;
import com.upotv.mcs.dict.service.DictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by wow on 2017/6/28.
 */
@Controller
@RequestMapping("/dict")
public class DictController {

    @Autowired
    private DictService dictService;

    @RequestMapping("")
    public String toDict(){
        return "dict/dict";
    }

    @RequestMapping("/getDict")
    @ResponseBody
    public List getDict(String typeCode,boolean cache) {
        return dictService.getDict(typeCode,cache);
    }

    @ResponseBody
    @RequestMapping("/getDictListPage")
    //获取字典列表
    public ResultData getDictListPage(McsCodeSelectVo vo){
        Page<McsCode> pageList = dictService.getDictListPage(vo);
        return new ResultData(pageList,pageList.getTotal());
    }

}
