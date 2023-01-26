import java.io.BufferedReader;
import java.net.Socket;
import java.sql.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Users extends DatabaseHandler {
    public static int id_enter_user;
    public void View_all_users() {
        try {
            Statement statement = getDbConnection().createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT idUsers, User_name, User_password FROM users");
            while (resultSet.next()) {
                String line_of_users = null;
                int id = resultSet.getInt(1);

                String name = resultSet.getString(2);
                String password = resultSet.getString(3);

                line_of_users = id + "," + name + "," + password;
                Main.server.sendMessageToClient(line_of_users);
            }
            Main.server.sendMessageToClient("stop");
        } catch (Exception ex) {
            System.out.println("Connection failed...");
            System.out.println(ex);
        }
    }

    public void add_new_users(String name, String password) {
        String insert = "INSERT INTO " + Const.USER_TABLE + "(" +
                Const.USER_LOGIN + "," + Const.USER_PASSWORD + ")" +
                "VALUES(?, ?)";
        PreparedStatement prSt = null;

        try {
            prSt = getDbConnection().prepareStatement(insert);
            prSt.setString(1, name);
            prSt.setString(2, password);

        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        //обновление
        try {
            prSt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void del_users(int id_users)
    {
        String insert = "DELETE FROM users WHERE idUsers = ('" + id_users + "')";
        PreparedStatement prSt = null;
        try {
            prSt = getDbConnection().prepareStatement(insert);

        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        //обновление
        try {
            prSt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void edit_users(String command, String column, int id_users) //column название параметра который надо изменить
    {
        switch (command) {
            case "Users_name" -> {
                String insert = "UPDATE users SET User_name = '" + column + "' WHERE idUsers = '" + id_users + "'";
                System.out.println(insert);
                PreparedStatement prSt = null;
                try {
                    prSt = getDbConnection().prepareStatement(insert);
                    prSt.executeUpdate();
                } catch (SQLException | ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }

            }
            case "Users_password" -> {
                String insert = "UPDATE users SET User_password = '" + column + "' WHERE idUsers = '" + id_users + "'";
                System.out.println(insert);
                PreparedStatement prSt = null;
                try {
                    prSt = getDbConnection().prepareStatement(insert);
                    prSt.executeUpdate();
                } catch (SQLException | ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }



        }

    }
}
