package org.spider.mcs.menu.dao;

import org.spider.mcs.core.McsBaseDao;
import org.spider.mcs.menu.entity.Menu;

import java.util.List;

/**
 * Created by wow on 2017/6/20.
 */
public interface MenuDao extends McsBaseDao {

    public List<Menu> getMenuListPage(Menu menu);
}
