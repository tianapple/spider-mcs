package org.spider.mcs.menu.service.impl;

import org.spider.mcs.menu.dao.MenuDao;
import org.spider.mcs.menu.entity.Menu;
import org.spider.mcs.menu.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by wow on 2017/6/20.
 */
@Service
public class MenuServiceImpl implements MenuService{

    @Autowired
    private MenuDao menuDao;


    @Override
    public List<Menu> getMenuListPage(Menu menu) {
        return menuDao.getMenuListPage(menu);
    }
}
