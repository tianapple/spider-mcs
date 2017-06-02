package org.spider.mcs.main.dao;

import org.spider.mcs.McsBaseDao;
import org.spider.mcs.domain.User;
import org.spider.mcs.main.domain.UserPermission;

import java.util.List;

/**
 * Created by tianapple on 2017/5/10.
 */
public interface UserDao extends McsBaseDao {
    //@Select("SELECT * FROM mcs_user where user_name=#{userName}")
    User getUser(String userName);
    List<UserPermission> getPermissions(int userId);
}
