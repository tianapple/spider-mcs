package com.upotv.mcs.user.dao;

import com.upotv.mcs.core.McsBaseDao;
import com.upotv.mcs.user.entity.User;
import com.upotv.mcs.user.entity.UserVo;

import java.util.List;

/**
 * Created by wow on 2017/6/22.
 */
public interface UserDao extends McsBaseDao {
    public List<User> getUserListPage(UserVo vo);

    public User getUserByUserName(String userId);

    public int delete(int userId);

    public int insert(UserVo vo);

    public int update(UserVo vo);
}
