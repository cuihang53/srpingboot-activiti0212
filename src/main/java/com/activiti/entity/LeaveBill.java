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
@Table(name = "a_leavebill")
public class LeaveBill {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "id",nullable = false,unique = true)
	private Long id;//主键ID
	
	@Column(name = "days",nullable = false,unique = true)
	private Integer days;// 请假天数
	
	@Column(name = "content",nullable = false,unique = true)
	private String content;// 请假内容
	
	@Column(name = "leave_date",nullable = false,unique = true)
	private Date leaveDate = new Date();// 请假时间
	
	@Column(name = "remark",nullable = false,unique = true)
	private String remark;// 备注
	
	@Column(name = "user_id",nullable = false,unique = true)
	private Integer userId;
	
//	private String user;// 请假人
	
	@Column(name = "state",nullable = false,unique = true)
	private Integer state=0;// 请假单状态 0初始录入,1.开始审批,2为审批完成

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getDays() {
		return days;
	}

	public void setDays(Integer days) {
		this.days = days;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getLeaveDate() {
		return leaveDate;
	}

	public void setLeaveDate(Date leaveDate) {
		this.leaveDate = leaveDate;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

//	public String getUser() {
//		return user;
//	}
//
//	public void setUser(String user) {
//		this.user = user;
//	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}
	
	
}
