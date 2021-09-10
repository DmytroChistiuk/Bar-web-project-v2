package SpringBeanClass.service;

import SpringBeanClass.entity.User;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Defines all methods that will be used with the entity User.
 */
@Component
public interface UserService {
    User createNewUser(String name, String surname, String login, String password) throws ServiceException;

    User getByUserLogin(String username) throws ServiceException;

    User getById(int id) throws ServiceException;

    List<User> findAll() throws ServiceException;

    void setAdminRole(int id) throws ServiceException;

    void setUserRole(int id) throws ServiceException;

}
