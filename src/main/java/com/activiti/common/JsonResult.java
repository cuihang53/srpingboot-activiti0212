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
public class JsonResult implements Serializable {

    @ApiModelProperty(value = "返回码：success，error，nologin，noallow")
    private String code;

    @ApiModelProperty(value = "返回错误信息")
    private String errMsg;

    @ApiModelProperty(value = "返回的数据，任意类型")
    private Object content;

    @ApiModelProperty(value = "返回的数据条数，分页时是总条数")
    private Long size;

    @ApiModelProperty(value = "返回的总页数，分页时使用")
    private Long totalPages;

    private int status;

    
    
}
