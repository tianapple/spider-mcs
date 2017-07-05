package com.upotv.mcs.menu.dao;

import com.upotv.mcs.menu.entity.Menu;
import com.upotv.mcs.core.McsBaseDao;

import java.util.List;

/**
 * Created by wow on 2017/6/20.
 */
public interface MenuDao extends McsBaseDao {

    public List<Menu> getMenuListPage(Menu menu);
}
