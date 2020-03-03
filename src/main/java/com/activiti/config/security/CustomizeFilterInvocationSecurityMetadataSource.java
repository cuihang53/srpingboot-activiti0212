package com.activiti.config.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import com.activiti.entity.SysPermission;
import com.activiti.web.dao.ISysPerminssionDao;

/**
 */
@Component
public class CustomizeFilterInvocationSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {
    AntPathMatcher antPathMatcher = new AntPathMatcher();
    
    @Autowired
    ISysPerminssionDao sysPermissionService;
    
    //一次将数据中所有权限数据查出来
    private static HashMap<String, Collection<ConfigAttribute>> map =null;
    
    public void loadResourceDefine(){
        map = new HashMap<>();
        Collection<ConfigAttribute> array;
        ConfigAttribute cfg;
        //去数据库查询 使用dao层。 你使用自己的即可
        List<SysPermission> permissions = sysPermissionService.findAll();
        for(SysPermission permission : permissions) {
            array = new ArrayList<>();
            //下面你可以添加你想要比较的信息过去。 注意的是，需要在用户登录时存储的权限信息一致
            cfg = new SecurityConfig(permission.getPermissionCode());
            //此处添加了资源菜单的名字，例如请求方法到ConfigAttribute的集合中去。此处添加的信息将会作为MyAccessDecisionManager类的decide的第三个参数。
           
            array.add(cfg);
            //用权限的getUrl() 作为map的key，用ConfigAttribute的集合作为 value，
            map.put(permission.getUrl(), array);
        }

    }
    
    
    
    @Override
    public Collection<ConfigAttribute> getAttributes(Object o) throws IllegalArgumentException {
//    	FilterInvocation filterInvocation = (FilterInvocation) o;
        //获取请求地址
//        String requestUrl = ((FilterInvocation) o).getRequestUrl();
//        String requestFullUrl = ((FilterInvocation) o).getFullRequestUrl();
        
        //若是静态资源 不做拦截  下面写了单独判断静态资源方法
//        if (isMatcherAllowedRequest(filterInvocation)) {
//            System.out.println("我没有被拦截"+requestFullUrl);
//           return null;
//        }
        if(map == null){
        	loadResourceDefine();
        }
        
//        AntPathRequestMatcher   例如  url  /workflow/tasklist/offset/1/limit/2    匹配   workflow/task/offset/*/limit/*
        HttpServletRequest request = ((FilterInvocation) o).getHttpRequest();
        AntPathRequestMatcher matcher;
        String resUrl;
        for(Iterator<String> iter = map.keySet().iterator(); iter.hasNext(); ) {
            resUrl = iter.next();
            matcher = new AntPathRequestMatcher(resUrl);
            if(matcher.matches(request)) {
                return map.get(resUrl);
            }
        }
        return null;
        
        
        //查询具体某个接口的权限
//        List<SysPermission> permissionList =  sysPermissionService.selectListByPath(requestUrl);
//        if(permissionList == null || permissionList.size() == 0){
//            //请求路径没有配置权限，表明该请求接口可以任意访问
//            return null;
//        }
//        String[] attributes = new String[permissionList.size()];
//        for(int i = 0;i<permissionList.size();i++){
//            attributes[i] = permissionList.get(i).getPermissionCode();
//        }
//        return SecurityConfig.createList(attributes);
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }
   
//    private boolean isMatcherAllowedRequest(FilterInvocation fi){
//        return allowedRequest().stream().map(AntPathRequestMatcher::new)
//                .filter(requestMatcher -> requestMatcher.matches(fi.getHttpRequest()))
//                .toArray().length > 0;
//    }
//    
//    private List<String> allowedRequest(){
//        return Arrays.asList("/login","/css/**","/fonts/**","/js/**","/scss/**","/img/**");
//    }
}
