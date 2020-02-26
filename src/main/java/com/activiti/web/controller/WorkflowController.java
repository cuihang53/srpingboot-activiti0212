package com.activiti.web.controller;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.activiti.common.DeploymentResponse;
import com.activiti.common.JsonResult;
import com.activiti.common.ResponseCode;
import com.activiti.dto.WorkflowDto;
import com.activiti.entity.WorkflowBean;
import com.activiti.utils.JsonUtil;
import com.activiti.web.controller.base.BaseRestController;
import com.activiti.web.service.WorkflowService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;


@CrossOrigin
@Api(value = "Workflow 控制中心")
@RestController
@RequestMapping("/wrokflow")
public class WorkflowController extends BaseRestController{

	@Autowired
	private WorkflowService workflowService;
	
	@Autowired
	private ProcessEngine processEngine;
	
	/**
	 * 部署对象列表
	 * @return
	 */
	@ApiOperation(value = "部署管理首页显示")
	@RequestMapping(value="/deployHome",method = RequestMethod.GET)
	public Object deployHome(){
		//1:查询部署对象信息，对应表（act_re_deployment）
		List<Deployment> depList = workflowService.findDeploymentList();
		List<DeploymentResponse> list = new ArrayList<>();
		for(Deployment d : depList){
			list.add(new DeploymentResponse(d));
			System.out.println("DeploymentId:"+d.getId() + " DeploymentName" + d.getName() 
				+ "DeploymentTenantId:" + d.getTenantId() +" Class:" +d.getClass() + " Category:" + d.getCategory());
		}
//		//2:查询流程定义的信息，对应表（act_re_procdef）
//		List<ProcessDefinition> pdList = workflowService.findProcessDefinitionList();
		
//		for(ProcessDefinition p : pdList){
//			System.out.println("id:"+p.getId()+" name:"+p.getName()+" key:" + p.getKey()+" version:"
//		+p.getVersion()+" resource:" + p.getDiagramResourceName());
//		}
		Map<String, Object> map= new HashMap<>();
		map.put("depList", list);
//		map.put("pdList", pdList);
		
		JsonResult result = new JsonResult();
		result.setContent(map);
		
		result.setStatus(HttpStatus.OK.value());
		result.setCode(ResponseCode.SUCCESS.value());
//		return JsonUtil.obj2String(result);
		return map;
	}
	
	/**
	 * 发布流程
	 * @return
	 */
	@ApiOperation(value = "部署bpmn/png对象")
	@RequestMapping(value="/newdeploy",method = RequestMethod.GET)
	public String newdeploy(){
		    RepositoryService repositoryService = processEngine.getRepositoryService();
		    //3.部署对象
		    Deployment deploy = repositoryService.createDeployment()
		        //添加bpmn资源
		         .addClasspathResource("processes/scriptTest.bpmn")
		        //添加图片文件资源
		         .addClasspathResource("processes/scriptTest.png")
		         .name("请假申请流程0218")
		         .deploy();
		    //4.输出部署的一些信息
		    System.out.println("deployId:" + deploy.getId() + "   deployName:" + deploy.getName());
		return "ok";
	}
	
	
	/****
	 * 发布zip流程
	 * @param workflowBean
	 * @return
	 */
	@ApiOperation(value = "部署Zip对象")
	@RequestMapping(value="/newdeployZip",method = RequestMethod.GET)
	public String newdeployZip(@RequestBody WorkflowBean workflowBean){
		File file = workflowBean.getFile();
		String filename = workflowBean.getFilename();
		workflowService.saveNewDeploye(file,filename);
		return "ok";
	}
	
	
	/**
	 * 删除部署信息
	 */
	@RequestMapping(value="/delDeployment",method = RequestMethod.POST)
	public String delDeployment(@RequestBody WorkflowBean workflowBean){
		//1：获取部署对象ID
		String deploymentId = workflowBean.getDeploymentId();
		//2：使用部署对象ID，删除流程定义
		workflowService.deleteProcessDefinitionByDeploymentId(deploymentId);
		return "list";
	}

	
//	/**
//	 * 查看流程图
//	 * @throws Exception 
//	 */
	@ApiOperation(value = "查看流程图")
	@RequestMapping(value="/viewImage/{deploymentId}/{imageName}",method = RequestMethod.GET)
	public String viewImage(@PathVariable("deploymentId") String deploymentId, @PathVariable("imageName") String imageName) throws Exception{
		//1：获取页面传递的部署对象ID和资源图片名称
		//部署对象ID
//		String deploymentId = workflowBean.getDeploymentId();
		//资源图片名称
//		String imageName = workflowBean.getImageName();
		//2：获取资源文件表（act_ge_bytearray）中资源图片输入流InputStream
		InputStream in = workflowService.findImageInputStream(deploymentId,imageName);
		//3：从response对象获取输出流
		OutputStream out = response.getOutputStream();
		//4：将输入流中的数据读取出来，写到输出流中
		for(int b=-1;(b=in.read())!=-1;){
			out.write(b);
		}
		out.close();
		in.close();
		//将图写到页面上，用输出流写
		return null;
	}

