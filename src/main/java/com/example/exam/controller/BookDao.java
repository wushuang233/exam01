package com.example.exam.controller;

import com.example.exam.model.Book;

import java.sql.*;

/**
 * @author wushuang
 */

public class BookDao {
    Connection connection = null;
    String driver = "com.mysql.cj.jdbc.Driver";
    String url = "jdbc:mysql://localhost:3306/exam01";
    PreparedStatement ps = null;
    public int insertBook(Book book) throws ClassNotFoundException, SQLException {
        int count = 0;
        try{
            Class.forName(driver);
            connection = DriverManager.getConnection(url,"root","root");
            String sql = "insert into tb_book(number,name,author,description) value(?,?,?,?)";
            ps = connection.prepareStatement(sql);
            ps.setString(1,book.getNumber());
            ps.setString(2, book.getName());
            ps.setString(3, book.getAuthor());
            ps.setString(4, book.getDescription());
            count = ps.executeUpdate();
        }catch (ClassNotFoundException e){
            throw new RuntimeException();
        }finally {
            connection.close();
            ps.close();
        }
        return count;
    }

    public int delBook(String name) throws ClassNotFoundException, SQLException {
        int count = 0;
        try{
            Class.forName(driver);
            connection = DriverManager.getConnection(url,"root","root");
            String sql = "delete from tb_book where name = ?";
            ps = connection.prepareStatement(sql);
            ps.setString(1,name);
            count = ps.executeUpdate();
        }catch (ClassNotFoundException e){
            throw new RuntimeException();
        }finally {
            connection.close();
            ps.close();
        }
        return count;
    }

    public int updateBook(Book book) throws ClassNotFoundException, SQLException {
        int count = 0;
        try{
            Class.forName(driver);
            connection = DriverManager.getConnection(url,"root","root");
            String sql = "update tb_book set author = ?,description = ? where name = ?";
            ps = connection.prepareStatement(sql);
            ps.setString(3,book.getName());
            ps.setString(1,book.getAuthor());
            ps.setString(2,book.getDescription());
            count = ps.executeUpdate();
        }catch (ClassNotFoundException e){
            throw new RuntimeException();
        }finally {
            connection.close();
            ps.close();
        }
        return count;
    }


    public Book selectBook(String name) throws ClassNotFoundException, SQLException {
        ResultSet rs = null;
        Book book = new Book();
        try{
            Class.forName(driver);
            connection = DriverManager.getConnection(url,"root","root");
            String sql = "select * from tb_book where name = ?";
            ps = connection.prepareStatement(sql);
            ps.setString(1,name);
            rs = ps.executeQuery();
            if(rs.next()){
                book.setName(rs.getString("name"));
                book.setAuthor(rs.getString("author"));
                book.setDescription(rs.getString("description"));
                book.setNumber(rs.getString("number"));
            }
        }catch (ClassNotFoundException e){
            throw new RuntimeException();
        }
        return book;
    }
}
