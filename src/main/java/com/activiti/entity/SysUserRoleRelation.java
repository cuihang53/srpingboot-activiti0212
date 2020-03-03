package com.activiti.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "sys_user_role_relation")
public class SysUserRoleRelation {
		

		@Id
		@GeneratedValue(strategy=GenerationType.IDENTITY)
		@Column(name = "id",nullable = false,unique = true)
		private Long id;
		
		@Column(name = "role_id",nullable = false,unique = true)
		private String roleId;
		
		@Column(name = "user_id",nullable = false,unique = true)
		private String userId;
	
	
}
