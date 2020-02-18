package com.activiti.web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.activiti.entity.TimerTask;
import com.activiti.web.dao.ITimerTaskDao;



@Service
public class TimerTaskService {

	@Autowired
	ITimerTaskDao timerTaskDao;

	public TimerTask insert(TimerTask timerTask){
		TimerTask result = timerTaskDao.save(timerTask);
		System.out.println(result.getId());
		return result;
	}
	
	
	public TimerTask findByProcessInstenceId(String procInstId){
		return timerTaskDao.findByProcessInstenceId(procInstId);
	}
}

