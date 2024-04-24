package com.example.exam.controller;

import com.example.exam.model.User;

import java.sql.*;
import java.util.Locale;

/**
 * @author wushuang
 */
public class UserDao {
    public int register(User user) throws ClassNotFoundException, SQLException {
        Connection connection = null;
        String driver = "com.mysql.cj.jdbc.Driver";
        String url = "jdbc:mysql://localhost:3306/exam01";
        PreparedStatement ps = null;
        int count = 0;
        try{
            Class.forName(driver);
            connection = DriverManager.getConnection(url,"root","root");
            Statement statement = connection.createStatement();
            String sql = "insert into tb_user(username,password) value(?,?)";
            ps = connection.prepareStatement(sql);
            ps.setString(1,user.getUsername());
            ps.setString(2,user.getPassword());
            count = ps.executeUpdate();
        }catch (ClassNotFoundException e){
            throw new RuntimeException();
        }finally {
            connection.close();
            ps.close();
        }
        return count;
    }

    public User login(User user) throws ClassNotFoundException, SQLException {
        Connection connection = null;
        String driver = "com.mysql.cj.jdbc.Driver";
        String url = "jdbc:mysql://localhost:3306/exam01";
        PreparedStatement ps = null;
        ResultSet rs;
        User data = new User();
        try{
            Class.forName(driver);
            connection = DriverManager.getConnection(url,"root","root");
            String sql = "select * from tb_user where username = ? and password = ?";
            ps = connection.prepareStatement(sql);
            ps.setString(1,user.getUsername());
            ps.setString(2,user.getPassword());
            rs = ps.executeQuery();
            if(rs.next()) {
                data.setUsername(rs.getString("username"));
                data.setPassword(rs.getString("password"));
            }
        }catch (ClassNotFoundException e){
            throw new RuntimeException();
        }finally {
            connection.close();
            ps.close();
        }
        return data;
    }
}
