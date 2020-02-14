package com.activiti.web.controller.base;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BaseRestController {
	
	protected HttpServletRequest request;
	protected HttpServletResponse response;
	
	@ModelAttribute
	public void setReqAndRes(HttpServletRequest request,HttpServletResponse response) {
		this.request = request;
		this.response = response;
	}
	
	
	

}
