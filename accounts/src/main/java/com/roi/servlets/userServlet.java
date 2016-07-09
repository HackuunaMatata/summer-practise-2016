package com.roi.servlets;

import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by Vesdet on 08.07.2016.
 */
@WebServlet(name = "userServlet", urlPatterns = "/user")
public class userServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/templates/user.html").forward(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession();
        String userID = (String) session.getAttribute("userID");

        // Получаем из базы по ID


        response.setContentType("application/json;charset=UTF-8");
        response.setHeader("Cache-Control", "no-cache");
        JSONObject json = new JSONObject();

        //Складываем поля(key) и их значения для текущ. user'a(value)
        json.put("Project", "bla-bla-bla");
        json.put("Name", "Nikita");
        json.put("Surname", "Pupkin");
        // . . .
        json.put("Phone", "89811231122");

        PrintWriter out = response.getWriter();
        out.print(json);

//        request.getRequestDispatcher("/WEB-INF/templates/user.html").forward(request, response);
    }
}
