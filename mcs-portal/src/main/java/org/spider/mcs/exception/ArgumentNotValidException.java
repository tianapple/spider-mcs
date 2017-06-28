package org.spider.mcs.exception;

import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import java.util.Iterator;

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
        Iterator var2 = this.bindingResult.getAllErrors().iterator();

        while(var2.hasNext()) {
            ObjectError error = (ObjectError)var2.next();
            sb.append("[").append(error.getDefaultMessage()).append("] ");
        }

        return sb.toString();
    }
}
