package com.cc.jobs.config;

import java.util.Map;

import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration 
	@EnableTransactionManagement
	@PropertySource("classpath:jdbc.properties")
	public class DBConfig {
		@Autowired
	        private Environment env;	
		
		private static String HOST_IP_PARAM="host_db_ip";
		private static String HOST_PORT_PARAM="host_db_port";
		
	        @Bean
		public DataSource dataSource() {
		    BasicDataSource dataSource = new BasicDataSource();
		    dataSource.setDriverClassName(env.getProperty("jdbc.driverClassName"));
		    
		    Map<String , String >  envs =System.getenv();
		    
		    String host_db_ip = envs.get(HOST_IP_PARAM);
		    String host_db_port =envs.get(HOST_PORT_PARAM);
		    
		    String jdbcUrl = env.getProperty("jdbc.url");
		    jdbcUrl =jdbcUrl.replace(HOST_IP_PARAM, host_db_ip);//127.0.0.1
		    jdbcUrl =jdbcUrl.replace(HOST_PORT_PARAM,host_db_port);//5432
		    dataSource.setUrl(jdbcUrl);
		    dataSource.setUsername(env.getProperty("jdbc.username"));
		    dataSource.setPassword(env.getProperty("jdbc.password"));
		    
		    
		    
		    System.out.println("jdbcUrl  "+jdbcUrl );

		    return dataSource;
		}
		@Bean
		public JdbcTemplate jdbcTemplate() {
		    return new JdbcTemplate(dataSource());
		}
		@Bean(name="transactionManager")
		public PlatformTransactionManager txManager(){
		    DataSourceTransactionManager transactionManager = new DataSourceTransactionManager(dataSource());
		    return transactionManager;
		}	
	} 

