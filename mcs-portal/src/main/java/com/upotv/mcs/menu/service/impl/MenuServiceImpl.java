package com.upotv.mcs.menu.service.impl;

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
    public Map<String,Object> menuManager(Menu vo){
        List<Menu>  list = menuDao.getMenuManagerList(vo);
        Map<String, Object>  param = new HashMap<String,Object>();
        List<Map<String,Object>> dateList = new ArrayList<Map<String,Object>>();
        if (list.size() > 0) {
            Map<String,Object> map = null;
            Map<String, Object>  params = new HashMap<String,Object>();
            for (Menu menu : list) {
                map = new HashMap<String,Object>();
                map.put("menuid", menu.getMenuid());
                map.put("name", menu.getName());
                map.put("path", menu.getPath());
                map.put("priority", menu.getPriority());
                map.put("isEnable", menu.getIsEnable());
                map.put("isEnableName", menu.getIsEnableName());
                map.put("remark", menu.getRemark());
                map.put("createtime", menu.getCreatetime());
                map.put("updatetime", menu.getUpdatetime());
                map.put("priv_id", menu.getPriv_id());
                map.put("priv_name", menu.getPriv_name());
                if ( menu.getParentid() == -1) {
                    map.put("_parentId", null);
                } else {
                    map.put("_parentId", menu.getParentid());
                }
                int count = menuDao.getMenuCount(menu.getMenuid());
                if(count > 0){
                    map.put("state", "closed");
                }
                dateList.add(map);
            }
        }
        param.put("rows", dateList);
        return param;
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
    public int delete(int menuid) {
        String[] menuids = menuDao.getMenuids(menuid).split(",");
        menuDao.deleteMenuPriv(menuids);
        menuDao.deletePermission(menuids);
        return menuDao.delete(menuid);
    }

    @Override
    public ResultMessage insertMenuPriv(MenuPrivVo vo) {
        String[] menuids = (vo.getMenuid()+"").split(",");
        menuDao.deleteMenuPriv(menuids);
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
                name = mu.getPriv_name();
                parentid = mu.getMenuid();
            }
            nodes.append("{'id':'"+menuid+"','pId':'"+parentid+"','name': '"+name+"',open:false},");
        }
        String str = nodes.substring(0,nodes.length()-1)+"]";
        return new ResultMessage(ResultMessage.SUCCESS,str);
    }
}
