package com.activiti.webservice;

import javax.jws.WebService;
 
@WebService(serviceName = "DemoService", // 与接口中指定的name一致
        targetNamespace = "http://webservice.activiti.com", // 与接口中的命名空间一致,一般是接口的包名倒
        endpointInterface = "com.activiti.webservice.DemoService"// 接口地址
)
public class DemoServiceImpl implements DemoService {
 
    @Override
    public String sayHello(String user) {
        return "zhangsan";
    }
 
}
