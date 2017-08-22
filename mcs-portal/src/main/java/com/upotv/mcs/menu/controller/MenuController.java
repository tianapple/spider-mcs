package com.upotv.mcs.menu.controller;

import com.github.pagehelper.Page;
import com.upotv.mcs.core.ResultData;
import com.upotv.mcs.core.ResultMessage;
import com.upotv.mcs.menu.entity.*;
import com.upotv.mcs.menu.service.MenuService;
import com.upotv.mcs.user.entity.UserRole;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Controller
@RequestMapping("menu")
public class MenuController {

    @Autowired
    private MenuService menuService;

    @RequestMapping("")
    @RequiresPermissions("menu/view")
    public String toMenu() {
        return "menu/menu";
    }

    @ResponseBody
    @RequestMapping("/menuListPage")
    @RequiresPermissions("menu/view")
    public List<MenuTreeGrid> getMenu(Integer parentId){
        return menuService.getMenu(parentId);
    }

    @ResponseBody
    @RequestMapping("/insert")
    @RequiresPermissions("menu/manager")
    public ResultMessage insert(@Validated MenuVo vo, BindingResult result) {
        return menuService.insert(vo);
    }

    @ResponseBody
    @RequestMapping("/update")
    @RequiresPermissions("menu/manager")
    public ResultMessage update(@Validated MenuVo vo, BindingResult result) {
        return menuService.update(vo);
    }

    @ResponseBody
    @RequestMapping("/delete")
    @RequiresPermissions("menu/manager")
    public ResultMessage delete(@NotNull Integer menuid) {
        return menuService.delete(menuid);
    }

    @ResponseBody
    @RequestMapping("/insertMenuPriv")
    @RequiresPermissions("menu/manager")
    public ResultMessage insertMenuPriv(@Validated MenuPrivVo vo, BindingResult result) {
        return menuService.insertMenuPriv(vo);
    }
}
