package server.model;

import client.view.LoginFrame;
import server.dao.IAccountDaoImp;
import server.idao.IAccountDao;
import server.util.SqlHelper;

import javax.swing.*;
import javax.swing.plaf.basic.BasicPanelUI;
import java.awt.*;


public class AccountFrame extends JFrame{
    public AccountFrame(Account account) {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int sw = screenSize.width;
        int sh = screenSize.height;
        this.setTitle("账户界面");
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
        JTextField nameField = new JTextField(account.getAccName()); //输入框
        nameField.setFont(new Font("宋体", Font.PLAIN, 45));
        nameField.setToolTipText("用户名");
        nameField.setEditable(false);
        nameField.setColumns(15);


        JLabel uLabel = new JLabel("用户 ID:"); //文本设计
        uLabel.setFont(new Font("宋体", Font.PLAIN, 50));
        uLabel.setForeground(Color.BLACK); // 设置前景色
        JTextField uField = new JTextField(account.getAccNo()); //输入框
        uField.setFont(new Font("宋体", Font.PLAIN, 45));
        uField.setToolTipText("ID");
        uField.setEditable(false);
        uField.setColumns(15);

        JLabel bLabel= new JLabel("账户余额 :");    //文本设计
        bLabel.setFont(new Font("宋体", Font.PLAIN, 50));
        bLabel.setForeground(Color.BLACK); // 设置前景色
        JTextField bField = new JTextField(String.valueOf(account.getBalance()));
        bField.setFont(new Font("Arial", Font.PLAIN, 45));
        bField.setEditable(false);
        bField.setColumns(15);


        JButton button1 = new JButton("修改");    //修改按钮
        button1.setToolTipText("修改");// 悬停显示
        JButton button2 = new JButton("转账");    //转账按钮
        button2.setToolTipText("转账");// 悬停显示
        JButton Menubutton3 = new JButton("注销");    //注销按钮
        Menubutton3.setToolTipText("注销");// 悬停显示
        JButton button4 = new JButton("删除账户");      //删除
        button4.setToolTipText("删除");
        JButton button5 = new JButton("刷新");        //刷新
        button5.setToolTipText("刷新");
        JButton button6 = new JButton("存款");        //取款
        button6.setToolTipText("存款");
        JButton button7 = new JButton("取款");        //取款
        button7.setToolTipText("取款");

        button1.setFont(new Font("宋体", Font.PLAIN, 30));
        button1.setForeground(Color.white); // 设置前景色
        button1.setBackground(Color.BLACK);
        button1.setDefaultCapable(true);
        button1.setBounds((this.getWidth() - 110 - 180+90) / 2, this.getHeight() - 350, 120, 40); // 设置按钮位置，及按钮大小
        button1.setCursor(new Cursor(Cursor.HAND_CURSOR));  //鼠标手势的设置

        button2.setFont(new Font("宋体", Font.PLAIN, 30));
        button2.setForeground(Color.white); // 设置前景色
        button2.setBackground(Color.BLACK);
        button2.setDefaultCapable(true);
        button2.setCursor(new Cursor(Cursor.HAND_CURSOR));//鼠标手势的设置
        button2.setBounds((this.getWidth() - 110 + 180+90+90) / 2, this.getHeight() - 350, 120, 40); // 设置按钮位置，及按钮大小

        Menubutton3.setFont(new Font("宋体", Font.PLAIN, 30));
        Menubutton3.setForeground(Color.white); // 设置前景色
        Menubutton3.setBackground(Color.BLACK);
        button2.setDefaultCapable(true);
        Menubutton3.setCursor(new Cursor(Cursor.HAND_CURSOR));
        Menubutton3.setBounds((this.getWidth()-120-180-360-90+90)/2, this.getHeight()-350, 120, 40); // 设置按钮位置，及按钮大小

        button4.setFont(new Font("宋体", Font.PLAIN, 30));
        button4.setForeground(Color.RED); // 设置前景色
        button4.setBackground(Color.WHITE);
        button4.setDefaultCapable(true);
        button4.setCursor(new Cursor(Cursor.HAND_CURSOR));//鼠标手势的设置
        button4.setBounds(this.getWidth()-250, this.getHeight() - 150, 240, 40); // 设置按钮位置，及按钮大小

        button5.setFont(new Font("宋体", Font.PLAIN, 30));
        button5.setForeground(Color.RED); // 设置前景色
        button5.setBackground(Color.WHITE);
        button5.setDefaultCapable(true);
        button5.setCursor(new Cursor(Cursor.HAND_CURSOR));//鼠标手势的设置
        button5.setBounds(80, this.getHeight() - 150, 120, 40); // 设置按钮位置，及按钮大小

        button6.setFont(new Font("宋体", Font.PLAIN, 30));
        button6.setForeground(Color.white); // 设置前景色
        button6.setBackground(Color.BLACK);
        button6.setDefaultCapable(true);
        button6.setCursor(new Cursor(Cursor.HAND_CURSOR));//鼠标手势的设置
        button6.setBounds((this.getWidth() - 110 - 180+90-180) / 2, this.getHeight() - 230, 120, 40); // 设置按钮位置，及按钮大小

        button7.setFont(new Font("宋体", Font.PLAIN, 30));
        button7.setForeground(Color.white); // 设置前景色
        button7.setBackground(Color.BLACK);
        button7.setDefaultCapable(true);
        button7.setCursor(new Cursor(Cursor.HAND_CURSOR));//鼠标手势的设置
        button7.setBounds((this.getWidth() - 110 - 180+90+180) / 2, this.getHeight() - 230, 120, 40); // 设置按钮位置，及按钮大小
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
        //小盒子，设计金额模块
        pbox.add(bLabel);
        pbox.add(Box.createHorizontalStrut(5));
        pbox.add(bField);


        vbox.add(Box.createVerticalStrut(65));//插入中间盒子高度为90，作为上下文本的空隙
        vbox.add(namebox);
        vbox.add(Box.createVerticalStrut(65));
        vbox.add(ubox);
        vbox.add(Box.createVerticalStrut(65));
        vbox.add(pbox);



        JPanel panel = new JPanel();
        panel.setUI(new BasicPanelUI());  //恢复基本视觉效果
//        panel.setOpaque(false); // 面板透明
        panel.add(vbox, BorderLayout.CENTER);//vbox盒子居中
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.add(button1);
        this.add(button2);
        this.add(Menubutton3);
        this.add(button4);
        this.add(button5);
        this.add(button6);
        this.add(button7);
        this.add(panel);
        this.setVisible(true);
        this.setResizable(false);
        IAccountDao IAccount = new IAccountDaoImp();
        //修改
        button1.addActionListener(e -> {
            JTextField new_nameField = new JTextField();
            Object[] message = {
                    "请输入新的用户名:",new_nameField,
            };
            int option= JOptionPane.showConfirmDialog(null,message,"修改",JOptionPane.OK_CANCEL_OPTION);
            if (JOptionPane.OK_OPTION == option) {
                String name1 = new_nameField.getText();
                nameField.setText(name1);
                IAccount.updateText_Name(uField.getText(),name1);

            }else{
                JOptionPane.showMessageDialog(null, "用户名太长", "错误", JOptionPane.ERROR_MESSAGE);
            }
        });
        //转账
        button2.addActionListener(e -> {
            if(IAccount.transfer1(uField.getText())){

                JOptionPane.showMessageDialog(null,"转账成功");

            }else{
                JOptionPane.showMessageDialog(null,"转账失败");
            }
        });
        //注销
        Menubutton3.addActionListener( e ->{
            this.dispose();
            new LoginFrame();
        });

        //删除

        button4.addActionListener(e -> {
            if(IAccount.deleteAccount(uField.getText())) {
                this.dispose();
                new LoginFrame();
            }else{
                JOptionPane.showMessageDialog(null,"删除失败","警告",JOptionPane.WARNING_MESSAGE);
            }
        });

        //刷新

        button5.addActionListener( e ->{
            Account account1 = IAccount.updateText(uField.getText());
            nameField.setText(account1.accName);
            uField.setText(account1.accNo);
            bField.setText(String.valueOf(account1.balance));
        });

        //存款
        button6.addActionListener(e -> {
            String accID = uField.getText();
            if(IAccount.add_Balance(accID)){
                JOptionPane.showMessageDialog(null,"存款成功");
                Account account1 = IAccount.updateText(uField.getText());
                nameField.setText(account1.accName);
                uField.setText(account1.accNo);
                bField.setText(String.valueOf(account1.balance));
            }else{
                JOptionPane.showMessageDialog(null,"存款失败");
            }
        });
        //取款
        button7.addActionListener(e -> {
            String accID = uField.getText();
            if(IAccount.dec_Balance(accID)){
                JOptionPane.showMessageDialog(null,"取款成功");
                Account account1 = IAccount.updateText(uField.getText());
                nameField.setText(account1.accName);
                uField.setText(account1.accNo);
                bField.setText(String.valueOf(account1.balance));
            }else{
                JOptionPane.showMessageDialog(null,"取款失败");
            }
        });
    }



}
