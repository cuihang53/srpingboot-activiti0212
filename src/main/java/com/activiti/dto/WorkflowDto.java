package com.activiti.dto;

import java.util.Map;

import lombok.Data;

@Data
public class WorkflowDto {

	private String taskId;  //流程实例ID
	
	private Map<String,Object>  variables; //流程下一步所需要的参数
	
	private String businessId;
	
	private String instanceKey;
}
