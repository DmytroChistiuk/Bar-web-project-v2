package SpringBeanClass;

import SpringBeanClass.config.ApplicationConfig;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Arrays;

public class SpringMain {
    public static void main(String[] args) {
        try(final AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ApplicationConfig.class)){
            String[] arrayOfBeans = context.getBeanDefinitionNames();
            System.out.println(Arrays.toString(arrayOfBeans));
        }
    }
}
