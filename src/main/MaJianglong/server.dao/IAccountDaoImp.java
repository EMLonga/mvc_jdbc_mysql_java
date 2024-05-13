package server.dao;

import server.idao.IAccountDao;
import server.model.Account;


import javax.swing.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class IAccountDaoImp implements IAccountDao {
    private static final int PORT = 9999;

    @Override
    public boolean deleteAccount(String accNo) {
        int result = JOptionPane.showConfirmDialog(null, "确定要删除账户吗？", "确认删除", JOptionPane.YES_NO_OPTION);
        boolean YES = false;
        try {
            Socket socket = new Socket("127.0.0.1",PORT);
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
            String s = "deleteAccount";
            oos.writeObject(s);
            oos.writeObject(accNo);
            oos.flush();
            YES = (boolean) ois.readObject();
            ois.close();
            oos.close();
            return YES;
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }
    public Account updateText(String accId){
        try {
            Socket socket = new Socket("127.0.0.1",PORT);
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
            String s = "updateText";
            oos.writeObject(s);
            oos.writeObject(accId);
            oos.flush();
            Account account = (Account) ois.readObject();
            ois.close();
            oos.close();
            return account;
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    public void updateText_Name(String accId,String name){
        try {
            Socket socket = new Socket("127.0.0.1",PORT);
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
            String s = "updateText_Name";
            oos.writeObject(s);
            oos.writeObject(accId);
            oos.writeObject(name);
            oos.flush();
            boolean result = (boolean)ois.readObject();
            ois.close();
            oos.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public boolean transfer1(String fromAccount){
        JTextField accountField = new JTextField();
        JTextField amountField = new JTextField();

        Object[] message = {
                "目的地账户ID:", accountField,
                "转账金额:", amountField
        };

        int option = JOptionPane.showConfirmDialog(null, message, "转账信息", JOptionPane.OK_CANCEL_OPTION);
        if(option == JOptionPane.OK_OPTION){
            try {
                double amount = Double.parseDouble(amountField.getText());
                // 处理转账操作

                try {
                    Socket socket = new Socket("127.0.0.1",PORT);
                    ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
                    ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                    String s = "transfer1";
                    oos.writeObject(s);
                    oos.writeObject(fromAccount);
                    oos.writeObject(accountField.getText());
                    oos.writeObject(amount);
                    oos.flush();
                    boolean result = (boolean) ois.readObject();

                    ois.close();
                    oos.close();
                    return result;
                } catch (IOException e) {
                    throw new RuntimeException(e);
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }




            } catch (NumberFormatException e) {
                // 处理无效输入的情况，例如显示错误消息或者进行其他逻辑
                JOptionPane.showMessageDialog(null, "请输入有效的金额", "错误", JOptionPane.ERROR_MESSAGE);
            }
        }
        return false;

    }

    @Override
    public boolean add_Balance(String accNo) {
        JTextField bField = new JTextField();

        Object[] message = {
                "请输入存款金额:", bField
        };

        int option = JOptionPane.showConfirmDialog(null, message, "存款信息", JOptionPane.OK_CANCEL_OPTION);
        if(option == JOptionPane.OK_OPTION) {
            try {
                double amount = Double.parseDouble(bField.getText());
                // 处理转账操作
                try {
                    Socket socket = new Socket("127.0.0.1",PORT);
                    ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
                    ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                    String s = "add_Balance";
                    oos.writeObject(s);
                    oos.writeObject(accNo);
                    oos.writeObject(Double.parseDouble(bField.getText()));
                    oos.flush();
                    boolean result = (boolean) ois.readObject();

                    ois.close();
                    oos.close();
                    return result;
                } catch (IOException e) {
                    throw new RuntimeException(e);
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }




            } catch (NumberFormatException e) {
                // 处理无效输入的情况，例如显示错误消息或者进行其他逻辑
                JOptionPane.showMessageDialog(null, "请输入有效的金额", "错误", JOptionPane.ERROR_MESSAGE);
            }
        }
        return false;
    }

    @Override
    public boolean dec_Balance(String accNo) {
        JTextField bField = new JTextField();

        Object[] message = {
                "请输入取款金额:", bField
        };

        int option = JOptionPane.showConfirmDialog(null, message, "取款信息", JOptionPane.OK_CANCEL_OPTION);
        if(option == JOptionPane.OK_OPTION) {
            try {
                double amount = Double.parseDouble(bField.getText());
                // 处理转账操作
                try {
                    Socket socket = new Socket("127.0.0.1",PORT);
                    ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
                    ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                    String s = "dec_Balance";
                    oos.writeObject(s);
                    oos.writeObject(accNo);
                    oos.writeObject(Double.parseDouble(bField.getText()));
                    oos.flush();
                    boolean result = (boolean) ois.readObject();

                    ois.close();
                    oos.close();
                    return result;
                } catch (IOException e) {
                    throw new RuntimeException(e);
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }




            } catch (NumberFormatException e) {
                // 处理无效输入的情况，例如显示错误消息或者进行其他逻辑
                JOptionPane.showMessageDialog(null, "请输入有效的金额", "错误", JOptionPane.ERROR_MESSAGE);
            }
        }
        return false;
    }
}
