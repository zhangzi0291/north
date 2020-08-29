package com.north.base.service;

import com.north.sys.entity.SysResource;
import com.north.sys.mapper.SysResourceMapper;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.filter.mgt.DefaultFilterChainManager;
import org.apache.shiro.web.filter.mgt.PathMatchingFilterChainResolver;
import org.apache.shiro.web.servlet.AbstractShiroFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class ShiroServiceImpl implements ShiroService {

    @Autowired
    @Qualifier("shiroFilter")
    private ShiroFilterFactoryBean shiroFilterBean;

    @Resource
    private SysResourceMapper sysResourceMapper;

    private List<String> filterChainDefinition;

    @Value("${north.shiro-filter.filter-chain-definition}")
    public void setFilterChainDefinitionMap(List<String> filterChainDefinitionMap) {
        this.filterChainDefinition = filterChainDefinitionMap;
    }

    @Override
    public void updateFilterChains() {
        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<>();

        List<SysResource> resourceList = sysResourceMapper.getResourceActions();
        for(SysResource resource : resourceList){
            filterChainDefinitionMap.put(resource.getResourceUrl(),resource.getPermission());
        }

        for(String filterStr : filterChainDefinition){
            if(!StringUtils.isEmpty(filterStr)){
                String[] filter = filterStr.split(":");
                filterChainDefinitionMap.put(filter[0].trim(),filter[1].trim());
            }
        }


        AbstractShiroFilter shiroFilter = null;
        try {
            shiroFilter = (AbstractShiroFilter) shiroFilterBean.getObject();

            // 获取过滤管理器
            PathMatchingFilterChainResolver filterChainResolver = (PathMatchingFilterChainResolver) shiroFilter
                    .getFilterChainResolver();
            DefaultFilterChainManager filterManager = (DefaultFilterChainManager) filterChainResolver.getFilterChainManager();

            //清空拦截管理器中的存储
            filterManager.getFilterChains().clear();
            /*
            清空拦截工厂中的存储,如果不清空这里,还会把之前的带进去
            ps:如果仅仅是更新的话,可以根据这里的 map 遍历数据修改,重新整理好权限再一起添加
             */
            shiroFilterBean.getFilterChainDefinitionMap().clear();

            // 相当于新建的 map, 因为已经清空了
            Map<String, String> chains = shiroFilterBean.getFilterChainDefinitionMap();
            //把修改后的 map 放进去
            chains.putAll(filterChainDefinitionMap);

            //这个相当于是全量添加
            for (Map.Entry<String, String> entry : filterChainDefinitionMap.entrySet()) {
                //要拦截的地址
                String url = entry.getKey().trim().replace(" ", "");
                //地址持有的权限
                String chainDefinition = entry.getValue().trim().replace(" ", "");
                //生成拦截
                filterManager.createChain(url, chainDefinition);
            }
        } catch (Exception e) {
//            logger.error("updatePermission error,filterMap=" + filterMap, e);
            e.printStackTrace();
        }
    }
}
