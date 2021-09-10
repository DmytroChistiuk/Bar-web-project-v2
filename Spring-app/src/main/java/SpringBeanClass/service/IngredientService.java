package SpringBeanClass.service;

import SpringBeanClass.entity.Ingredient;
import org.springframework.stereotype.Component;

import java.util.List;
/**
 * Defines all methods that will be used with the entity Ingredient.
 */
@Component
public interface IngredientService {
    Ingredient create(String name) throws ServiceException;

    Ingredient getById(int id) throws ServiceException;

    Ingredient getByName(String name) throws ServiceException;

    List<Ingredient> findAll() throws ServiceException;

    boolean checkIngredientInDatabase(String name) throws ServiceException;
}
