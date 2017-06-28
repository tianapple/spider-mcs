package org.spider.mcs.user.controller;

import com.github.pagehelper.Page;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.spider.mcs.core.ResultData;
import org.spider.mcs.core.ResultMessage;
import org.spider.mcs.exception.ArgumentNotValidException;
import org.spider.mcs.user.entity.User;
import org.spider.mcs.user.entity.UserVo;
import org.spider.mcs.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;
import java.util.List;

/**
 * Created by wow on 2017/6/22.
 */
@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @ResponseBody
    @RequestMapping("/getUserListPage")
    //@RequiresPermissions("user/query")
    public ResultData getUserListPage(UserVo vo) {
        Page<User> pagelist = userService.getUserListPage(vo);
        return new ResultData(pagelist,pagelist.getTotal());
    }

    @ResponseBody
    @RequestMapping("/delete")
    @RequiresPermissions("user/delete")
    public ResultMessage delete(int userId){
        int cnt = userService.delete(userId);
        return new ResultMessage("0000",cnt+"");
    }

    @ResponseBody
    @RequestMapping("/create")
    //@RequiresPermissions("user/create")
    public ResultMessage create(@Validated UserVo vo,BindingResult result){
        if (result.hasErrors()) {
            throw new ArgumentNotValidException(result);
        }
        return userService.insert(vo);
    }

    @ResponseBody
    @RequestMapping("/update")
    //@RequiresPermissions("user/update")
    public ResultMessage update(@Validated UserVo vo,BindingResult result){
        if (result.hasErrors()) {
            throw new ArgumentNotValidException(result);
        }
        return userService.update(vo);
    }

}
