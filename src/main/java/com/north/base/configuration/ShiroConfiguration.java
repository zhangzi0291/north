package com.north.base.configuration;

import com.north.base.session.RedisSessionDao;
import com.north.base.session.SimpleSessionFactory;
import com.north.base.shiro.ShiroPermissionsFilter;
import com.north.base.shiro.ShiroRealm;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.spring.web.config.DefaultShiroFilterChainDefinition;
import org.apache.shiro.spring.web.config.ShiroFilterChainDefinition;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 类的描述
 *
 * @Author zhengxiangnan
 * @Date 2018/6/21 10:46
 */
@Configuration
public class ShiroConfiguration {

    /**
     * 自定义的Realm
     */
    @Bean(name = "myShiroRealm")
    public ShiroRealm shiroRealm(){
        ShiroRealm myShiroRealm = new ShiroRealm();
        return myShiroRealm;
    }

    @Bean
    public DefaultWebSessionManager configWebSessionManager(RedisSessionDao redisSessionDao){
        DefaultWebSessionManager manager = new DefaultWebSessionManager();
//        manager.setCacheManager(cacheManager);// 加入缓存管理器
        manager.setSessionDAO(redisSessionDao);// 设置SessionDao
        manager.setSessionFactory(new SimpleSessionFactory());
        manager.setDeleteInvalidSessions(true);// 删除过期的session
        manager.setGlobalSessionTimeout(redisSessionDao.getExpireTime());// 设置全局session超时时间
        manager.setSessionValidationSchedulerEnabled(true);// 是否定时检查session

        return manager;
    }

    /**
     * 安全管理器DefaultWebSecurityManager是Shiro的核心模块
     * @return
     */
    @Bean
    public DefaultWebSecurityManager securityManager(RedisSessionDao redisSessionDao){
        DefaultWebSecurityManager securityManager =  new DefaultWebSecurityManager();
        //设置realm
        securityManager.setRealm(shiroRealm());

        securityManager.setSessionManager(configWebSessionManager(redisSessionDao));

        return securityManager;
    }

    /**
     * ShiroFilterFactoryBean用来配置需要被拦截的请求
     * @param securityManager
     * @return
     */
    @Bean(name = "shiroFilter")
    public ShiroFilterFactoryBean shiroFilterFactoryBean(DefaultWebSecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);

        Map<String, Filter> filters = new LinkedHashMap<>();
        ShiroPermissionsFilter permissionsFilter = new ShiroPermissionsFilter();
        filters.put("permissionsFilter",permissionsFilter);
        shiroFilterFactoryBean.setFilters(filters);

        //权限过滤
        Map<String, String> filterChainDefinitionMap = new LinkedHashMap <>();
        filterChainDefinitionMap.put("/sys/login", "anon");
        filterChainDefinitionMap.put("/sys/logout", "anon");
        filterChainDefinitionMap.put("/**","authc");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);

        //未登录跳转
        shiroFilterFactoryBean.setLoginUrl("/");
        //未认证跳转
        shiroFilterFactoryBean.setUnauthorizedUrl("/sys/403");

        return shiroFilterFactoryBean;
    }


    @Bean
    public ShiroFilterChainDefinition shiroFilterChainDefinition() {
        DefaultShiroFilterChainDefinition chainDefinition = new DefaultShiroFilterChainDefinition();

        return chainDefinition;
    }

}
