package com.roi.servlets;

import org.json.JSONArray;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

/**
 * Created by Vesdet on 09.07.2016.
 */
@WebServlet(name = "getQuestionsServlet", urlPatterns = "/questionsInfo")
public class getQuestionsServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        PrintWriter out = response.getWriter();

        // Из базы заполнить JSON:       вопрос :   состояние         : (false(не изм), yes, no)
        //                                          type              : (text, number, list, date)
        //                                          ответы(если list) : ["one", "two", "three"]

        JSONObject json = new JSONObject();
        JSONArray param;
        JSONObject check;
        JSONObject type;
        JSONObject variants;

        // Sample (project)
        param = new JSONArray();

        check = new JSONObject();
        type = new JSONObject();
        variants = new JSONObject();

        ArrayList<String> projects = new ArrayList<String>();
        projects.add("project_one");
        projects.add("project_two");
        projects.add("project_three");
        variants.put("Variants", projects);

        check.put("Check", false);
        type.put("Type", "list");

        param.put(check);
        param.put(type);
        param.put(variants);

        json.put("Project", param);

        // Sample (name)
        param = new JSONArray();

        check = new JSONObject();
        type = new JSONObject();
        check.put("Check", "yes");
        type.put("Type", "text");
        param.put(check);
        param.put(type);

        json.put("Name", param);

        // Sample (ID)
        param = new JSONArray();

        check = new JSONObject();
        type = new JSONObject();
        check.put("Check", "no");
        type.put("Type", "number");
        param.put(check);
        param.put(type);

        json.put("ID", param);

        out.print(json);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
