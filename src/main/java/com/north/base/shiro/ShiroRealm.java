package com.north.base.shiro;

import com.alibaba.fastjson.JSON;
import com.north.sys.entity.SysUser;
import com.north.sys.service.SysUserService;
import com.north.utils.SessionUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.Base64;

/**
 * 类的描述
 *
 * @Author zhengxiangnan
 * @Date 2018/6/21 10:49
 */
public class ShiroRealm extends AuthorizingRealm {

    @Resource
    private SysUserService userService;


    /**
     * 获取授权信息
     *
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        return authorizationInfo;
    }

    /**
     * 登录认证
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        //获取用户账号
        String username = authenticationToken.getPrincipal().toString();

        SysUser user = userService.findByName(username);

        if(user != null && !StringUtils.isEmpty(user.getPassword())){
            if("0".equals(user.getStatus())){
                throw new LockedAccountException("帐号已经禁止登录！");
            }
            AuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
                    user,   //认证通过后，存放在session,一般存放user对象
                    user.getPassword(),   //用户数据库中的密码
                    getName());    //返回Realm名

            return authenticationInfo;
        }

        return null;
    }


}
