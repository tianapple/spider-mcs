package com.upotv.mcs.menu.dao;

import com.github.pagehelper.Page;
import com.upotv.mcs.menu.entity.Menu;
import com.upotv.mcs.core.McsBaseDao;
import com.upotv.mcs.menu.entity.MenuPrivVo;
import com.upotv.mcs.menu.entity.MenuVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by wow on 2017/6/20.
 */
public interface MenuDao extends McsBaseDao {

    public List<Menu> getMenuListPage(Menu menu);

    Page<Menu> getMenuManagerList(Menu menu);

    List<Menu> getRoleMenuList();

    int getMenuCount(int parentid);

    Menu getMenuByName(String name);

    int insert(MenuVo vo);

    int update(MenuVo vo);

    String getMenuids(int menuid);

    int insertMenuPriv(MenuPrivVo vo);

    int checkMenu(Integer menuid);

    void delete(Integer menuid);

    void deleteMenuPriv(Integer menuid);

    void deletePermission(Integer menuid);
}