	/**
	 * 任务管理首页显示
	 * @return
	 * @throws JSONException 
	 * name:当前登录人
	 */
	@ApiOperation(value = "登陆人的代办任务")
	@RequestMapping(value="/listTask/offset/{offset}/limit/{limit}",method = RequestMethod.GET)
	public String listTask(@PathVariable("offset") Integer offset, @PathVariable("limit") Integer limit) {
		String result = workflowService.findTaskListByName("zhangsan",offset,limit);
		return result;
	}

	
	@ApiOperation(value = "查看任务历史列表（历史任务包含当前正在处理节点信息）")
	@RequestMapping(value="/hisTaskList/pagenum/{pagenum}/pagesize/{pagesize}",method = RequestMethod.GET)
	public String hisTaskList(@PathVariable("pagenum") Integer pageNum, @PathVariable("pagesize") Integer pageSize) throws IOException {
		String result = workflowService.taskHisList(pageNum,pageSize);
		return result;
	}
	
	
	@RequestMapping(value="/startProcess",method = RequestMethod.POST)
	@ApiOperation(value = "新增任务实例")
	public String startProcess(@ApiParam(required = true) @RequestBody WorkflowDto workflow) {
		JsonResult result = new JsonResult();
		try{
			workflowService.saveStartProcess(workflow.getVariables(), workflow.getBusinessId(), workflow.getInstanceKey());
			result.setStatus(HttpStatus.OK.value());
			result.setCode(ResponseCode.SUCCESS.value());
		}catch(Exception e){
			result.setStatus(HttpStatus.BAD_REQUEST.value());
			result.setCode(ResponseCode.ERROR.value());
			result.setErrMsg(e.toString());
		}
		return JsonUtil.obj2String(result);
	}
	
	
	@RequestMapping(value="/taskComplete",method = RequestMethod.POST)
	@ApiOperation(value = "处理待办任务")
	public String taskComplete(@ApiParam(required = true) @RequestBody WorkflowDto workflow) {
		JsonResult result = new JsonResult();
		try{
			workflowService.taskComplete(workflow.getTaskId(), workflow.getVariables());
			result.setStatus(HttpStatus.OK.value());
			result.setCode(ResponseCode.SUCCESS.value());
		}catch(Exception e){
			result.setStatus(HttpStatus.BAD_REQUEST.value());
			result.setCode(ResponseCode.ERROR.value());
			result.setErrMsg(e.toString());
		}
		return JsonUtil.obj2String(result);
	}
	
	
	
