
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.*;
//import static Registration.dbConnection;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

//import static Main.server;
public class Authorization extends DatabaseHandler {
    //ServerSettings socket=new ServerSettings();
    public void SingInAdmin(String Login, String Password) {
        try {

            Statement statement = getDbConnection().createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT Admin_name, Admin_password FROM admins");
            String proverka = "not_ok";
            while (resultSet.next()) {
                String name = resultSet.getString(1);
                String password = resultSet.getString(2);
                Socket in = null;
                if (Login.equals(name) && Password.equals(password)) {

                    proverka = "ok";
                }

            }
            Main.server.sendMessageToClient(proverka);
        } catch (Exception ex) {
            System.out.println("Connection failed...");

            System.out.println(ex);
        }
    }

    public void SingInUser(String Login, String Password) {
        try {

            Statement statement = getDbConnection().createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT idUsers, User_name, User_password FROM users");

            String proverka = "not_ok";
            while (resultSet.next())
            {
                int id=resultSet.getInt(1);
                String name = resultSet.getString(2);
                String password = resultSet.getString(3);

                Socket in = null;
                if (Login.equals(name) && Password.equals(password)) {
                    Users.id_enter_user=id;
                    System.out.println(id+"//"+ Users.id_enter_user);
                    proverka = "ok";
                }
            }
            Main.server.sendMessageToClient(proverka);
        } catch (Exception ex) {
            System.out.println("Connection failed...");

            System.out.println(ex);
        }

    }


}
