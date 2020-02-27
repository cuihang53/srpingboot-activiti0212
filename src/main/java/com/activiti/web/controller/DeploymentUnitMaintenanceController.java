package com.activiti.web.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.activiti.common.JsonResult;
import com.activiti.dto.DeploymentUnitMaintenanceDto;
import com.activiti.entity.DeploymentUnitMaintenance;
import com.activiti.utils.JsonUtil;
import com.activiti.web.controller.base.BaseRestController;
import com.activiti.web.service.DeploymentUnitMaintenanceService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
@Api(value = "部署单元维护")
@CrossOrigin
@RestController
@RequestMapping("/deploymentunit")
public class DeploymentUnitMaintenanceController extends BaseRestController {
	
	@Autowired
	private DeploymentUnitMaintenanceService deploymentUnitMaintenanceService;
	
	@RequestMapping(value="/add",method = RequestMethod.POST)
	@ApiOperation(value = "新增部署单元维护")
	public String insert(@ApiParam(required = true)@RequestBody(required=false) DeploymentUnitMaintenanceDto deploymentUnitMaintenance) {
		JsonResult result = new JsonResult();
		try{
			DeploymentUnitMaintenance deploymentUnitMaintenance1 = 	deploymentUnitMaintenanceService.insert(deploymentUnitMaintenance);
			result.setContent(deploymentUnitMaintenance1);
			result.setStatus(HttpStatus.OK.value());
			result.setCode(HttpStatus.OK.getReasonPhrase());
		}catch(Exception e){
			result.setStatus(HttpStatus.BAD_REQUEST.value());
			result.setCode(HttpStatus.BAD_REQUEST.getReasonPhrase());
			result.setErrMsg(e.toString());
		}
		return JsonUtil.obj2String(result);
	}
	/**修改*/
	@RequestMapping(value="/updata",method = RequestMethod.POST)
	@ApiOperation(value = "更新部署单元维护")
	public String updata(@ApiParam(required = true) @RequestBody DeploymentUnitMaintenanceDto entity) {
		JsonResult result = new JsonResult();
		try{
			DeploymentUnitMaintenance deploymentUnitMaintenance1 = deploymentUnitMaintenanceService.updata(entity);
			result.setContent(deploymentUnitMaintenance1);
			
			result.setStatus(HttpStatus.OK.value());
			result.setCode(HttpStatus.OK.getReasonPhrase());
		}catch(Exception e){
			result.setStatus(HttpStatus.BAD_REQUEST.value());
			result.setCode(HttpStatus.BAD_REQUEST.getReasonPhrase());
			result.setErrMsg(e.toString());
		}
		return JsonUtil.obj2String(result);
		
	}
	
//	/**修改*/
//	@RequestMapping(value="/delete",method = RequestMethod.POST)
//	@ApiOperation(value = "删除部署单元维护")
//	public String delete(@ApiParam(required = true) @RequestBody DeploymentUnitMaintenanceDto entity) {
//		JsonResult result = new JsonResult();
//		try{
//			DeploymentUnitMaintenance deploymentUnitMaintenance1 = deploymentUnitMaintenanceService.delete(entity);
//			result.setContent(deploymentUnitMaintenance1);
//			
//			result.setStatus(HttpStatus.OK.value());
//			result.setCode(ResponseCode.SUCCESS.value());
//		}catch(Exception e){
//			result.setStatus(HttpStatus.BAD_REQUEST.value());
//			result.setCode(ResponseCode.ERROR.value());
//			result.setErrMsg(e.toString());
//		}
//		return JsonUtil.obj2String(result);	
//	}
	
	
	
//	/**查询*/
//	@RequestMapping(value="/findAll",method = RequestMethod.POST)
//	@ApiOperation(value = "查询部署单元维护")
//	public String findAll(@ApiParam(required = true) @RequestBody DeploymentUnitMaintenanceDto entity) {
//		JsonResult result = new JsonResult();
//		try{
//			DeploymentUnitMaintenance deploymentUnitMaintenance1 = deploymentUnitMaintenanceService.findAll(entity);
//			result.setContent(deploymentUnitMaintenance1);
//			
//			result.setStatus(HttpStatus.OK.value());
//			result.setCode(ResponseCode.SUCCESS.value());
//		}catch(Exception e){
//			result.setStatus(HttpStatus.BAD_REQUEST.value());
//			result.setCode(ResponseCode.ERROR.value());
//			result.setErrMsg(e.toString());
//		}
//		return JsonUtil.obj2String(result);	
//	}
	
//	@RequestMapping("/query")
//	@ApiOperation(value = "分页部署单元维护")
//	public EasyUIResult findDeploymentUnitMaintenanceByPage(Integer page,Integer rows){
//		
//		return deploymentUnitMaintenanceService.findDeploymentUnitMaintenanceByPage(page,rows);
//	}
}
