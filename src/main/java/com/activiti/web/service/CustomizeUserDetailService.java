package com.activiti.web.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.activiti.entity.SysPermission;
import com.activiti.entity.SysUser;
import com.activiti.web.dao.ISysPerminssionDao;
import com.activiti.web.dao.ISysUserDao;

public class CustomizeUserDetailService implements UserDetailsService{
	
	@Autowired
	private ISysUserDao sysUserDao;
	
	@Autowired 
	private ISysPerminssionDao sysPerminssionDao;
	//	String username：用户名
	//	String password： 密码
	//	boolean enabled： 账号是否可用
	//	boolean accountNonExpired：账号是否过期
	//	boolean credentialsNonExpired：密码是否过期
	//	boolean accountNonLocked：账号是否锁定
	//	Collection<? extends GrantedAuthority> authorities)：用户权限列表

	@Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    	
    	if (username == null || "".equals(username)) {
            throw new RuntimeException("用户不能为空");
        }
        //根据用户名查询用户
    	 SysUser sysUser = sysUserDao.findByUserName(username);
        if (sysUser == null) {
            throw new RuntimeException("用户不存在");
        }
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        if (sysUser != null) {
            //获取该用户所拥有的权限
            List<SysPermission> sysPermissions = sysPerminssionDao.selectListByUser(sysUser.getId());
            // 声明用户授权
            sysPermissions.forEach(sysPermission -> {
            	//将权限存入grantedAuthorities 供访问决策管理器（CustomizeAccessDecisionManager）使用
                GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(sysPermission.getPermissionCode());
                grantedAuthorities.add(grantedAuthority);
            });
        }
        return new User(
	        		sysUser.getAccount(), 
	        		sysUser.getPassword(), 
	        		sysUser.getEnabled(), 
	        		sysUser.getAccountNonExpired(), 
	        		sysUser.getCredentialsNonExpired(), 
	        		sysUser.getAccountNonLocked(), 
	        		grantedAuthorities
        		);
	}
	
}

