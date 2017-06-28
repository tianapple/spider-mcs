package org.spider.mcs.main.dao;

import org.spider.mcs.core.McsBaseDao;
import org.spider.mcs.main.entity.Mcs_user;
import org.spider.mcs.main.entity.Mcs_menu;
import org.spider.mcs.main.entity.UserPermission;

import java.util.List;

/**
 * Created by tianapple on 2017/5/10.
 */
public interface LoginDao extends McsBaseDao {
    List<UserPermission> getPermissions(int userId); //获取用户权限数据

    List<Mcs_menu> getMenuList(int userId, int parentId); //获取普通用户授权的菜单

    List<Mcs_menu> getAdminMenuList(int parentId);  //获取管理员的菜单

    Mcs_user getUserByUserName(String userName);
}
