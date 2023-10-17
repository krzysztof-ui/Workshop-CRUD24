package pl.coderslab.users;

import DAO.User;
import DAO.UserDao;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/user/edit")
public class UserEdit extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String id = request.getParameter("id");
        UserDao userDao = new UserDao();
        User read = null;
        try {
            read = userDao.read(Integer.parseInt(id));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        request.setAttribute("user", read);

        getServletContext().getRequestDispatcher("/users/edit.jsp")

                .forward(request, response);

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        User user = new User();
        user.setId(Integer.parseInt(req.getParameter("id")));
        user.setUserName(req.getParameter("userName"));
        user.setEmail(req.getParameter("userEmail"));
        user.setPassword(req.getParameter("userPassword"));
        UserDao userDao = new UserDao();

        try {
            userDao.update(user);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        resp.sendRedirect(req.getContextPath() + "/users/list.jsp");

    }

}
