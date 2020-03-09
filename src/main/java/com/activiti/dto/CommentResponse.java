package com.activiti.dto;

import java.util.Date;

import org.activiti.engine.task.Comment;

import lombok.Data;

@Data
public class CommentResponse {

	
	private String id;
	private String processInstanceId;
	private String taskId;
	private Date time;
	private String type;
	private String userId;
	
	 public CommentResponse(Comment comment) {
		    setId(comment.getId());
		    setProcessInstanceId(comment.getProcessInstanceId());
		    setTaskId(comment.getTaskId());
		    setTime(comment.getTime());
		    setType(comment.getType());
		    setUserId(comment.getUserId());
		  }
	
	
}
