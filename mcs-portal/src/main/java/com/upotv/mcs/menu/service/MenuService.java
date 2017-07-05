package com.upotv.mcs.menu.service;

import com.upotv.mcs.menu.entity.Menu;

import java.util.List;

/**
 * Created by wow on 2017/6/20.
 */
public interface MenuService {
    public List<Menu> getMenuListPage(Menu menu);
}
