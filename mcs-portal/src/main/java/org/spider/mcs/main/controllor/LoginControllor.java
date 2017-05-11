package org.spider.mcs.main.controllor;

import org.spider.mcs.entity.User;
import org.spider.mcs.main.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by tianapple on 2017/5/10.
 */
@Controller
public class LoginControllor {
    @Autowired
    private UserDao userDao;

    @ResponseBody
    @RequestMapping(value = "/sys/login")
    public User login(String userName, String password, HttpServletRequest request) {
        User user = userDao.getUser(userName);
        return user;
    }
}
