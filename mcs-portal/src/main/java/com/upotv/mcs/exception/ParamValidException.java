package com.upotv.mcs.exception;

import com.alibaba.fastjson.JSON;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by wow on 2017/7/24.
 */
public class ParamValidException extends RuntimeException {
    private List<ObjectError> fieldErrors;

    public ParamValidException(List<ObjectError> errors) {
        this.fieldErrors = errors;
    }

    @Override
    public String getMessage() {
        List<Map> list = new ArrayList<>();
        for (ObjectError error : fieldErrors) {
            FieldError e = (FieldError) error;
            String paramName = e.getField();
            String msg = e.getDefaultMessage();
            Map<String, String> param = new HashMap<>();
            param.put("paramName", paramName);
            param.put("paramMsg", msg);
            list.add(param);
        }
        return JSON.toJSONString(list);
    }
}
