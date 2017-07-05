package com.upotv.mcs.main.controllor;

import com.upotv.mcs.main.entity.Mcs_menu;
import com.upotv.mcs.main.entity.Mcs_user;
import com.upotv.mcs.main.entity.TreeData;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.upotv.mcs.main.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
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
    private LoginService loginService;

    /**
     * 用户登陆
     *
     * @param user
     * @param model
     * @return
     */
    @RequestMapping("/login") //url
    public void dologin(HttpServletResponse response, Mcs_user user, Model model) throws IOException {
        String info = loginUser(user);
        if (!"success".equals(info)) {
            model.addAttribute("failMsg", info);
            response.sendRedirect("/");
        } else {
            model.addAttribute("successMsg", "登陆成功！");//返回到页面说夹带的参数
            model.addAttribute("name", user.getUserName());
            response.sendRedirect("/main");
        }
    }

    private String loginUser(Mcs_user user) {
        if (isRelogin(user)) {
            return "success"; // 如果已经登陆，无需重新登录
        }
        return shiroLogin(user); // 调用shiro的登陆验证
    }

    /**
     * shiro登陆
     *
     * @param user
     * @return
     */
    private String shiroLogin(Mcs_user user) {
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(user.getUserName(), user.getPassword());
        token.setRememberMe(true);
        // shiro登陆验证
        try {
            subject.login(token);
        } catch (UnknownAccountException ex) {
            return "用户不存在或者密码错误！";
        } catch (IncorrectCredentialsException ex) {
            return "用户不存在或者密码错误！";
        } catch (Exception ex) {
            ex.printStackTrace();
            return "内部错误，请重试！";
        }
        return "success";
    }

    /**
     * 验证是否登陆
     *
     * @param user
     * @return
     */
    private boolean isRelogin(Mcs_user user) {
        Subject us = SecurityUtils.getSubject();
        if (us.isAuthenticated()) {
            return true; // 参数未改变，无需重新登录，默认为已经登录成功
        }
        return false; // 需要重新登陆
    }

    /**
     * 登出
     *
     * @param request
     * @param response
     * @throws IOException
     */
    @RequestMapping("/logout")
    public void logout(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Subject subject = SecurityUtils.getSubject();
        if (subject != null) {
            try {
                subject.logout();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        response.sendRedirect("/login");
    }

    /**
     * 获得根菜单
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getMenu")
    public List<TreeData> getMenu(int pid) {
        Mcs_user user = getUser();
        List<Mcs_menu> menuList = loginService.getMenuList(user.getUserId(), pid);

        if (user.isAdmin()) {
            List<Mcs_menu> menuAdminList = loginService.getAdminMenuList(pid);
            if (menuAdminList.size() > 0) {
                menuList.addAll(menuAdminList);
            }
        }
        menuList = menuList.stream().distinct().collect(Collectors.toList()); //排除重复

        List<TreeData> trees = new ArrayList<>();
        for (Mcs_menu mcsMenu : menuList) {
            TreeData treeData = TreeData.parse(mcsMenu);
            trees.add(treeData);
        }
        return trees;
    }



    /**
     * 获得菜单
     * @return

    @ResponseBody
    @RequestMapping(value = "/getMenu")
    public List<TreeData> getMenuList() {
        try {
            Mcs_user user = getUser();
            return getMenuList(user, 0);
        } catch (Exception e) {
            LOGGER.warn("", e);
            return new ArrayList<>();
        }
    }
     */
    /**
     * 获得菜单列表
     * @param user
     * @param parentId
     * @return

    private List<TreeData> getMenuList(Mcs_user user, int parentId) {
        //获取一级菜单
        List<Mcs_menu> menuList = loginService.getMenuList(user.getUserId(), parentId);
        if (user.isAdmin()) {
            List<Mcs_menu> menuAdminList = loginService.getAdminMenuList(parentId);
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
     */
    private Mcs_user getUser() {
        Subject subject = SecurityUtils.getSubject();
        Object user = subject.getSession().getAttribute("user");
        if (user != null) {
            return (Mcs_user) user;
        }
        return null;
    }

    /*
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
                    , request.getScheme(), request.getServerName(), request.getServerPort(), request.getContextPath());
            model.setViewName(loginUrl);
            model.getModel().put("msg", e.getMessage());
        }
        return model;
    }
    */
}
