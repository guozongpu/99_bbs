package com.config;

import java.util.Properties;

import javax.annotation.Resource;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.util.ConfigConstant;


/** 
* @ClassName: HibernateConfiguration 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author (黄志强)  
* @date 2016年9月7日 下午2:55:42 
* @version V1.0 
*/
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(value = "com.respositry")
@PropertySource(value = { "classpath:jdbc.properties" })
public class HibernateConfiguration {

    @Resource
    private Environment env;

    /**
     * 配置数据源
     */
    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();

        dataSource.setDriverClassName(env.getRequiredProperty(ConfigConstant.DRIVER_NAME));
        dataSource.setUrl(env.getRequiredProperty(ConfigConstant.URL));
        dataSource.setUsername(env.getRequiredProperty(ConfigConstant.USERNAME));
        dataSource.setPassword(env.getRequiredProperty(ConfigConstant.PASSWORD));

        return dataSource;
    }

   /* @Bean
    public LocalSessionFactoryBean sessionFactory() {
        LocalSessionFactoryBean sessionFactoryBean = new LocalSessionFactoryBean();
        sessionFactoryBean.setDataSource(dataSource());
        sessionFactoryBean.setPackagesToScan(env.getRequiredProperty(ConfigConstant.PACKAGE_SCAN));
        sessionFactoryBean.setHibernateProperties(hibProperties());
        return sessionFactoryBean;
        }*/
    
    @Bean
    public EntityManagerFactory entityManagerFactory() {
        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        vendorAdapter.setGenerateDdl(true);//创建/更新相关的表
       

//        Properties properties = new Properties();//这是一种配置方式
//        properties.setProperty("hibernate.ejb.naming_strategy", "org.hibernate.cfg.ImprovedNamingStrategy");

        LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
        factory.setJpaVendorAdapter(vendorAdapter);
        factory.setPackagesToScan("com.entity");
        factory.setDataSource(dataSource());
        factory.setJpaProperties(hibProperties());


        //factory.setValidationMode(ValidationMode.NONE);

        factory.afterPropertiesSet();

        return factory.getObject();
    }
  //配置事务
    @Bean
    public PlatformTransactionManager transactionManager() {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory());
        transactionManager.setDataSource(dataSource());
        return transactionManager;
    }

    /**
     * 配置hibernate
     */
    private Properties hibProperties() {
        Properties properties = new Properties();
        properties.put(ConfigConstant.HIBERNATE_DIALECT, env.getRequiredProperty(ConfigConstant.HIBERNATE_DIALECT));
        properties.put(ConfigConstant.HIBERNATE_SHOW_SQL, env.getRequiredProperty(ConfigConstant.HIBERNATE_SHOW_SQL));
        properties.put(ConfigConstant.HIBERNATE_FORMAT_SQL, env.getRequiredProperty(ConfigConstant.HIBERNATE_FORMAT_SQL));
        return properties;
    }

    /**
     * 配置事务管理
     */
    /*@Bean
    public HibernateTransactionManager transactionManager() {
        HibernateTransactionManager transactionManager = new HibernateTransactionManager();
        transactionManager.setSessionFactory(sessionFactory().getObject());
        return transactionManager;
    }*/
}
