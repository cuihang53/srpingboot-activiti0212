package com.activiti.web.controller.base;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RestController;

import com.activiti.common.Constants;
import com.activiti.entity.SysUser;

@RestController
public class BaseRestController {
	public final static String HEADER_REAL_IP="X-Real-IP";
	public final static String HEADER_USER_NAME="X-Request-User";
	public final static String HEADER_USER_ID="X-Request-User-ID";
	public final static String HEADER_FORWARDED_FOR="X-Forwarded-For";
	
	protected HttpServletRequest request;
	protected HttpServletResponse response;
	
	@ModelAttribute
	public void setReqAndRes(HttpServletRequest request,HttpServletResponse response) {
		this.request = request;
		this.response = response;
	}
	
	/**
     * 获取当前用户
     * @param request
     * @return
     */
    public String getCurrentUser(HttpServletRequest request){
    	String username=request.getHeader(HEADER_USER_NAME);
		User userDetails = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if(!StringUtils.isEmpty(username)) {
			return username;
		}else if(userDetails != null){
			return userDetails.getUsername();
		}else {
			HttpSession session = request.getSession(true);
			Object obj=session.getAttribute(Constants.CURRENT_USER);
			if(!StringUtils.isEmpty(obj)) {
				SysUser user=(SysUser)obj;
				return user.getAccount();
			}
			return null;
		}
		
	}
	
	public static String getIp(HttpServletRequest request) {
        String ip = request.getHeader(HEADER_FORWARDED_FOR);
        if(!StringUtils.isEmpty(ip) && !"unKnown".equalsIgnoreCase(ip)){
            //多次反向代理后会有多个ip值，第一个ip才是真实ip
            int index = ip.indexOf(",");
            if(index != -1){
                return ip.substring(0,index);
            }else{
                return ip;
            }
        }
        ip = request.getHeader(HEADER_REAL_IP);
        if(!StringUtils.isEmpty(ip) && !"unKnown".equalsIgnoreCase(ip)){
            return ip;
        }
        return request.getRemoteAddr();
    }

  

}
