import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.sql.Date;

public class Orders extends DatabaseHandler {
    public void View_all_orders() {
        try {
            Statement statement = getDbConnection().createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT idOrders, Product_name, Product_gender, Product_cost, " +
                    "Product_amount, idUsers, Date FROM orders");
            while (resultSet.next()) {
                String line_of_products = null;
                int id = resultSet.getInt(1);
                String name = resultSet.getString(2);
                String gender = resultSet.getString(3);
                int cost = resultSet.getInt(4);
                int amount = resultSet.getInt(5);
                int id_users = resultSet.getInt(6);
                String date = resultSet.getString(7);
                String[] num=null;
                num = date.split("-");
                date=num[2]+"."+num[1]+"."+num[0];
                line_of_products = id + "," + name + "," + gender + "," + cost + "," + amount + "," + id_users + "," + date;
                Main.server.sendMessageToClient(line_of_products);
            }
            Main.server.sendMessageToClient("stop");
        } catch (Exception ex) {
            System.out.println("Connection failed...");
            System.out.println(ex);
        }
    }

    public void View_user_orders() {
        try {
            Statement statement = getDbConnection().createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT idOrders, Product_name, Product_gender, Product_cost, " +
                    "Product_amount FROM orders WHERE idUsers= " + Users.id_enter_user);
            while (resultSet.next()) {
                String line_of_products = null;
                int id = resultSet.getInt(1);

                String name = resultSet.getString(2);
                String gender = resultSet.getString(3);
                int cost = resultSet.getInt(4);
                int amount = resultSet.getInt(5);
//                int id_users = resultSet.getInt(6);
                line_of_products = id + "," + name + "," + gender + "," + cost + "," + amount;
                Main.server.sendMessageToClient(line_of_products);
            }
            Main.server.sendMessageToClient("stop");
        } catch (Exception ex) {
            System.out.println("Connection failed...");
            System.out.println(ex);
        }
    }

    public void add_new_orders(String name, String gender, int cost, int amount) {
        String update = "UPDATE products SET Product_amount = Product_amount- " + amount + "  WHERE Product_name = '" + name + "'";
//        System.out.println(insert);
        PreparedStatement prStt = null;
        try {
            prStt = getDbConnection().prepareStatement(update);
            prStt.executeUpdate();
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        String insert = "INSERT INTO " + Const.ORDERS_TABLE + "(" +
                Const.ORDERS_NAME + "," + Const.ORDERS_GENDER + "," + Const.ORDERS_COST + "," + Const.ORDERS_AMOUNT + "," + Const.ORDERS_USERS + "," + Const.ORDERS_DATE + ")" +
                "VALUES(?, ?, ?, ?, ?, ? )";
        PreparedStatement prSt = null;
//        DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        Date date = new Date(System.currentTimeMillis());
//        System.out.println(dateFormat.format(date));
        try {
            prSt = getDbConnection().prepareStatement(insert);
            prSt.setString(1, name);
            prSt.setString(2, gender);
            prSt.setInt(3, cost);
            prSt.setInt(4, amount);
            prSt.setInt(5, Users.id_enter_user);
            prSt.setDate(6, date);
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
    public void calculate_profit_all()
    {
        try {
            Statement statement = getDbConnection().createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT Product_cost, Product_amount FROM orders");
            int profit = 0;
            while (resultSet.next()) {

                int cost = resultSet.getInt(1);
                int amount = resultSet.getInt(2);
                profit+=cost*amount;
            }


            Main.server.sendMessageToClient(String.valueOf(profit));
//            Main.server.sendMessageToClient("stop");
        } catch (Exception ex) {
            System.out.println("Connection failed...");
            System.out.println(ex);
        }
    }

    public void calculate_profit_date(String date1, String date2) {
        try {
            Statement statement = getDbConnection().createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT Product_cost, Product_amount FROM orders WHERE Date BETWEEN '" + date1 + "' AND '" + date2+"'");
            int profit = 0;
            while (resultSet.next()) {

                int cost = resultSet.getInt(1);
                int amount = resultSet.getInt(2);
                profit+=cost*amount;
            }


            Main.server.sendMessageToClient(String.valueOf(profit));
//            Main.server.sendMessageToClient("stop");
        } catch (Exception ex) {
            System.out.println("Connection failed...");
            System.out.println(ex);
        }
    }

    public void del_orders(int id_order) {
        String insert = "DELETE FROM orders WHERE idOrders = ('" + id_order + "')";
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

}