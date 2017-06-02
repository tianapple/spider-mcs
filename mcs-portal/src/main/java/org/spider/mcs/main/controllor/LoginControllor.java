package org.spider.mcs.main.controllor;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.Subject;
import org.spider.mcs.domain.User;
import org.spider.mcs.main.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by tianapple on 2017/5/10.
 */
@Controller
public class LoginControllor {
    @Autowired
    private UserDao userDao;

    //@ResponseBody
    @RequestMapping(value = "/login.do")
    public ModelAndView login(String userName, String password, HttpServletRequest request, HttpServletResponse response) {
        UsernamePasswordToken token = new UsernamePasswordToken(userName, password);
        ModelAndView model = new ModelAndView();
        try {
            Subject subject = SecurityUtils.getSubject();
            subject.login(token);
            //List<Menu> menus = getMenus(userName);
            model.setViewName("index_menu");
            model.getModel().put("hello", "hello word");
            //response.sendRedirect("/index.html");
        } catch (Exception e) {
            e.printStackTrace();
            String web = request.getContextPath();
            String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + web + "/login.html?login_error=1";
            model.setViewName("redirect:" + basePath);
        }
        return model;
//        User user = userDao.getUser(userName);
//        return user;
    }

    @ResponseBody
    @RequestMapping(value = "/user")
    @RequiresPermissions("admin/role/add")
    public User getUser() {
        Subject subject = SecurityUtils.getSubject();
        User user = (User) subject.getSession().getAttribute("user");
        return user;
    }
}
