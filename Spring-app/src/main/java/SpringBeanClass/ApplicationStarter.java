package SpringBeanClass;

import SpringBeanClass.entity.User;
import SpringBeanClass.repository.UserRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class ApplicationStarter {
    public static void main(String[] args) {
        try(final ConfigurableApplicationContext ctx = SpringApplication.run(ApplicationStarter.class)) {
        UserRepository userRepository = ctx.getBean(UserRepository.class);
            List<User> users = userRepository.findAll();
            users.forEach(System.out::println);
            System.out.println("~~~~~~~~~~~~~~~~");
            System.out.println(userRepository.findByLoginAndRole("123","user"));
            System.out.println("~~~~~~~~~~~~~~~~");
            User user = new User();
            user.setName("Stanislav");
            user.setSurname("Lolikov");
            user.setLogin("stas1");
            user.setPassword("123");
            user.setRole("user");
            User saved = userRepository.save(user);
            System.out.println("~~~~~~~~~~~~~~~~");
            System.out.println(saved.toString());
            userRepository.deleteById(saved.getId());
            System.out.println("The user was deleted");
        }
    }
}
