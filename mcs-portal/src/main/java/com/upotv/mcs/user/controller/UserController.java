package com.upotv.mcs.user.controller;

import com.github.pagehelper.Page;
import com.upotv.mcs.core.ResultData;
import com.upotv.mcs.exception.ArgumentNotValidException;
import com.upotv.mcs.user.entity.User;
import com.upotv.mcs.user.entity.UserVo;
import com.upotv.mcs.user.service.UserService;
import com.upotv.mcs.core.ResultMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by wow on 2017/6/22.
 */
@Controller
@RequestMapping("/user")
//@RequiresPermissions("user/view")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping("")
    public String toUser() {
        return "user/user"; //user.html
    }

    @ResponseBody
    @RequestMapping("/getUserListPage")
    public ResultData getUserListPage(UserVo vo) {
        Page<User> pagelist = userService.getUserListPage(vo);
        return new ResultData(pagelist, pagelist.getTotal());
    }

    @ResponseBody
    @RequestMapping("/delete")
    public ResultMessage delete(int userId) {
        int cnt = userService.delete(userId);
        return new ResultMessage("0000", cnt + "");
    }

    @ResponseBody
    @RequestMapping("/create")
    public ResultMessage create(@Validated UserVo vo, BindingResult result) {
        if (result.hasErrors()) {
            throw new ArgumentNotValidException(result);
        }
        return userService.insert(vo);
    }

    @ResponseBody
    @RequestMapping("/update")
    public ResultMessage update(@Validated UserVo vo, BindingResult result) {
        if (result.hasErrors()) {
            throw new ArgumentNotValidException(result);
        }
        return userService.update(vo);
    }
}
