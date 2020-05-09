package com.north.base.configuration;

import com.north.base.session.RedisSessionDao;
import com.north.base.session.SimpleSessionFactory;
import com.north.base.shiro.ShiroPermissionsFilter;
import com.north.base.shiro.ShiroRealm;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.spring.web.config.DefaultShiroFilterChainDefinition;
import org.apache.shiro.spring.web.config.ShiroFilterChainDefinition;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.Cookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
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
@ConditionalOnProperty(name="north.shiro-filter.enable-redis",havingValue = "true")
public class ShiroRedisConfiguration {

    private List<String> filterChainDefinitionMap;

    public List<String> getFilterChainDefinitionMap() {
        return filterChainDefinitionMap;
    }

    @Value("${north.shiro-filter.filter-chain-definition-map}")
    public void setFilterChainDefinitionMap(List<String> filterChainDefinitionMap) {
        this.filterChainDefinitionMap = filterChainDefinitionMap;
    }

    /**
     * 自定义的Realm
     */
    @Bean(name = "myShiroRealm")
    public AuthorizingRealm shiroRealm(){
        ShiroRealm myShiroRealm = new ShiroRealm();
        return myShiroRealm;
    }

    @Bean
    public DefaultWebSessionManager configWebSessionManager(RedisSessionDao redisSessionDao,SimpleSessionFactory simpleSessionFactory){
        DefaultWebSessionManager manager = new DefaultWebSessionManager();
        manager.getSessionIdCookie().setSameSite(Cookie.SameSiteOptions.NONE);
//        manager.setCacheManager(cacheManager);// 加入缓存管理器
        manager.setSessionDAO(redisSessionDao);// 设置SessionDao
        manager.setSessionFactory(simpleSessionFactory);
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
    public DefaultWebSecurityManager securityManager(RedisSessionDao redisSessionDao,SimpleSessionFactory simpleSessionFactory){
        DefaultWebSecurityManager securityManager =  new DefaultWebSecurityManager();
        //设置realm
        securityManager.setRealm(shiroRealm());

        securityManager.setSessionManager(configWebSessionManager(redisSessionDao,simpleSessionFactory));

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
        for(String filterStr : getFilterChainDefinitionMap()){
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
