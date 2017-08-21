package com.upotv.mcs.menu.service;

import com.github.pagehelper.Page;
import com.upotv.mcs.core.ResultData;
import com.upotv.mcs.core.ResultMessage;
import com.upotv.mcs.menu.entity.*;

import java.util.List;
import java.util.Map;

/**
 * Created by wow on 2017/6/20.
 */
public interface MenuService {

    public List<MenuTreeGrid> getMenu(int parentId);

    ResultMessage insert(MenuVo vo);

    ResultMessage update(MenuVo vo);

    ResultMessage delete(Integer menuid);

    ResultMessage insertMenuPriv(MenuPrivVo vo);

}
