package com.activiti.config;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.engine.parse.BpmnParseHandler;
import org.activiti.spring.SpringProcessEngineConfiguration;
import org.activiti.spring.boot.AbstractProcessEngineAutoConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import com.activiti.explorer.ExtensionUserTaskParseHandler;

@Configuration
public class ActivityDataSourceConfig extends AbstractProcessEngineAutoConfiguration {
 
 
    @Autowired
	private DataSource dataSource;
    
    @Bean
    public PlatformTransactionManager transactionManager() {
        return new DataSourceTransactionManager(dataSource);
    }
    
 
    @Bean
    public SpringProcessEngineConfiguration springProcessEngineConfiguration() {
    	
    	List<BpmnParseHandler> bpmnParseHandlers = new ArrayList<BpmnParseHandler>();
    	bpmnParseHandlers.add( new ExtensionUserTaskParseHandler() );
        SpringProcessEngineConfiguration configuration = new SpringProcessEngineConfiguration();
        configuration.setDataSource(dataSource);
        configuration.setDatabaseSchemaUpdate(ProcessEngineConfiguration.DB_SCHEMA_UPDATE_TRUE);
        configuration.setJobExecutorActivate(true);
        configuration.setTransactionManager(transactionManager());
        configuration.setActivityFontName("宋体");
        configuration.setLabelFontName("宋体");
        configuration.setAnnotationFontName("宋体");
        configuration.setCustomDefaultBpmnParseHandlers(bpmnParseHandlers);
        return configuration;
    }
 
    
   
}
