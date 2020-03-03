package com.activiti.web.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.activiti.entity.SysUser;



@Repository
public interface ISysUserDao extends JpaRepository<SysUser,String>{

	@Query(value ="select u from SysUser u where u.account=:account")
	SysUser findByUserName(@Param("account")String userName);


}
