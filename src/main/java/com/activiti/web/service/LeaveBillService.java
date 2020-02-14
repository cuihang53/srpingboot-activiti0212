package com.activiti.web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.activiti.dto.LeaveBillDto;
import com.activiti.entity.LeaveBill;
import com.activiti.utils.CopyObjectUtil;
import com.activiti.web.dao.ILeaveBillDao;

@Service
public class LeaveBillService {

	@Autowired
	ILeaveBillDao leaveBillDao;
	/**
	public void insert(LeaveBillDto leaveBill){
		//获取instanceKey 对应表act_re_procdef的KEY_ 和 leaveBill2.bpmn的ID
		String instanceKey = leaveBill.getInstanceKey();
		//将dto数据转到entity中
		LeaveBill newLeave = new LeaveBill();
		CopyObjectUtil.copyPropertiesIgnoreNull(leaveBill, newLeave);
		//保存请假工单
		LeaveBill result = leaveBillDao.save(newLeave);
		System.out.println(result.getId());
		//TODO:根据业务 查询处理人
		String inputUser = "lisi";
		Map<String, Object> variables = new HashMap<String, Object>();
		variables.put("inputUser", inputUser);
		//创建一个流程实例 并指定下一个步骤处理人
		workflowService.saveStartProcess(variables, String.valueOf(result.getId()), instanceKey);
	}
	**/
	

	public LeaveBill insert(LeaveBillDto leaveBill){
		//将dto数据转到entity中
		LeaveBill newLeave = new LeaveBill();
		CopyObjectUtil.copyPropertiesIgnoreNull(leaveBill, newLeave);
		//保存请假工单
		LeaveBill result = leaveBillDao.save(newLeave);
		System.out.println(result.getId());
		
		return result;
	}
	
}

