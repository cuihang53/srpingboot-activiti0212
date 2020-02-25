package com.activiti.taskhandler;

import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;

/**
 * 员工经理任务分配
 *
 */
@SuppressWarnings("serial")
public class ManagerTaskHandler implements TaskListener {
	@Override
	public void notify(DelegateTask delegateTask) {
		//设置个人任务的办理人
		delegateTask.setAssignee("华谊兄弟");
	}
}
