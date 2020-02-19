package com.activiti.taskhandler;



import java.util.Date;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.Expression;
 
import org.activiti.engine.delegate.JavaDelegate;
import org.springframework.transaction.annotation.Transactional;

import com.activiti.entity.TimerTask;
import com.activiti.utils.DatetimeUtil;
import com.activiti.utils.SpringUtil;
import com.activiti.web.service.TimerTaskService;

/**  
 *   
 * @version   
 */

public class ServiceTaskHandler implements JavaDelegate{
	TimerTaskService timerTaskService = SpringUtil.getBean("timerTaskService");
//	获取自定义属性
	private Expression timeoutTime;
	
	@Override
    @Transactional(rollbackFor = Exception.class)
	public void execute(DelegateExecution execution) throws Exception {
//		String timeOutTime = workflowService.findServiceTaskProperty(execution.getProcessDefinitionId(), execution.getCurrentActivityId());
		String timeout= (String)timeoutTime.getValue(execution);
		
		Thread thread = new Thread(new TimerTaskThread(execution));
		thread.run();//同步执行
		
		boolean result = true;
		//TODO:2.while循环，每隔5秒查询定时任务表状态，没有状态则继续sleep
		TimerTask newJob = new TimerTask();
		int index = 0;			  //计数器
		Date beginTime = null;
		while (result) {
	      try {
		     	Date currentDate = new Date();
		     	if(index==0){
		     		beginTime = currentDate;
		     	}
		     	if(timeout==null || timeout.length()==0){
		     		timeout = "0";
		     	}
		     	//超时处理 直接终止
		     	if(DatetimeUtil.calLastedTime(beginTime)>=Long.parseLong(timeout)){
		     		System.out.println("自动节点处理超时");
					break;
				}
				newJob = timerTaskService.findByProcessInstenceId(execution.getProcessInstanceId());
				System.out.println(newJob.getId() + " " + newJob.getActId() + " " + newJob.getBusinessKey() + " " +newJob.getInstanceKey());
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
		if(newJob.getStatus() == 2){
			execution.setVariable("messages", 409);
		}else{
			execution.setVariable("messages", 501); //走错误分支
		}
		System.out.println("serviceTask已经执行已经执行！");
	}
	
	public static class TimerTaskThread implements Runnable{
		TimerTaskService timerTaskService = SpringUtil.getBean("timerTaskService");
		private DelegateExecution execution;
		public TimerTaskThread(DelegateExecution execution){
			this.execution = execution;
		}
		@Override
		public void run() {
			timerTaskService.insert(execution);
		}
	}
	
}