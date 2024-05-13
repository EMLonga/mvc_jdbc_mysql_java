package client.view;


import server.model.Account;
import server.model.AccountFrame;

import javax.swing.*;

import javax.swing.plaf.basic.BasicPanelUI;
import java.awt.*;
import java.io.*;
import java.net.Socket;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*
 *   登录窗口
 */
public class LoginFrame extends JFrame {
    private static final int PORT = 9999;
    public LoginFrame() {
        super("HI登录界面");
        //获取显示屏的大小
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int sw = screenSize.width;
        int sh = screenSize.height;
        int width = 1000;
        int height = 750;
        this.setBounds((sw - width) / 2, (sh - height) / 2, width, height);// 设置窗口的位置
        JPanel imagePanel = (JPanel) this.getContentPane(); // 把内容窗格转化为JPanel，否则不能用方法setOpaque()来使内容窗格透明
        imagePanel.setOpaque(false); // 窗口透明

        JPanel panel = new JPanel();
        //盒子模块
        Box ubox = Box.createHorizontalBox();
        Box pbox = Box.createHorizontalBox();
        Box vbox = Box.createVerticalBox();

        //创建界面工具类
        JLabel uLabel = new JLabel("用户ID:"); //文本设计
        uLabel.setFont(new Font("宋体", Font.PLAIN, 50));
        uLabel.setForeground(Color.BLACK); // 设置前景色
        JTextField uField = new JTextField(); //输入框
        uField.setFont(new Font("宋体", Font.PLAIN, 40));
        uField.setToolTipText("用户名");// 悬停显示
        uField.setColumns(15);

        JLabel pLabel = new JLabel("密  码:");    //文本设计
        pLabel.setFont(new Font("宋体", Font.PLAIN, 50));
        pLabel.setForeground(Color.BLACK); // 设置前景色
        JPasswordField pFd = new JPasswordField();
        pFd.setFont(new Font("宋体", Font.PLAIN, 40));
        pFd.setToolTipText("密码");// 悬停显示
        pFd.setColumns(15);
        // 如果使用其他回显字符，可以设置大小，但是星星不可以。。。
        pFd.setEchoChar('●');// 星星符号

        JButton button1 = new JButton("登录");    //登录按钮
        button1.setToolTipText("登录");// 悬停显示
        JButton button2 = new JButton("重置");    //重置按钮
        button2.setToolTipText("重置");// 悬停显示
        JButton Menubutton3 = new JButton("注册账号");    //重置按钮
        Menubutton3.setToolTipText("注册账号");// 悬停显示
        // 字体设置
        button1.setFont(new Font("宋体", Font.PLAIN, 40));
        button1.setForeground(Color.white); // 设置前景色
        button1.setBackground(new Color(0x08BDFD));
        button1.setDefaultCapable(true);
        button1.setBounds((this.getWidth() - 110 - 180) / 2, 350, 120, 45); // 设置按钮位置，及按钮大小
        button1.setCursor(new Cursor(Cursor.HAND_CURSOR));  //鼠标手势的设置

        button2.setFont(new Font("宋体", Font.PLAIN, 40));
        button2.setForeground(Color.white); // 设置前景色
        button2.setBackground(new Color(0x08BDFD));
        button2.setDefaultCapable(true);
        button2.setCursor(new Cursor(Cursor.HAND_CURSOR));//鼠标手势的设置
        button2.setBounds((this.getWidth() - 110 + 180) / 2, 350, 120, 45); // 设置按钮位置，及按钮大小

        Menubutton3.setFont(new Font("宋体", Font.PLAIN, 40));
        Menubutton3.setForeground(new Color(0x02FCFC)); // 设置前景色
        Menubutton3.setBounds(20, 400, 240, 50); // 设置按钮位置，及按钮大小
        Menubutton3.setContentAreaFilled(false); // 设置按钮透明
        Menubutton3.setCursor(new Cursor(Cursor.HAND_CURSOR));

        /*
         分区模块布局
        */
        //小盒子，设计用户名布局模块
        ubox.add(uLabel);
        ubox.add(Box.createHorizontalStrut(5));//插入中间盒子宽度为5，作为相邻文本的空隙
        ubox.add(uField);
        //小盒子，设计密码框布局模块
        pbox.add(pLabel);
        pbox.add(Box.createHorizontalStrut(5));//插入中间盒子宽度为5，作为相邻文本的空隙
        pbox.add(pFd);

        //大盒子
        vbox.add(Box.createVerticalStrut(90));//插入中间盒子高度为80，作为上下文本的空隙
        vbox.add(ubox);
        vbox.add(Box.createVerticalStrut(60));//插入中间盒子高度为60，作为上下文本的空隙
        vbox.add(pbox);


        uField.setText("3037502828");   //设置默认账号
        pFd.setText("123456");  //设置默认密码
        panel.setUI(new BasicPanelUI());  //恢复基本视觉效果
        panel.setOpaque(false); // 面板透明
        panel.add(vbox, BorderLayout.CENTER);//vbox盒子居中
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.add(button1);
        this.add(button2);
        this.add(Menubutton3);
        this.add(panel);
        this.setVisible(true);
        this.setResizable(false);

        //点击按钮的监听事件
        button1.addActionListener(e -> {    //登录按钮监听事件
            try {
                //正则法则，验证输入数据的字符是否都是数字
                String HI =uField.getText().trim();
                String regex = "^\\d+$";
                Pattern p =Pattern.compile(regex);
                Matcher m = p.matcher(HI);
                if(m.matches()){//匹配成功，则传入输入的数据
                    init(this,uField, pFd);
                }else {
                    JOptionPane.showMessageDialog(null, "只能输入数字，请正确输入！", "警告", JOptionPane.WARNING_MESSAGE);
                }
            } catch (Exception exception) {
                JOptionPane.showMessageDialog(null, "异常", "警告", JOptionPane.WARNING_MESSAGE);
            }
        });
        button2.addActionListener(e -> {//重置按钮监听事件
            uField.setText("");
            pFd.setText("");
        });
        Menubutton3.addActionListener(e -> {//跳转到注册窗口按钮监听事件
            try {
                this.dispose();
                Thread.sleep(10);
                new RegisterFrame();
            } catch (InterruptedException interruptedException) {
                JOptionPane.showMessageDialog(null, "异常", "警告", JOptionPane.WARNING_MESSAGE);
            }
        });
    }
    public void init(LoginFrame loginDemo,JTextField uField, JPasswordField pFd) {
        Login login;
        try {
            login = new Login();
            login.setLoginFrame(loginDemo);
            login.setId(uField.getText());
            char[] p = pFd.getPassword();
            login.setPassword(new String(p));
            //增加
            Socket socket = new Socket("127.0.0.1",PORT);
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
            String s = "LoginFrame";
            oos.writeObject(s);
            oos.writeObject(login);
            oos.flush();
            Account account = (Account) ois.readObject();
            ois.close();
            oos.close();
            if(account!=null) {
                this.dispose();
                new AccountFrame(account);
            }else{
                JOptionPane.showMessageDialog(null,"账户或密码错误，请重新输入");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "异常", "警告", JOptionPane.WARNING_MESSAGE);
        }
    }


}