package com.upotv.mcs.config;

import com.upotv.mcs.core.MyBatisConfig;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.annotation.MapperScan;
import com.upotv.mcs.core.McsBaseDao;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

/**
 * mcs主库Java配置类
 * <p>
 * Created by tianapple on 2017/5/10.
 */
@Configuration
@ConfigurationProperties(prefix = "jdbc.mcs")
@MapperScan(basePackages = "com.upotv.mcs", markerInterface = McsBaseDao.class,sqlSessionFactoryRef = "mcsSqlSessionFactory")
@EnableTransactionManagement
public class McsDBConfig extends MyBatisConfig {

    @Override
    @Bean(name = "mcsDataSource")
    public DataSource getDataSource() throws Exception {
        return super.getDataSource();
    }

    @Override
    @Bean(name = "mcsSqlSessionFactory")
    public SqlSessionFactory sqlSessionFactory(@Qualifier("mcsDataSource") DataSource dataSource) throws Exception {
        return super.sqlSessionFactory(dataSource);
    }
}
