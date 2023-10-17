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

@WebServlet("/user/add")
public class UserAdd extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        getServletContext().getRequestDispatcher("/users/add.jsp")

                .forward(request, response);

    }

    @Override

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        DAO.User user = new DAO.User();

        user.setUserName(req.getParameter("userName"));

        user.setEmail(req.getParameter("userEmail"));

        user.setPassword(req.getParameter("userPassword"));
        DAO.UserDao userDao = new DAO.UserDao();
        try {
            userDao.create(user);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        resp.sendRedirect(req.getContextPath() + "/users/list.jsp");

    }



}
