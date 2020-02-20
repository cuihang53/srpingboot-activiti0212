package com.activiti.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * 部署单元维护
 * @param <childPageFormJson>
 * */
@Entity
@Table(name = "deployment_unit_maintenance")
public class DeploymentUnitMaintenance implements Serializable {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "id",nullable = false,unique = true)
	private 	Integer  	id;						//部署单元维护id
	@Column(name = "leaveBillId",nullable = false,unique = true)
	private     Integer     leaveBillId;			//请假单id
	@Column(name = "applicant",nullable = false,unique = true)
	private 	String 		applicant;				// 申请人
	@Column(name = "emergencyLevel",nullable = false,unique = true)
	private 	String		emergencyLevel;			// 紧急程度
	@Column(name = "applicationSubsystem",nullable = false,unique = true)
	private		String		applicationSubsystem;	// 应用子系统
	@Column(name = "resourceType",nullable = false,unique = true)
	private 	String		resourceType;			//资源类型
	@Column(name = "applicationType",nullable = false,unique = true)
	private 	String      applicationType;		//申请类型
	@Column(name = "applicationSummary",nullable = false,unique = true)
	private		String      applicationSummary;		//申请概要
	@Column(name = "detailedDescription",nullable = false,unique = true)
	private     String      detailedDescription;	//详细描述
	@Column(name = "createdTime",nullable = false,unique = true)
	private 	Date 		createdTime;
	@Column(name = "modifiedTime",nullable = false,unique = true)
	private  	Date 		modifiedTime;
	//@Column(name = "childPageFormJson",nullable = false,unique = true)
	//private     childPageFormJson      childPageFormJson;                 //子页面分组
	@Column(name = "leaves",nullable = false,unique = true)
	private String leaves;
	@Column(name = "leaveDays",nullable = false,unique = true)
	private String leaveDays;
	@Column(name = "leaveStartTime",nullable = false,unique = true)
	private Date leaveStartTime;
	@Column(name = "reasonForLeave",nullable = false,unique = true)
	private	String reasonForLeave;
	
	
	@Column(name = "status",nullable = false,unique = true)
	private	Integer status;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getApplicant() {
		return applicant;
	}
	public void setApplicant(String applicant) {
		this.applicant = applicant;
	}
	public String getEmergencyLevel() {
		return emergencyLevel;
	}
	public void setEmergencyLevel(String emergencyLevel) {
		this.emergencyLevel = emergencyLevel;
	}
	public String getApplicationSubsystem() {
		return applicationSubsystem;
	}
	public void setApplicationSubsystem(String applicationSubsystem) {
		this.applicationSubsystem = applicationSubsystem;
	}
	public String getResourceType() {
		return resourceType;
	}
	public void setResourceType(String resourceType) {
		this.resourceType = resourceType;
	}
	public String getApplicationType() {
		return applicationType;
	}
	public void setApplicationType(String applicationType) {
		this.applicationType = applicationType;
	}
	public String getApplicationSummary() {
		return applicationSummary;
	}
	public void setApplicationSummary(String applicationSummary) {
		this.applicationSummary = applicationSummary;
	}
	public String getDetailedDescription() {
		return detailedDescription;
	}
	public void setDetailedDescription(String detailedDescription) {
		this.detailedDescription = detailedDescription;
	}
	public Date getCreatedTime() {
		return createdTime;
	}
	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}
	public Date getModifiedTime() {
		return modifiedTime;
	}
	public void setModifiedTime(Date modifiedTime) {
		this.modifiedTime = modifiedTime;
	}
	
	public String getLeaves() {
		return leaves;
	}
	public void setLeaves(String leaves) {
		this.leaves = leaves;
	}
	public String getLeaveDays() {
		return leaveDays;
	}
	public void setLeaveDays(String leaveDays) {
		this.leaveDays = leaveDays;
	}
	public Date getLeaveStartTime() {
		return leaveStartTime;
	}
	public void setLeaveStartTime(Date leaveStartTime) {
		this.leaveStartTime = leaveStartTime;
	}
	public Integer getLeaveBillId() {
		return leaveBillId;
	}
	public void setLeaveBillId(Integer leaveBillId) {
		this.leaveBillId = leaveBillId;
	}
	public String getReasonForLeave() {
		return reasonForLeave;
	}
	public void setReasonForLeave(String reasonForLeave) {
		this.reasonForLeave = reasonForLeave;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}

	
}
