package SpringBeanClass.service;

import SpringBeanClass.entity.Cocktail;
import SpringBeanClass.entity.User;
import org.springframework.stereotype.Component;

import java.util.List;
/**
 * Defines all methods that will be used with the entity UserBar.
 */
@Component
public interface UserBarService {
    Cocktail addCocktail(int id, Cocktail cocktail) throws ServiceException;
    List<Cocktail> getUserBar(int id) throws ServiceException;
    Cocktail deleteCocktail(Cocktail cocktail, User user) throws ServiceException;
    Cocktail deleteDuplicateCocktail(Cocktail cocktail, User user) throws ServiceException;
}
