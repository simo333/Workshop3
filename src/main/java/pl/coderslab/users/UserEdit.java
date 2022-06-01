package pl.coderslab.users;

import pl.coderslab.entities.User;
import pl.coderslab.entities.UserDAO;
import pl.coderslab.utils.UserFormValidator;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "UserEdit", value = "/user/edit")
public class UserEdit extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        UserDAO userDAO = new UserDAO();
        request.setAttribute("users", userDAO.findAll());
        getServletContext().getRequestDispatcher("/users/edit.jsp")
                .forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        if (UserFormValidator.validateForm(request)) {
            User editUser = new User();
            editUser.setUserName(username);
            editUser.setEmail(email);
            editUser.setPassword(password);
            UserDAO userDAO = new UserDAO();
            if (!userDAO.findByEmail(editUser)) {
                userDAO.update(editUser);
                response.sendRedirect("/user/list");
            } else {
                request.setAttribute("emailError", "Ten email jest już zajęty.");
            }
        }
        if (!response.isCommitted()) {
            getServletContext().getRequestDispatcher("/users/edit.jsp")
                    .forward(request, response);
        }
    }


}
