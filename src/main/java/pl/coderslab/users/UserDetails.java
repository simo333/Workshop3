package pl.coderslab.users;

import pl.coderslab.entities.User;
import pl.coderslab.entities.UserDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "UserDetails", value = "/user/show")
public class UserDetails extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        UserDAO userDAO = new UserDAO();
        try {
            int userId = Integer.parseInt(request.getParameter("id"));
            User user = userDAO.read(userId);
            request.setAttribute("userId", userId);
            request.setAttribute("usernameField", user.getUserName());
            request.setAttribute("emailField", user.getEmail());
        } catch (NumberFormatException | NullPointerException e) {
            response.sendError(404);
        }
        if (!response.isCommitted()) {
            getServletContext().getRequestDispatcher("/users/details.jsp")
                    .forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }
}
