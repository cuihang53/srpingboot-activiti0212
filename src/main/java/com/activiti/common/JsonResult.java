package com.activiti.common;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import java.io.Serializable;


@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@JsonIgnoreProperties
public class JsonResult<T> implements Serializable {

    
	private int status;

	private String code;

    @ApiModelProperty(value = "返回错误信息")
    private String errMsg;

    @ApiModelProperty(value = "返回的数据，任意类型")
    private T content;
    
    
    public JsonResult() {
    }

    public JsonResult(boolean success,String errorMsg) {
        this.status = success ? ResultCode.OK.getCode() : ResultCode.BAD_REQUEST.getCode();
        this.code = success ? ResultCode.OK.getMessage() : ResultCode.BAD_REQUEST.getMessage();
        this.errMsg = success? null : errorMsg;
    }

    
    public JsonResult(boolean success, T data) {
    	 this.status = success ? ResultCode.OK.getCode() : ResultCode.BAD_REQUEST.getCode();
         this.code = success ? ResultCode.OK.getMessage() : ResultCode.BAD_REQUEST.getMessage();
         this.content = data;
    }

    public JsonResult(boolean success, ResultCode resultEnum) {
        this.status = success ? ResultCode.SUCCESS.getCode() : (resultEnum == null ? ResultCode.COMMON_FAIL.getCode() : resultEnum.getCode());
        this.code = success ? ResultCode.SUCCESS.getMessage() : (resultEnum == null ? ResultCode.COMMON_FAIL.getMessage() : resultEnum.getMessage());
    }
    
    
}
