package com.activiti.web.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.activiti.entity.TimerTask;

@Repository
public interface ITimerTaskDao extends JpaRepository<TimerTask,String>{

	@Query(value ="select t from TimerTask t where t.instanceKey=:instanceKey")
	TimerTask findByProcessInstenceId(@Param("instanceKey")String instanceKey);
}
