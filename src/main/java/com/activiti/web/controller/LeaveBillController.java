package com.activiti.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.activiti.common.ApiVersion;
import com.activiti.common.JsonResult;
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
@RequestMapping("leaveBill")
public class LeaveBillController extends BaseRestController{
	
	@Autowired
	LeaveBillService leaveBillService;
	
	@RequestMapping(value="/add",method = RequestMethod.POST)
	@ApiOperation(value = "新增请假单")
	public String insert(@ApiParam(required = true) @RequestBody LeaveBillDto leaveBill) {
		JsonResult<LeaveBill> result = new JsonResult<LeaveBill>();
		try{
			LeaveBill leave = leaveBillService.insert(leaveBill);
			result.setContent(leave);
			result.setStatus(HttpStatus.OK.value());
			result.setCode(HttpStatus.OK.getReasonPhrase());
		}catch(Exception e){
			result.setStatus(HttpStatus.BAD_REQUEST.value());
			result.setCode(HttpStatus.BAD_REQUEST.getReasonPhrase());
			result.setErrMsg(e.toString());
		}
		return JsonUtil.obj2String(result);
	}
	
	
	
	/**
     * version
     * @return
     */
    @GetMapping("/test/{version}")
    @ApiVersion(2.0)
    public String searchTargetImage() {
        return "Hello! Welcome to Version2";
    }
 
 
 
    /**
 	* @param attribute
     * @return
     */
    @GetMapping("/test/{version}")
    @ApiVersion(1.0)
    public String searchTargetImage1() {
        return "Hello! Welcome to Version1";
    }
}
