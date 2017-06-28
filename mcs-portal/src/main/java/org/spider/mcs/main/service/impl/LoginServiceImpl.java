package org.spider.mcs.main.service.impl;

import org.spider.mcs.main.entity.Mcs_user;
import org.spider.mcs.main.dao.LoginDao;
import org.spider.mcs.main.entity.Mcs_menu;
import org.spider.mcs.main.entity.UserPermission;
import org.spider.mcs.main.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by wow on 2017/6/21.
 */
@Service
public class LoginServiceImpl implements LoginService{

    @Autowired
    private LoginDao loginDao;

    @Override
    public Mcs_user getUserByUserName(String userName) {
        return loginDao.getUserByUserName(userName);
    }

    @Override
    public List<UserPermission> getPermissions(int userId) {
        return loginDao.getPermissions(userId);
    }

    @Override
    public List<Mcs_menu> getMenuList(int userId, int parentId) {
        return loginDao.getMenuList(userId,parentId);
    }

    @Override
    public List<Mcs_menu> getAdminMenuList(int parentId) {
        return loginDao.getAdminMenuList(parentId);
    }

}
