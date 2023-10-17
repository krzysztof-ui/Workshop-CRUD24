package DAO;//package pl.coderslab.entity;
import org.mindrot.jbcrypt.BCrypt;
import pl.coderslab.utils.DbUtil;
import pl.coderslab.utils.DbUtil;

import java.sql.*;
import java.util.Arrays;

public class UserDao {

    private static Connection conn;

    static {
        try {
            conn = DbUtil.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);

        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private static final String CREATE_USER_QUERY =

            "INSERT INTO users (username, email, password) VALUES (?, ?, ?)";

    private  static final String readById = "SELECT * FROM users where id = ? ";

    private static final String updateDataOfUser = "UPDATE users SET username = ?, email = ?, password = ? where id = ?";

    private static final String deleteById = "DELETE FROM users where id = ?";

    private static final String findByUserName = "SELECT * FROM users"; //username zaczynający się na podana literę


    public String hashPassword(String password) {

        return BCrypt.hashpw(password, BCrypt.gensalt());

    }

    public DAO.User create(DAO.User user) throws SQLException {

            PreparedStatement statement =

                    conn.prepareStatement(CREATE_USER_QUERY, Statement.RETURN_GENERATED_KEYS);

            statement.setString(1, user.getUserName());

            statement.setString(2, user.getEmail());

            statement.setString(3, hashPassword(user.getPassword()));

            statement.executeUpdate();

            //Pobieramy wstawiony do bazy identyfikator, a następnie ustawiamy id obiektu user.

            ResultSet resultSet = statement.getGeneratedKeys();

            if (resultSet.next()) {

                user.setId(resultSet.getInt(1));

            }

            return user;

    }

    public DAO.User read(int userId) throws SQLException {

            DAO.User user = new DAO.User();
            PreparedStatement statement = conn.prepareStatement(readById);
            statement.setInt(1, userId);
            ResultSet rs = statement.executeQuery();

        //statement.execute();
        while (rs.next()) {
            int id = rs.getInt("id");
            user.setId(id);
            String email = rs.getString("email");
            user.setEmail(email);
            String username = rs.getString("username");
            user.setUserName(username);
            String password = rs.getString("password");
            user.setPassword(password);
            // int id = rs.getInt("id");
        }
       if (user.getUserName() != null){
           return user;
       } else {
            return null;

        }
    }

    public void update(DAO.User user) throws SQLException {

        PreparedStatement statement = conn.prepareStatement(updateDataOfUser, Statement.RETURN_GENERATED_KEYS);

        statement.setString(1, user.getUserName());
        statement.setString(2, user.getEmail());
        statement.setString(3, hashPassword(user.getPassword()));
        statement.setInt(4, user.getId());
        statement.executeUpdate();
    }


    public void delete(int userId) throws SQLException {

        DAO.User user = new DAO.User();
        PreparedStatement statement = conn.prepareStatement(deleteById);

        statement.setInt(1, userId);
        statement.executeUpdate();

    }

    private DAO.User[] addToArray(DAO.User u, DAO.User[] users) {

        DAO.User[] tmpUsers = Arrays.copyOf(users, users.length + 1); // Tworzymy kopię tablicy powiększoną o 1.

        tmpUsers[users.length] = u; // Dodajemy obiekt na ostatniej pozycji.

        return tmpUsers; // Zwracamy nową tablicę.

    }

    public DAO.User[] findAll() throws SQLException, ClassNotFoundException {

            //Class.forName("com.mysql.cj.jdbc.Driver");



        DAO.User[] users = new DAO.User[0];
        //PreparedStatement statement = conn.prepareStatement(findByUserName);
        PreparedStatement statement = conn.prepareStatement(findByUserName);
        //statement.setInt(1, userId);
        ResultSet rs = statement.executeQuery();

        //statement.execute();
        while (rs.next()) {
            DAO.User user = new DAO.User();
            int id = rs.getInt("id");
            user.setId(id);
            String email = rs.getString("email");
            user.setEmail(email);
            String username = rs.getString("username");
            user.setUserName(username);
            String password = rs.getString("password");
            user.setPassword(password);
            users = addToArray(user, users);

        }

        return users;
    }


}
