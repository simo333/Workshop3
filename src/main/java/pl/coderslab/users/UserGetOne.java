package pl.coderslab.users;

import pl.coderslab.entities.User;
import pl.coderslab.entities.UserDAO;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "UserGetOne", value = "/user/get")
public class UserGetOne extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        UserDAO userDAO = new UserDAO();
        try {
            int userId = Integer.parseInt(request.getParameter("id"));
            User user = userDAO.read(userId);
            response.setContentType("text/plain");
            response.getWriter().write(user.getUserName());
        } catch (NumberFormatException | NullPointerException e) {
            response.sendError(404);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
