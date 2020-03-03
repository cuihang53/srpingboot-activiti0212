package com.activiti.web.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.activiti.entity.SysPermission;

@Repository
public interface ISysPerminssionDao extends JpaRepository<SysPermission,String>{

	@Query(value ="SELECT p FROM SysUser AS u "
			+ "LEFT JOIN SysUserRoleRelation AS ur 		 ON u.id = ur.userId "
			+ "LEFT JOIN SysRole AS r 					 ON r.id = ur.roleId "
			+ "LEFT JOIN SysRolePermissionRelation AS rp ON r.id = rp.roleId "
			+ "LEFT JOIN SysPermission AS p 			 ON p.id = rp.permissionId "
			+ "WHERE u.id = :userId")
	List<SysPermission> selectListByUser(@Param("userId")Long userId);

	
	@Query(value ="SELECT pe FROM SysPermission pe "
//			+ "LEFT JOIN SysRequestPathPermissionRelation re ON re.permissionId = pe.id "
//			+ "LEFT JOIN SysRequestPath 				  pa ON pa.id 			= re.urlId "
			+ "WHERE pe.url = :url")
	List<SysPermission> selectListByPath(@Param("url")String url);


//	@Query(value ="SELECT pe.permissionCode, pa.url FROM sys_permission pe "
//			+ "LEFT JOIN Sys_Request_Path_Permission_Relation re ON re.permission_Id = pe.id "
//			+ "LEFT JOIN Sys_Request_Path 				  pa ON pa.id 			= re.url_Id "
//			)
//	List<Object[]> alls();

	


}