	/**
	 * 查看当前流程图（查看当前活动节点，并使用红色的框标注）
	 */
	@RequestMapping(value="/viewCurrentImage/task/{taskId}",method = RequestMethod.GET)
	public String viewCurrentImage(@PathVariable("taskId") String taskId){
		//任务ID
//		String taskId = workflowBean.getTaskId();
		/**一：查看流程图*/
		//1：获取任务ID，获取任务对象，使用任务对象获取流程定义ID，查询流程定义对象
		ProcessDefinition pd = workflowService.findProcessDefinitionByTaskId(taskId);
		
		/**二：查看当前活动，获取当期活动对应的坐标x,y,width,height，将4个值存放到Map<String,Object>中*/
		Map<String, Object> map = workflowService.findCoordingByTask(taskId);
		Map<String, Object> cordingMap = new HashMap<String, Object>();
		cordingMap.put("cording", map);
		cordingMap.put("deploymentId", pd.getDeploymentId());
		cordingMap.put("imageName", pd.getDiagramResourceName());
		
		JsonResult result = new JsonResult();
		result.setContent(cordingMap);
		
		result.setStatus(HttpStatus.OK.value());
		result.setCode(ResponseCode.SUCCESS.value());
		
		return JsonUtil.obj2String(result);
	}
	
	
	@RequestMapping(value="/test",method = RequestMethod.GET)
	@ApiOperation(value = "远程任务")
	public String test() {
		
		return "zhangsan";
	}
	
	
//	@ApiOperation(value = "登陆人的任务列表")
//	@RequestMapping(value="/tasklist/",method = RequestMethod.GET)
//	public String listTask() {
//		//1：从Session中获取当前用户名
//		String name = "cuihang";
//		//2：使用当前用户名查询正在执行的任务表，获取当前任务的集合List<Task>
//		List<Task> list = workflowService.findTaskListByName(name); 
//		for(Task t : list){
//			System.out.println("teskId:"+t.getId() + " taskName:"+t.getName() + " createTime:"+t.getCreateTime() + " Assignee:"+t.getAssignee());
//		}
//		
//		WorkflowBean workflowBean = new WorkflowBean();
//		workflowBean.setTaskId(list.get(0).getId());
//		workflowBean.setId(1L);
//		workflowBean.setComment("test");
//		
//		workflowService.saveSubmitTask(workflowBean);
//		return "listTask  ok";
//	}
	
	
//	
//	/**
//	 * 打开任务表单
//	 */
////	public String viewTaskForm(WorkflowBean workflowBean){
////		//任务ID
////		String taskId = workflowBean.getTaskId();
////		//获取任务表单中任务节点的url连接
////		String url = workflowService.findTaskFormKeyByTaskId(taskId);
////		url += "?taskId="+taskId;
////		ValueContext.putValueContext("url", url);
////		return "viewTaskForm";
////	}
//	
//	// 准备表单数据
////	public String audit(WorkflowBean workflowBean){
////		//获取任务ID
////		String taskId = workflowBean.getTaskId();
////		/**一：使用任务ID，查找请假单ID，从而获取请假单信息*/
////		LeaveBill leaveBill = workflowService.findLeaveBillByTaskId(taskId);
////		ValueContext.putValueStack(leaveBill);
////		/**二：已知任务ID，查询ProcessDefinitionEntiy对象，从而获取当前任务完成之后的连线名称，并放置到List<String>集合中*/
////		List<String> outcomeList = workflowService.findOutComeListByTaskId(taskId);
////		ValueContext.putValueContext("outcomeList", outcomeList);
////		/**三：查询所有历史审核人的审核信息，帮助当前人完成审核，返回List<Comment>*/
////		List<Comment> commentList = workflowService.findCommentByTaskId(taskId);
////		ValueContext.putValueContext("commentList", commentList);
////		return "taskForm";
////	}
//	
//	/**
//	 * 提交任务
//	 */
//	@RequestMapping(value="/submitTask",method = RequestMethod.POST)
//	public String submitTask(@RequestBody WorkflowBean workflowBean){
//		workflowService.saveSubmitTask(workflowBean);
//		return "listTask";
//	}

//	
//	// 查看历史的批注信息
//	@RequestMapping(value="/viewHisComment",method = RequestMethod.POST)
//	public String viewHisComment(@RequestBody WorkflowBean workflowBean){
//		//获取清单ID
////		Long id = workflowBean.getId();
////		//1：使用请假单ID，查询请假单对象，将对象放置到栈顶，支持表单回显
////		LeaveBill leaveBill = leaveBillService.findLeaveBillById(id);
////		ValueContext.putValueStack(leaveBill);
////		//2：使用请假单ID，查询历史的批注信息
////		List<Comment> commentList = workflowService.findCommentByLeaveBillId(id);
////		ValueContext.putValueContext("commentList", commentList);
//		return "viewHisComment";
//	}
}
