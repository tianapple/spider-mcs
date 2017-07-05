package com.upotv.mcs.user.service;

import com.github.pagehelper.Page;
import com.upotv.mcs.user.entity.User;
import com.upotv.mcs.user.entity.UserVo;
import com.upotv.mcs.core.ResultMessage;

/**
 * Created by wow on 2017/6/22.
 */
public interface UserService {
    public Page<User> getUserListPage(UserVo user);

    public int delete(int userId);

    public ResultMessage insert(UserVo vo);

    public ResultMessage update(UserVo vo);
}
