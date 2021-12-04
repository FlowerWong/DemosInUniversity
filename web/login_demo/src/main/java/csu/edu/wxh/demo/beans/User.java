package csu.edu.wxh.demo.beans;

/**
 * @author FlowerWang
 */
public class User {
    private final String account;
    private final String password;

    public User(String account, String password) {
        this.account = account;
        this.password = password;
    }

    public String getAccount() {
        return account;
    }

    public String getPassword() {
        return password;
    }
}
