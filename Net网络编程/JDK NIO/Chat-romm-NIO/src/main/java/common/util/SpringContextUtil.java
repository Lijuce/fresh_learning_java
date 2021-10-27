package common.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @ClassName SpringContextUtil
 * @Description IOC容器工具类
 * @Author Lijuce_K
 * @Date 2021/10/22 0022 20:54
 * @Version 1.0
 **/
@Slf4j
public class SpringContextUtil {
    private SpringContextUtil() {

    }

    private static ApplicationContext applicationContext;

    static {
        applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
    }

    public static <T> T getBean(String beanId) {
        T bean = null;
        try {
            if (StringUtils.isNotEmpty(StringUtils.trim(beanId))) {
                bean = (T) applicationContext.getBean(beanId);
            }
        } catch (NoSuchBeanDefinitionException e) {
            log.error("获取bean失败");
            return null;
        }
        return bean;
    }

    public static <T> T getBean(String... partName) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < partName.length; ++i) {
            sb.append(partName[i]);
            if (i != partName.length - 1) {
                sb.append(".");
            }
        }
        return getBean(sb.toString());
    }
}
