package com.north.base.configuration;

import com.github.streamone.shiro.session.RedissonSessionDao;
import com.github.streamone.shiro.session.RedissonWebSessionManager;
import com.north.base.shiro.ShiroPermissionsFilter;
import com.north.base.shiro.ShiroRealm;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.spring.web.config.DefaultShiroFilterChainDefinition;
import org.apache.shiro.spring.web.config.ShiroFilterChainDefinition;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.Cookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.redisson.api.RedissonClient;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

import javax.servlet.Filter;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * shiro相关配置
 *
 * @Author zhengxiangnan
 * @Date 2018/6/21 10:46
 */
@Configuration
public class ShiroConfiguration {

    @Value("${north.shiro-filter.global-session-timeout}")
    private Long globalSessionTimeout;

    private List<String> filterChainDefinition;

    @Value("${north.shiro-filter.filter-chain-definition}")
    public void setFilterChainDefinitionMap(List<String> filterChainDefinitionMap) {
        this.filterChainDefinition = filterChainDefinitionMap;
    }

    /**
     * 自定义的Realm
     */
    @Bean(name = "myShiroRealm")
    public AuthorizingRealm shiroRealm(){
        ShiroRealm myShiroRealm = new ShiroRealm();
        return myShiroRealm;
    }

    /**
     * 安全管理器DefaultWebSecurityManager是Shiro的核心模块
     * @return
     */
    @Bean
    public DefaultWebSecurityManager securityManager(){
        DefaultWebSecurityManager securityManager =  new DefaultWebSecurityManager();
        //设置realm
        securityManager.setRealm(shiroRealm());
        securityManager.setSessionManager(configWebSessionManager());
        return securityManager;
    }
    @Bean
    public DefaultWebSessionManager configWebSessionManager(){
        DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
        //允许cookie跨域访问
        sessionManager.getSessionIdCookie().setSameSite(Cookie.SameSiteOptions.NONE);

        return sessionManager;
    }

    /**
     * 安全管理器DefaultWebSecurityManager是Shiro的核心模块
     * @return
     */
    @Bean
    @ConditionalOnBean(value = RedissonClient.class)
    public DefaultWebSecurityManager securityManager(RedissonClient redisson){
        RedissonSessionDao sessionDao = new RedissonSessionDao();
        sessionDao.setRedisson(redisson);

        RedissonWebSessionManager sessionManager = new RedissonWebSessionManager();
        sessionManager.setSessionDAO(sessionDao);
        sessionManager.getSessionIdCookie().setSameSite(Cookie.SameSiteOptions.NONE);
        sessionManager.setGlobalSessionTimeout(1800000);

        DefaultWebSecurityManager securityManager =  new DefaultWebSecurityManager();
        //设置realm
        securityManager.setRealm(shiroRealm());
        securityManager.setSessionManager(sessionManager);

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
        filters.put("authc", new ShiroPermissionsFilter());
        shiroFilterFactoryBean.setFilters(filters);
        //权限过滤
        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<>();
        for(String filterStr : filterChainDefinition){
            if(!StringUtils.isEmpty(filterStr)){
                String[] filter = filterStr.split(":");
                filterChainDefinitionMap.put(filter[0].trim(),filter[1].trim());
            }
        }
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);

        //未登录跳转
        shiroFilterFactoryBean.setLoginUrl("/sys/unlogin");
        //未认证跳转
        shiroFilterFactoryBean.setUnauthorizedUrl("/sys/403");

        return shiroFilterFactoryBean;
    }


    @Bean
    public ShiroFilterChainDefinition shiroFilterChainDefinition() {
        DefaultShiroFilterChainDefinition chainDefinition = new DefaultShiroFilterChainDefinition();
        return chainDefinition;
    }

    /**
     * 不加似乎影响spring本身的事务
     * @return
     */
    @Bean
    public static DefaultAdvisorAutoProxyCreator getDefaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
        defaultAdvisorAutoProxyCreator.setUsePrefix(true);
        return defaultAdvisorAutoProxyCreator;
    }

}
