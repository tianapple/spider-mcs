package com.upotv.mcs.config;

import com.upotv.mcs.core.McsBaseDao;
import com.upotv.mcs.core.MyBatisConfig;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * mcs主库Java配置类
 * <p>
 * Created by tianapple on 2017/5/10.
 */
@Configuration
@ConfigurationProperties(prefix = "jdbc.mcs")
@MapperScan(basePackages = "com.upotv.mcs", markerInterface = McsBaseDao.class, sqlSessionFactoryRef = "mcsSqlSessionFactory")
public class McsDBConfig extends MyBatisConfig {

    @Override
    @Bean(name = "mcsDataSource")
    public DataSource getDataSource() throws Exception {
        return super.getDataSource();
    }

    @Override
    @Bean(name = "mcsSqlSessionFactory")
    public SqlSessionFactory sqlSessionFactory(@Qualifier("mcsDataSource") DataSource dataSource) throws Exception {
        //DataSource dataSource = (DataSource)applicationContext.getBean("mcsDataSource");
        return super.sqlSessionFactory(dataSource);
    }

//    @Override
//    @Bean(name = "mcsTransactionManager")
//    protected DataSourceTransactionManager transactionManager(@Qualifier("mcsDataSource")DataSource dataSource) {
//        return super.transactionManager(dataSource);
//    }
}
