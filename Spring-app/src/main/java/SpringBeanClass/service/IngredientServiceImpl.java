package SpringBeanClass.service;

import SpringBeanClass.dao.IngredientDaoImpl;
import SpringBeanClass.entity.Ingredient;
import org.apache.log4j.Logger;

import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
/**
 * Implementation of IngredientService interface.
 * This class implements all the logic of working with the entity Ingredient.
 * This class work with IngredientDao.
 * In the methods of this class creates connection for transfer to Database.
 */
@Service
public class IngredientServiceImpl implements IngredientService{
    private final IngredientDaoImpl ingredientDaoImpl;
    private static final Logger logger = Logger.getLogger(IngredientServiceImpl.class);
    private final DataSource dataSource;
    public IngredientServiceImpl(IngredientDaoImpl ingredientDaoImpl, DataSource dataSource) {
        this.ingredientDaoImpl = ingredientDaoImpl;
        this.dataSource = dataSource;
    }
    @Override
    public Ingredient create(String name) throws ServiceException {
        Ingredient ingredient = new Ingredient();
        ingredient.setName(name);
        try (Connection connection = dataSource.getConnection()) {
            return ingredientDaoImpl.createIngredient(ingredient, connection);
        } catch (SQLException e) {
            logger.error("Failed to create ingredient", e);
            throw new ServiceException("Failed to create ingredient");
        }
    }
    @Override
    public Ingredient getById(int id) throws ServiceException {
        try (Connection connection = dataSource.getConnection()) {
            return ingredientDaoImpl.findById(id, connection);
        } catch (SQLException e) {
            logger.error("Failed to get ingredient by id", e);
            throw new ServiceException("Failed to get ingredient by id");
        }
    }
    @Override
    public Ingredient getByName(String name) throws ServiceException {
        try (Connection connection = dataSource.getConnection()) {
            return ingredientDaoImpl.findByName(name, connection);
        } catch (SQLException e) {
            logger.error("Failed to get ingredient by name", e);
            throw new ServiceException("Failed to get ingredient by name");
        }
    }
    @Override
    public List<Ingredient> findAll() throws ServiceException {
        try (Connection connection = dataSource.getConnection()) {
            return ingredientDaoImpl.findAllIngredients(connection);
        } catch (SQLException e) {
            logger.error("Failed to find all ingredients", e);
            throw new ServiceException("Failed to find all ingredients");
        }
    }

    /**
     * This method checks is ingredient exist in database and return true or false.
     * Method is used to prevent duplicates in database.
     * @param name
     * @return
     * @throws ServiceException
     */
    @Override
    public boolean checkIngredientInDatabase(String name) throws ServiceException {
        try {
            List<Ingredient> allIngredient = findAll();
            for (Ingredient ingredient : allIngredient) {
                if (ingredient.getName().equals(name)) {
                    return true;
                }
            }
        } catch (SQLException e) {
            logger.error("Failed to check is ingredient exist in database", e);
            throw new ServiceException("Failed to check is ingredient exist in database");
        }
        return false;
    }

}
