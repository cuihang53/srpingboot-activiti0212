package com.activiti.entity;


import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

/**
 * 请假单
 */
@Data
@Entity
@Table(name = "timer_task")
public class TimerTask {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "id",nullable = false,unique = true)
	private Long id;
	
	@Column(name = "act_id",nullable = false,unique = true)
	private String actId;
	
	@Column(name = "execution_id",nullable = false,unique = true)
	private String executionId;
	
	@Column(name = "instance_key",nullable = false,unique = true)
	private String instanceKey;
	
	@Column(name = "time_out",nullable = false,unique = true)
	private String timeOut;
	
	@Column(name = "rest_url",nullable = false,unique = true)
	private String restUrl;
	
	@Column(name = "status",nullable = false,unique = true)
	private int status;
	
	@Column(name = "param",nullable = false,unique = true)
	private String param;
	
	@Column(name = "business_key",nullable = false,unique = true)
	private String businessKey;
	
	@Column(name = "CREATE_TIME",nullable = false,unique = true)
	private Date createTime ;
	
	@Column(name = "UPDATE_TIME",nullable = false,unique = true)
	private Date updateTime;
	
}
