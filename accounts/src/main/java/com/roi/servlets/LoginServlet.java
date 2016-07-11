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

@WebServlet(name = "loginServlet", urlPatterns = "/login")
public class loginServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String name = request.getParameter("name");
        String surname = request.getParameter("surname");
        String userID = request.getParameter("userID");

        response.setContentType("application/json;charset=UTF-8");
        response.setHeader("Cache-Control", "no-cache");
        PrintWriter out = response.getWriter();

        // Если userID="", и запрос к базе нашёл нескольких, то идем в if, иначе в else

        // Тут логика с БД

        JSONObject json = new JSONObject();

        if (userID.equals("") /* тут еще проверка какая-то*/  ) {
            json.put("isUnique", false);

            out.print(json.toString());
        } else {
            HttpSession session = request.getSession();
            session.setAttribute("userID", userID);

            json.put("isUnique", true);
            out.print(json.toString());
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/templates/login.html").forward(request, response);
    }
}
