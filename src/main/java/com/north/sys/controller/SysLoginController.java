package com.north.sys.controller;


import com.north.base.R;
import com.north.base.service.RedisService;
import com.north.sys.entity.SysResource;
import com.north.sys.entity.SysUser;
import com.north.sys.service.SysResourceService;
import com.north.utils.EncryptionUtil;
import com.north.utils.SessionUtil;
import io.swagger.annotations.*;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;


/**
 * 类的描述
 *
 * @Author zhengxiangnan
 * @Date 2018/3/6 9:06
 */
@Api("登录相关")
@RestController
@RequestMapping("sys")
public class SysLoginController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Value("${north.token-key}")
    private String tokenKey;
    @Resource
    private SysResourceService resourceService;
    @Resource
    private RedisService redisService;
    @Resource
    private SessionUtil sessionUtil;

    @ApiOperation(value = "根据用户id查询菜单")
    @RequestMapping(path = "/getMenu", method = {RequestMethod.GET, RequestMethod.POST})
    public R getMenu(@ApiParam(name = "id",value = "用户ID",required = true)String id) {
        return R.ok("menu",resourceService.getMenus(id));
    }

    @RequestMapping(path = "/get", method = {RequestMethod.GET, RequestMethod.POST})
    public R get(String username) {
        redisService.set("a",resourceService.getMenus(null));

        List<SysResource> s = redisService.get("a",List.class);
        System.out.println(s);
        return R.ok("menu").putObject("key",s);
    }

    @ApiOperation("未登录")
    @RequestMapping(path = "/unlogin", method = {RequestMethod.GET, RequestMethod.POST})
    public R get(HttpServletResponse response) {
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        return R.error(R.ReturnCodeEnum.UNAUTHORIZED.getCode(),"未登录");
    }

    @ApiOperation("获取Token")
    @RequestMapping(path = "getToken", method = {RequestMethod.GET, RequestMethod.POST})
    public R getToken(SysUser user) {
        String username = user.getUsername();
        String password = user.getPassword();
        if(StringUtils.isEmpty(username) || StringUtils.isEmpty(password)){
            return R.error(R.ReturnCodeEnum.UNAUTHORIZED.getCode(),"用户名或密码没有填写");
        }
        UsernamePasswordToken token = new UsernamePasswordToken(user.getUsername(), EncryptionUtil.getPasswordEncoder(user.getUsername(),user.getPassword()));
        //获取当前的Subject
        Subject currentUser = SecurityUtils.getSubject();
        System.out.println(currentUser.hashCode());
        Session session = SecurityUtils.getSubject().getSession();
        //登陆检查
        R r = loginCheck(username, token, currentUser);
        if(r!=null){
            return r;
        }
        //验证是否登录成功
        if(currentUser.isAuthenticated()){
            logger.debug("用户[{}]登录认证通过",username);
            session = SecurityUtils.getSubject().getSession(true);
            SysUser nowUser = (SysUser) currentUser.getPrincipal();
            session.setAttribute("user",nowUser);
            nowUser.setPassword(null);
            //保存Token
            String tokenStr = session.getId().toString();
            return R.ok("登陆成功").putObject(tokenKey,tokenStr);
        }else{
            token.clear();
        }
        return R.error("登陆失败");
    }

    @ApiOperation("登陆")
    @ApiImplicitParams({
        @ApiImplicitParam(name="username",value="用户名",dataType="string", paramType = "query"),
        @ApiImplicitParam(name="password",value="密码",dataType="string", paramType = "query"),
    })
    @RequestMapping(path = "login", method = {RequestMethod.GET, RequestMethod.POST})
    @ApiModelProperty(name = "email",hidden = true)
    public R login(SysUser user) {
        String username = user.getUsername();
        String password = user.getPassword();
        if(StringUtils.isEmpty(username) || StringUtils.isEmpty(password)){
            return R.error(R.ReturnCodeEnum.UNAUTHORIZED.getCode(),"用户名或密码没有填写");
        }

        UsernamePasswordToken token = new UsernamePasswordToken(user.getUsername(), EncryptionUtil.getPasswordEncoder(user.getUsername(),user.getPassword()));
        //获取当前的Subject
        Subject currentUser = SecurityUtils.getSubject();
        Session session = SecurityUtils.getSubject().getSession();
        //登陆检查
        R r = loginCheck(username, token, currentUser);
        if(r != null){
            return r;
        }
        //验证是否登录成功
        if(currentUser.isAuthenticated()){
            logger.debug("用户[{}]登录认证通过",username);
//            session = SecurityUtils.getSubject().getSession();
            SysUser nowUser = (SysUser) currentUser.getPrincipal();
            session.setAttribute("user",nowUser);
            nowUser.setPassword(null);
            //保存Token
            String tokenStr = session.getId().toString();
            return R.ok("登陆成功").putObject("user",SecurityUtils.getSubject().getPrincipal()).putObject(tokenKey,tokenStr);
        }else{
            token.clear();
        }
        return R.error("登陆失败");
    }

    private R loginCheck(String username, UsernamePasswordToken token, Subject currentUser) {
        try {
            //在调用了login方法后,SecurityManager会收到AuthenticationToken,并将其发送给已配置的Realm执行必须的认证检查
            //每个Realm都能在必要时对提交的AuthenticationTokens作出反应
            //所以这一步在调用login(token)方法时,它会走到MyRealm.doGetAuthenticationInfo()方法中,具体验证方式详见此方法
            logger.debug("对用户[{}]进行登录验证..验证开始",username);
            currentUser.login(token);
            logger.debug("对用户[{}]进行登录验证..验证通过",username);
        }catch(UnknownAccountException uae){
            logger.debug("对用户[{}]进行登录验证..验证未通过,未知账户",username);
            return R.error(R.ReturnCodeEnum.UNAUTHORIZED.getCode(),"未知账户");
        }catch(IncorrectCredentialsException ice){
            logger.debug("对用户[{}]进行登录验证..验证未通过,错误的凭证",username);
            return R.error(R.ReturnCodeEnum.UNAUTHORIZED.getCode(),"密码不正确");
        }catch(LockedAccountException lae){
            logger.debug("对用户[{}]进行登录验证..验证未通过,账户已锁定",username);
            return R.error(R.ReturnCodeEnum.UNAUTHORIZED.getCode(),"账户已锁定");
        }catch(ExcessiveAttemptsException eae){
            logger.debug("对用户[{}]进行登录验证..验证未通过,错误次数过多",username);
            return R.error(R.ReturnCodeEnum.UNAUTHORIZED.getCode(),"用户名或密码错误次数过多");
        }catch(AuthenticationException ae){
            //通过处理Shiro的运行时AuthenticationException就可以控制用户登录失败或密码错误时的情景
            logger.debug("对用户[{}]进行登录验证..验证未通过",username);
            return R.error(R.ReturnCodeEnum.UNAUTHORIZED.getCode(),"用户名或密码不正确");
        }
        return null;
    }

    @ApiOperation("注销")
    @RequestMapping(path = "/logout", method = {RequestMethod.GET, RequestMethod.POST})
    public R logOut(HttpServletRequest request) {
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        return R.ok("登出");
    }

    @ApiOperation("权限不足")
    @RequestMapping(path = "/403", method = {RequestMethod.GET, RequestMethod.POST})
    public R unauthorized() {
        return R.error(R.ReturnCodeEnum.FORBIDDEN.getCode(),"权限不足");
    }

}
