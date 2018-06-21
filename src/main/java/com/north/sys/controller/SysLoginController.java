package com.north.sys.controller;


import com.north.base.R;
import com.north.sys.entity.SysUser;
import com.north.sys.service.SysResourceService;
import com.north.utils.EncryptionUtil;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;


/**
 * 类的描述
 *
 * @Author zhengxiangnan
 * @Date 2018/3/6 9:06
 */
@RestController
@RequestMapping("sys")
public class SysLoginController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    private SysResourceService resourceService;

    @RequestMapping("/getMenu")
    public R getMenu(Integer id) {
        return R.ok("menu",resourceService.getMenus(id));
    }
    @RequestMapping("/get")
    public R get(String username) {
        return R.ok("menu");
    }

    @RequestMapping("/unlogin")
    public R get() {
        return R.error("未登录");
    }

    @RequestMapping("login")
    public R login(SysUser user) {
        String username = user.getUsername();

        UsernamePasswordToken token = new UsernamePasswordToken(user.getUsername(), EncryptionUtil.getPasswordEncoder(user.getUsername(),user.getPassword()));
        //获取当前的Subject
        Subject currentUser = SecurityUtils.getSubject();
        try {
            //在调用了login方法后,SecurityManager会收到AuthenticationToken,并将其发送给已配置的Realm执行必须的认证检查
            //每个Realm都能在必要时对提交的AuthenticationTokens作出反应
            //所以这一步在调用login(token)方法时,它会走到MyRealm.doGetAuthenticationInfo()方法中,具体验证方式详见此方法
            logger.debug("对用户[" + username + "]进行登录验证..验证开始");
            currentUser.login(token);
            logger.debug("对用户[" + username + "]进行登录验证..验证通过");
        }catch(UnknownAccountException uae){
            logger.debug("对用户[" + username + "]进行登录验证..验证未通过,未知账户");
            return R.error(R.ReturnCodeEnum.UNAUTHORIZED.getCode(),"未知账户");
        }catch(IncorrectCredentialsException ice){
            logger.debug("对用户[" + username + "]进行登录验证..验证未通过,错误的凭证");
            return R.error(R.ReturnCodeEnum.UNAUTHORIZED.getCode(),"密码不正确");
        }catch(LockedAccountException lae){
            logger.debug("对用户[" + username + "]进行登录验证..验证未通过,账户已锁定");
            return R.error(R.ReturnCodeEnum.UNAUTHORIZED.getCode(),"账户已锁定");
        }catch(ExcessiveAttemptsException eae){
            logger.debug("对用户[" + username + "]进行登录验证..验证未通过,错误次数过多");
            return R.error(R.ReturnCodeEnum.UNAUTHORIZED.getCode(),"用户名或密码错误次数过多");
        }catch(AuthenticationException ae){
            //通过处理Shiro的运行时AuthenticationException就可以控制用户登录失败或密码错误时的情景
            logger.debug("对用户[" + username + "]进行登录验证..验证未通过");
            return R.error(R.ReturnCodeEnum.UNAUTHORIZED.getCode(),"用户名或密码不正确");
        }
        //验证是否登录成功
        if(currentUser.isAuthenticated()){
            logger.debug("用户[" + username + "]登录认证通过(这里可以进行一些认证通过后的一些系统参数初始化操作)");
            return R.ok("登陆成功");
        }else{
            token.clear();
        }
        return R.error("登陆失败");
    }

    @RequestMapping("/logout")
    public R logOut(HttpServletRequest request) {
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        return R.ok("登出");
    }

    @RequestMapping("/403")
    public R unauthorized() {
        return R.error(R.ReturnCodeEnum.FORBIDDEN.getCode(),"权限不足");
    }

}
