package SpringBeanClass.service;

import SpringBeanClass.dao.UserBarDaoImpl;
import SpringBeanClass.entity.Cocktail;
import SpringBeanClass.entity.User;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
/**
 * Implementation of UserBarService interface.
 * This class implements all the logic of working with the entity UserBar.
 * This class work with UserBarDao.
 * In the methods of this class creates connection for transfer to Database.
 */
@Service
public class UserBarServiceImpl implements UserBarService {
    private static final Logger logger = Logger.getLogger(UserBarServiceImpl.class);
    private final UserBarDaoImpl userBarDaoImpl;
    private final DataSource dataSource;
    public UserBarServiceImpl(UserBarDaoImpl userBarDaoImpl, DataSource dataSource) {
        this.userBarDaoImpl = userBarDaoImpl;
        this.dataSource = dataSource;
    }

    @Override
    public Cocktail addCocktail(int id, Cocktail cocktail) throws ServiceException {
        try (Connection connection = dataSource.getConnection()) {
            return userBarDaoImpl.addCocktailToUserBar(id, cocktail, connection);
        } catch (SQLException e) {
            logger.error("Failed to add cocktail to user's bar", e);
            throw new ServiceException("Failed to find all ingredients");
        }
    }

    /**
     * Getting all cocktails that are in the bar of the current user.
     * @param id
     * @return
     * @throws ServiceException
     */
    @Override
    public List<Cocktail> getUserBar(int id) throws ServiceException {
        try (Connection connection = dataSource.getConnection()) {
            return userBarDaoImpl.findAllCocktailByUserBarId(id, connection);
        } catch (SQLException e) {
            logger.error("Failed to get user's bar", e);
            throw new ServiceException("Failed to find all ingredients");
        }
    }

    @Override
    public Cocktail deleteCocktail(Cocktail cocktail, User user) throws ServiceException {
        try (Connection connection = dataSource.getConnection()) {
            userBarDaoImpl.deleteCocktailFromUserBar(cocktail.getCocktailName(), user.getId(), connection);
        } catch (SQLException e) {
            logger.error("Failed to delete cocktail from user's bar", e);
            throw new ServiceException("Failed to find all ingredients");
        }
        return null;
    }

    /**
     * Deleting all duplicate cocktails takes 2 steps:
     * deleting all cocktail from user's bar with current name;
     * adding one copy of cocktail with current name.
     * In this method is used transaction.
     * @param cocktail
     * @param user
     * @return
     * @throws ServiceException
     */
    @Override
    public Cocktail deleteDuplicateCocktail(Cocktail cocktail, User user) throws ServiceException {

        Connection connection = null;
        try {
            connection = dataSource.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            connection.setAutoCommit(false);
            userBarDaoImpl.deleteCocktailFromUserBar(cocktail.getCocktailName(), user.getId(), connection);
            userBarDaoImpl.addCocktailToUserBar(user.getId(), cocktail, connection);
            connection.commit();
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            logger.error("Failed to delete duplicate cocktail from user's bar", e);
            throw new ServiceException("Failed to find all ingredients");
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
