package com.activiti.explorer;

import java.util.Map;

import org.activiti.bpmn.model.BaseElement;
import org.activiti.bpmn.model.CustomProperty;
import org.activiti.bpmn.model.FlowElement;
import org.activiti.bpmn.model.UserTask;
import org.activiti.editor.language.json.converter.UserTaskJsonConverter;

import com.activiti.common.Constants;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class CustomUserTaskJsonConverter extends UserTaskJsonConverter {
	 
    @Override
    protected FlowElement convertJsonToElement(JsonNode elementNode, JsonNode modelNode, Map<String, JsonNode> shapeMap) {
        FlowElement flowElement = super.convertJsonToElement(elementNode, modelNode, shapeMap);
        UserTask userTask = (UserTask) flowElement;
        //将自己的属性添加到activiti自带的自定义属性中
        CustomProperty customProperty = new CustomProperty();
        customProperty.setName(Constants.EXT_APPROVE_URL);
//        customProperty.setName(Constants.EXT_APPROVE_GROUP_ROLE);
        customProperty.setSimpleValue(this.getPropertyValueAsString(Constants.EXT_APPROVE_URL, elementNode));
//        customProperty.setSimpleValue(this.getPropertyValueAsString(Constants.EXT_APPROVE_GROUP_ROLE, elementNode));
        userTask.getCustomProperties().add(customProperty);
        return userTask;
    }
 
    @Override
    protected void convertElementToJson(ObjectNode propertiesNode, BaseElement baseElement) {
        super.convertElementToJson(propertiesNode, baseElement);
    }
}
