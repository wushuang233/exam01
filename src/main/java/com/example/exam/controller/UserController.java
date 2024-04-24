package com.example.exam.controller;

import com.example.exam.Utils.JSONUtils;
import com.example.exam.model.User;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;

/**
 * @author wushuang
 */
@WebServlet(name = "user", value = "/user/*")
public class UserController extends HttpServlet {

    UserDao userdao = new UserDao();
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) {
        response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");
        String action = request.getRequestURI();


    }
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html; charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        String action = request.getRequestURI();
        switch (action) {
            case "/user/register" : {
                try {
                    doRegistration(request, response);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }break;
            case "/user/login" : {
                try {
                    doLogin(request, response);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }break;
        }

    }

    public void doLogin(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException, ClassNotFoundException {
        String str = JSONUtils.readJson(request);
        Gson gson = new Gson();
        User user = gson.fromJson(str,User.class);
        Map map = new HashMap<>();
        if(null == user || null == user.getUsername() || null == user.getPassword()){
            map.put("code",4000);
            map.put("msg","用户名或密码不能为空");
            response.getWriter().write(gson.toJson(map));
            return;
        }
        User data = userdao.login(user);
        if(data == null || !(user.getPassword().equals(data.getPassword()) && user.getUsername().equals(data.getUsername()))){
            map.put("code",4000);
            map.put("msg","用户名或密码错误");
        }else{
            map.put("code",0);
            map.put("msg","登录成功");
            request.getSession().setAttribute("user",data.getUsername());
        }
        response.getWriter().write(gson.toJson(map));
    }

    public void doRegistration(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException, ClassNotFoundException {
        String str = JSONUtils.readJson(request);
        Gson gson = new Gson();
        User user = gson.fromJson(str,User.class);
        Map map = new HashMap<>();
        if(null == user || null == user.getUsername() || null == user.getPassword()){
            map.put("code",4000);
            map.put("msg","用户名或密码不能为空");
            response.getWriter().write(gson.toJson(map));
            return;
        }
        int status = userdao.register(user);

        if(status == 1){
            map.put("code",200);
            map.put("msg","注册成功");
        }else {
            map.put("code",4000);
            map.put("msg","注册失败");
        }
        response.getWriter().write(gson.toJson(map));
    }

    public boolean isLogin(HttpServletRequest request, HttpServletResponse response){
        if(request.getSession().getAttribute("user") == null){
            return false;
        }else{
            return true;
        }

    }
}
