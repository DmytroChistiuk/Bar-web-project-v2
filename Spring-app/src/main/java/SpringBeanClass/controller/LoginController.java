package SpringBeanClass.controller;

import SpringBeanClass.entity.User;
import org.apache.log4j.Logger;
import SpringBeanClass.service.UserServiceImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static SpringBeanClass.util.Sha256Encryption.getSha256;
/**
 * The class realized logic of login.
 * The user's parameters is got by request parameter.
 * The user's id sets to session parameter.
 */
public class LoginController implements Controller {
    private static final Logger logger = Logger.getLogger(LoginController.class);
    private UserServiceImpl userServiceImpl;

    @Override
    public ControllerResultDto execute(HttpServletRequest req, HttpServletResponse resp) {
        try {
            String login = req.getParameter("login");
            String password = req.getParameter("password");
            User user = userServiceImpl.getByUserLogin(login);
            if (user.getPassword().equals(getSha256(password))) {
                req.setAttribute("user", user);
                HttpSession session = req.getSession();
                session.setAttribute("userId", user.getId());
                return new ControllerResultDto("profile", true);
            } else {
                return new ControllerResultDto("error-403");
            }
        } catch (Exception e) {
            logger.error("Failed to get results from service (get user)", e);
            return new ControllerResultDto("error-500");
        }
    }
}
