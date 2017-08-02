package com.upotv.mcs.role.service;
import com.github.pagehelper.Page;
import com.upotv.mcs.core.ResultMessage;
import com.upotv.mcs.role.entity.PermissionVo;
import com.upotv.mcs.role.entity.Role;
import com.upotv.mcs.role.entity.RoleVo;

import java.util.List;

/**
 * Created by wangyunpeng on 2017/7/18.
 */
public interface RoleService {

    Page<Role> getRoleListPage(RoleVo vo);

    List<Role> getRoleList(RoleVo vo);

    ResultMessage insert(RoleVo vo);

    ResultMessage update(RoleVo vo);

    int delete(int roleid);

    List<PermissionVo> getPermissionList(PermissionVo vo);

    ResultMessage insertPermission(PermissionVo vo);
}
