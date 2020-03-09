package com.activiti.common;

import org.springframework.web.servlet.mvc.condition.RequestCondition;

import javax.servlet.http.HttpServletRequest;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
 
/**
 * 版本号匹配筛选器
 * @author Cuihang
 */
public class ApiVersionCondition implements RequestCondition<ApiVersionCondition> {
 
	  // 路径中版本的前缀， 这里用 /v[1-9]/的形式
//    private final static Pattern VERSION_PREFIX_PATTERN = Pattern.compile("v(\\d+)/");
	/**
	 * 路径中版本的正则表达式匹配， 这里用 /1.0的形式
	 */
    private static final Pattern VERSION_PREFIX_PATTERN = Pattern.compile("^\\S+/([1-9][.][0-9])$");
    private double apiVersion;
 
    public ApiVersionCondition(double apiVersion) {
        this.apiVersion = apiVersion;
    }
 
    @Override
    public ApiVersionCondition combine(ApiVersionCondition other) {
        // 采用最后定义优先原则，则方法上的定义覆盖类上面的定义
        return new ApiVersionCondition(other.getApiVersion());
    }
 
    @Override 
    public ApiVersionCondition getMatchingCondition(HttpServletRequest request) {
 
        Matcher m = VERSION_PREFIX_PATTERN.matcher(request.getRequestURI());
        if (m.find()) {
            Double version = Double.valueOf(m.group(1));
            // 如果请求的版本号大于配置版本号， 则满足
            if (version >= this.apiVersion) {
                return this;
            }
        }
        return null;
    }
 
    @Override
    public int compareTo(ApiVersionCondition other, HttpServletRequest request) {
        // 优先匹配最新的版本号
        return Double.compare(other.getApiVersion(), this.apiVersion);
    }
 
    public double getApiVersion() {
        return apiVersion;
 
    }
 
 
}
