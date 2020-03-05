package com.activiti.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.web.cors.CorsUtils;

import com.activiti.config.security.CustomizeAbstractSecurityInterceptor;
import com.activiti.config.security.CustomizeAccessDecisionManager;
import com.activiti.config.security.CustomizeAccessDeniedHandler;
import com.activiti.config.security.CustomizeAuthenticationEntryPoint;
import com.activiti.config.security.CustomizeAuthenticationFailureHandler;
import com.activiti.config.security.CustomizeAuthenticationSuccessHandler;
import com.activiti.config.security.CustomizeFilterInvocationSecurityMetadataSource;
import com.activiti.config.security.CustomizeLogoutSuccessHandler;
import com.activiti.config.security.CustomizeSessionInformationExpiredStrategy;
//import com.activiti.config.security.TokenLoginFilter;
//import com.activiti.config.security.TokenLogoutHandler;
//import com.activiti.config.security.TokenManager;
import com.activiti.web.service.CustomizeUserDetailService;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	public static void main(String[] args) {
		System.out.println( new BCryptPasswordEncoder().encode("123456"));
	}

  //登录成功处理逻辑
    @Autowired
    private CustomizeAuthenticationSuccessHandler authenticationSuccessHandler;

    //登录失败处理逻辑
    @Autowired
    private CustomizeAuthenticationFailureHandler authenticationFailureHandler;
    
    //登出成功处理逻辑
    @Autowired
    private CustomizeLogoutSuccessHandler logoutSuccessHandler;
    
    //匿名用户访问无权限资源时的异常
    @Autowired
    private CustomizeAuthenticationEntryPoint authenticationEntryPoint;
	
  //会话失效(账号被挤下线)处理逻辑
    @Autowired
    private CustomizeSessionInformationExpiredStrategy sessionInformationExpiredStrategy;
    
//	  权限拒绝处理逻辑
    @Autowired
    private CustomizeAccessDeniedHandler accessDeniedHandler;

    //访问决策管理器
    @Autowired
    private CustomizeAccessDecisionManager accessDecisionManager;
    
    //实现权限拦截
    @Autowired
    private CustomizeFilterInvocationSecurityMetadataSource securityMetadataSource;

    @Autowired
    private CustomizeAbstractSecurityInterceptor securityInterceptor;

    
//    @Autowired
//    private TokenManager tokenManager;
    
    //获取用户账号密码及权限信息
	@Bean
    public UserDetailsService userDetailsService() {
        return new CustomizeUserDetailService();
    }
	
	// 设置默认的加密方式（强hash方式加密）
	@Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
	
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //配置认证方式等
        auth.userDetailsService(userDetailsService());
    }
    
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //http相关的配置，包括登入登出、异常处理、会话管理等
    	http.cors().and().csrf().disable().authorizeRequests().requestMatchers(CorsUtils::isPreFlightRequest).permitAll();
    	
    	http.authorizeRequests()
    	
//    	.antMatchers("/wrokflow/test").hasAuthority("query_user")
    	//查询当前链接需要什么权限，再看看当前登录用户是否有权限
    	.withObjectPostProcessor(new ObjectPostProcessor<FilterSecurityInterceptor>() {
              @Override
              public <O extends FilterSecurityInterceptor> O postProcess(O o) {
                  o.setAccessDecisionManager(accessDecisionManager);//决策管理器
                  o.setSecurityMetadataSource(securityMetadataSource);//安全元数据源
                  return o;
              }
         })
    	
    	.and().exceptionHandling().
	        accessDeniedHandler(accessDeniedHandler).//权限拒绝处理逻辑
	        authenticationEntryPoint(authenticationEntryPoint)//未登录处理
        
        
      //登入
        .and().formLogin().
        	permitAll().//允许所有用户
        	successHandler(authenticationSuccessHandler).//登录成功处理逻辑
        	failureHandler(authenticationFailureHandler)//登录失败处理逻辑
        
        .and().logout().
        	permitAll().//允许所有用户
        	logoutSuccessHandler(logoutSuccessHandler).//登出成功处理逻辑
        	deleteCookies("JSESSIONID")//登出之后删除cookie
    	//token 认证
//        	.addLogoutHandler(new TokenLogoutHandler(tokenManager)).and()
//            .addFilter(new TokenLoginFilter(authenticationManager(), tokenManager))
//            .addFilter(new TokenAuthenticationFilter(authenticationManager(), tokenManager)).httpBasic();
        	
//		一个账户只能登录一个人， //否则进入   CustomizeSessionInformationExpiredStrategy
//      我电脑上用postman登录
//      我电脑上请求资源接口，可以请求，如下左图
//      在旁边电脑上再登录一次刚刚的账号
//      在我电脑上再次请求资源接口，提示"账号下线"
    	.and().sessionManagement().
         maximumSessions(1).
         expiredSessionStrategy(sessionInformationExpiredStrategy);
    	
    	
    	
    	http.addFilterBefore(securityInterceptor, FilterSecurityInterceptor.class);
        
//        super.configure(http);
    }
}
