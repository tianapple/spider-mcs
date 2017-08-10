package com.upotv.mcs.menu.service.impl;

import com.github.pagehelper.Page;
import com.upotv.mcs.core.ResultData;
import com.upotv.mcs.core.ResultMessage;
import com.upotv.mcs.menu.dao.MenuDao;
import com.upotv.mcs.menu.entity.Menu;
import com.upotv.mcs.menu.entity.MenuPrivVo;
import com.upotv.mcs.menu.entity.MenuVo;
import com.upotv.mcs.role.entity.PermissionVo;
import com.upotv.mcs.menu.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.apache.commons.lang.StringUtils;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by wow on 2017/6/20.
 */
@Service
public class MenuServiceImpl implements MenuService{

    @Autowired
    private MenuDao menuDao;

    @Override
    public List<Menu> getMenuListPage(Menu menu) {
        return menuDao.getMenuListPage(menu);
    }

    @Override
    public ResultData menuManager(Menu vo){
        Page<Menu> list = menuDao.getMenuManagerList(vo);
        if (list.size() > 0) {
            for (Menu menu : list) {
                if ( menu.getParentid() == -1) {
                    menu.set_parentId(null);
                } else {
                    menu.set_parentId(menu.getParentid());
                }
                int count = menuDao.getMenuCount(menu.getMenuid());
                if(count > 0){
                    menu.setState("closed");
                }
            }
        }
        return new ResultData(list,list.getTotal());
    }

    @Override
    public ResultMessage insert(MenuVo vo) {
        Menu menu = menuDao.getMenuByName(vo.getName());
        if (menu != null) {
            return new ResultMessage(ResultMessage.FAILE,"菜单已经存在");
        }
        int ct = menuDao.insert(vo);
        MenuPrivVo privVo = new MenuPrivVo();
        privVo.setMenuid(vo.getMenuid());
        privVo.setPriv_id("view");
        privVo.setPriv_name("页面权限");
        menuDao.insertMenuPriv(privVo);
        return new ResultMessage(ResultMessage.SUCCESS,ct+"");
    }

    @Override
    public ResultMessage update(MenuVo vo) {
        Menu menu = menuDao.getMenuByName(vo.getName());
        if (menu != null) {
            if (menu.getMenuid() != vo.getMenuid()) {
                return new ResultMessage(ResultMessage.FAILE, "菜单已经存在");
            }
        }
        int ct = menuDao.update(vo);
        return new ResultMessage(ResultMessage.SUCCESS,ct+"");
    }

    @Override
    public ResultMessage delete(Integer menuid) {
        int checkMenuCount = menuDao.checkMenu(menuid);
        if(checkMenuCount > 0){
            return new ResultMessage(ResultMessage.FAILE,"该菜单下面包含子菜单，不允许删除！");
        }else{
            menuDao.delete(menuid);
            menuDao.deleteMenuPriv(menuid);
            menuDao.deletePermission(menuid);
        }

        return new ResultMessage(ResultMessage.SUCCESS,"删除成功！");
    }

    @Override
    public ResultMessage insertMenuPriv(MenuPrivVo vo) {
        menuDao.deleteMenuPriv(vo.getMenuid());
        String[] priv_ids = vo.getPriv_id().split(",");
        String[] priv_names = vo.getPriv_name().split(",");
        for (int i = 0; i < priv_ids.length; i++) {
            if (StringUtils.isNotBlank(priv_ids[i])) {
                vo.setPriv_id(priv_ids[i]);
                vo.setPriv_name(priv_names[i]);
                menuDao.insertMenuPriv(vo);
            }
        }
        return new ResultMessage(ResultMessage.SUCCESS,"1");
    }

    @Override
    public ResultMessage roleMenu() {
        Menu menu = new Menu();
        menu.setParentid(-1);
        List<Menu>  list = menuDao.getRoleMenuList();
        StringBuffer nodes = new StringBuffer("[{id:-1, pId:-2, name:'系统菜单',open:true},");
        for(Menu mu:list){
            String menuid = String.valueOf(mu.getMenuid());
            String name = mu.getName();
            int parentid = mu.getParentid();
            if (!"view".equals(mu.getPriv_id())) {
                menuid = "priv_" + mu.getMenuid() + "_" + mu.getPriv_id();
                parentid = mu.getMenuid();
            }
            nodes.append("{'id':'"+menuid+"','pId':'"+parentid+"','name': '"+name+"',open:false},");
        }
        String str = nodes.substring(0,nodes.length()-1)+"]";
        return new ResultMessage(ResultMessage.SUCCESS,str);
    }
}
