package com.activiti.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

	@Entity
	@Table(name = "sys_permission")
	@Data
public class SysPermission {
		

		@Id
		@GeneratedValue(strategy=GenerationType.IDENTITY)
		@Column(name = "id",nullable = false,unique = true)
		private Long id;
		
		@Column(name = "permission_code",nullable = false,unique = true)
		private String permissionCode;
		
		@Column(name = "url",nullable = false,unique = true)
		private String url;
		
		@Column(name = "permission_name",nullable = false,unique = true)
		private String permissionName;
	
	
}
