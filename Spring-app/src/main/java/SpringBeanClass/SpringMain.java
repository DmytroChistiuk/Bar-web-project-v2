package SpringBeanClass;

import SpringBeanClass.config.ApplicationConfig;
import SpringBeanClass.dao.DaoException;
import SpringBeanClass.dao.UserDaoImpl;
import SpringBeanClass.entity.User;
import SpringBeanClass.service.ServiceException;
import SpringBeanClass.service.UserServiceImpl;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import java.util.Arrays;

public class SpringMain {
    public static void main(String[] args) throws DaoException, ServiceException {
        try(final AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ApplicationConfig.class)){
            String[] arrayOfBeans = context.getBeanDefinitionNames();
        UserServiceImpl userService = context.getBean(UserServiceImpl.class);
            for (User user : userService.findAll()) {
                System.out.println(user.toString());
            }
            System.out.println(Arrays.toString(arrayOfBeans));

        }
    }
}
