package com.activiti.dto;

import java.util.Date;

import com.activiti.dto.base.BaseDto;

import lombok.Data;

@Data
public class LeaveBillDto extends BaseDto{
	
	private Long id;//主键ID
	
	private Integer days;// 请假天数
	
	private String content;// 请假内容
	
	private Date leaveDate;// 请假时间
	
	private String remark;// 备注
	
	private Integer userId;
	
	private Integer state=0;// 请假单状态 0初始录入,1.开始审批,2为审批完成
	
	private String instanceKey; //任务ID
	
}
