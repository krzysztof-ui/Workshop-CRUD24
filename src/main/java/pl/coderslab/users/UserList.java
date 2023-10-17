package pl.coderslab.users;

import DAO.UserDao;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

@WebServlet("/user/list")
public class UserList extends HttpServlet {

        protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

            UserDao userDao = new UserDao();

            //DAO.User user = new DAO.User();
            DAO.User[] users = new DAO.User[0];
            try {
                users = userDao.findAll();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }


                request.setAttribute("users", users);


            getServletContext().getRequestDispatcher("/users/list.jsp")

                    .forward(request, response);

        }
}
