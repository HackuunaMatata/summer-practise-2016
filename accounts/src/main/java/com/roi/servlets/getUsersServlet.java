package com.roi.servlets;

import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by Vesdet on 09.07.2016.
 */
@WebServlet(name = "getUsersServlet", urlPatterns = "/usersInfo")
public class getUsersServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        PrintWriter out = response.getWriter();

        JSONObject json = new JSONObject();

        String user = "Sasha" + " " + "Pankratova";
        json.put("11", user);

        String user2 = "Nikita" + " " + "Ryzhov";
        json.put("13", user2);

        out.print(json);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
