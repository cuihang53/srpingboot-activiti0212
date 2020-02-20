package com.activiti.webservice;


import javax.jws.WebService;
 
@WebService(name = "DemoService", // 暴露服务名称
    targetNamespace = "http://webservice.activiti.com"// 命名空间,一般是接口的包名倒序
)
public interface DemoService {
 
    public String sayHello(String user);
 
}
