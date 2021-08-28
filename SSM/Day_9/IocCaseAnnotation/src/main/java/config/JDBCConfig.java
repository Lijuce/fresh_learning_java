package config;

import com.alibaba.druid.pool.DruidDataSourceFactory;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.apache.commons.dbutils.QueryRunner;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import javax.sql.DataSource;
import java.beans.PropertyVetoException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

//@Configuration
public class JDBCConfig {

//    @Value("${jdbc.drive}")
//    private String DriverClass;
//
//    @Value("${jdbc.url}")
//    private String jdbcUrl;
//
//    @Value("${jdbc.username}")
//    private String username;
//
//    @Value("${jdbc.password}")
//    private String password;

    @Bean(name = "runner")
//    @Scope("prototype")  //
    public QueryRunner createQueryRunner(DataSource dataSource){
        return new QueryRunner(dataSource);
    }

    @Bean(name="dataSource")
    public DataSource crateDataSource(){
        try {
            Properties properties = new Properties();
            InputStream resourceAsStream = JDBCConfig.class.getClassLoader().getResourceAsStream("druid.properties");
            properties.load(resourceAsStream);
            DataSource cPDS = DruidDataSourceFactory.createDataSource(properties);
//            ComboPooledDataSource cPDS = new ComboPooledDataSource();
//            cPDS.setDriverClass(DriverClass);
//            cPDS.setJdbcUrl(jdbcUrl);
//            cPDS.setUser(username);
//            cPDS.setPassword(password);
            return cPDS;
        } catch (PropertyVetoException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
