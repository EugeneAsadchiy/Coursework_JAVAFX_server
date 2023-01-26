import java.io.BufferedReader;
import java.net.Socket;
import java.sql.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Products extends DatabaseHandler{
    public void View_all_products() {
        try
        {
            Statement statement = getDbConnection().createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT Product_id, Product_name, Product_gender, Product_cost, " +
                    "Product_amount FROM products");
            while (resultSet.next())
            {
                String line_of_products=null;
                int id = resultSet.getInt(1);
                String name = resultSet.getString(2);
                String gender = resultSet.getString(3);
                int cost = resultSet.getInt(4);
                int amount = resultSet.getInt(5);
                line_of_products=id + ","+name + "," + gender+ ","+cost+ ","+amount;
                Main.server.sendMessageToClient(line_of_products);
            }
            Main.server.sendMessageToClient("stop");
        } catch (Exception ex) {
            System.out.println("Connection failed...");
            System.out.println(ex);
        }
    }

    public void add_new_products(String name, String gender, int cost,int amount)
    {
        String insert = "INSERT INTO " + Const.PRODUCTS_TABLE + "(" +
                Const.PRODUCTS_NAME +","+ Const.PRODUCTS_GENDER +","+ Const.PRODUCTS_COST+","+ Const.PRODUCTS_AMOUNT+ ")" +
                "VALUES(?, ?, ?, ?)";
        PreparedStatement prSt = null;

        try {
            prSt = getDbConnection().prepareStatement(insert);
            prSt.setString(1, name);
            prSt.setString(2, gender);
            prSt.setInt(3, cost);
            prSt.setInt(4, amount);
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
    public void del_products(int id_product)
    {
        String insert = "DELETE FROM products WHERE Product_id = ('" + id_product + "')";
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
    public void edit_products(String command, String column, int id_product) //column название параметра который надо изменить
    {
        switch (command)
        {
            case "Products_name"->
            {
                String insert ="UPDATE products SET Product_name = '"+column+"' WHERE Product_id = '" + id_product +"'";
                System.out.println(insert);
                PreparedStatement prSt = null;
                try {
                    prSt = getDbConnection().prepareStatement(insert);
                    prSt.executeUpdate();
                } catch (SQLException | ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }

            }
            case "Products_gender"->
            {
                String insert ="UPDATE products SET Product_gender = '"+column+"' WHERE Product_id = '" + id_product +"'";
                System.out.println(insert);
                PreparedStatement prSt = null;
                try {
                    prSt = getDbConnection().prepareStatement(insert);
                    prSt.executeUpdate();
                } catch (SQLException | ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }
            case "Products_cost" ->
            {
                String insert ="UPDATE products SET Product_cost = '"+column+"' WHERE Product_id = '" + id_product +"'";
//                System.out.println(insert);
                PreparedStatement prSt = null;
                try {
                    prSt = getDbConnection().prepareStatement(insert);
                    prSt.executeUpdate();
                } catch (SQLException | ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }
            case "Products_amount"->
            {
                String insert ="UPDATE products SET Product_amount = '"+column+"' WHERE Product_id = '" + id_product +"'";
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

//        String command=bufferedReader.readLine();
    }
}
