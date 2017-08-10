package com.upotv.mcs.menu.service;

import com.upotv.mcs.core.ResultData;
import com.upotv.mcs.core.ResultMessage;
import com.upotv.mcs.menu.entity.Menu;
import com.upotv.mcs.menu.entity.MenuPrivVo;
import com.upotv.mcs.menu.entity.MenuVo;
import com.upotv.mcs.role.entity.PermissionVo;

import java.util.List;
import java.util.Map;

/**
 * Created by wow on 2017/6/20.
 */
public interface MenuService {
    List<Menu> getMenuListPage(Menu menu);

    ResultData menuManager(Menu vo);

    ResultMessage insert(MenuVo vo);

    ResultMessage update(MenuVo vo);

    ResultMessage delete(Integer menuid);

    ResultMessage insertMenuPriv(MenuPrivVo vo);

    ResultMessage roleMenu();
}
