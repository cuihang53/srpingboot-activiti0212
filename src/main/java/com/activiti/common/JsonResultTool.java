package com.activiti.common;

/**
 * @Author: Cuihang
 * @Description:
 * @Date Create in 2019/7/22 19:52
 */
public class JsonResultTool {
	    public static JsonResult<?> success() {
	        return new JsonResult<>(true,"");
	    }

	    public static <T> JsonResult<T> success(T data) {
	        return new JsonResult<T>(true, data);
	    }
	    public static JsonResult<?> fail(String errorMsg) {
	        return new JsonResult<>(false,errorMsg);
	        
	    }

	    public static JsonResult<?> fail(ResultCode resultEnum) {
	        return new JsonResult<>(false, resultEnum);
	    }
	}
