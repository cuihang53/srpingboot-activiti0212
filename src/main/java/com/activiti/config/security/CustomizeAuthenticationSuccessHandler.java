package com.activiti.config.security;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.activiti.common.JsonResult;
import com.activiti.entity.SysUser;
import com.activiti.utils.JsonUtil;
import com.activiti.web.dao.ISysUserDao;

/**
 * @Author: Cuihang
 * @Description: 登录成功处理逻辑
 */
@Component
public class CustomizeAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    @Autowired
    ISysUserDao sysUserDao;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
        //更新用户表上次登录时间、更新人、更新时间等字段
//    	SecurityContextHolder是spring security最基本的组件。用于存储安全上下文（security context）的信息。
//    	当前操作的用户是谁，该用户是否已经被认证，他拥有哪些角色权限等这些都被保存在SecurityContextHolder中。
//    	SecurityContextHolder默认是使用ThreadLocal实现的，这样就保证了本线程内所有的方法都可以获得SecurityContext对象。
        User userDetails = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();

//        String ip =  ((WebAuthenticationDetails) SecurityContextHolder.getContext().getAuthentication().getDetails()).getRemoteAddress();
         
        SysUser sysUser = sysUserDao.findByUserName(userDetails.getUsername());
        sysUser.setLastLoginTime(new Date());
        sysUser.setUpdateTime(new Date());
        sysUser.setUpdateUser(sysUser.getId());
        sysUserDao.save(sysUser);
        
        //此处还可以进行一些处理，比如登录成功之后可能需要返回给前台当前用户有哪些菜单权限，
        //进而前台动态的控制菜单的显示等，具体根据自己的业务需求进行扩展

        //返回json数据
        JsonResult<SysUser> result = new JsonResult<>(true, sysUser);
       //处理编码方式，防止中文乱码的情况
        httpServletResponse.setContentType("text/json;charset=utf-8");
       //塞到HttpServletResponse中返回给前台
        httpServletResponse.getWriter().write(JsonUtil.obj2String(result));
    }
}
