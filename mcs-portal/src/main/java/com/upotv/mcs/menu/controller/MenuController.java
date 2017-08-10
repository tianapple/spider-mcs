package com.upotv.mcs.menu.controller;

import com.github.pagehelper.Page;
import com.upotv.mcs.core.ResultData;
import com.upotv.mcs.core.ResultMessage;
import com.upotv.mcs.menu.entity.Menu;
import com.upotv.mcs.menu.entity.MenuPrivVo;
import com.upotv.mcs.menu.entity.MenuVo;
import com.upotv.mcs.menu.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Map;


@Controller
@RequestMapping("menu")
public class MenuController {

    @Autowired
    private MenuService menuService;

    @RequestMapping("")
    public String toMenu() {
        return "menu/menu";
    }

    @ResponseBody
    @RequestMapping("/menuListPage")
    public List<Menu> getMenu(Menu vo) {
        return menuService.getMenuListPage(vo);
    }

    @ResponseBody
    @RequestMapping("/menuManagerPage")
    public ResultData menuManager(Menu vo) {
        return menuService.menuManager(vo);
    }

    @ResponseBody
    @RequestMapping("/insert")
    public ResultMessage insert(@Validated MenuVo vo, BindingResult result) {
        return menuService.insert(vo);
    }

    @ResponseBody
    @RequestMapping("/update")
    public ResultMessage update(@Validated MenuVo vo, BindingResult result) {
        return menuService.update(vo);
    }

    @ResponseBody
    @RequestMapping("/delete")
    public ResultMessage delete(@NotNull Integer menuid) {
        return menuService.delete(menuid);
    }

    @ResponseBody
    @RequestMapping("/insertMenuPriv")
    public ResultMessage insertMenuPriv(@Validated MenuPrivVo vo, BindingResult result) {
        return menuService.insertMenuPriv(vo);
    }

    @ResponseBody
    @RequestMapping("/roleMenu")
    public ResultMessage roleMenu(){
        return menuService.roleMenu();
    }



}
