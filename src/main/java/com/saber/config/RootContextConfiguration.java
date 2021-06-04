package com.saber.config;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.hibernate.Hibernate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.provider.HibernateUtils;
import org.springframework.data.jpa.repository.query.Jpa21Utils;
import org.springframework.jdbc.datasource.AbstractDriverBasedDataSource;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManager;
import javax.persistence.SharedCacheMode;
import javax.persistence.ValidationMode;
import javax.sql.DataSource;
import java.beans.PropertyVetoException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;

@Configuration
@ComponentScan(basePackages = "com.saber.site"
        , excludeFilters = @ComponentScan.Filter(Controller.class))
@EnableTransactionManagement
public class RootContextConfiguration {

    @Bean
    public DataSource dataSource() throws PropertyVetoException {
        ComboPooledDataSource dataSource = new ComboPooledDataSource();
        dataSource.setDriverClass("com.mysql.jdbc.driver");
        dataSource.setJdbcUrl("jdbc:mysql://localhost:3306/test");
        dataSource.setUser("saber66");
        dataSource.setPassword("AdminSaber66");
        dataSource.setMinPoolSize(20);
        dataSource.setMaxPoolSize(50);
        dataSource.setMaxIdleTime(30000);
        return dataSource;
    }

    @Bean
    public LocalSessionFactoryBean sessionFactoryBean() throws PropertyVetoException {
        LocalSessionFactoryBean sessionFactoryBean = new LocalSessionFactoryBean();
        sessionFactoryBean.setDataSource(dataSource());
        sessionFactoryBean.setPackagesToScan("com.saber.site.entities");
        sessionFactoryBean.setHibernateProperties(hibernateProperties());

        return sessionFactoryBean;
    }
    @Bean
    public HibernateTransactionManager hibernateTransactionManager() throws PropertyVetoException {
        HibernateTransactionManager transactionManager= new HibernateTransactionManager();
        transactionManager.setSessionFactory(sessionFactoryBean().getObject());
        return transactionManager;
    }


    private JpaVendorAdapter adapter(){
        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        vendorAdapter.setShowSql(true);
        vendorAdapter.setDatabase(Database.MYSQL);
        vendorAdapter.setDatabasePlatform("org.hibernate.dialect.MySQL5Dialect");
        return vendorAdapter;
    }

    private Map<String,String> properties(){
        Map<String,String> properties= new HashMap<>();
        properties.put("hibernate.hbm2ddl.auto","update");
        properties.put("hibernate.dialect","org.hibernate.dialect.MySQL5Dialect");
        properties.put("hibernate.show_Sql","true");
        properties.put("hibernate.format_Sql","true");
        properties.put("javax.persistence.schema-generation.database.action",
                "none");
        return properties;
    }
    private Properties hibernateProperties(){
        Properties properties= new Properties();
        properties.put("hibernate.hbm2ddl.auto","update");
        properties.put("hibernate.dialect","org.hibernate.dialect.MySQL5Dialect");
        properties.put("hibernate.show_Sql","true");
        properties.put("hibernate.format_Sql","true");
        properties.put("javax.persistence.schema-generation.database.action",
                "none");
        return properties;
    }

    //@Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactoryBean() throws PropertyVetoException {
        LocalContainerEntityManagerFactoryBean entityManagerFactoryBean= new LocalContainerEntityManagerFactoryBean();
        entityManagerFactoryBean.setDataSource(dataSource());
        entityManagerFactoryBean.setPackagesToScan("com.saber.site.entities");
        entityManagerFactoryBean.setSharedCacheMode(SharedCacheMode.ENABLE_SELECTIVE);
        entityManagerFactoryBean.setValidationMode(ValidationMode.NONE);
        entityManagerFactoryBean.setJpaVendorAdapter(adapter());
        entityManagerFactoryBean.setJpaPropertyMap(properties());
        return entityManagerFactoryBean;
    }
//    @Bean
//    public EntityManager entityManager() throws PropertyVetoException {
//        return Objects.requireNonNull(entityManagerFactoryBean().getObject()).createEntityManager();
//    }

    //@Bean
    public PlatformTransactionManager transactionManager() throws PropertyVetoException {
        JpaTransactionManager transactionManager= new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactoryBean().getObject());
        return transactionManager;
    }
}
