package SpringBeanClass.service;

import SpringBeanClass.dao.UserDaoImpl;
import SpringBeanClass.entity.User;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import static SpringBeanClass.util.Sha256Encryption.getSha256;

/**
 * Implementation of UserService interface.
 * This class implements all the logic of working with the entity User.
 * This class work with UserDao.
 * In the methods of this class creates connection for transfer to Database.
 */
@Service
public class UserServiceImpl implements UserService {
    private static final Logger logger = Logger.getLogger(UserBarServiceImpl.class);
    private final UserDaoImpl userDaoImpl;
    private final DataSource dataSource;

    public UserServiceImpl(UserDaoImpl userDaoImpl, DataSource dataSource) {
        this.userDaoImpl = userDaoImpl ;
        this.dataSource = dataSource;
    }

    @Override
    public User createNewUser(String name, String surname, String login, String password) throws ServiceException {
        User user = new User();
        user.setName(name);
        user.setSurname(surname);
        user.setLogin(login);
        user.setPassword(getSha256(password));
        user.setRole("user");
        try (Connection connection = dataSource.getConnection()) {
            return userDaoImpl.createUser(user, connection);
        } catch (SQLException e) {
            logger.error("Failed to create new user", e);
            throw new ServiceException("Failed to create new user");
        }
    }

    @Override
    public User getByUserLogin(String username) throws ServiceException {
        try (Connection connection = dataSource.getConnection()) {
            return userDaoImpl.findByLogin(username, connection);
        } catch (SQLException e) {
            logger.error("Failed to get user by login", e);
            throw new ServiceException("Failed to get user by login");
        }
    }

    @Override
    public User getById(int id) throws ServiceException {
        try (Connection connection = dataSource.getConnection()) {
            return userDaoImpl.findById(id, connection);
        } catch (SQLException e) {
            logger.error("Failed to get user by id", e);
            throw new ServiceException("Failed to get user by id");
        }
    }

    @Override
    public List<User> findAll() throws ServiceException {
        try (Connection connection = dataSource.getConnection()) {
            return userDaoImpl.findAll(connection);
        } catch (SQLException e) {
            logger.error("Failed to get all users", e);
            throw new ServiceException("Failed to get all users");
        }
    }

    @Override
    public void setAdminRole(int id) throws ServiceException {
        try (Connection connection = dataSource.getConnection()) {
            userDaoImpl.setAdminRole(id, connection);
        } catch (SQLException e) {
            logger.error("Failed to set admin role", e);
            throw new ServiceException("Failed to set admin role");
        }
    }

    @Override
    public void setUserRole(int id) throws ServiceException {
        try (Connection connection = dataSource.getConnection()) {
            userDaoImpl.setUserRole(id, connection);
        } catch (SQLException e) {
            logger.error("Failed to set user role", e);
            throw new ServiceException("Failed to set user role");
        }
    }
}

