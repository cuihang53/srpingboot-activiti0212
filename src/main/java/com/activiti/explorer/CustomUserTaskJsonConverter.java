package com.activiti.explorer;

import java.util.Map;

import org.activiti.bpmn.model.BaseElement;
import org.activiti.bpmn.model.CustomProperty;
import org.activiti.bpmn.model.FlowElement;
import org.activiti.bpmn.model.UserTask;
import org.activiti.editor.language.json.converter.UserTaskJsonConverter;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class CustomUserTaskJsonConverter extends UserTaskJsonConverter {
	 
    @Override
    protected FlowElement convertJsonToElement(JsonNode elementNode, JsonNode modelNode, Map<String, JsonNode> shapeMap) {
        FlowElement flowElement = super.convertJsonToElement(elementNode, modelNode, shapeMap);
        UserTask userTask = (UserTask) flowElement;
        //将自己的属性添加到activiti自带的自定义属性中
        CustomProperty customProperty = new CustomProperty();
        customProperty.setName("approvetype");
        customProperty.setSimpleValue(this.getPropertyValueAsString("approvetype", elementNode));
        userTask.getCustomProperties().add(customProperty);
        return userTask;
    }
 
    @Override
    protected void convertElementToJson(ObjectNode propertiesNode, BaseElement baseElement) {
        super.convertElementToJson(propertiesNode, baseElement);
    }
}
