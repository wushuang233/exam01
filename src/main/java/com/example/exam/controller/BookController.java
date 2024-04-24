package com.example.exam.controller;

import com.example.exam.Utils.JSONUtils;
import com.example.exam.model.Book;
import com.example.exam.model.User;
import com.google.gson.Gson;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@WebServlet(name = "book", value = "/book/*")
public class BookController extends HttpServlet {

    BookDao bookDao = new BookDao();
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");
        String action = request.getRequestURI();
        switch (action) {
            case "/book/search":
                try {
                    doSearch(request, response);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
                break;
        }
    }
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");
        String action = request.getRequestURI();
        UserController userController = new UserController();
//        System.out.println(userController.isLogin(request, response));
        if(!userController.isLogin(request, response)){
            Map<String, Object> map = new HashMap<>();
            map.put("code", 301);
            map.put("msg", "请先登录");
            response.getWriter().write(new Gson().toJson(map));
            return;
        }
        switch (action) {
            case "/book/insert":
                try {
                    doInsert(request, response);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
                break;
            case "/book/update":
                try {
                    doUpdate(request, response);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }break;
            case "/book/del":
                try {
                    doDel(request, response);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
                break;
        }
    }

    public void doSearch(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException, ClassNotFoundException {

        String name = request.getParameter("name");
        Map map = new HashMap<>();
        Gson gson = new Gson();
        if(null == name || "".equals(name.trim())){
            map.put("code", 200);
            map.put("msg", "请输入书名");
            response.getWriter().write(gson.toJson(map));
            return ;
        }
        Book data = bookDao.selectBook(name);
        if(data == null || null == data.getName() || !data.getName().equals("" + name)){
            map.put("code", 200);
            map.put("msg", "查无此书");
        }else {
            map.put("code", 200);
            map.put("msg", "查询成功");
            map.put("data", data);
        }
        response.getWriter().write(gson.toJson(map));
    }

    public void doInsert(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException, ClassNotFoundException {
        String str = JSONUtils.readJson(request);
        Gson gson = new Gson();
        Map map = new HashMap<>();
        Book book = gson.fromJson(str, Book.class);
        if(null == book || null == book.getName() || null == book.getAuthor() || null == book.getDescription()){
            map.put("code", 200);
            map.put("msg", "请输入完整信息");
            response.getWriter().write(gson.toJson(map));
            return ;
        }
        if(bookDao.insertBook(book) == 1){
            map.put("code", 200);
            map.put("msg", "添加成功");
        }else {
            map.put("code", 200);
            map.put("msg", "添加失败");
        }
        response.getWriter().write(gson.toJson(map));
   }

   public void doUpdate(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException, ClassNotFoundException {
       String str = JSONUtils.readJson(request);
       Gson gson = new Gson();
       Book book = gson.fromJson(str, Book.class);
       Map map = new HashMap<>();
       if(null == book || null == book.getName()){
           map.put("code", 200);
           map.put("msg", "请输入书名");
           response.getWriter().write(gson.toJson(map));
           return ;
       }
       if(bookDao.updateBook(book) == 1){
           map.put("code", 200);
           map.put("msg", "修改成功");
       }else {
           map.put("code", 200);
           map.put("msg", "修改失败");
       }
       response.getWriter().write(gson.toJson(map));
   }

   public void doDel(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException, ClassNotFoundException {

        String str = JSONUtils.readJson(request);
       Gson gson = new Gson();
       Book book = gson.fromJson(str, Book.class);
       Map map = new HashMap<>();

       if(null == book || null == book.getName()){
           map.put("code", 200);
           map.put("msg", "请输入书名");
           response.getWriter().write(gson.toJson(map));
           return ;
       }

       if(bookDao.delBook(book.getName()) == 1){
           map.put("code", 200);
           map.put("msg", "删除成功");
       }else {
           map.put("code", 200);
           map.put("msg", "删除失败");
       }
       response.getWriter().write(gson.toJson(map));
   }
}
