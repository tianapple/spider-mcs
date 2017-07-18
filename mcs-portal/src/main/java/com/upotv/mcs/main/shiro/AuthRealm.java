package com.upotv.mcs.main.shiro;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.upotv.mcs.main.entity.Mcs_user;
import com.upotv.mcs.main.entity.UserPermission;
import com.upotv.mcs.main.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tianapple on 2017/5/31.
 */
public class AuthRealm extends AuthorizingRealm {
    private static Logger LOGGER = LoggerFactory.getLogger(AuthRealm.class);

    @Autowired
    private LoginService loginService;

    /**
     * 第一步：帐号认证：
     * 验证当前登录的Subject ,返回一个封装了Shiro 能够理解的帐户数据格式的AuthenticationInfo 实例。
     * 完整流程：
     * 本例中该方法的调用时机为LoginController.login()方法中执行Subject.login()时
     * 在 doGetAuthenticationInfo() 方法中须要返回一个正确的 SimpleAuthenticationInfo 对象，这样 Shiro 就会和 Subject 的 login() 方法中传入的 token 信息进行比对，完成认证的操作。
     * <p>
     * 我们从参数 AuthenticationToken 对象中取出用户填写的用户名和密码信息，这个 token 其实就是 Subject 使用 login() 方法中传入的 UsernamePasswordToken 对象，
     * 我们通过这个 UsernamePasswordToken 对象获得用户填写的用户名和密码，然后我们应该通过用户的用户名去数据库查询数据库是否有这个用户名：如果没有，抛出一个用户名不存在异常；如果用户名存在，返回一个用户对象（带密码的），
     * 再用数据库返回的密码数据和用户填写的密码数据进行比对，如果错误，就抛出异常，如果正确，就要把正确的用户名和密码信息封装成一个 SimpleAuthenticationInfo 对象返回，这才是一个比较完整并且正确的流程
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken) throws AuthenticationException {
        UsernamePasswordToken token = (UsernamePasswordToken) authcToken;
        String pwd = new String(token.getPassword());
        String userName = token.getUsername();

        LOGGER.info("{} token is {}", userName, token);
        Mcs_user user = loginService.getUserByUserName(userName);

        if(user == null){
            return null;
        }else if(user.isLock()){
            throw new LockedAccountException("Account [" + user.getUserName() + "] is locked.");
        }else{
            //ByteSource credentialsSalt = ByteSource.Util.bytes(userName);//这里的参数要给个唯一的;
            AuthenticationInfo authcInfo = new SimpleAuthenticationInfo(user.getUserName(), user.getPassword(), getName());
            setSession("user", user);
            return authcInfo;
        }
    }

    /**
     * 第二步：帐号授权：
     * 将一些数据放到ShiroSession中,以便于其它地方使用
     * 比如Controller,使用时直接用HttpSession.getAttribute(key)就可以取到
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        if (principals == null) {
            throw new AuthorizationException("PrincipalCollection method argument cannot be null.");
        }
        Subject subject = SecurityUtils.getSubject();
        Mcs_user user = (Mcs_user) subject.getSession().getAttribute("user");
        LOGGER.info("login user is {}", user.getUserName());
        //获取用户角色、权限
        List<UserPermission> userPermissions = loginService.getPermissions(user.getUserId());
        //收集权限点列表
        List<String> permissionPointList = new ArrayList<>();
        for (UserPermission userPermission : userPermissions) {
            List<String> list = userPermission.getPermissionList();
            if (list != null && list.size() > 0) {
                permissionPointList.addAll(list);
            }
        }
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        info.addStringPermissions(permissionPointList);
        return info;
    }

    private void setSession(Object key, Object value) {
        Subject currentUser = SecurityUtils.getSubject();
        if (currentUser != null) {
            Session session = currentUser.getSession();
            if (session != null) {
                session.setAttribute(key, value);
            }
        }
    }

//    //认证.登录
//    @Override
//    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
//        UsernamePasswordToken utoken=(UsernamePasswordToken) token;//获取用户输入的token
//        String username = utoken.getUsername();
//        User user = userDao.findUserByUserName(username);
//        return new SimpleAuthenticationInfo(user, user.getPassword(),this.getClass().getName());//放入shiro.调用CredentialsMatcher检验密码
//    }
//
//    //授权
//    @Override
//    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principal) {
//        User user=(User) principal.fromRealm(this.getClass().getName()).iterator().next();//获取session中的用户
//        List<String> permissions=new ArrayList<>();
//        Set<Role> roles = user.getRoles();
//        if(roles.size()>0) {
//            for(Role role : roles) {
//                Set<Module> modules = role.getModules();
//                if(modules.size()>0) {
//                    for(Module module : modules) {
//                        permissions.add(module.getMname());
//                    }
//                }
//            }
//        }
//        SimpleAuthorizationInfo info=new SimpleAuthorizationInfo();
//        info.addStringPermissions(permissions);//将权限放入shiro中.
//        return info;
//    }
}
