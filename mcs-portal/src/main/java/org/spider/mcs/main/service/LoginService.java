package org.spider.mcs.main.service;

import org.spider.mcs.main.entity.Mcs_user;
import org.spider.mcs.main.entity.Mcs_menu;
import org.spider.mcs.main.entity.UserPermission;

import java.util.List;

/**
 * Created by wow on 2017/6/21.
 */
public interface LoginService {
    /**
     * 根据用户名查询用户信息
     * @param userName
     * @return
     */
    Mcs_user getUserByUserName(String userName);

    /**
     * 获取用户权限数据
     * @param userId
     * @return
     */
    List<UserPermission> getPermissions(int userId);

    /**
     * 获取普通用户授权的菜单
     * @param userId
     * @param parentId
     * @return
     */
    List<Mcs_menu> getMenuList(int userId, int parentId);

    /**
     * 获取管理员的菜单
     * @param parentId
     * @return
     */
    List<Mcs_menu> getAdminMenuList(int parentId);

}
