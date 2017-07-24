package com.upotv.mcs.user.service;

import com.github.pagehelper.Page;
import com.upotv.mcs.user.entity.*;
import com.upotv.mcs.core.ResultMessage;

import java.util.List;

/**
 * Created by wow on 2017/6/22.
 */
public interface UserService {
    public Page<User> getUserListPage(UserVo vo);

    public int delete(int userId);

    public ResultMessage insert(UserVo vo);

    public ResultMessage update(UserVo vo);

    public Page<UserRole> getUserRoleListPage(UserRoleQryVo vo);

    public int relateRole(UserRoleVo vo);
}
