package com.upotv.mcs.exception;

import org.springframework.context.i18n.LocaleContext;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import java.util.Iterator;
import java.util.List;
import java.util.Locale;

/**
 * Created by wow on 2017/6/23.
 */
public class ArgumentNotValidException extends RuntimeException {
    private final BindingResult bindingResult;

    public ArgumentNotValidException(BindingResult bindingResult) {
        this.bindingResult = bindingResult;
    }

    public String getMessage() {
        StringBuilder sb = new StringBuilder("参数错误：");
        List<FieldError> fieldErrors = bindingResult.getFieldErrors();
        Locale currentLocale = LocaleContextHolder.getLocale();

        for (FieldError fieldError : fieldErrors) {
            String errorMsg = fieldError.getDefaultMessage();
            sb.append(fieldError.getField() + ":" + errorMsg);
        }

        return sb.toString();
    }
}
