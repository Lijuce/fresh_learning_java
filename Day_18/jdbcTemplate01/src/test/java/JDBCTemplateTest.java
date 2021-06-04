import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;

public class JDBCTemplateTest {

    @Test
    public void test(){
        ApplicationContext cs = new ClassPathXmlApplicationContext("bean.xml");
        JdbcTemplate jdbcTemplate = cs.getBean("jdbcTemplate2", JdbcTemplate.class);
//        jdbcTemplate.execute("insert into user (uid, name) values (11, 'lijuce')");
        System.out.println(jdbcTemplate);

    }
}
