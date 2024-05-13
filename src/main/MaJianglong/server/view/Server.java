package server.view;
import client.view.Login;
import client.view.Register;
import server.model.Account;
import server.util.SqlHelper;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private static final int PORT = 9999;

    public static void main(String[] args) {
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(PORT);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Server started...");
            while (true) {
                try {
                    Socket clientSocket = serverSocket.accept();
                    System.out.println("Client connected: " + clientSocket.getInetAddress());
                    ServerHandler serverHandler = new ServerHandler(clientSocket);
                    new Thread(serverHandler).start();

                } catch (IOException e) {
                    throw new RuntimeException(e);
                }


            }

        }
}
class ServerHandler implements Runnable{
    ObjectInputStream ois;
    ObjectOutputStream oos;

    Socket clientSocket;
    SqlHelper sqlHelper;
    ServerHandler(Socket clientSocket){
        try {
            this.clientSocket = clientSocket;
            ois = new ObjectInputStream(clientSocket.getInputStream());
            oos = new ObjectOutputStream(clientSocket.getOutputStream());
            sqlHelper = new SqlHelper();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void run() {

            try {
                //读取操作指令
                String s = (String)ois.readObject();
                System.out.println(s);
                if("LoginFrame".equals(s)){
                    Login login = (Login) ois.readObject();
                    System.out.println("登录账户");
                    Account account = sqlHelper.readLogin(login);
                    oos.writeObject(account);
                    oos.flush();
                }else if("RegisterFrame".equals(s)){
                    Register register = (Register) ois.readObject();
                    System.out.println("注册账户");
                    Account account = sqlHelper.writeRegister(register);
                    oos.writeObject(account);
                    oos.flush();
                }else if("deleteAccount".equals(s)){
                    String accNo = (String) ois.readObject();
                    System.out.println("删除账户");
                    boolean YES = sqlHelper.deleteAccount(accNo);
                    oos.writeObject(YES);
                    oos.flush();
                }else if("updateText".equals(s)){
                    String accNo = (String) ois.readObject();
                    System.out.println("更新账户");
                    Account account = sqlHelper.updateAccountInfo(accNo);
                    oos.writeObject(account);
                    oos.flush();
                }else if("updateText_Name".equals(s)){
                    String accNo = (String) ois.readObject();
                    String name = (String) ois.readObject();
                    System.out.println("修改账户");
                    boolean result = sqlHelper.updateText_Name(accNo,name);
                    oos.writeObject(result);
                    oos.flush();
                }else if("transfer1".equals(s)){
                    synchronized (this.getClass()) {
                        String from = (String) ois.readObject();
                        String to = (String) ois.readObject();
                        double amount = (double) ois.readObject();
                        boolean result = sqlHelper.transfer(from, to, amount);
                        oos.writeObject(result);
                        oos.flush();
                    }
                }else if("add_Balance".equals(s)){
                    String accNo = (String) ois.readObject();
                    Double balance = (Double) ois.readObject();
                    boolean deposit = sqlHelper.deposit(accNo, balance);
                    oos.writeObject(deposit);
                    oos.flush();

                }else if("dec_Balance".equals(s)){
                    String accNo = (String) ois.readObject();
                    Double balance = (Double) ois.readObject();
                    boolean deposit = sqlHelper.withdraw(accNo, balance);
                    oos.writeObject(deposit);
                    oos.flush();
                }
                else{
                    System.out.println("指令错误");
                    oos.flush();
                }
                clientSocket.close();
                oos.close();
                ois.close();
            } catch (EOFException e) {
                System.out.println("指令失败");
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }

    }
}
