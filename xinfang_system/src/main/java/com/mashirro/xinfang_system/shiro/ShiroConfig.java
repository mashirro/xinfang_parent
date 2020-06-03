package com.mashirro.xinfang_system.shiro;


import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.mgt.DefaultSessionManager;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;


/**
 * shiro配置类
 */
@Configuration
public class ShiroConfig {

    /**
     * define the web-enabled SecurityManager
     *
     * @param userRealm
     * @return
     */
    @Bean
    public SecurityManager securityManager(UserRealm userRealm) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(userRealm);
        securityManager.setSessionManager(sessionManager());
        return securityManager;
    }

    /**
     * (1)在web环境中，Shiro的默认会话管理器SessionManager实现是ServletContainerSessionManager。
     * 这个非常简单的实现将所有会话管理任务(如果servlet容器支持会话集群)委托给运行时servlet容器。
     * 它本质上是Shiro的会话API与servlet容器之间的桥梁，除此之外几乎不做其他事情。
     * (2)要为您的web应用程序启用本地会话管理，您需要配置一个支持本地web的会话管理器，以覆盖默认的基于servlet容器的会话管理器。
     * 您可以通过在Shiro的SecurityManager上配置一个DefaultWebSessionManager实例来实现这一点。
     * @return
     */
    @Bean
    public SessionManager sessionManager() {
        DefaultSessionManager sessionManager = new DefaultWebSessionManager();
        /**
         * 默认情况下，Shiro的SessionManager实现默认为30分钟的会话超时。也就是说，如果创建的
         * 任何会话保持空闲(未使用，它的lastAccessedTime未更新)30分钟或更长时间，则认为该会话已过期，不允许再使用它。
         */
        sessionManager.setGlobalSessionTimeout(10000L);     //10秒钟(单位:毫秒值)
        //sessionManager.setSessionDAO();
        return sessionManager;
    }

    /**
     * 配置ShiroFilterFactoryBean
     *
     * @param securityManager
     * @return
     */
    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        HashMap<String, String> filterChainDefinitionMap = new HashMap<>();
        filterChainDefinitionMap.put("/user/login", "anon");
        filterChainDefinitionMap.put("/user/list", "authc");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        return shiroFilterFactoryBean;
    }
}
