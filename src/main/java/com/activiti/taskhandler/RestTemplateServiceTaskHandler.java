package com.activiti.taskhandler;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;
import org.springframework.web.client.RestTemplate;

import com.activiti.utils.SpringUtil;

public class RestTemplateServiceTaskHandler implements JavaDelegate{
	
	@Override
	public void execute(DelegateExecution execution) throws Exception {
		RestTemplate restTemplate = SpringUtil.getBean("restTemplate");
        String url="http://127.0.0.1:8081/user/test";
        
        String result = restTemplate.getForObject(url, String.class);
//        ResponseEntity<String> results = restTemplate.exchange(url, HttpMethod.GET, null, String.class);
//        String json = results.getBody();
        System.out.println(result);
		
	}

}
