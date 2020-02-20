package com.activiti.dto;

import java.io.Serializable;
import java.util.Date;

public class ChildPageFormJson implements Serializable  {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String leave;
	private String leaveDays;
	private Date leaveStartTime;
	private	String ReasonForLeave;
	public String getLeave() {
		return leave;
	}
	public void setLeave(String leave) {
		this.leave = leave;
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
		return ReasonForLeave;
	}
	public void setReasonForLeave(String reasonForLeave) {
		ReasonForLeave = reasonForLeave;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	@Override
	public String toString() {
		return "ChildPageFormJson [leave=" + leave + ", leaveDays=" + leaveDays + ", leaveStartTime=" + leaveStartTime
				+ ", ReasonForLeave=" + ReasonForLeave + "]";
	}
	
	
}
