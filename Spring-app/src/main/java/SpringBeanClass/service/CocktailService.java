package SpringBeanClass.service;

import SpringBeanClass.entity.Cocktail;
import org.springframework.stereotype.Component;

import java.util.List;
/**
 * Defines all methods that will be used with the entity Cocktail.
 */
@Component
public interface CocktailService {
    void delete(int id) throws ServiceException;

    Cocktail create(String cocktailName, String recipe, String cocktailType, String cocktailHistory, String icon, String photo) throws ServiceException;

    Cocktail getById(int id) throws ServiceException;

    Cocktail getByName(String name) throws ServiceException;

    List<Cocktail> findAll() throws ServiceException;

    boolean checkCocktailInDatabase(String name) throws ServiceException;
}