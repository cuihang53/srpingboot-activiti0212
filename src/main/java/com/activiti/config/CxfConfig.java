//package com.activiti.config;
//
//import org.apache.cxf.Bus;
//import org.apache.cxf.bus.spring.SpringBus;
//import org.apache.cxf.jaxws.EndpointImpl;
//import org.apache.cxf.transport.servlet.CXFServlet;
//import org.springframework.boot.web.servlet.ServletRegistrationBean;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import com.activiti.webservice.DemoService;
//import com.activiti.webservice.DemoServiceImpl;
//
//import javax.xml.ws.Endpoint;
// 
////@Configuration
//public class CxfConfig {
// 
//    @Bean
//    public ServletRegistrationBean<CXFServlet> dispatcherServlet() {
//        return new ServletRegistrationBean<CXFServlet>(new CXFServlet(),"/webservice/demo/*");
//    }
// 
//    @Bean(name = Bus.DEFAULT_BUS_ID)
//    public SpringBus springBus() {
//        return new SpringBus();
//    }
// 
//    @Bean
//    public DemoService demoService() {
//        return new DemoServiceImpl();
//    }
// 
//    @Bean
//    public Endpoint endpoint() {
//        EndpointImpl endpoint = new EndpointImpl(springBus(), demoService());
//        endpoint.publish("/test");
//        return endpoint;
//    }
// 
//}
