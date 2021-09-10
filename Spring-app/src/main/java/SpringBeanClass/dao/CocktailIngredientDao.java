package SpringBeanClass.dao;

import SpringBeanClass.entity.Cocktail;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.util.HashMap;
import java.util.List;
/**
 * Defines all methods that will be used with the entity Ingredient and Cocktail simultaneously in database.
 */
@Repository

public interface CocktailIngredientDao {
    HashMap<String, List<Cocktail>> findAllCocktailsByIngredientName(String name, Connection connection) throws DaoException;

    void create(int cocktailId, int ingredientId, Connection connection) throws DaoException;
}
