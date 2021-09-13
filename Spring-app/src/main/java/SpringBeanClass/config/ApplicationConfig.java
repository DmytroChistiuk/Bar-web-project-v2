package SpringBeanClass.config;



import com.mysql.cj.jdbc.MysqlDataSource;
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
        public DataSource getDataSource() throws SQLException {
            MysqlDataSource mysqlDataSource = new MysqlDataSource();
            mysqlDataSource.setUser(env.getProperty("datasource.username"));
            mysqlDataSource.setPassword(env.getProperty("datasource.password"));
            mysqlDataSource.setURL(env.getProperty("datasource.url"));
            return mysqlDataSource.unwrap(DataSource.class);
        }
    }

