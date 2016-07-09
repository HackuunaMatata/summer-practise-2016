package com.roi.servlets;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Map;
import java.util.Set;

/**
 * Created by Vesdet on 09.07.2016.
 */
@WebServlet(name = "savingServlet", urlPatterns = "/save")
public class savingServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Map<String, String[]> map = request.getParameterMap();
        Set<String> setKeys = map.keySet();

        // From this map to DB

        for (String setKey : setKeys) {
            for (String s : map.get(setKey)) {
                System.out.println(setKey + "|" + s);
            }
        }

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
