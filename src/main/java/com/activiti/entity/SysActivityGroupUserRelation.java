package com.activiti.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "sys_activity_group_user_relation")
@Data
public class SysActivityGroupUserRelation {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "id",nullable = false,unique = true)
	private Long id;
	
	@Column(name = "user_id",nullable = false,unique = true)
	private String userId;
	
	@Column(name = "group_code",nullable = false,unique = true)
	private String groupCode;
	
}
