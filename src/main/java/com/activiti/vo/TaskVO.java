package com.activiti.vo;

import java.util.Date;

import lombok.Data;

@Data
public class TaskVO {

	private String taskId;
	private String taskDefinitionKey;
	private String taskName;
	private String assignee;
	private Date createTime;
	private String instanceKey;
	
}
