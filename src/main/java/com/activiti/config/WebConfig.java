package com.activiti.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import com.activiti.interceptor.CustomRequestMappingHandler;

/***
 * 接口版本连接器 web配置
 * @author CUIHANG
 *
 */
@Configuration
public class WebConfig extends WebMvcConfigurationSupport {
    @Override
    public RequestMappingHandlerMapping requestMappingHandlerMapping() {
    	CustomRequestMappingHandler handlerMapping = new CustomRequestMappingHandler();
        handlerMapping.setOrder(0);
        handlerMapping.setInterceptors(getInterceptors());
        return handlerMapping;
    }
}