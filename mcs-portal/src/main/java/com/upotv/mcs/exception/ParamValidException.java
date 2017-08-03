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
        StringBuffer sb = new StringBuffer();
        for (ObjectError error : fieldErrors) {
            String msg = error.getDefaultMessage();
            String objectName = error.getObjectName();
            sb.append(objectName).append(":").append(msg).append(";");
        }
        return sb.toString();
    }
}
