package SpringBeanClass.controller;

import SpringBeanClass.entity.Ingredient;
import org.apache.log4j.Logger;
import SpringBeanClass.service.IngredientServiceImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
/**
 * The class realized logic of finding all ingredients which exist in database.
 * The result will send by respond parameter.
 */
public class ShowAllIngredientController implements Controller {
    private static final Logger logger = Logger.getLogger(ShowAllIngredientController.class);
    private IngredientServiceImpl ingredientServiceImpl;

    @Override
    public ControllerResultDto execute(HttpServletRequest req, HttpServletResponse resp) {
        try {
            List<Ingredient> ingredients = ingredientServiceImpl.findAll();
            req.setAttribute("ingredients", ingredients);
            return new ControllerResultDto("allIngredients");
        } catch (Exception e) {
            logger.error("Failed to get results from service (find all ingredient)", e);
            return new ControllerResultDto("error-500");
        }
    }
}
