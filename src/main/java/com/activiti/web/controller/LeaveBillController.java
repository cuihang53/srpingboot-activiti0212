package com.activiti.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.activiti.common.JsonResult;
import com.activiti.common.ResponseCode;
import com.activiti.dto.LeaveBillDto;
import com.activiti.entity.LeaveBill;
import com.activiti.utils.JsonUtil;
import com.activiti.web.controller.base.BaseRestController;
import com.activiti.web.service.LeaveBillService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Api(value = "请假申请")
@RestController
public class LeaveBillController extends BaseRestController{
	
	@Autowired
	LeaveBillService leaveBillService;
	
	@RequestMapping(value="/add",method = RequestMethod.POST)
	@ApiOperation(value = "新增请假单")
	public String insert(@ApiParam(required = true) @RequestBody LeaveBillDto leaveBill) {
		JsonResult result = new JsonResult();
		try{
			LeaveBill leave = leaveBillService.insert(leaveBill);
			result.setContent(leave);
			result.setStatus(HttpStatus.OK.value());
			result.setCode(ResponseCode.SUCCESS.value());
		}catch(Exception e){
			result.setStatus(HttpStatus.BAD_REQUEST.value());
			result.setCode(ResponseCode.ERROR.value());
			result.setErrMsg(e.toString());
		}
		return JsonUtil.obj2String(result);
	}
	
}
