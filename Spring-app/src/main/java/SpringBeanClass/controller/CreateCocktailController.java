package SpringBeanClass.controller;

import SpringBeanClass.service.CocktailServiceImpl;
import SpringBeanClass.entity.Cocktail;
import SpringBeanClass.entity.Ingredient;
import org.apache.log4j.Logger;
import SpringBeanClass.service.CocktailIngredientServiceImpl;
import SpringBeanClass.service.IngredientServiceImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;
/**
 * The class realized logic of creating new cocktail and adding that cocktail to database.
 * The cocktail's parameters is got by request parameter.
 * Сhecks if the created cocktail exists in the database.
 * Сhecks if the created cocktail's ingredients exist in the database.
 */
public class CreateCocktailController implements Controller {
    private static final Logger logger = Logger.getLogger(CreateCocktailController.class);
    private CocktailServiceImpl cocktailServiceImpl;
    private IngredientServiceImpl ingredientServiceImpl;
    private CocktailIngredientServiceImpl cocktailIngredientServiceImpl;

    @Override
    public ControllerResultDto execute(HttpServletRequest req, HttpServletResponse resp) {
        try {
            String cocktailName = req.getParameter("cocktailName");
            String recipe = req.getParameter("recipe");
            String cocktailType = req.getParameter("cocktailType");
            String cocktailHistory = req.getParameter("cocktailHistory");
            String cocktailIcon = req.getParameter("cocktailIcon");
            String cocktailPhoto = req.getParameter("cocktailPhoto");
            String ingredientsName = req.getParameter("ingredientsName");
            if (!cocktailServiceImpl.checkCocktailInDatabase(cocktailName)) {
                Cocktail cocktailFromDatabase = cocktailServiceImpl.create(cocktailName, recipe, cocktailType, cocktailHistory, cocktailIcon, cocktailPhoto);
                if (Objects.isNull(cocktailFromDatabase)) {
                    return new ControllerResultDto("error-403");
                } else {
                    Cocktail cocktail = cocktailServiceImpl.getByName(cocktailName);
                    for (String ingredient : ingredientsName.split(",")) {
                        if (!ingredientServiceImpl.checkIngredientInDatabase(ingredient)) {
                            Ingredient ingredientFromDatabase = ingredientServiceImpl.create(ingredient);
                            if (Objects.isNull(ingredientFromDatabase)) {
                                return new ControllerResultDto("error-403");
                            }
                            Ingredient currentCreatedIngredient = ingredientServiceImpl.getByName(ingredient);
                            cocktailIngredientServiceImpl.setChainCocktailIngredient(cocktail.getCocktailId(), currentCreatedIngredient.getIngredientId());
                        } else {
                            Ingredient currentIngredient = ingredientServiceImpl.getByName(ingredient);
                            cocktailIngredientServiceImpl.setChainCocktailIngredient(cocktail.getCocktailId(), currentIngredient.getIngredientId());

                        }
                    }
                }
            } else {
                return new ControllerResultDto("cocktailAlreadyInDB");
            }
            return new ControllerResultDto("success");
        } catch (Exception e) {
            logger.error("Failed to get results from service (create cocktail, set chain cocktailID->ingredientID)", e);
            return new ControllerResultDto("error-500");
        }
    }
}