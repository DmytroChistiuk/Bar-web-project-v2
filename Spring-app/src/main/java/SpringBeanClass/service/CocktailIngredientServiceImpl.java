package SpringBeanClass.service;

import SpringBeanClass.dao.CocktailIngredientDaoImpl;
import SpringBeanClass.entity.Cocktail;
import SpringBeanClass.util.MySQLUtil;
import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
/**
 * Implementation of CocktailIngredientService interface.
 * This class implements all the logic of working with the entity Cocktail and Ingredient in the same time.
 * This class work with CocktailIngredientDao.
 * In the methods of this class creates connection for transfer to Database.
 */
@Service
public class CocktailIngredientServiceImpl implements CocktailIngredientService {
    private static final Logger logger = Logger.getLogger(CocktailIngredientServiceImpl.class);
    private final CocktailIngredientDaoImpl cocktailIngredientDaoImpl;

    public CocktailIngredientServiceImpl( CocktailIngredientDaoImpl cocktailIngredientDaoImpl) {
        this.cocktailIngredientDaoImpl = cocktailIngredientDaoImpl;
    }

    /**
     * Gets all cocktails from database which has current ingredient.
     * Return HashMap of key - ingredient name, object - list cocktails.
     * @param name
     * @return
     * @throws ServiceException
     */
    @Override
    public List<Cocktail> getAllCocktailsByIngredientName(String name) throws ServiceException {
        try (Connection connection = MySQLUtil.getConnection()) {
            HashMap<String, List<Cocktail>> currentMap = cocktailIngredientDaoImpl.findAllCocktailsByIngredientName(name, connection);
            return currentMap.get(name);
        } catch (SQLException e) {
            logger.error("Failed to get all cocktails by ingredient name", e);
            throw new ServiceException("Failed to get all cocktails by ingredient name");
        }
    }

    /**
     * This method making connection between cocktails and their ingredients.
     * @param cocktailId
     * @param ingredientId
     * @throws ServiceException
     */
    @Override
    public void setChainCocktailIngredient(int cocktailId, int ingredientId) throws ServiceException {
        try (Connection connection = MySQLUtil.getConnection()) {
            cocktailIngredientDaoImpl.create(cocktailId, ingredientId, connection);
        } catch (SQLException e) {
            logger.error("Failed to set chain cocktailID -> ingredientID", e);
            throw new ServiceException("Failed to set chain cocktailID -> ingredientID");
        }
    }

}
