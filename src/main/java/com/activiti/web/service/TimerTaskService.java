package com.activiti.web.service;


import java.util.Date;

import org.activiti.engine.delegate.DelegateExecution;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
//import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.activiti.entity.TimerTask;
import com.activiti.web.dao.ITimerTaskDao;



@Service
public class TimerTaskService {

	@Autowired
	ITimerTaskDao timerTaskDao;
	
	
	
//	@Rollback(false)
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	public TimerTask insert(DelegateExecution execution){
//		TimerTaskService timerTaskService = SpringUtil.getBean("timerTaskService");
		TimerTask job = new TimerTask();
		String procInstId = execution.getProcessInstanceId();
	//	execution.getProcessBusinessKey();
		job.setRestUrl("http://localhost:8080/httpJob.html");
		job.setActId(execution.getCurrentActivityId());
		job.setExecutionId(execution.getId());
		job.setInstanceKey(procInstId);
		//1待处理状态 2执行成功  3超时
		job.setStatus(1);
		job.setParam("");
		job.setBusinessKey(execution.getBusinessKey());
		job.setCreateTime(new Date());
		job.setUpdateTime(new Date());
		return insert(job);
	}
	
//	@Rollback(false)
	public TimerTask insert(TimerTask timerTask){
		TimerTask result = timerTaskDao.save(timerTask);
		System.out.println(result.getId() + timerTask.getRestUrl());
		return result;
	}
	
	public TimerTask findByProcessInstenceId(String procInstId){
		return timerTaskDao.findByProcessInstenceId(procInstId);
	}
}

