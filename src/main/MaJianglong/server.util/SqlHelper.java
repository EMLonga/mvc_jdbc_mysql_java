package server.util;

import client.view.Login;
import client.view.Register;
import server.model.Account;

import javax.swing.*;
import java.sql.*;


public class SqlHelper {
    private static final String URL = "jdbc:mysql://localhost:3306/Bankmjl";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "123456";
    private Connection connection = null;
    PreparedStatement prepare;
    ResultSet resultSet;
    boolean loginSuccess;

    public SqlHelper() {

        try {
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public Account readLogin(Login login){
        try {
            String sql = "SELECT * FROM User WHERE accNo = ? AND  UserPassword = ?";//查询语句
            prepare = connection.prepareStatement(sql);//创建执行环境
            prepare.setString(1, login.getId());
            prepare.setString(2, login.getPassword());
            resultSet = prepare.executeQuery();//执行查询语句,如数据匹配成功，则返回true
            if (resultSet.next()) { //迭代查询
                sql = "SELECT * FROM account2022011049 WHERE accNo = ?";//查询语句
                prepare = connection.prepareStatement(sql);//创建执行环境
                prepare.setString(1, login.getId());
                resultSet = prepare.executeQuery();//执行查询语句,如数据匹配成功，则返回true
                if(resultSet.next()){
                    float money = resultSet.getFloat("balance");
                    String accName = resultSet.getString("accName");
                    Account account = new Account();
                    account.setAccName(accName);
                    account.setBalance(money);
                    account.setAccNo(login.getId());
                    JOptionPane.showMessageDialog(null, "登录成功");
                    return account;

                }
            } else {
                login.setLoginSuccess(false);
                if (login.getId().isEmpty() || login.getPassword().isEmpty()) {
                    if (login.getId().isEmpty()) {
                        JOptionPane.showMessageDialog(null, "用户名不能为空！", "提示", JOptionPane.WARNING_MESSAGE);
                    }
                    if (login.getPassword().isEmpty()){
                        JOptionPane.showMessageDialog(null, "密码不能为空！","提示", JOptionPane.WARNING_MESSAGE);
                    }
                }else{
                    JOptionPane.showMessageDialog(null, "用户名不存在！", "警告", JOptionPane.ERROR_MESSAGE);
                }
            }


        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "异常", "警告", JOptionPane.ERROR_MESSAGE);
        }
        finally {
            //释放资源和空间
            try {
                connection.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            try {
                prepare.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            try {
                resultSet.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            loginSuccess = login.isLoginSuccess();
        }

        return null;
    }
    public Account writeRegister(Register register){
        int flag;
        try {

            String sql = "INSERT INTO User VALUES (?,?,?)"; //插入语句
            prepare = connection.prepareStatement(sql);
            prepare.setString(1,register.getId());
            prepare.setString(2,register.getPassword());
            prepare.setString(3,register.getName());
            flag = prepare.executeUpdate(); //成功插入式返回1
            if(flag == 1) {

                String checkUserExistsQuery = "SELECT Count(*) FROM account2022011049 WHERE accNo = ?";
                PreparedStatement checkUserExistsStatement = connection.prepareStatement(checkUserExistsQuery);
                checkUserExistsStatement.setString(1, register.getId());
                ResultSet resultSet = checkUserExistsStatement.executeQuery();
                if(resultSet.next()){
                    int count = resultSet.getInt(1);
                    if(count>0) {
                        JOptionPane.showMessageDialog(null,"用户已存在");
                    }else {
                        String sql1 = "INSERT INTO account2022011049 VALUES (?,?,?,?)";
                        PreparedStatement prepare1 = connection.prepareStatement(sql1);
                        prepare1.setString(1, register.getId());
                        prepare1.setString(2, register.getName());
                        prepare1.setDouble(3, 0.0);
                        prepare1.setDate(4, new java.sql.Date(System.currentTimeMillis()));                     flag = prepare1.executeUpdate(); //成功插入式返回1
                        if (flag == 1) {

                            Account account = new Account();
                            account.setAccNo(register.getId());
                            account.setAccName(register.getName());
                            account.setBalance(0);
                            return account;
                        }
                    }
                }
            }else {
                JOptionPane.showMessageDialog(null,"注册失败","提示",JOptionPane.WARNING_MESSAGE);
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null,"用户名已存在！","警告",JOptionPane.WARNING_MESSAGE);
        }
        try {
            prepare.close();
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return null;
    }

    public boolean deleteAccount(String accNo) {
        try {
            // 检查账户是否存在
            String checkAccountQuery = "SELECT COUNT(*) FROM account2022011049 WHERE accNo = ?";
            PreparedStatement checkAccountStatement = connection.prepareStatement(checkAccountQuery);
            checkAccountStatement.setString(1, accNo);
            ResultSet resultSet = checkAccountStatement.executeQuery();
            if (resultSet.next()) {
                int count = resultSet.getInt(1);
                if (count > 0) {
                    // 账户存在，执行删除操作
                    String deleteAccountQuery = "DELETE FROM account2022011049 WHERE accNo = ?";
                    PreparedStatement deleteAccountStatement = connection.prepareStatement(deleteAccountQuery);
                    deleteAccountStatement.setString(1, accNo);
                    int rowsAffected = deleteAccountStatement.executeUpdate();
                    if (rowsAffected > 0) {
                        // 删除 User 表中对应的账户信息
                        String deleteUserQuery = "DELETE FROM User WHERE accNo = ?";
                        PreparedStatement deleteUserStatement = connection.prepareStatement(deleteUserQuery);
                        deleteUserStatement.setString(1, accNo);
                        int rowsAffectedUser = deleteUserStatement.executeUpdate();
                        if (rowsAffectedUser > 0) {
                            System.out.println("账户删除成功");
                            return true;
                        } else {
                            System.out.println("删除 User 表中的账户信息失败");
                        }
                    } else {
                        System.out.println("账户删除失败");
                    }
                } else {
                    System.out.println("账户不存在");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "删除账户时发生错误", "错误", JOptionPane.ERROR_MESSAGE);
        }
        return false;
    }

    public boolean transfer(String fromAccNo, String toAccNo, double amount) {
        try {
            // 检查转出账户和转入账户是否存在
            String checkFromAccountQuery = "SELECT COUNT(*) FROM account2022011049 WHERE accNo = ?";
            PreparedStatement checkFromAccountStatement = connection.prepareStatement(checkFromAccountQuery);
            checkFromAccountStatement.setString(1, fromAccNo);
            ResultSet fromAccountResult = checkFromAccountStatement.executeQuery();

            String checkToAccountQuery = "SELECT COUNT(*) FROM account2022011049 WHERE accNo = ?";
            PreparedStatement checkToAccountStatement = connection.prepareStatement(checkToAccountQuery);
            checkToAccountStatement.setString(1, toAccNo);
            ResultSet toAccountResult = checkToAccountStatement.executeQuery();

            if (fromAccountResult.next() && toAccountResult.next()) {
                int fromAccountCount = fromAccountResult.getInt(1);
                int toAccountCount = toAccountResult.getInt(1);

                if (fromAccountCount > 0 && toAccountCount > 0) {
                    // 查询转出账户余额
                    String getBalanceQuery = "SELECT balance FROM account2022011049 WHERE accNo = ?";
                    PreparedStatement getBalanceStatement = connection.prepareStatement(getBalanceQuery);
                    getBalanceStatement.setString(1, fromAccNo);
                    ResultSet balanceResult = getBalanceStatement.executeQuery();

                    if (balanceResult.next()) {
                        double fromAccountBalance = balanceResult.getDouble("balance");

                        if (fromAccountBalance >= amount) {
                            // 更新转出账户余额
                            String updateFromAccountQuery = "UPDATE account2022011049 SET balance = balance - ? WHERE accNo = ?";
                            PreparedStatement updateFromAccountStatement = connection.prepareStatement(updateFromAccountQuery);
                            updateFromAccountStatement.setDouble(1, amount);
                            updateFromAccountStatement.setString(2, fromAccNo);
                            int updateFromAccountRows = updateFromAccountStatement.executeUpdate();

                            if (updateFromAccountRows > 0) {
                                // 更新转入账户余额
                                String updateToAccountQuery = "UPDATE account2022011049 SET balance = balance + ? WHERE accNo = ?";
                                PreparedStatement updateToAccountStatement = connection.prepareStatement(updateToAccountQuery);
                                updateToAccountStatement.setDouble(1, amount);
                                updateToAccountStatement.setString(2, toAccNo);
                                int updateToAccountRows = updateToAccountStatement.executeUpdate();

                                if (updateToAccountRows > 0) {
                                    // 记录转账信息
                                    String insertTransactionQuery = "INSERT INTO transaction2022011049 (fromAccNo, toAccNo, amount, transactionTime) VALUES (?, ?, ?, ?)";
                                    PreparedStatement insertTransactionStatement = connection.prepareStatement(insertTransactionQuery);
                                    insertTransactionStatement.setString(1, fromAccNo);
                                    insertTransactionStatement.setString(2, toAccNo);
                                    insertTransactionStatement.setDouble(3, amount);
                                    insertTransactionStatement.setDate(4,new java.sql.Date(System.currentTimeMillis()));
                                    int insertTransactionRows = insertTransactionStatement.executeUpdate();
                                    if (insertTransactionRows > 0) {
                                        System.out.println("转账成功");
                                        return true;
                                    } else {
                                        System.out.println("记录转账信息失败");
                                    }
                                } else {
                                    System.out.println("更新转入账户余额失败");
                                }
                            } else {
                                System.out.println("更新转出账户余额失败");
                            }
                        } else {
                            System.out.println("转出账户余额不足");
                        }
                    }
                } else {
                    System.out.println("转出账户或转入账户不存在");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "转账时发生错误", "错误", JOptionPane.ERROR_MESSAGE);
        }
        return false;
    }
    public Account updateAccountInfo(String accNo) {
        // 查询数据库，获取最新的账户信息并更新界面显示
        try {
            String query = "SELECT accName, balance FROM account2022011049 WHERE accNo = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, accNo);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                String accName = resultSet.getString("accName");
                double balance = resultSet.getDouble("balance");
                Account account = new Account();
                account.setAccNo(accNo);
                account.setBalance((float) balance);
                account.setAccName(accName);
                return account;
            } else {
                JOptionPane.showMessageDialog(null,"更新数据失败");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
    public boolean updateText_Name(String accId,String name) {
        try {
            String updateQuery = "UPDATE account2022011049 SET accName = ? WHERE accNo = ?";
            PreparedStatement statement = connection.prepareStatement(updateQuery);
            statement.setString(1, name);
            statement.setString(2, accId);
            int rowsUpdated = statement.executeUpdate();

            if (rowsUpdated > 0) {
                String updateUserQuery = "UPDATE User SET accName = ? WHERE accNo = ?";
                PreparedStatement updateUserStatement = connection.prepareStatement(updateUserQuery);
                updateUserStatement.setString(1,name);
                updateUserStatement.setString(2,accId);
                int rowsUpdated1 = updateUserStatement.executeUpdate();
                if(rowsUpdated1>0) {
                    JOptionPane.showMessageDialog(null, "用户名更新成功");
                    return true;
                }else{
                    JOptionPane.showMessageDialog(null,"更新失败");
                }
            } else {
                JOptionPane.showMessageDialog(null, "未找到要更新的账户");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "更新用户名时发生异常：" + e.getMessage());
        }
        return false;
    }
    //存款
    public boolean deposit(String accNo, double amount) {
        try {
            // 检查账户是否存在
            String checkAccountQuery = "SELECT COUNT(*) FROM account2022011049 WHERE accNo = ?";
            PreparedStatement checkAccountStatement = connection.prepareStatement(checkAccountQuery);
            checkAccountStatement.setString(1, accNo);
            ResultSet accountResult = checkAccountStatement.executeQuery();

            if (accountResult.next()) {
                int accountCount = accountResult.getInt(1);

                if (accountCount > 0) {
                    // 更新账户余额
                    String updateBalanceQuery = "UPDATE account2022011049 SET balance = balance + ? WHERE accNo = ?";
                    PreparedStatement updateBalanceStatement = connection.prepareStatement(updateBalanceQuery);
                    updateBalanceStatement.setDouble(1, amount);
                    updateBalanceStatement.setString(2, accNo);
                    int updateBalanceRows = updateBalanceStatement.executeUpdate();

                    if (updateBalanceRows > 0) {
                        System.out.println("存款成功");
                        return true;
                    } else {
                        System.out.println("更新账户余额失败");
                    }
                } else {
                    System.out.println("账户不存在");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "存款时发生错误", "错误", JOptionPane.ERROR_MESSAGE);
        }
        return false;
    }
    //取款
    public boolean withdraw(String accNo, double amount) {
        try {
            // 检查账户是否存在
            String checkAccountQuery = "SELECT COUNT(*) FROM account2022011049 WHERE accNo = ?";
            PreparedStatement checkAccountStatement = connection.prepareStatement(checkAccountQuery);
            checkAccountStatement.setString(1, accNo);
            ResultSet accountResult = checkAccountStatement.executeQuery();

            if (accountResult.next()) {
                int accountCount = accountResult.getInt(1);

                if (accountCount > 0) {
                    // 查询账户余额
                    String getBalanceQuery = "SELECT balance FROM account2022011049 WHERE accNo = ?";
                    PreparedStatement getBalanceStatement = connection.prepareStatement(getBalanceQuery);
                    getBalanceStatement.setString(1, accNo);
                    ResultSet balanceResult = getBalanceStatement.executeQuery();

                    if (balanceResult.next()) {
                        double currentBalance = balanceResult.getDouble("balance");

                        if (currentBalance >= amount) {
                            // 更新账户余额
                            String updateBalanceQuery = "UPDATE account2022011049 SET balance = balance - ? WHERE accNo = ?";
                            PreparedStatement updateBalanceStatement = connection.prepareStatement(updateBalanceQuery);
                            updateBalanceStatement.setDouble(1, amount);
                            updateBalanceStatement.setString(2, accNo);
                            int updateBalanceRows = updateBalanceStatement.executeUpdate();

                            if (updateBalanceRows > 0) {
                                System.out.println("取款成功");
                                return true;
                            } else {
                                System.out.println("更新账户余额失败");
                            }
                        } else {
                            System.out.println("账户余额不足");
                        }
                    }
                } else {
                    System.out.println("账户不存在");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "取款时发生错误", "错误", JOptionPane.ERROR_MESSAGE);
        }
        return false;
    }


}
