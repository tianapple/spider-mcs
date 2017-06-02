package org.spider.mcs.main.shiro;

import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.spider.mcs.main.shiro.AuthRealm;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by tianapple on 2017/5/31.
 */
@Configuration
public class ShiroConfig {
    @Bean(name = "authRealm")
    public AuthRealm authRealm() {
        return new AuthRealm();
    }

//    @Bean(name = "authFilter")
//    public AuthFilter authFilter() {
//        return new AuthFilter();
//    }

    //配置核心安全事务管理器
    @Bean(name = "securityManager")
    public SecurityManager securityManager(@Qualifier("authRealm") AuthRealm authRealm) {
        DefaultWebSecurityManager manager = new DefaultWebSecurityManager();
        manager.setRealm(authRealm);
        return manager;
    }

    @Bean(name = "shiroFilter")
    public ShiroFilterFactoryBean shiroFilter(@Qualifier("securityManager") SecurityManager manager) {
        ShiroFilterFactoryBean bean = new ShiroFilterFactoryBean();
        bean.setSecurityManager(manager);
        //配置登录的url和登录成功的url
        bean.setLoginUrl("/login");
        bean.setSuccessUrl("/index");
        bean.setUnauthorizedUrl("/login");
        //配置访问权限
        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<>();
        filterChainDefinitionMap.put("/login*", "anon"); //表示可以匿名访问
        filterChainDefinitionMap.put("/logout", "anon");
        filterChainDefinitionMap.put("/error*", "anon");
        filterChainDefinitionMap.put("/static/*", "anon");
        filterChainDefinitionMap.put("/templates/*", "authc");
//        filterChainDefinitionMap.put("/*", "authc");//表示需要认证才可以访问
//        filterChainDefinitionMap.put("/**", "authc");//表示需要认证才可以访问
//        filterChainDefinitionMap.put("/*.*", "authc");
        bean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        //授权过滤配置
//        , @Qualifier("authFilter") AuthFilter filter
//        Map<String, Filter> filters = new HashMap<>();
//        filters.put("authc", filter);
//        bean.setFilters(filters);
        return bean;
    }

    //保证实现了Shiro内部lifecycle函数的bean执行
    @Bean
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }

    //开启Shiro的注解(如@RequiresRoles,@RequiresPermissions)
    @Bean
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator creator = new DefaultAdvisorAutoProxyCreator();
        creator.setProxyTargetClass(true);
        return creator;
    }

    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(@Qualifier("securityManager") SecurityManager manager) {
        AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();
        advisor.setSecurityManager(manager);
        return advisor;
    }

//    //配置自定义的密码比较器
//    @Bean(name = "credentialsMatcher")
//    public CredentialsMatcher credentialsMatcher() {
//        return new CredentialsMatcher();
//    }
}
