package org.spider.mcs.user.service;

import com.github.pagehelper.Page;
import org.spider.mcs.core.ResultMessage;
import org.spider.mcs.user.entity.User;
import org.spider.mcs.user.entity.UserVo;

import java.util.List;

/**
 * Created by wow on 2017/6/22.
 */
public interface UserService {
    public Page<User> getUserListPage(UserVo user);

    public int delete(int userId);

    public ResultMessage insert(UserVo vo);

    public ResultMessage update(UserVo vo);
}
