import java.io.IOException;
import java.net.Socket;
import java.sql.*;

public class Registration extends DatabaseHandler{

    public void SingUpUser(String Login, String Password) throws IOException {
        String proverka = "ok";
        try {
            Statement statement = getDbConnection().createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT User_name, User_password FROM users");
//                    + Const.USER_LOGIN + ", " + Const.USER_PASSWORD + " FROM" + Const.USER_TABLE);
//            PreparedStatement prSt = null;

            while (resultSet.next())
            {
                String name = resultSet.getString(1);
                String password = resultSet.getString(2);
                Socket in = null;
                if (Login.equals(name)) {
                    proverka = "not_ok";
                }
                System.out.println(name + "," + password);
            }

        } catch (Exception ex) {
            System.out.println("Connection failed...");

            System.out.println(ex);
        }


        if (proverka.equals("ok"))
        {
            String insert = "INSERT INTO " + Const.USER_TABLE + "(" +
                    Const.USER_LOGIN + "," + Const.USER_PASSWORD + ")" +
                    "VALUES(?, ?)";
            PreparedStatement prSt = null;

            try {
                prSt = getDbConnection().prepareStatement(insert);
                prSt.setString(1, Login);
                prSt.setString(2, Password);
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
        Main.server.sendMessageToClient(proverka);
    }
    public void SingUpAdmin(String Login, String Password) throws IOException {
        String proverka = "ok";
        try {
            Statement statement = getDbConnection().createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT Admin_name, Admin_password FROM admins");


            while (resultSet.next())
            {
                String name = resultSet.getString(1);
                String password = resultSet.getString(2);
                Socket in = null;
                if (Login.equals(name)) {
                    proverka = "not_ok";
                }
//                System.out.println(name + "," + password);
            }

        } catch (Exception ex) {
            System.out.println("Connection failed...");

            System.out.println(ex);
        }


        if(proverka.equals("ok"))
        {
            String insert = "INSERT INTO " + Const.ADMIN_TABLE + "(" +
                    Const.ADMIN_LOGIN +","+ Const.ADMIN_PASSWORD + ")" +
                    "VALUES(?, ?)";
            PreparedStatement prSt = null;

            try {
                prSt = getDbConnection().prepareStatement(insert);
                prSt.setString(1, Login);
                prSt.setString(2, Password);
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
        Main.server.sendMessageToClient(proverka);
    }

}
