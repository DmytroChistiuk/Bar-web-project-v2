package SpringBeanClass.config;


import com.mysql.cj.jdbc.MysqlDataSource;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;


import javax.sql.DataSource;
import java.sql.SQLException;

@Configuration
@ComponentScan(basePackages = "SpringBeanClass")
@PropertySource("classpath:application.properties")
public class ApplicationConfig {
    @Autowired
    Environment env;

    @Bean
    public DataSource getDataSource(){
        HikariConfig config = new HikariConfig();
        HikariDataSource ds;

        config.setJdbcUrl(env.getProperty("datasource.url"));
        config.setUsername(env.getProperty("datasource.username"));
        config.setPassword(env.getProperty("datasource.password"));
        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", "250");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
        ds = new HikariDataSource(config);
        return ds;

    }

}

