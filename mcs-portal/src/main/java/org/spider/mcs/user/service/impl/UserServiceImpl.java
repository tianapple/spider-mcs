package org.spider.mcs.user.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;
import org.spider.mcs.core.ResultMessage;
import org.spider.mcs.user.dao.UserDao;
import org.spider.mcs.user.entity.User;
import org.spider.mcs.user.entity.UserVo;
import org.spider.mcs.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by wow on 2017/6/22.
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    public Page<User> getUserListPage(UserVo vo) {
        PageHelper.startPage(vo.getPage(), vo.getRows());
        return (Page<User>) userDao.getUserListPage(vo);
    }

    @Override
    public int delete(int userId) {
        return userDao.delete(userId);
    }


    @Override
    public ResultMessage insert(UserVo vo) {
        User user = userDao.getUserByUserName(vo.getUserName());
        if(user != null){
            return new ResultMessage(ResultMessage.FAILE,"用户已经存在");
        }
        vo.setPassword(md5(vo.getPassword(), 1));
        int cnt = userDao.insert(vo);
        return new ResultMessage(ResultMessage.SUCCESS,cnt+"");
    }

    @Override
    public ResultMessage update(UserVo vo) {
        User user = userDao.getUserByUserName(vo.getUserName());
        if(user == null){
            return new ResultMessage(ResultMessage.FAILE,"用户不存在");
        }

        String oldPwd = user.getPassword();
        String newPwd = vo.getPassword();
        if(!oldPwd.equals(newPwd)){
            vo.setPassword(md5(vo.getPassword(), 1));
        }
        int cnt = userDao.update(vo);
        return  new ResultMessage(ResultMessage.SUCCESS,cnt+"");
    }


    /**
     * MD5
     * @param credentials
     * @param hashIterations 循环几次
     * @return
     */
    private String md5(String credentials,int hashIterations){
        String hashAlgorithmName = "MD5";
        //ByteSource credentialsSalt = ByteSource.Util.bytes(vo.getUserId());//加slat
        Object obj = new SimpleHash(hashAlgorithmName, credentials,null, hashIterations);
        return obj.toString();
    }
}