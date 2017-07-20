package com.upotv.mcs.role.controller;

import com.github.pagehelper.Page;
import com.upotv.mcs.core.ResultData;
import com.upotv.mcs.core.ResultMessage;
import com.upotv.mcs.exception.ArgumentNotValidException;
import com.upotv.mcs.role.entity.Role;
import com.upotv.mcs.role.entity.RoleVo;
import com.upotv.mcs.role.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by wangyunpeng on 2017/7/18.
 */
@Controller
@RequestMapping("/role")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @RequestMapping("")
    public String toUser() {
        return "role/role";
    }

    @ResponseBody
    @RequestMapping("/getRoleListPage")
    public ResultData getUserListPage(RoleVo vo) {
        Page<Role> pagelist = roleService.getRoleListPage(vo);
        return new ResultData(pagelist, pagelist.getTotal());
    }

    @ResponseBody
    @RequestMapping("/insert")
    public ResultMessage insert(@Validated RoleVo vo, BindingResult result) {
        if (result.hasErrors()) {
            throw new ArgumentNotValidException(result);
        }
        return roleService.insert(vo);
    }

    @ResponseBody
    @RequestMapping("/update")
    public ResultMessage update(@Validated RoleVo vo, BindingResult result) {
        if (result.hasErrors()) {
            throw new ArgumentNotValidException(result);
        }
        return roleService.update(vo);
    }

    @ResponseBody
    @RequestMapping("/delete")
    public ResultMessage delete(int roleid) {
        int cnt = roleService.delete(roleid);
        return new ResultMessage("0000", cnt + "");
    }
}
