package client.view;

import java.io.Serializable;

public class Login implements Serializable {

    String id;
    String password;
    LoginFrame loginDemo;
    boolean loginSuccess = false;

    public LoginFrame getLoginDemo() {
        return loginDemo;
    }
    public void setLoginFrame(LoginFrame loginDemo) {
        this.loginDemo = loginDemo;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isLoginSuccess() {
        return loginSuccess;
    }

    public void setLoginSuccess(boolean loginSuccess) {
        this.loginSuccess = loginSuccess;
    }


}