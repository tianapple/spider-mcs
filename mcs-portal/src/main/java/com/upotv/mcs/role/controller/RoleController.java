package com.upotv.mcs.role.controller;

import com.github.pagehelper.Page;
import com.upotv.mcs.core.ResultData;
import com.upotv.mcs.core.ResultMessage;
import com.upotv.mcs.exception.ArgumentNotValidException;
import com.upotv.mcs.role.entity.PermissionVo;
import com.upotv.mcs.role.entity.Role;
import com.upotv.mcs.role.entity.RoleVo;
import com.upotv.mcs.role.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by wangyunpeng on 2017/7/18.
 */
@Controller
@RequestMapping("/role")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @RequestMapping("")
    public String toRole() {
        return "role/role";
    }

    @RequestMapping("/roleMenu")
    public String toRoleMenu() {
        return "role/roleMenu";
    }

    @ResponseBody
    @RequestMapping("/getRoleListPage")
    public ResultData getUserListPage(RoleVo vo) {
        Page<Role> pagelist = roleService.getRoleListPage(vo);
        return new ResultData(pagelist, pagelist.getTotal());
    }

    @ResponseBody
    @RequestMapping("/getPermissionList")
    public List<PermissionVo> getPermissionList(PermissionVo vo) {
        return roleService.getPermissionList(vo);
    }

    @ResponseBody
    @RequestMapping("/getRoleList")
    public List<Role> getUserList() {
        return roleService.getRoleList(null);
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

    @ResponseBody
    @RequestMapping("/insertPermission")
    public ResultMessage insertPermission(PermissionVo vo) {
        return roleService.insertPermission(vo);
    }
}
