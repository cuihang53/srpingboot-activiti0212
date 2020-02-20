package com.activiti.dto;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


import lombok.Data;
@Data
public class DeploymentUnitMaintenanceDto implements Serializable {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private 	Integer  	id;						//部署单元维护id
	private     Integer     leaveBillId;			//请假单id
	private 	String 		applicant;				// 申请人
	private 	String		emergencyLevel;			// 紧急程度
	private		String		applicationSubsystem;	// 应用子系统
	private 	String		resourceType;			//资源类型
	private 	String      applicationType;		//申请类型
	private		String      applicationSummary;		//申请概要
	private     String      detailedDescription;	//详细描述
	private 	Date 		createdTime;
	private  	Date 		modifiedTime;
	
	private     ChildPageFormJson    childPageFormJson;                 //子页面分组
	
	
}
