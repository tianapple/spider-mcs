package org.spider.mcs;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;

/**
 * mcs主库Java配置类，可灵活定制
 * <p>
 * Created by tianapple on 2017/5/10.
 */
@Configuration
@ConfigurationProperties(prefix = "jdbc.mcs")
@MapperScan(basePackages = "org.spider.mcs", markerInterface = McsBaseDao.class)
public class MyBatisConfig {
//    @Autowired
//    private ApplicationContext applicationContext;
//    @Autowired
//    private ResourceLoader resourceLoader;

    private String url;
    private String username;
    private String password;
    //连接只读数据库时配置为true， 保证安全
    private boolean readOnly = false;
    //等待连接池分配连接的最大时长（毫秒），超过这个时长还没可用的连接则发生SQLException， 缺省:30秒
    private int connTimeout = 15000;
    //一个连接idle状态的最大时长（毫秒），超时则被释放（retired），缺省:10分钟
    private int idleTimeout = 600000;
    //一个连接的生命时长（毫秒），超时而且没被使用则被释放（retired），缺省:30分钟，建议设置比数据库超时时长少30秒，参考MySQL wait_timeout参数（show variables like '%timeout%';）
    private int maxLifetime = 1800000;
    //连接池中允许的最大连接数。缺省值：10；推荐的公式：((core_count * 2) + effective_spindle_count)
    private int maxPoolSize = 20;
    private int minimumIdle = 4; //

    public void setUrl(String url) {
        this.url = url;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setReadOnly(boolean readOnly) {
        this.readOnly = readOnly;
    }

    public void setConnTimeout(int connTimeout) {
        this.connTimeout = connTimeout;
    }

    public void setIdleTimeout(int idleTimeout) {
        this.idleTimeout = idleTimeout;
    }

    public void setMaxLifetime(int maxLifetime) {
        this.maxLifetime = maxLifetime;
    }

    public void setMaxPoolSize(int maxPoolSize) {
        this.maxPoolSize = maxPoolSize;
    }

    public void setMinimumIdle(int minimumIdle) {
        this.minimumIdle = minimumIdle;
    }

    @Bean(name = "mcsDataSource")
    public DataSource getDataSource() throws Exception {
        HikariConfig config = new HikariConfig();
        config.setDriverClassName("com.mysql.jdbc.Driver");
        config.setConnectionInitSql("select 1");
        config.setJdbcUrl(url);
        config.setUsername(username);
        config.setPassword(password);
        config.setReadOnly(readOnly);
        config.setConnectionTimeout(connTimeout);
        config.setIdleTimeout(idleTimeout);
        config.setMaxLifetime(maxLifetime);
        config.setMaximumPoolSize(maxPoolSize);
        config.setMinimumIdle(minimumIdle);
        return new HikariDataSource(config);
    }

    @Bean(name = "mcsSqlSessionFactory")
    public SqlSessionFactory sqlSessionFactory(@Qualifier("mcsDataSource") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean fb = new SqlSessionFactoryBean();
        fb.setDataSource(dataSource);
        String mapperPath = "classpath:mapper/*.xml";
        fb.setMapperLocations(new PathMatchingResourcePatternResolver().getResources(mapperPath)); // env.getProperty("mybatis.mapperLocations" 指定xml文件位置
        return fb.getObject();
    }
}
