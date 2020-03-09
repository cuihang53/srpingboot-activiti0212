package com.activiti.common;

import org.springframework.web.bind.annotation.Mapping;

import java.lang.annotation.*;
 
/**
 * 接口版本版本控制
 * @author Cuihang
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Mapping
public @interface ApiVersion {
    /**
     * 标识版本号
     * @return
     */
    double value();
}