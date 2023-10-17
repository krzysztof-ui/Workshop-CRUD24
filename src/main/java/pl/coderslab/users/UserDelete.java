package pl.coderslab.users;

import DAO.UserDao;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/user/delete")
public class UserDelete extends HttpServlet {
   // W metodzie **doGet** wyświetlimy formularz z pliku **jsp** wypełniony danymi edytowanego użytkownika

//```java

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        UserDao userDao = new UserDao();

        try {
            userDao.delete(Integer.parseInt(request.getParameter("id")));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        response.sendRedirect(request.getContextPath() + "/users/list.jsp");

    }
}
