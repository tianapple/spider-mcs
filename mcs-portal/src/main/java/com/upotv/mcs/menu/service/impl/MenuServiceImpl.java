package com.upotv.mcs.menu.service.impl;

import com.upotv.mcs.core.ResultMessage;
import com.upotv.mcs.menu.dao.MenuDao;
import com.upotv.mcs.menu.entity.Menu;
import com.upotv.mcs.menu.entity.MenuPrivVo;
import com.upotv.mcs.menu.entity.MenuTreeGrid;
import com.upotv.mcs.menu.entity.MenuVo;
import com.upotv.mcs.menu.service.MenuService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wow on 2017/6/20.
 */
@Service
public class MenuServiceImpl implements MenuService{

    @Autowired
    private MenuDao menuDao;

    public List<MenuTreeGrid> getMenu(int parentId){
        return initAllMenu(parentId);
    }

    private List<MenuTreeGrid> initAllMenu(int pid) {
        List<MenuTreeGrid> treeDataList = new ArrayList<>();

        List<Menu> menuList = menuDao.getMenuList(pid);

        for (Menu menu : menuList) {
            MenuTreeGrid treeData = new MenuTreeGrid();
            treeData.setId(menu.getMenuId());
            treeData.setIconCls(menu.getIconCls());
            treeData.setName(menu.getName());
            treeData.setRemark(menu.getRemark());
            treeData.setPriority(menu.getPriority());
            treeData.setCreatetime(menu.getCreatetime());
            treeData.setUpdatetime(menu.getUpdatetime());
            treeData.setPath(menu.getPath());
            treeData.setPrivId(menu.getPrivId());
            treeData.setPrivName(menu.getPrivName());
            treeDataList.add(treeData);
            List<MenuTreeGrid> child = initAllMenu(menu.getMenuId());
            treeData.setChildren(child);
            treeData.setState("open");
        }
        return treeDataList;
    }

    @Override
    public ResultMessage insert(MenuVo vo) {
        int ct = menuDao.insert(vo);
        MenuPrivVo privVo = new MenuPrivVo();
        privVo.setMenuid(vo.getMenuid());
        privVo.setPriv_id("view");
        privVo.setPriv_name("页面权限");
        menuDao.insertMenuPriv(privVo);
        return new ResultMessage(ResultMessage.SUCCESS,ct);
    }

    @Override
    public ResultMessage update(MenuVo vo) {
        int ct = menuDao.update(vo);
        return new ResultMessage(ResultMessage.SUCCESS,ct);
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
}
