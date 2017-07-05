package com.upotv.mcs.menu.controller;

import com.upotv.mcs.menu.entity.Menu;
import com.upotv.mcs.menu.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;


@Controller
@RequestMapping("menu")
public class MenuController {

    @Autowired
    private MenuService menuService;

    @ResponseBody
    @RequestMapping("/menuListPage")
    public List<Menu> getMenu(Menu vo) {
        return menuService.getMenuListPage(vo);
    }
}
