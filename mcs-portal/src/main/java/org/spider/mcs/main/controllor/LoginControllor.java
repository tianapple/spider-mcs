package org.spider.mcs.main.controllor;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spider.mcs.entity.Mcs_user;
import org.spider.mcs.main.dao.UserDao;
import org.spider.mcs.main.entity.Mcs_menu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

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
            //获取菜单
            List<Mcs_menu> menus = getMenuList();
            model.setViewName("index_menu");
            model.getModel().put("menus", menus);
        } catch (Exception e) {
            LOGGER.warn("{} login error: {}", userName, e);
            String web = request.getContextPath();
            String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + web + "/login.html?login_error=1";
            model.setViewName("redirect:" + basePath);
        }
        return model;
    }

    private List<Mcs_menu> getMenuList() {
        Mcs_user user = getUser();
        //获取一级菜单
        List<Mcs_menu> menuList = userDao.getMenuList(user.getUserId(), 0);
        if (user.isAdmin()) {
            List<Mcs_menu> menuAdminList = userDao.getAdminMenuList(0);
            if (menuAdminList.size() > 0) {
                menuList.addAll(menuAdminList);
            }
        }
        //获取二级菜单
        for (Mcs_menu menu : menuList) {
            List<Mcs_menu> childMenus;
            if (user.isAdmin()) {
                childMenus = userDao.getAdminMenuList(menu.getMenuId());
            } else {
                childMenus = userDao.getMenuList(user.getUserId(), menu.getMenuId());
            }
            menu.setChildMenus(childMenus);
        }
        return menuList;
    }

    private Mcs_user getUser() {
        Subject subject = SecurityUtils.getSubject();
        Mcs_user user = (Mcs_user) subject.getSession().getAttribute("user");
        return user;
    }
}
