

import com.mysql.cj.x.protobuf.MysqlxCrud;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Calendar;
import java.util.Objects;

public class ServerSettings {
    private ServerSocket serverSocket;
    private Socket socket;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;

    public void Server_proslushka() {
        Registration reg = new Registration();
        Authorization aut = new Authorization();
        Products prod = new Products();
        Orders order=new Orders();
        Users user=new Users();
        while (socket.isConnected()) {
            String Login = null;
            String Password = null;
            String command = null;

            try {
                command = bufferedReader.readLine();

                switch (command) {
                    case "registration.user" -> {
                        Login = bufferedReader.readLine();
                        Password = bufferedReader.readLine();
                        reg.SingUpUser(Login, Password);

                    }
                    case "registration.admin" -> {
                        Login = bufferedReader.readLine();
                        Password = bufferedReader.readLine();
                        reg.SingUpAdmin(Login, Password);
                    }
                    case "authorization.admin" -> {
                        Login = bufferedReader.readLine();
                        Password = bufferedReader.readLine();
                        aut.SingInAdmin(Login, Password);
                    }
                    case "authorization.user" -> {
                        Login = bufferedReader.readLine();
                        Password = bufferedReader.readLine();
                        aut.SingInUser(Login, Password);
                    }
                    case "check_users"->user.View_all_users();
                    case "add_users" ->
                    {
                        String Name_user = bufferedReader.readLine();
                        String Password_user = bufferedReader.readLine();

                        user.add_new_users(Name_user,Password_user);
                    }
                    case "del_users" ->
                    {
                        int id_product = Integer.parseInt(bufferedReader.readLine());

                        user.del_users(id_product);

                    }
                    case "edit_users"->
                    {
                        command = bufferedReader.readLine();
                        String[] num=null;
                        num = command.split("/");
                        user.edit_users(num[0], num[1], Integer.parseInt(num[2]));
                    }
                    case "check_orders_user" ->
                    {
                        order.View_user_orders();
                    }
                    case "check_orders_admin" ->
                    {
                        order.View_all_orders();
                    }
                    case "add_orders"->
                    {
                        command = bufferedReader.readLine();
                        String[] num=null;
                        num = command.split("/");
                        order.add_new_orders(num[0],num[1],Integer.parseInt(num[2]),Integer.parseInt(num[3]));

                    }
                    case "calculate_profit_date"->
                    {
                        command = bufferedReader.readLine();
                        String[] num=null;
                        num = command.split("/");
                        order.calculate_profit_date(num[0],num[1]);
                    }
                    case "calculate_profit_all"->
                    {
                        order.calculate_profit_all();
                    }
                    case "del_orders" ->
                    {
                        int id_order = Integer.parseInt(bufferedReader.readLine());

                        order.del_orders(id_order);

                    }
                    case "check_products" -> prod.View_all_products();

                    case "add_products" ->
                    {
                        String Name_product = bufferedReader.readLine();
                        String Gender_product = bufferedReader.readLine();
                        int Cost_product = Integer.parseInt(bufferedReader.readLine());
                        int Amount_product = Integer.parseInt(bufferedReader.readLine());
                        prod.add_new_products(Name_product,Gender_product, Cost_product, Amount_product);
                    }
                    case "del_products" ->
                    {
                        int id_product = Integer.parseInt(bufferedReader.readLine());

                        prod.del_products(id_product);

                    }
                    case "edit_products"->
                    {
                        command = bufferedReader.readLine();
                        String[] num=null;
                        num = command.split("/");
                        prod.edit_products(num[0], num[1], Integer.parseInt(num[2]));
                    }
                }
//

            } catch (IOException e) {
                System.out.println("Client disconnected!");
//                closeEverything(socket, bufferedReader, bufferedWriter);
                break;
            }
        }
    }

    public void sendMessageToClient(String messageToClient) throws IOException {
        bufferedWriter.write(messageToClient);
        bufferedWriter.newLine();
        bufferedWriter.flush();
    }

    public ServerSettings(ServerSocket serverSocket) {
        try {
            this.serverSocket = serverSocket;
            this.socket = serverSocket.accept();
            this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            System.out.println("The client has joined the server!");
        } catch (IOException e) {
            System.out.println("Error creating server");
            e.printStackTrace();
//            closeEverything(socket, bufferedReader, bufferedWriter);
        }
    }

}
