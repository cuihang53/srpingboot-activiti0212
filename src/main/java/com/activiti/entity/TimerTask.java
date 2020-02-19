package com.activiti.entity;


import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 请假单
 */
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

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getActId() {
		return actId;
	}

	public void setActId(String actId) {
		this.actId = actId;
	}

	public String getExecutionId() {
		return executionId;
	}

	public void setExecutionId(String executionId) {
		this.executionId = executionId;
	}

	public String getInstanceKey() {
		return instanceKey;
	}

	public void setInstanceKey(String instanceKey) {
		this.instanceKey = instanceKey;
	}

	public String getTimeOut() {
		return timeOut;
	}

	public void setTimeOut(String timeOut) {
		this.timeOut = timeOut;
	}

	public String getRestUrl() {
		return restUrl;
	}

	public void setRestUrl(String restUrl) {
		this.restUrl = restUrl;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getParam() {
		return param;
	}

	public void setParam(String param) {
		this.param = param;
	}

	public String getBusinessKey() {
		return businessKey;
	}

	public void setBusinessKey(String businessKey) {
		this.businessKey = businessKey;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	
}
