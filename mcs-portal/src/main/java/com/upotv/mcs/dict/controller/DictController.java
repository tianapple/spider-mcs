package com.upotv.mcs.dict.controller;

import com.upotv.mcs.dict.service.DictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by wow on 2017/6/28.
 */
@Controller
@RequestMapping("/dict")
public class DictController {

    @Autowired
    private DictService dictService;

    @RequestMapping("/getDict")
    @ResponseBody
    public List getDict(String typeCode,boolean cache) {
        return dictService.getDict(typeCode,cache);
    }
}
