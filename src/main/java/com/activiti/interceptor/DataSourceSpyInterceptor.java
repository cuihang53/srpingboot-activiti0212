//package com.activiti.interceptor;
//
//import org.aopalliance.intercept.MethodInvocation;
//
//import com.mysql.jdbc.Connection;
//
//import net.sf.log4jdbc.ConnectionSpy;
//import net.sf.log4jdbc.DriverSpy;
//import net.sf.log4jdbc.SpyLogFactory;
//
//public class DataSourceSpyInterceptor extends MethodInterceptor{
//
//	private  RdbmsSpecifics rdbmsSpecifics =  null ;
//    
//    private  RdbmsSpecifics getRdbmsSpecifics(Connection conn) {
//        if (rdbmsSpecifics ==  null ) {
//            rdbmsSpecifics = DriverSpy.getRdbmsSpecifics(conn);
//        }
//        return  rdbmsSpecifics;
//    }
//    
//    public  Object invoke(MethodInvocation invocation)  throws  Throwable {
//        Object result = invocation.proceed();
//        if (SpyLogFactory.getSpyLogDelegator().isJdbcLoggingEnabled()) {
//            if (result  instanceof  Connection) {
//                Connection conn = (Connection)result;
//                return  new  ConnectionSpy(conn,getRdbmsSpecifics(conn));
//            }
//        }
//        return  result;
//    }
//}
