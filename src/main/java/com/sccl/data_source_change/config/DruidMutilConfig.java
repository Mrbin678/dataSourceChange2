package com.sccl.data_source_change.config;

import com.sccl.data_source_change.datasource.DynamicDataSource;
import com.sccl.data_source_change.enumConst.DataSourceEnum;
import com.sccl.data_source_change.utils.PackagesSqlSessionFactoryBean;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.jta.atomikos.AtomikosDataSourceBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.lang.Nullable;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * druid 配置多数据源
 *
 * @author sccl
 */
@Configuration
@EnableTransactionManagement //开启事务
@MapperScan("com.sccl.data_source_change.**.mapper")
public class DruidMutilConfig {


    @Bean(name = "masterDataSource")
    public DataSource masterDataSource(Environment env) {
        String sourceName = "master";
        Properties prop = build(env, "spring.datasource.druid.master.");
        AtomikosDataSourceBean xaDataSource = new AtomikosDataSourceBean();
        //druid的数据库驱动换成xa的
        xaDataSource.setXaDataSourceClassName("com.alibaba.druid.pool.xa.DruidXADataSource");
        xaDataSource.setUniqueResourceName(sourceName);
        xaDataSource.setPoolSize(5);
        xaDataSource.setXaProperties(prop);
        return xaDataSource;
    }

    @Bean(name = "slaveDataSource")
    public DataSource slaveDataSource(Environment env) {
        String sourceName = "slave";
        Properties prop = build(env, "spring.datasource.druid.slave.");
        AtomikosDataSourceBean xaDataSource = new AtomikosDataSourceBean();
        //druid的数据库驱动换成xa的
        xaDataSource.setXaDataSourceClassName("com.alibaba.druid.pool.xa.DruidXADataSource");
        xaDataSource.setUniqueResourceName(sourceName);
        xaDataSource.setPoolSize(5);
        xaDataSource.setXaProperties(prop);
        return xaDataSource;

    }

    private Properties build(Environment env, String prefix) {

        Properties prop = new Properties();
        prop.put("url", env.getProperty(prefix + "url"));
        prop.put("username", env.getProperty(prefix + "username"));
        prop.put("password", env.getProperty(prefix + "password"));
        prop.put("driverClassName", env.getProperty(prefix + "driverClassName", ""));
        //这里只设置了简单的几个属性，如果想做更多的配置可以继续往下添加即可
        return prop;
    }

    /**
     * 动态数据源,在这继续添加 DataSource Bean
     */
    @Bean(name = "dynamicDataSource")
    @Primary
    public DynamicDataSource dataSource(@Qualifier("masterDataSource") DataSource masterDataSource, @Nullable @Qualifier("slaveDataSource") DataSource slaveDataSource) {
        Map<Object, Object> targetDataSources = new HashMap<>();
        targetDataSources.put(DataSourceEnum.MASTER.getName(), masterDataSource);
        if (slaveDataSource != null){
            targetDataSources.put(DataSourceEnum.SLAVE.getName(), slaveDataSource);
        }
        // 还有数据源,在targetDataSources中继续添加
        return new DynamicDataSource(masterDataSource, targetDataSources);
    }

    @Bean(name = "sqlSessionFactory")
    @Primary
    public SqlSessionFactory sqlSessionFactory(@Qualifier("dynamicDataSource") DataSource dataSource)
            throws Exception {
        //参照的别人的代码说需要将会话工厂改成mybatis-plus的sql会话工厂，
        //经测试发现使用mybatis的会话工厂也可以运行，不会报错
//        MybatisSqlSessionFactoryBean bean = new MybatisSqlSessionFactoryBean();
        //使用了PackagesSqlSessionFactoryBean继承SqlSessionFactoryBean，重写了配置别名的方法
        PackagesSqlSessionFactoryBean bean = new PackagesSqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        //设置多数据源分布式事务
        bean.setTransactionFactory(new MultiDataSourceTransactionFactory());
        bean.setTypeAliasesPackage("com.sccl.data_source_change.*.domain");//通配符设置包别名
        bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath*:mybatis/**/*.xml"));// 扫描指定目录的xml
        return bean.getObject();
    }

    @Bean(name = "sqlSessionTemplate")
    @Primary
    public SqlSessionTemplate sqlSessionTemplate(
            @Qualifier("sqlSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory);
    }
}
