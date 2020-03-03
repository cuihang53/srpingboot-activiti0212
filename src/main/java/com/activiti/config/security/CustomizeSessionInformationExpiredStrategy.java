package com.activiti.config.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.web.session.SessionInformationExpiredEvent;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;
import org.springframework.stereotype.Component;

import com.activiti.common.JsonResult;
import com.activiti.common.ResultCode;
import com.activiti.utils.JsonUtil;

/**
 * @Author: Cuihang
 * @Description: 会话信息过期策略
 */
@Component
public class CustomizeSessionInformationExpiredStrategy implements SessionInformationExpiredStrategy {
    @Override
    public void onExpiredSessionDetected(SessionInformationExpiredEvent sessionInformationExpiredEvent) throws IOException, ServletException {
//    	Map<String,Object> map = new HashMap<>();
//        map.put("code",0);
//        map.put("msg","已经另一台机器登录，您被迫下线。" + event.getSessionInformation().getLastRequest());
    	JsonResult<?> result = new JsonResult<>(false,ResultCode.USER_ACCOUNT_USE_BY_OTHERS);
        HttpServletResponse httpServletResponse = sessionInformationExpiredEvent.getResponse();
        httpServletResponse.setContentType("text/json;charset=utf-8");
        httpServletResponse.getWriter().write(JsonUtil.obj2String(result));
    }
}
