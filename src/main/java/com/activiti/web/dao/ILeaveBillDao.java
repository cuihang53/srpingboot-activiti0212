package com.activiti.web.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.activiti.entity.LeaveBill;



@Repository
public interface ILeaveBillDao extends JpaRepository<LeaveBill,String>{

	
//	@Query(value ="select * from a_leavebill g")
//	List<LeaveBill> findLeaveBillList();

//	@
//	void saveLeaveBill(LeaveBill leaveBill);
//
	@Query(value ="select g from LeaveBill g where g.id=:id")
	LeaveBill findLeaveBillById(@Param("id")Long id);
//
//	void updateLeaveBill(LeaveBill leaveBill);
//
//	void deleteLeaveBillById(Long id);


}
