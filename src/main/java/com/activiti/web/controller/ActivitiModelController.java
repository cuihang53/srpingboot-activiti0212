package com.activiti.web.controller;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletResponse;

import org.activiti.bpmn.converter.BpmnXMLConverter;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.editor.constants.ModelDataJsonConstants;
import org.activiti.editor.language.json.converter.BpmnJsonConverter;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.Model;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.activiti.common.ToWeb;
import com.activiti.explorer.CustomBpmnJsonConverter;
import com.activiti.explorer.CustomUserTaskJsonConverter;
import com.activiti.web.controller.base.RestServiceController;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("models")
public class ActivitiModelController implements RestServiceController<Model, String>{
 
//    private static final Logger log = LogManager.getLogger(ActivitiModelController.class);
 
    @Autowired
    ProcessEngine processEngine;
    
    @Autowired
    ObjectMapper objectMapper;
 
    @Autowired
    RepositoryService repositoryService;
    /**
     * 新建一个空模型
     */
    @RequestMapping("/create")
    public void newModel(HttpServletResponse response) throws IOException {
        RepositoryService repositoryService = processEngine.getRepositoryService();
        //初始化一个空模型
        Model model = repositoryService.newModel();
 
        //设置一些默认信息
        String uuId= UUID.randomUUID().toString();
        String name = uuId;
        String description = "";
        int revision = 1;
        String key = uuId;
 
        ObjectNode modelNode = objectMapper.createObjectNode();
        modelNode.put(ModelDataJsonConstants.MODEL_NAME, name);
        modelNode.put(ModelDataJsonConstants.MODEL_DESCRIPTION, description);
        modelNode.put(ModelDataJsonConstants.MODEL_REVISION, revision);
 
        model.setName(name);
        model.setKey(key);
        model.setMetaInfo(modelNode.toString());
 
        repositoryService.saveModel(model);
        String id = model.getId();
 
        //完善ModelEditorSource
        ObjectNode editorNode = objectMapper.createObjectNode();
        editorNode.put("id", "canvas");
        editorNode.put("resourceId", "canvas");
        ObjectNode stencilSetNode = objectMapper.createObjectNode();
        stencilSetNode.put("namespace",
                "http://b3mn.org/stencilset/bpmn2.0#");
        editorNode.put("stencilset", stencilSetNode);
        repositoryService.addModelEditorSource(id,editorNode.toString().getBytes("utf-8"));
        response.sendRedirect("/modeler.html?modelId="+id);
//        ToWeb.buildResult().redirectUrl("/modeler.html?modelId="+id);
    }
 
    /**
     * 获取所有模型
     */
    @RequestMapping("/modelList")
    @ResponseBody
    @ApiOperation(value = "模型列表")
    public Object modelList(){
        RepositoryService repositoryService = processEngine.getRepositoryService();
        return repositoryService.createModelQuery().list();
    }
 
    /**
     * 发布模型为流程定义
     */
    @RequestMapping("/{modelId}/deployment")
    @ResponseBody
    @ApiOperation(value = "发布模型")
    public Object deploy(@PathVariable("modelId")String modelId) throws Exception {
 
        //获取模型
        RepositoryService repositoryService = processEngine.getRepositoryService();
        Model modelData = repositoryService.getModel(modelId);
        byte[] bytes = repositoryService.getModelEditorSource(modelData.getId());
 
        if (bytes == null) {
            return "模型数据为空，请先设计流程并成功保存，再进行发布。";
        }
 
        JsonNode modelNode = new ObjectMapper().readTree(bytes);

        //自定义用户任务节点属性
        CustomBpmnJsonConverter.getConvertersToBpmnMap().put("UserTask", CustomUserTaskJsonConverter.class);
        
        BpmnModel model = new BpmnJsonConverter().convertToBpmnModel(modelNode);
        if(model.getProcesses().size()==0){
            return "数据模型不符要求，请至少设计一条主线流程。";
        }
        byte[] bpmnBytes = new BpmnXMLConverter().convertToXML(model);
 
        //发布流程
        String processName = modelData.getName() + ".bpmn20.xml";
        Deployment deployment = repositoryService.createDeployment()
                .name(modelData.getName())
                .addString(processName, new String(bpmnBytes, "UTF-8"))
                .deploy();
        modelData.setDeploymentId(deployment.getId());
        repositoryService.saveModel(modelData);
 
        return "SUCCESS";
    }

    
    @Override
    public Object getOne(@PathVariable("id") String id) {
        Model model = repositoryService.createModelQuery().modelId(id).singleResult();
        return ToWeb.buildResult().setObjData(model);
    }
    
    
    @Override
    public Object getList(@RequestParam(value = "rowSize", defaultValue = "1000", required = false) Integer rowSize, @RequestParam(value = "page", defaultValue = "1", required = false) Integer page) {
        List<Model> list = repositoryService.createModelQuery().listPage(rowSize * (page - 1)
                , rowSize);
        long count = repositoryService.createModelQuery().count();

        return ToWeb.buildResult().setRows(
                ToWeb.Rows.buildRows().setCurrent(page)
                        .setTotalPages((int) (count/rowSize+1))
                        .setTotalRows(count)
                        .setList(list)
                        .setRowSize(rowSize)
        );
    }

    public Object deleteOne(@PathVariable("id")String id){
        repositoryService.deleteModel(id);
        return ToWeb.buildResult().refresh();
    }
    

	@Override
	public Object postOne(Model entity) {
		return null;
	}

	@Override
	public Object putOne(String id, Model entity) {
		return null;
	}

	@Override
	public Object patchOne(String id, Model entity) {
		return null;
	}

 
}
