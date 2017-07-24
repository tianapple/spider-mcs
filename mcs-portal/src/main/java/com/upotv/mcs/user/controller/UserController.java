package com.upotv.mcs.user.controller;

import com.github.pagehelper.Page;
import com.upotv.mcs.core.ResultData;
import com.upotv.mcs.user.entity.*;
import com.upotv.mcs.user.service.UserService;
import com.upotv.mcs.core.ResultMessage;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Created by wow on 2017/6/22.
 */
@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping("")
    @RequiresPermissions("user/view")
    public String toUser() {
        return "user/user"; //user.html
    }

    @ResponseBody
    @RequestMapping("/getUserListPage")
    @RequiresPermissions("user/view")
    public ResultData getUserListPage(UserVo vo) {
        Page<User> pagelist = userService.getUserListPage(vo);
        return new ResultData(pagelist, pagelist.getTotal());
    }

    @ResponseBody
    @RequestMapping("/delete")
    @RequiresPermissions("user/manager")
    public ResultMessage delete(@NotNull Integer userId) {
        int cnt = userService.delete(userId);
        return new ResultMessage(ResultMessage.SUCCESS, cnt + "");
    }

    @ResponseBody
    @RequestMapping("/create")
    @RequiresPermissions("user/manager")
    public ResultMessage create(@Validated UserVo vo, BindingResult result) {
        return userService.insert(vo);
    }

    @ResponseBody
    @RequestMapping("/update")
    @RequiresPermissions("user/manager")
    public ResultMessage update(@Validated UserVo vo, BindingResult result) {
        return userService.update(vo);
    }

    @RequestMapping("/toRelateRole")
    public String toRelateRole() {
        return "user/relateRole";
    }

    @ResponseBody
    @RequestMapping("/getUserRoleList")
    @RequiresPermissions("user/view")
    public ResultData getUserRoleList(UserRoleQryVo vo) {
        Page<UserRole> pagelist  = userService.getUserRoleListPage(vo);
        return new ResultData(pagelist,pagelist.getTotal());
    }

    @ResponseBody
    @RequestMapping("/relateRole")
    @RequiresPermissions("user/manager")
    public ResultMessage relateRole(@Validated @RequestBody UserRoleVo vo, BindingResult result) {
        int cnt = userService.relateRole(vo);
        return new ResultMessage(ResultMessage.SUCCESS, cnt + "");
    }

    /**
     * 修改密码页
     *
     * @return
     */
    @RequestMapping("/toEditPwd")
    public String editPwd() {
        return "user/userEditPwd";
    }
}
