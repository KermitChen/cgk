package com.ht.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import javax.sql.DataSource;

/**
 *@CreateDate 2018/9/10 11:43
 *@描述: mysql 数据源模版
 */
@Configuration
@PropertySource(value="classpath:application.properties")
@MapperScan(basePackages = MySqlDataSourceConfig.PACKAGE, sqlSessionFactoryRef = MySqlDataSourceConfig.MYSQL_SQL_SESSION_FACTORY)
public class MySqlDataSourceConfig {
    static final String MYSQL_SQL_SESSION_FACTORY = "mySqlSessionFactory";
    static final String PACKAGE = "com.ht.dao";
    static final String MAPPER_LOCATION = "classpath:com/ht/mapper/*.xml";

    @Value("${web.datasource.url}")
    private String url;

    @Value("${web.datasource.driverClassName}")
    private String driverClass;

    @Value("${web.datasource.username}")
    private String username;

    @Value("${web.datasource.password}")
    private String password;

    @Value("${web.datasource.initialSize}")
    private Integer initialSize;

    @Value("${web.datasource.minIdle}")
    private Integer minIdle;

    @Value("${web.datasource.maxActive}")
    private Integer maxActive;

    @Value("${web.datasource.maxWait}")
    private long maxWait;

    @Value("${web.datasource.validationQuery}")
    private String validationQuery;

    @Bean(name = "mySqlDataSource")
    public DataSource mySqlDataSource() {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setDriverClassName(driverClass);
        dataSource.setUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        
        dataSource.setInitialSize(initialSize);
        dataSource.setMinIdle(minIdle);
        dataSource.setMaxActive(maxActive);
        dataSource.setMaxWait(maxWait);
        
        dataSource.addConnectionProperty("validationQuery", validationQuery);
        dataSource.setTestOnBorrow(false);
        dataSource.setTestOnReturn(false);
        dataSource.setTestWhileIdle(true);
        dataSource.setTimeBetweenEvictionRunsMillis(60000);
        dataSource.setMinEvictableIdleTimeMillis(300000);//5分钟
        
        dataSource.setRemoveAbandoned(true);
        dataSource.setRemoveAbandonedTimeout(1800);//30分钟
        
        return dataSource;
    }

    @Bean(name = "mySqlTransactionManager")
    public DataSourceTransactionManager mySQLTransactionManager(@Qualifier("mySqlDataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean(name = MYSQL_SQL_SESSION_FACTORY)
    public SqlSessionFactory mySqlSessionFactory(@Qualifier("mySqlDataSource") DataSource mySQLDataSource) throws Exception {
        final SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(mySQLDataSource);
        sessionFactory.setMapperLocations(new PathMatchingResourcePatternResolver().getResources(MySqlDataSourceConfig.MAPPER_LOCATION));
        return sessionFactory.getObject();
    }
}