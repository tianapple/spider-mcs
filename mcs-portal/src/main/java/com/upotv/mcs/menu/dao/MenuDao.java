package com.upotv.mcs.menu.dao;

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

    List<Menu> getMenuManagerList(Menu menu);

    List<Menu> getRoleMenuList();

    int getMenuCount(int parentid);

    Menu getMenuByName(String name);

    int insert(MenuVo vo);

    int update(MenuVo vo);

    String getMenuids(int menuid);

    int delete(int menuid);

    int deleteMenuPriv(@Param(value="menuids")String[] menuids);

    int deletePermission(@Param(value="menuids")String[] menuids);

    int insertMenuPriv(MenuPrivVo vo);

}
