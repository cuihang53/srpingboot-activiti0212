package com.activiti.dto;

import java.io.Serializable;
import java.util.Date;

public class ChildPageFormJson implements Serializable  {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String leaves;
	private String leaveDays;
	private Date leaveStartTime;
	private	String reasonForLeave;
	
	
	
	
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
	public String getReasonForLeave() {
		return reasonForLeave;
	}
	public void setReasonForLeave(String reasonForLeave) {
		this.reasonForLeave = reasonForLeave;
	}
	
	@Override
	public String toString() {
		return "ChildPageFormJson [leave=" + leaves + ", leaveDays=" + leaveDays + ", leaveStartTime=" + leaveStartTime
				+ ", ReasonForLeave=" + reasonForLeave + "]";
	}
	
	
}
