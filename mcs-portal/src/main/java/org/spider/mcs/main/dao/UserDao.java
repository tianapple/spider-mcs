package org.spider.mcs.main.dao;

import org.spider.mcs.McsBaseDao;
import org.spider.mcs.entity.Mcs_user;
import org.spider.mcs.main.entity.Mcs_menu;
import org.spider.mcs.main.entity.UserPermission;

import java.util.List;

/**
 * Created by tianapple on 2017/5/10.
 */
public interface UserDao extends McsBaseDao {
    Mcs_user getUserByUserName(String userName);
}
