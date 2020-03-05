package com.activiti.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "sys_activity_group")
@Data
public class SysActivityGroup {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "id",nullable = false,unique = true)
	private Long id;
	
	@Column(name = "group_name",nullable = false,unique = true)
	private String groupName;
	
	@Column(name = "group_code",nullable = false,unique = true)
	private String groupCode;
	
}
