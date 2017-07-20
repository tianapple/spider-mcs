package com.upotv.mcs.role.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.upotv.mcs.core.ResultMessage;
import com.upotv.mcs.role.dao.RoleDao;
import com.upotv.mcs.role.entity.Role;
import com.upotv.mcs.role.entity.RoleVo;
import com.upotv.mcs.role.service.RoleService;
import com.upotv.mcs.user.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by wangyunpeng on 2017/7/18.
 */
@Service
public class RoleServiceImpl implements RoleService{

    @Autowired
    private RoleDao roleDao;

    @Override
    public Page<Role> getRoleListPage(RoleVo vo) {
        PageHelper.startPage(vo.getPage(), vo.getRows());
        return (Page<Role>) roleDao.getRoleListPage(vo);
    }

    @Override
    public ResultMessage insert(RoleVo vo) {
        Role role = roleDao.getRoleByName(vo.getName());
        if(role != null){
            return new ResultMessage(ResultMessage.FAILE,"角色已经存在");
        }
        int ct = roleDao.insert(vo);
        return new ResultMessage(ResultMessage.SUCCESS,ct+"");
    }

    @Override
    public ResultMessage update(RoleVo vo) {
        Role role = roleDao.getRoleByName(vo.getName());
        if(role != null){
            if (!(role.getRoleid()+"").equals(vo.getRoleid())) {
                return new ResultMessage(ResultMessage.FAILE,"角色已经存在");
            }
        }
        int ct = roleDao.update(vo);
        return new ResultMessage(ResultMessage.SUCCESS,ct+"");
    }

    @Override
    public int delete(int roleid) {
        return roleDao.delete(roleid);
    }
}
