package com.activiti.web.service;


import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.activiti.dto.DeploymentUnitMaintenanceDto;
import com.activiti.entity.DeploymentUnitMaintenance;
import com.activiti.entity.SysUser;
import com.activiti.utils.CopyObjectUtil;
import com.activiti.web.dao.DeploymentUnitMaintenanceDao;


@Service
public class DeploymentUnitMaintenanceService {
	@Autowired
	private DeploymentUnitMaintenanceDao deploymentUnitMaintenanceDao;
	
	@Autowired
	private WorkflowService workflowService;
	
	@Autowired
	private SysUserService userService;
	
//	private static final Map<String, String> map = new HashMap<String,String>();
//	static{
//		map.put("inputUser", "范冰冰");
//		map.put("leader", "冯小刚");
//	}
	
	public DeploymentUnitMaintenance insert(DeploymentUnitMaintenanceDto deploymentUnitMaintenance) {
			//将dto数据转到entity中
			DeploymentUnitMaintenance dum = new DeploymentUnitMaintenance();
			CopyObjectUtil.copyPropertiesIgnoreNull(deploymentUnitMaintenance, dum);
			dum.setLeaves(deploymentUnitMaintenance.getChildPageFormJson().getLeaves());
			dum.setLeaveDays(deploymentUnitMaintenance.getChildPageFormJson().getLeaveDays());
			dum.setLeaveStartTime(deploymentUnitMaintenance.getChildPageFormJson().getLeaveStartTime());
			dum.setReasonForLeave(deploymentUnitMaintenance.getChildPageFormJson().getReasonForLeave());
			//保存请假工单
			DeploymentUnitMaintenance result = deploymentUnitMaintenanceDao.save(dum);
			
			SysUser user = userService.findByUserAccount();
			
			//启动流程实例
			if(result!=null && result.getId()!=null){
				Map<String,Object>  variables = new HashMap<>();
				variables.put("inputUser", user.getId());
//				ProcessDefinition p = repositoryService.createProcessDefinitionQuery().deploymentId(deploymentUnitMaintenance.getDeploymentId()).singleResult();
				String instanceId = workflowService.saveStartProcessByDeploymentId(variables, String.valueOf(result.getId()), deploymentUnitMaintenance.getDeploymentId());
				variables.put("inputUser", user.getParentId());
				workflowService.taskComplateByInstanceId(variables, instanceId, String.valueOf(user.getId()));
			}
			dum.setStatus(1);//1 是审批中
			deploymentUnitMaintenanceDao.save(dum);
			return result;
	}

	public DeploymentUnitMaintenance updata(DeploymentUnitMaintenanceDto entity) {
		DeploymentUnitMaintenance deploymentUnitMaintenance1 = new DeploymentUnitMaintenance();
		CopyObjectUtil.copyPropertiesIgnoreNull(entity, deploymentUnitMaintenance1);
		DeploymentUnitMaintenance result = deploymentUnitMaintenanceDao.save(deploymentUnitMaintenance1);
		System.out.println(result.getId());
		return result;
	}

//	public DeploymentUnitMaintenance insert(Object setApplicant) {
//		DeploymentUnitMaintenance deploymentUnitMaintenance1 = new DeploymentUnitMaintenance();
//		CopyObjectUtil.copyPropertiesIgnoreNull(setApplicant, deploymentUnitMaintenance1);
//		//保存请假工单
//		DeploymentUnitMaintenance result = deploymentUnitMaintenanceDao.save(deploymentUnitMaintenance1);
//		System.out.println(result.getId());
//			
//			return result;
//	}

	

//	public DeploymentUnitMaintenance delete(DeploymentUnitMaintenanceDto entity) {
//		DeploymentUnitMaintenance deploymentUnitMaintenance1 = new DeploymentUnitMaintenance();
//		CopyObjectUtil.copyPropertiesIgnoreNull(entity, deploymentUnitMaintenance1);
//		DeploymentUnitMaintenance result = deploymentUnitMaintenanceDao.delete(entity);
//		System.out.println(result.getId());
//		return result;
//	}

//	public DeploymentUnitMaintenance findAll(DeploymentUnitMaintenanceDto entity) {
//		
//		DeploymentUnitMaintenance deploymentUnitMaintenance1 = new DeploymentUnitMaintenance();
//		CopyObjectUtil.copyPropertiesIgnoreNull(entity, deploymentUnitMaintenance1);
//		DeploymentUnitMaintenance result = deploymentUnitMaintenanceDao.findAll(entity);
//		System.out.println(result.getId());
//		return result;
//	}

//	public EasyUIResult findDeploymentUnitMaintenanceByPage(Integer page, Integer rows) {
//		int total = deploymentUnitMaintenanceDao.selectCount(null);
//		
//		int start = (page - 1) * rows;
//		
//		List<DeploymentUnitMaintenance> deploymentUnitMaintenanceList = deploymentUnitMaintenanceDao.findDeploymentUnitMaintenanceByPage(start,rows);
//		
//		EasyUIResult result = new EasyUIResult(total, deploymentUnitMaintenanceList);
//		
//		return result;
//	}

	

	

	

}
