package pl.coderslab.users;

import pl.coderslab.entities.User;
import pl.coderslab.entities.UserDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "UserAdd", value = "/user/add")
public class UserAdd extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        UserDAO userDAO = new UserDAO();
        request.setAttribute("users", userDAO.findAll());
        getServletContext().getRequestDispatcher("/users/add.jsp")
                .forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        if (formValidator(request)) {
            User newUser = new User();
            newUser.setUserName(username);
            newUser.setEmail(email);
            newUser.setPassword(password);
            new UserDAO().create(newUser);
            response.sendRedirect("/user/list");
        } else {
            getServletContext().getRequestDispatcher("/users/add.jsp")
                    .forward(request, response);
        }
    }

    boolean formValidator(HttpServletRequest request) {
        boolean isValid = true;
        String username = request.getParameter("username");
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        if (username.equals("")) {
            request.setAttribute("usernameError", "Pole 'Nazwa' nie może być puste.");
            isValid = false;
        }
        if (email.equals("")) {
            request.setAttribute("emailError", "Pole 'Email' nie może być puste.");
            isValid = false;
        }
        if (password.equals("")) {
            request.setAttribute("passwordError", "Pole 'Hasło' nie może być puste.");
            isValid = false;
        }
        return isValid;
    }
}
