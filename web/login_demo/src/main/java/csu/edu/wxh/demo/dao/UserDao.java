package csu.edu.wxh.demo.dao;

import csu.edu.wxh.demo.beans.User;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;

/**
 * @author FlowerWang
 */
public class UserDao {
    Map<String, User> users = new HashMap<>();

    public Map<String, User> queryAllUser() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/web", "root", "this is a secret");

            PreparedStatement ps = connection.prepareStatement("select* from user");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                users.put(rs.getString("account"), new User(rs.getString("account"), rs.getString("password")));
            }
            ps.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return users;
    }
}
