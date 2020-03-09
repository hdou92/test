package com.hd.test;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;
import org.junit.Test;

/**
 * 用于测试shiro安全认证功能
 */
public class shiro {

    @Test
    public void loginTest() {
        //创建IniSecurityManager工程对象:加载配置文件
        Factory<SecurityManager> factory = new IniSecurityManagerFactory("classpath:shiro.ini");
        //通过工厂对象创建SecurityManager对象
        SecurityManager securityManager = factory.getInstance();
        // 将SecurityManager绑定到当前运行环境中，让系统随时可以访问SecurityManager对象
        SecurityUtils.setSecurityManager(securityManager);
        //创建登录主体 注意：此时主体没有经过验证，仅仅是个空的对象
        Subject subject = SecurityUtils.getSubject();
        //绑定主体登陆的身份、凭证 即账号密码
        UsernamePasswordToken token = new UsernamePasswordToken("root", "root");
        //登陆
        try {
            subject.login(token);
            //没有抛异常则登录成功
            String currentUser = subject.getPrincipal().toString();
            System.out.println("当前登录的用户是：" + currentUser);
            //判断用户是否是拥有某种角色
            boolean isRole = subject.hasRole("admin");
            //是否拥有某种功能
            boolean isPer = subject.isPermitted("test:run");
            System.out.println("是否有admin权限结果:" + isRole);
            //判断是否有test功能
            System.out.println("是否有test功能结果:" + isPer);
            //登出操作
            subject.logout();
            //登出判断结果
            String isLogoutResult = !subject.isAuthenticated() ? "成功" : "失败";
            System.out.println("登出结果:" + isLogoutResult);
        } catch (UnknownAccountException uae) {
            System.out.println("用户名不存在");
        } catch (IncorrectCredentialsException ice) {
            System.out.println("密码错误");
        } catch (LockedAccountException lae) {
            System.out.println("用户被锁定，不能登录");
        } catch (AuthenticationException ae) {
            System.out.println("严重的错误");
        }
    }
}
