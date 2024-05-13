package client.view;

import server.model.Account;
import server.model.AccountFrame;


import javax.swing.*;
import java.awt.*;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*
 *   注册窗口
 */
public class RegisterFrame extends JFrame{
    private static final int PORT = 9999;
    public RegisterFrame() {
        //获取显示屏的大小
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int sw = screenSize.width;
        int sh = screenSize.height;
        this.setTitle("用户注册界面");
        int width = 1000;
        int height = 750;
        this.setBounds((sw - width) / 2, (sh - height) / 2, width, height);// 设置窗口的位置
        JPanel imagePanel = (JPanel) this.getContentPane(); // 把内容窗格转化为JPanel，否则不能用方法setOpaque()来使内容窗格透明
        imagePanel.setOpaque(false); // 窗口透明
        //盒子模块
        Box namebox = Box.createHorizontalBox();
        Box ubox = Box.createHorizontalBox();
        Box pbox = Box.createHorizontalBox();
        Box repbox = Box.createHorizontalBox();
        Box vbox = Box.createVerticalBox();

        JLabel nameLabel = new JLabel("用户 名:"); //文本设计
        nameLabel.setFont(new Font("宋体", Font.PLAIN, 50));
        nameLabel.setForeground(Color.BLACK); // 设置前景色
        JTextField nameField = new JTextField(); //输入框
        nameField.setFont(new Font("宋体", Font.PLAIN, 45));
        nameField.setToolTipText("用户名");
        nameField.setColumns(15);


        JLabel uLabel = new JLabel("用户 ID:"); //文本设计
        uLabel.setFont(new Font("宋体", Font.PLAIN, 50));
        uLabel.setForeground(Color.BLACK); // 设置前景色
        JTextField uField = new JTextField(); //输入框
        uField.setFont(new Font("宋体", Font.PLAIN, 45));
        uField.setToolTipText("ID");
        uField.setColumns(15);

        JLabel pLabel = new JLabel("密     码 :");    //文本设计
        pLabel.setFont(new Font("微软雅黑", Font.PLAIN, 50));
        pLabel.setForeground(Color.BLACK); // 设置前景色
        JPasswordField pFd = new JPasswordField();
        pFd.setFont(new Font("Arial", Font.PLAIN, 45));
        pFd.setToolTipText("密码");// 悬停显示
        pFd.setColumns(15);
        // 如果使用其他回显字符，可以设置大小，但是星星不可以。。。
        pFd.setEchoChar('●');// 星星符号


        JLabel RepLabel = new JLabel("确认密码:");    //文本设计
        RepLabel.setFont(new Font("微软雅黑", Font.PLAIN, 50));
        RepLabel.setForeground(Color.BLACK); // 设置前景色
        JPasswordField RepFd = new JPasswordField();
        RepFd.setFont(new Font("Arial", Font.PLAIN, 45));
        RepFd.setToolTipText("确认密码");// 悬停显示
        RepFd.setColumns(15);
        // 如果使用其他回显字符，可以设置大小，但是星星不可以。。。
        RepFd.setEchoChar('●');// 星星符号

        JButton button1 = new JButton("注册");    //登录按钮
        button1.setToolTipText("注册");// 悬停显示
        JButton button2 = new JButton("重置");    //重置按钮
        button2.setToolTipText("重置");// 悬停显示
        JButton Menubutton3 = new JButton("返回登录");    //重置按钮
        Menubutton3.setToolTipText("返回登录");// 悬停显示

        button1.setFont(new Font("宋体", Font.PLAIN, 30));
        button1.setForeground(Color.white); // 设置前景色
        button1.setBackground(Color.BLACK);
        button1.setDefaultCapable(true);
        button1.setBounds((this.getWidth() - 120 - 180) / 2, this.getHeight() - 150, 120, 40); // 设置按钮位置，及按钮大小
        button1.setCursor(new Cursor(Cursor.HAND_CURSOR));  //鼠标手势的设置

        button2.setFont(new Font("宋体", Font.PLAIN, 30));
        button2.setForeground(Color.white); // 设置前景色
        button2.setBackground(Color.BLACK);
        button2.setDefaultCapable(true);
        button2.setCursor(new Cursor(Cursor.HAND_CURSOR));//鼠标手势的设置
        button2.setBounds((this.getWidth() - 120 + 180) / 2, this.getHeight() - 150, 120, 40); // 设置按钮位置，及按钮大小

        Menubutton3.setFont(new Font("宋体", Font.PLAIN, 30));
        Menubutton3.setForeground(Color.blue); // 设置前景色
        Menubutton3.setBackground(Color.white);
        button2.setDefaultCapable(true);
        Menubutton3.setCursor(new Cursor(Cursor.HAND_CURSOR));
        Menubutton3.setBounds((this.getWidth()-120-180-500)/2, this.getHeight()-150, 240, 40); // 设置按钮位置，及按钮大小


        /*
         分区模块布局
        */

        //小盒子，设计用户名模块
        namebox.add(nameLabel);
        namebox.add(Box.createHorizontalStrut(5));//插入中间盒子宽度为5，作为相邻文本的空隙
        namebox.add(nameField);

        ubox.add(uLabel);
        ubox.add(Box.createHorizontalStrut(5));//插入中间盒子宽度为5，作为相邻文本的空隙
        ubox.add(uField);
        //小盒子，设计密码模块
        pbox.add(pLabel);
        pbox.add(Box.createHorizontalStrut(5));
        pbox.add(pFd);

        repbox.add(RepLabel);
        repbox.add(Box.createHorizontalStrut(5));
        repbox.add(RepFd);

        vbox.add(Box.createVerticalStrut(75));//插入中间盒子高度为90，作为上下文本的空隙
        vbox.add(namebox);
        vbox.add(Box.createVerticalStrut(65));
        vbox.add(ubox);
        vbox.add(Box.createVerticalStrut(65));
        vbox.add(pbox);
        vbox.add(Box.createVerticalStrut(65));
        vbox.add(repbox);


        JPanel panel = new JPanel();
        panel.add(vbox, BorderLayout.CENTER);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.add(button1);
        this.add(button2);
        this.add(Menubutton3);
        this.add(panel);
        this.setVisible(true);
        this.setResizable(false);

        //注册按钮监听
        button1.addActionListener(e -> {
            String pField = new String(pFd.getPassword());//转换
            String repField = new String(RepFd.getPassword());//转换
            try {
                if (nameField.getText().isEmpty() || pField.isEmpty() || repField.isEmpty() || uField.getText().isEmpty()) {
                    if (nameField.getText().isEmpty()) {
                        JOptionPane.showMessageDialog(null, "用户名不能为空", "提示", JOptionPane.WARNING_MESSAGE);
                    } else if (uField.getText().isEmpty()) {
                        JOptionPane.showMessageDialog(null, "HI号不能为空", "提示", JOptionPane.WARNING_MESSAGE);
                    } else if (pField.isEmpty()) {
                        JOptionPane.showMessageDialog(null, "密码不能为空", "提示", JOptionPane.WARNING_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(null, "确认密码不能为空", "提示", JOptionPane.WARNING_MESSAGE);
                    }
                } else {
                    if (!pField.equals(repField)) {
                        JOptionPane.showMessageDialog(null, "两次密码不一致", "提示", JOptionPane.WARNING_MESSAGE);
                    } else {
                        String HI = uField.getText().trim();
                        String regex = "^\\d+$";
                        Pattern p = Pattern.compile(regex);
                        Matcher m = p.matcher(HI);
                        if (m.matches()) {
                            init(nameField, uField, pFd);
                        } else {
                            JOptionPane.showMessageDialog(null, "HI号只能输入数字，请正确输入！", "警告", JOptionPane.WARNING_MESSAGE);
                        }
                    }
                }
            } catch (Exception exception) {
                JOptionPane.showMessageDialog(null, "异常", "警告", JOptionPane.WARNING_MESSAGE);
            }
        });

        //重置按钮监听
        button2.addActionListener(e -> {
            nameField.setText(null);
            uField.setText(null);
            pFd.setText(null);
            RepFd.setText(null);
        });
        Menubutton3.addActionListener(e -> {
            try {
                System.out.println("开始");
                this.dispose();
                new LoginFrame();
                System.out.println("结束");
            } catch (Exception exception) {
                JOptionPane.showMessageDialog(null, "异常", "警告", JOptionPane.WARNING_MESSAGE);
            }
        });
    }


    public void init(JTextField nameField,JTextField uField, JPasswordField pFd) {
        Register register;
        try {
            register = new Register();
            register.setName(nameField.getText());
            register.setId(uField.getText());
            char[] p1 = pFd.getPassword();
            register.setPassword(new String(p1));

            Socket socket = new Socket("127.0.0.1",PORT);
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
            String s = "RegisterFrame";
            oos.writeObject(s);
            oos.writeObject(register);
            oos.flush();
            Account account = (Account) ois.readObject();
            ois.close();
            oos.close();
            this.dispose();
            new AccountFrame(account);
            uField.setText(null);
            nameField.setText(null);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "异常", "警告", JOptionPane.WARNING_MESSAGE);
        }
    }
}