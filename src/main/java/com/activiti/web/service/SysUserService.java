package com.activiti.web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import com.activiti.entity.SysUser;
import com.activiti.web.dao.ISysUserDao;

@Service
public class SysUserService {

	@Autowired
	ISysUserDao sysUserDao;

	
	
	public SysUser save(SysUser user){
		return sysUserDao.save(user);
	}
	
	public SysUser findByUserAccount(String account){
		return sysUserDao.findByUserName(account);
	}

	
	public SysUser findByUserAccount(){
		User userDetails = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		return sysUserDao.findByUserName(userDetails.getUsername());
	}

}

