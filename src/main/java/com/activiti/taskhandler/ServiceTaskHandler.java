package com.activiti.taskhandler;



import java.text.SimpleDateFormat;
import java.util.Date;

import org.activiti.engine.RuntimeService;
import org.activiti.engine.delegate.DelegateExecution;

import org.activiti.engine.delegate.Expression;

import org.activiti.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;

import com.activiti.entity.TimerTask;
import com.activiti.utils.DatetimeUtil;
import com.activiti.web.service.TimerTaskService;

/**  
 *   
 * @version   
 */

//实现自动节点逻辑
//1、工单申请流转后，调用自动节点(service task)。
//2、进入java方法后，将URL(跟吉胜平确认，用来做测试使用，不需要业务处理)，工单号等写入到定时任务表中。 
//3、然后while循环，每隔5秒查询定时任务表状态，没有状态则继续sleep。 
//4、当查询到状态改变后，这通过表中状态判断是否接口执行成功。
//5、根据状态，set不同的值到临时变量中，用于分支判断。
//3、定时任务读取定时任务表中数据，发现未执行的则读取数据，调用表中URL执行，执行完成后，将结果数据回写到定时任务表中(最好是删除定时任务表、新增到历史表，这个跟崔航商量)。 吉胜平 

public class ServiceTaskHandler implements JavaDelegate{

	//流程变量
	
	@Autowired
	RuntimeService runtimeService;
	
	@Autowired
	TimerTaskService timerTaskService;


	@SuppressWarnings("deprecation")
	@Override
	public void execute(DelegateExecution execution) throws Exception {
		
			//TODO:1.保存URL,工单号到定时任务表
		TimerTask job = new TimerTask();
		String procInstId = execution.getProcessInstanceId();
//		execution.getProcessBusinessKey();
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
		timerTaskService.insert(job);
		
		boolean result = true;
		//TODO:2.while循环，每隔5秒查询定时任务表状态，没有状态则继续sleep
		TimerTask newJob = new TimerTask();
		int index = 0;			  //计数器
		Long timeoutTime = 50L;  //超时时间50秒
		Date beginTime = null;
		while (result) {
		     try {
			     	Date currentDate = new Date();
			     	if(index==0){
			     		beginTime = currentDate;
			     	}
			     	//超时处理 直接终止
			     	if(DatetimeUtil.calLastedTime(beginTime)>=timeoutTime){
			     		System.out.println("自动节点处理超时");
						break;
					}
					newJob = timerTaskService.findByProcessInstenceId(procInstId);
					//状态成功 结束循环
					if(newJob!=null && newJob.getStatus() == 2){
						result = false;
					}
					System.out.println(DatetimeUtil.getDate(currentDate, DatetimeUtil.DEFAULT_FORMAT) + "--自动节点执行第" + (++index) + "次");
					Thread.sleep(5 * 1000); //设置暂停的时间 5 秒
		     } catch (InterruptedException e) {
		         e.printStackTrace();
		     }
		}
		if(newJob.getStatus() != 2){
//			execution.setVariable("messages", 409);
//		}else{
			execution.setVariable("messages", 501); //走错误分支
		}
		System.out.println("serviceTask已经执行已经执行！");
	}
	

}