package com.activiti.web.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.activiti.entity.DeploymentUnitMaintenance;



@Repository
public interface DeploymentUnitMaintenanceDao extends JpaRepository<DeploymentUnitMaintenance,String>{
	
	@Query(value ="select g from DeploymentUnitMaintenance g where g.id=:id")
	DeploymentUnitMaintenance findDeploymentUnitMaintenanceById(@Param("id")Long id);

	//DeploymentUnitMaintenance delete(DeploymentUnitMaintenanceDto entity);

	//DeploymentUnitMaintenance findAllById(DeploymentUnitMaintenanceDto entity);

	//DeploymentUnitMaintenance findAll(DeploymentUnitMaintenanceDto entity);

	//int selectCount(Object object);

	//List<DeploymentUnitMaintenance> findDeploymentUnitMaintenanceByPage(int start, Integer rows);
	
	
	
	//void saveDeploymentUnitMaintenance(DeploymentUnitMaintenance deploymentUnitMaintenanceDao);
//	@Update(value = { "update deploymentUnitMaintenance set"
//			+ "applicant=#{applicant},"
//			+ "emergencyLevel=#{emergencyLevel}"
//			+ "applicationSubsystem=#{applicationSubsystem},"
//			+ "resourceType=#{resourceType}"
//			+ "applicationType=#{applicationType}"
//			+ "applicationSummary=#{applicationSummary}"
//			+ "detailedDescription=#{detailedDescription}"
//			+ "modifiedTime=noe()" })
//	DeploymentUnitMaintenance updata(DeploymentUnitMaintenance deploymentUnitMaintenance1);
//	@Insert(value = { "insert to deploymentUnitMaintenance(id,LeaveBillId,"
//			+ "applicant,emergencyLevel,applicationSubsystem,resourceType,applicationType,applicationSummary,detailedDescription,createdTime,modifiedTime)"
//			+ "values(#{id},#{LeaveBillId},#{applicant},#{emergencyLevel},#{applicationSubsystem},#{resourceType},#{applicationType},#{applicationSummary},#{detailedDescription},now(),now()" })
//	DeploymentUnitMaintenance insert(DeploymentUnitMaintenance deploymentUnitMaintenance1);
}
