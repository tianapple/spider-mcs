package org.spider.mcs.main.controllor;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spider.mcs.entity.Mcs_user;
import org.spider.mcs.main.dao.UserDao;
import org.spider.mcs.main.entity.Mcs_menu;
import org.spider.mcs.main.entity.TreeData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by tianapple on 2017/5/10.
 */
@Controller
public class LoginControllor {
    private static Logger LOGGER = LoggerFactory.getLogger(LoginControllor.class);

    @Autowired
    private UserDao userDao;

    @RequestMapping(value = "/login.do")
    public ModelAndView login(String userName, String password, HttpServletRequest request) {
        UsernamePasswordToken token = new UsernamePasswordToken(userName, password);
        ModelAndView model = new ModelAndView();
        try {
            Subject subject = SecurityUtils.getSubject();
            subject.login(token);
            model.setViewName("index");
            model.getModel().put("msg", "ok");
        } catch (Exception e) {
            LOGGER.warn("{} login error: {}", userName, e);
            String loginUrl = String.format("redirect:%s://%s:%s%s/login.html"
                    , request.getScheme(),request.getServerName(),request.getServerPort(),request.getContextPath());
            model.setViewName(loginUrl);
            model.getModel().put("msg", e.getMessage());
        }
        return model;
    }

    @ResponseBody
    @RequestMapping(value = "/getMenu")
    public List<TreeData> getMenuList() {
        Mcs_user user = getUser();
        return getMenuList(user, 0);
    }

    private List<TreeData> getMenuList(Mcs_user user, int parentId) {
        //获取一级菜单
        List<Mcs_menu> menuList = userDao.getMenuList(user.getUserId(), parentId);
        if (user.isAdmin()) {
            List<Mcs_menu> menuAdminList = userDao.getAdminMenuList(parentId);
            if (menuAdminList.size() > 0) {
                menuList.addAll(menuAdminList);
            }
        }

        List<TreeData> trees = new ArrayList<>();

        //获取子菜单
        if (menuList != null && menuList.size() > 0) {
            menuList = menuList.stream().distinct().collect(Collectors.toList()); //排除重复
            for (Mcs_menu mcsMenu : menuList) {
                TreeData treeData = TreeData.parse(mcsMenu);
                trees.add(treeData);
                List<TreeData> children = getMenuList(user, mcsMenu.getMenuId());
                if (children.size() != 0) {
                    treeData.setChildren(children);
                }
            }
        }
        return trees;
    }

    private Mcs_user getUser() {
        Subject subject = SecurityUtils.getSubject();
        Object user = subject.getSession().getAttribute("user");
        if (user != null) {
            return (Mcs_user) user;
        }
        return null;
    }
}
