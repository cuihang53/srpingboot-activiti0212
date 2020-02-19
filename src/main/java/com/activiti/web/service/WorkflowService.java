package com.activiti.web.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipInputStream;

import org.activiti.engine.FormService;
import org.activiti.engine.HistoryService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.form.TaskFormData;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.history.HistoricVariableInstance;
import org.activiti.engine.impl.RepositoryServiceImpl;
import org.activiti.engine.impl.identity.Authentication;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.pvm.PvmTransition;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Comment;
import org.activiti.engine.task.Task;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.activiti.common.JsonResult;
import com.activiti.common.ResponseCode;
import com.activiti.entity.LeaveBill;
import com.activiti.entity.WorkflowBean;
import com.activiti.utils.JsonUtil;
import com.activiti.vo.TaskVO;
import com.activiti.web.dao.ILeaveBillDao;

@Service
public class WorkflowService {
	/**请假申请Dao*/
	@Autowired
	private ILeaveBillDao leaveBillDao;
	
	@Autowired
	private RepositoryService repositoryService;
	
	@Autowired
	private RuntimeService runtimeService;
	
	@Autowired
	private TaskService taskService;
	
	@Autowired
	private FormService formService;
	
	@Autowired
	private HistoryService historyService;
	

	/**部署流程定义*/
	public void saveNewDeploye(File file, String filename) {
		try {
			//2：将File类型的文件转化成ZipInputStream流
			ZipInputStream zipInputStream = new ZipInputStream(new FileInputStream(file));
			repositoryService.createDeployment()//创建部署对象
							.name(filename)//添加部署名称
							.addZipInputStream(zipInputStream)//
							.deploy();//完成部署
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	/**查询部署对象信息，对应表（act_re_deployment）*/
	public List<Deployment> findDeploymentList() {
		List<Deployment> list = repositoryService.createDeploymentQuery()//创建部署对象查询
							.orderByDeploymenTime().asc()//
							.list();
		return list;
	}
	
	/**查询流程定义的信息，对应表（act_re_procdef）*/
	public List<ProcessDefinition> findProcessDefinitionList() {
		List<ProcessDefinition> list = repositoryService.createProcessDefinitionQuery()//创建流程定义查询
							.orderByProcessDefinitionVersion()
//							.processDefinitionName("")
//							.processDefinitionKey("")
//							.processDefinitionId("")
							.asc()//
//							.listPage(firstResult, maxResults)
//							.count()
							.list();
		return list;
	}
	
	
	/**使用部署对象ID和资源图片名称，获取图片的输入流*/
	public InputStream findImageInputStream(String deploymentId,
			String imageName) {
		return repositoryService.getResourceAsStream(deploymentId, imageName);
	}
	
	/**使用部署对象ID，删除流程定义*/
	
	public void deleteProcessDefinitionByDeploymentId(String deploymentId) {
		repositoryService.deleteDeployment(deploymentId, true);
	}
	
	/**更新请假状态，启动流程实例，让启动的流程实例关联业务*/
	
//	public void saveStartProcess(WorkflowBean workflowBean) {
//		//1：获取请假单ID，使用请假单ID，查询请假单的对象LeaveBill
////		Long id = workflowBean.getId();
//		Long businessId = 1L;
//		LeaveBill leaveBill = leaveBillDao.findLeaveBillById(businessId);
//		//2：更新请假单的请假状态从0变成1（初始录入-->审核中）
//		leaveBill.setState(1);
//		//3：使用当前对象获取到流程定义的key（对象的名称就是流程定义的key）
////		String key = leaveBill.getClass().getSimpleName();
////		String key = "leaveBill2";
//		/**
//		 * 4：从Session中获取当前任务的办理人，使用流程变量设置下一个任务的办理人
//			    * inputUser是流程变量的名称，
//			    * 获取的办理人是流程变量的值
//		 */
//		Map<String, Object> variables = new HashMap<String,Object>();
//		variables.put("inputUser", "cuihang");//表示惟一用户
//		
////		runtimeService.startProcessInstanceById("LeaveBill2:1:15004",variables);
//		runtimeService.startProcessInstanceByKey("LeaveBill2", String.valueOf(businessId),variables);
//		
//	}
	
	
	public void saveStartProcess(Map<String, Object> variables, String businessId, String instanceKey){
		runtimeService.startProcessInstanceByKey(instanceKey, String.valueOf(businessId),variables);
	}
	
	
	/***
	 * 公共数据扭转方法
	 * @param taskId  act_ru_task   ID_
	 * @param variables 节点参数   例如：驳回/通过 处理人等 
	 * @return ProcessInstance 流程变量实例 用于判断流程是否走完  用作改变业务代码工单状态
	 */
//	public ProcessInstance taskComplete(String taskId, Map<String, Object> variables){
		public void taskComplete(String taskId, Map<String, Object> variables){
			//查询任务
			Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
			
			//获取流程实例ID 对应act_ru_execution的ID
//			String processInstanceId = task.getProcessInstanceId();
			//TODO:后期删除 start
			ProcessDefinitionEntity pro = (ProcessDefinitionEntity) ((RepositoryServiceImpl)repositoryService).getDeployedProcessDefinition(task.getProcessDefinitionId());
			List<ActivityImpl> actList = pro.getActivities();
			String excId = task.getExecutionId();
			ExecutionEntity execution = (ExecutionEntity) runtimeService.createExecutionQuery().executionId(excId).singleResult();
			String activitiId = execution.getActivityId();
			for(ActivityImpl activityImpl :actList){
				String id = activityImpl.getId();
				if(activitiId.equals(id)){
					  System.out.println("当前任务："+activityImpl.getProperty("name")+";自定义属性值:"+activityImpl.getProperty("approvetype")); 
				}
			}
			//end
			//批注
//			Authentication.setAuthenticatedUserId("cuihang");
//			taskService.addComment(taskId, processInstanceId, message);
			//完成任务
			//variables.put("inputUser","张三")
			//当前流程图使用listeners 监听的 com.activiti.taskhandler.ManagerTaskHandler 指定下个节点审批人
			taskService.complete(taskId, variables);
			
			//查询act_ru_execution表 如果为空表示流程结束
//			ProcessInstance pi = runtimeService.createProcessInstanceQuery()//
//							.processInstanceId(processInstanceId)//使用流程实例ID查询
//							.singleResult();
//			return pi;
			
	}
	
	/**
	 * 2：使用当前用户名查询正在执行的任务表，获取当前任务的集合List<Task>
	 * */
	public String findTaskListByName(String assignee, Integer offset, Integer limit) {
		//分页结果
		List<Task> taskList = taskService.createTaskQuery()
					.taskAssignee(assignee)//指定个人任务查询
					.orderByTaskCreateTime().asc()
					.listPage(offset, limit); //分页
		
		//Task是懒加载的对象 直接转json会报错(lazy loading outside command context  activiti)，需要copy数据
		List<TaskVO> customTaskList = new ArrayList<TaskVO>();
	    for (Task task : taskList) {
	    	TaskVO to = new TaskVO();
	        to.setTaskId(task.getId());
	        to.setTaskDefinitionKey(task.getTaskDefinitionKey());
	        to.setTaskName(task.getName());
	        to.setAssignee(task.getAssignee());
	        to.setCreateTime(task.getCreateTime());
	        to.setInstanceKey(task.getProcessInstanceId());
	        customTaskList.add(to);
	    }
		//totalCount
		Long count  = taskService.createTaskQuery()//
				.taskAssignee(assignee)
				.orderByTaskCreateTime().asc()
				.count();
		//构造返回数据类型
		Map<String,Object> result = new HashMap<>();
		result.put("taskList", customTaskList);
		result.put("count", count);
		JsonResult json = new JsonResult();
		json.setContent(result);
		json.setStatus(HttpStatus.OK.value());
		json.setCode(ResponseCode.SUCCESS.value());
		return JsonUtil.obj2String(json);
	}
	
	/****
	 *  历史列表 包含当前正在进行的节点 所以只需要查历史列表
	 * @param offset
	 * @param limit
	 * @return
	 * @throws IOException 
	 */
	public String  taskHisList(Integer pageNum, Integer pageSize) throws IOException{
		List<HistoricTaskInstance> htiList = historyService.createHistoricTaskInstanceQuery()//历史任务表查询
				//.processInstanceId(processInstanceId)//使用流程实例ID查询
				.orderByProcessDefinitionId().asc()
				.listPage(pageSize * (pageNum - 1), pageSize);
		
		
		List<TaskVO> customTaskList = new ArrayList<TaskVO>();
	    for (HistoricTaskInstance task : htiList) {
	    	//TODO:查看自定义属性
	    	ProcessDefinitionEntity processDefinitionEntity = (ProcessDefinitionEntity) repositoryService.getProcessDefinition(task.getProcessDefinitionId());
//	    	List<ActivityImpl> activitis =  processDefinitionEntity.getActivities();
//	    	for(ActivityImpl activiti : activitis){
//	    		System.out.println("url:"+activiti.getProperty("urlType"));
//	    	}
	    	
	    	String url = hisurl(task.getProcessInstanceId(), task.getId());
	    	
	    	//查看自定义属性结束
	    	TaskVO to = new TaskVO();
	        to.setTaskId(task.getId());
	        to.setTaskDefinitionKey(task.getTaskDefinitionKey());
	        to.setTaskName(task.getName());
	        to.setAssignee(task.getAssignee());
	        to.setCreateTime(task.getCreateTime());
	        to.setInstanceKey(task.getProcessInstanceId());
	        to.setUrl(url);
	        customTaskList.add(to);
	    }
		
		Long count = historyService.createHistoricTaskInstanceQuery()//历史任务表查询
				//.processInstanceId(processInstanceId)//使用流程实例ID查询
				.count();
		
		Map<String,Object> result = new HashMap<>();
		result.put("taskList", customTaskList);
		result.put("count", count);
		JsonResult json = new JsonResult();
		json.setContent(result);
		json.setStatus(HttpStatus.OK.value());
		json.setCode(ResponseCode.SUCCESS.value());
		return JsonUtil.obj2String(json);
		
	}
	
	
	public String hisurl(String procInstanceId, String taskId) throws IOException{
	     //1.通过procInstanceId获取所有历史节点
	     List<HistoricActivityInstance> activityInstances =historyService
	               .createHistoricActivityInstanceQuery()
	               .processInstanceId(procInstanceId)
	               .list();
	   
	     for(HistoricActivityInstance act :activityInstances) {
	      //2.然后根据当前任务获取当前流程的流程定义，然后根据流程定义获得所有的节点：
	         ProcessDefinitionEntity def = (ProcessDefinitionEntity) ((RepositoryServiceImpl)repositoryService).getDeployedProcessDefinition(act.getProcessDefinitionId());
	         List<ActivityImpl> activitiList = def.getActivities(); //rs是指RepositoryService的实例
	         //3.获取当前节点的任务id
	         String id =act.getTaskId();
	         if(taskId.equals(id)&&id!=null){
	          //4.匹配对应任务id的节点id
		          String activitiId=act.getActivityId();
		    
		         for(ActivityImpl activityImpl:activitiList){
			          String activityImplid = activityImpl.getId();
			          //5.循环所有节点，匹配对应节点，输出其属性
			          if(activitiId.equals(activityImplid)){
			        	  System.out.println("当前任务："+activityImpl.getProperty("name")+
			        			  ";自定义属性值:"+activityImpl.getProperty("approvetype")); //输出某个节点的某种属性
			        	  return String.valueOf(activityImpl.getProperty("approvetype"));
			          }
		         }
	         }
	     }
	     return null;
    }
	
	
	/**
	 * 使用任务ID，获取当前任务节点中对应的Form key中的连接的值
	 * */
	public String findTaskFormKeyByTaskId(String taskId) {
		TaskFormData formData = formService.getTaskFormData(taskId);
		//获取Form key的值
		String url = formData.getFormKey();
		return url;
	}
	
	
	
	public String findServiceTaskProperty(String proDefId, String activitiId) {
		//普通类从spring容器中拿出service
		ProcessDefinitionEntity pro = (ProcessDefinitionEntity) ((RepositoryServiceImpl)repositoryService).getDeployedProcessDefinition(proDefId);
		List<ActivityImpl> actList = pro.getActivities();
		for(ActivityImpl activityImpl :actList){
			String id = activityImpl.getId();
			if(activitiId.equals(id)){
				  System.out.println("当前任务："+activityImpl.getProperty("name")+";超时时间:"+activityImpl.getProperty("timeoutTime")+activityImpl.getProperty("timeoutTime")); 
			}
		}
		return null;
	}
	
	
	/**
	 * 一：使用任务ID，查找请假单ID，从而获取请假单信息
	 * */
	public LeaveBill findLeaveBillByTaskId(String taskId) {
		//1：使用任务ID，查询任务对象Task
		Task task = taskService.createTaskQuery()//
						.taskId(taskId)//使用任务ID查询
						.singleResult();
		//2：使用任务对象Task获取流程实例ID
		String processInstanceId = task.getProcessInstanceId();
		//3：使用流程实例ID，查询正在执行的执行对象表，返回流程实例对象
		ProcessInstance pi = runtimeService.createProcessInstanceQuery()//
						.processInstanceId(processInstanceId)//使用流程实例ID查询
						.singleResult();
		//4：使用流程实例对象获取BUSINESS_KEY
		String buniness_key = pi.getBusinessKey();
		//5：获取BUSINESS_KEY对应的主键ID，使用主键ID，查询请假单对象（LeaveBill.1）
		String id = "";
		if(StringUtils.isNotBlank(buniness_key)){
			//截取字符串，取buniness_key小数点的第2个值
			id = buniness_key.split("\\.")[1];
		}
		//查询请假单对象
		//使用hql语句：from LeaveBill o where o.id=1
		LeaveBill leaveBill = leaveBillDao.findLeaveBillById(Long.parseLong(id));
		return leaveBill;
	}
	
	/**
	 * 二：已知任务ID，查询ProcessDefinitionEntiy对象，从而获取当前任务完成之后的连线名称，并放置到List<String>集合中
	 * */
	public List<String> findOutComeListByTaskId(String taskId) {
		//返回存放连线的名称集合
		List<String> list = new ArrayList<String>();
		//1:使用任务ID，查询任务对象
		Task task = taskService.createTaskQuery()//
					.taskId(taskId)//使用任务ID查询
					.singleResult();
		//2：获取流程定义ID
		String processDefinitionId = task.getProcessDefinitionId();
		//3：查询ProcessDefinitionEntiy对象
		ProcessDefinitionEntity processDefinitionEntity = (ProcessDefinitionEntity) repositoryService.getProcessDefinition(processDefinitionId);
		//使用任务对象Task获取流程实例ID
		String processInstanceId = task.getProcessInstanceId();
		//使用流程实例ID，查询正在执行的执行对象表，返回流程实例对象
		ProcessInstance pi = runtimeService.createProcessInstanceQuery()//
					.processInstanceId(processInstanceId)//使用流程实例ID查询
					.singleResult();
		//获取当前活动的id
		String activityId = pi.getActivityId();
		//4：获取当前的活动
		ActivityImpl activityImpl = processDefinitionEntity.findActivity(activityId);
		//5：获取当前活动完成之后连线的名称
		List<PvmTransition> pvmList = activityImpl.getOutgoingTransitions();
		if(pvmList!=null && pvmList.size()>0){
			for(PvmTransition pvm:pvmList){
				String name = (String) pvm.getProperty("name");
				if(StringUtils.isNotBlank(name)){
					list.add(name);
				}
				else{
					list.add("默认提交");
				}
			}
		}
		return list;
	}
	
	/**
	 * 指定连线的名称完成任务
	 * */
	public void saveSubmitTask(WorkflowBean workflowBean) {
		//获取任务ID
		String taskId = workflowBean.getTaskId();
		//获取连线的名称
		String outcome = workflowBean.getOutcome();
		//批注信息
		String message = workflowBean.getComment();
		//获取请假单ID
		Long id = workflowBean.getId();
		
		/**
		 * 1：在完成之前，添加一个批注信息，向act_hi_comment表中添加数据，用于记录对当前申请人的一些审核信息
		 */
		//使用任务ID，查询任务对象，获取流程流程实例ID
		Task task = taskService.createTaskQuery()//
						.taskId(taskId)//使用任务ID查询
						.singleResult();
		//获取流程实例ID
		String processInstanceId = task.getProcessInstanceId();
		/**
		 * 注意：添加批注的时候，由于Activiti底层代码是使用：
		 * 		String userId = Authentication.getAuthenticatedUserId();
			    CommentEntity comment = new CommentEntity();
			    comment.setUserId(userId);
			  所有需要从Session中获取当前登录人，作为该任务的办理人（审核人），对应act_hi_comment表中的User_ID的字段，不过不添加审核人，该字段为null
			 所以要求，添加配置执行使用Authentication.setAuthenticatedUserId();添加当前任务的审核人
		 * */
		/**8
		 * TODO:
		 * 暂时不用
		 */
		Authentication.setAuthenticatedUserId("cuihang");
		taskService.addComment(taskId, processInstanceId, message);
		/**
		 * 2：如果连线的名称是“默认提交”，那么就不需要设置，如果不是，就需要设置流程变量
		 * 在完成任务之前，设置流程变量，按照连线的名称，去完成任务
				 流程变量的名称：outcome
				 流程变量的值：连线的名称
		 */
		Map<String, Object> variables = new HashMap<String,Object>();
		if(outcome!=null && !outcome.equals("默认提交")){
			variables.put("outcome", outcome);
		}

		//3：使用任务ID，完成当前人的个人任务，同时流程变量
		taskService.complete(taskId, variables);
		//4：当任务完成之后，需要指定下一个任务的办理人（使用类）-----已经开发完成
		
		/**
		 * 5：在完成任务之后，判断流程是否结束
   			如果流程结束了，更新请假单表的状态从1变成2（审核中-->审核完成）
		 */
		ProcessInstance pi = runtimeService.createProcessInstanceQuery()//
						.processInstanceId(processInstanceId)//使用流程实例ID查询
						.singleResult();
		//流程结束了
		if(pi==null){
			//更新请假单表的状态从1变成2（审核中-->审核完成）
			LeaveBill bill = leaveBillDao.findLeaveBillById(id);
			bill.setState(2);
			//submit
		}
	}
	
	/**获取批注信息，传递的是当前任务ID，获取历史任务ID对应的批注*/
	
	public List<Comment> findCommentByTaskId(String taskId) {
		List<Comment> list = new ArrayList<Comment>();
		//使用当前的任务ID，查询当前流程对应的历史任务ID
		//使用当前任务ID，获取当前任务对象
		Task task = taskService.createTaskQuery()//
				.taskId(taskId)//使用任务ID查询
				.singleResult();
		//获取流程实例ID
		String processInstanceId = task.getProcessInstanceId();
//		//使用流程实例ID，查询历史任务，获取历史任务对应的每个任务ID
//		List<HistoricTaskInstance> htiList = historyService.createHistoricTaskInstanceQuery()//历史任务表查询
//						.processInstanceId(processInstanceId)//使用流程实例ID查询
//						.list();
//		//遍历集合，获取每个任务ID
//		if(htiList!=null && htiList.size()>0){
//			for(HistoricTaskInstance hti:htiList){
//				//任务ID
//				String htaskId = hti.getId();
//				//获取批注信息
//				List<Comment> taskList = taskService.getTaskComments(htaskId);//对用历史完成后的任务ID
//				list.addAll(taskList);
//			}
//		}
		list = taskService.getProcessInstanceComments(processInstanceId);
		return list;
	}
	
	/**使用请假单ID，查询历史批注信息*/
	
	public List<Comment> findCommentByLeaveBillId(Long id) {
		//使用请假单ID，查询请假单对象
		LeaveBill leaveBill = leaveBillDao.findLeaveBillById(id);
		//获取对象的名称
		String objectName = leaveBill.getClass().getSimpleName();
		//组织流程表中的字段中的值
		String objId = objectName+"."+id;
		
		/**1:使用历史的流程实例查询，返回历史的流程实例对象，获取流程实例ID*/
//		HistoricProcessInstance hpi = historyService.createHistoricProcessInstanceQuery()//对应历史的流程实例表
//						.processInstanceBusinessKey(objId)//使用BusinessKey字段查询
//						.singleResult();
//		//流程实例ID
//		String processInstanceId = hpi.getId();
		/**2:使用历史的流程变量查询，返回历史的流程变量的对象，获取流程实例ID*/
		HistoricVariableInstance hvi = historyService.createHistoricVariableInstanceQuery()//对应历史的流程变量表
						.variableValueEquals("objId", objId)//使用流程变量的名称和流程变量的值查询
						.singleResult();
		//流程实例ID
		String processInstanceId = hvi.getProcessInstanceId();
		List<Comment> list = taskService.getProcessInstanceComments(processInstanceId);
		return list;
	}
	
	/**1：获取任务ID，获取任务对象，使用任务对象获取流程定义ID，查询流程定义对象*/
	
	public ProcessDefinition findProcessDefinitionByTaskId(String taskId) {
		//使用任务ID，查询任务对象
		Task task = taskService.createTaskQuery()//
					.taskId(taskId)//使用任务ID查询
					.singleResult();
		//获取流程定义ID
		String processDefinitionId = task.getProcessDefinitionId();
		//查询流程定义的对象
		ProcessDefinition pd = repositoryService.createProcessDefinitionQuery()//创建流程定义查询对象，对应表act_re_procdef 
					.processDefinitionId(processDefinitionId)//使用流程定义ID查询
					.singleResult();
		return pd;
	}
	
	/**
	 * 二：查看当前活动，获取当期活动对应的坐标x,y,width,height，将4个值存放到Map<String,Object>中
		 map集合的key：表示坐标x,y,width,height
		 map集合的value：表示坐标对应的值
	 */
	
	public Map<String, Object> findCoordingByTask(String taskId) {
		//存放坐标
		Map<String, Object> map = new HashMap<String,Object>();
		//使用任务ID，查询任务对象
		Task task = taskService.createTaskQuery()//
					.taskId(taskId)//使用任务ID查询
					.singleResult();
		//获取流程定义的ID
		String processDefinitionId = task.getProcessDefinitionId();
		//获取流程定义的实体对象（对应.bpmn文件中的数据）
		ProcessDefinitionEntity processDefinitionEntity = (ProcessDefinitionEntity)repositoryService.getProcessDefinition(processDefinitionId);
		//流程实例ID
		String processInstanceId = task.getProcessInstanceId();
		//使用流程实例ID，查询正在执行的执行对象表，获取当前活动对应的流程实例对象
		ProcessInstance pi = runtimeService.createProcessInstanceQuery()//创建流程实例查询
					.processInstanceId(processInstanceId)//使用流程实例ID查询
					.singleResult();
		//获取当前活动的ID
		String activityId = pi.getActivityId();
		//获取当前活动对象
		ActivityImpl activityImpl = processDefinitionEntity.findActivity(activityId);//活动ID
		//获取坐标
		map.put("x", activityImpl.getX());
		map.put("y", activityImpl.getY());
		map.put("width", activityImpl.getWidth());
		map.put("height", activityImpl.getHeight());
		return map;
	}
}
