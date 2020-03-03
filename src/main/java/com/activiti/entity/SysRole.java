package com.activiti.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "sys_role")
@Data
public class SysRole {
	

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "id",nullable = false,unique = true)
	private Long id;
	
	@Column(name = "role_code",nullable = false,unique = true)
	private String roleCode;
	
	@Column(name = "role_name",nullable = false,unique = true)
	private String roleName;

	@Column(name = "role_description",nullable = false,unique = true)
	private String roleDescription;

}
