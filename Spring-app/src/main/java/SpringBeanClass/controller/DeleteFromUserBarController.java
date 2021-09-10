package SpringBeanClass.controller;

import SpringBeanClass.service.CocktailServiceImpl;
import SpringBeanClass.entity.Cocktail;
import SpringBeanClass.entity.User;
import org.apache.log4j.Logger;

import SpringBeanClass.service.UserBarServiceImpl;
import SpringBeanClass.service.UserServiceImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
/**
 * The class realized logic of deleting current cocktail from user's bar.
 * The cocktail's id is got by request parameter.
 * The user's id is got by session parameter.
 */
public class DeleteFromUserBarController implements Controller {
    private static final Logger logger = Logger.getLogger(DeleteFromUserBarController.class);
    private UserServiceImpl userServiceImpl;
    private CocktailServiceImpl cocktailServiceImpl;
    private UserBarServiceImpl userBarServiceImpl;

    @Override
    public ControllerResultDto execute(HttpServletRequest req, HttpServletResponse resp) {
        try {
            String cocktailId = req.getParameter("cocktailId");
            Cocktail cocktail = cocktailServiceImpl.getById(Integer.parseInt(cocktailId));
            Integer userId = (Integer) req.getSession().getAttribute("userId");
            User user = userServiceImpl.getById(userId);
            userBarServiceImpl.deleteCocktail(cocktail, user);
            req.setAttribute("userBar", userBarServiceImpl.getUserBar(user.getId()));
            return new ControllerResultDto("mybar");
        } catch (Exception e) {
            logger.error("Failed to get results from service (delete cocktail, get user's bar)", e);
            return new ControllerResultDto("error-500");
        }
    }
}
