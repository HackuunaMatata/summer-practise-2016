/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Елена
 */
@WebServlet(name = "MyServlet", urlPatterns = {"/myservlet"})
public class MyServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @EJB
    private MySessionBean sessionBean;
   protected void processRequest(HttpServletRequest request, HttpServletResponse response)
           throws ServletException, IOException {
       response.setContentType("text/html;charset=UTF-8");
       try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
           out.println("<!DOCTYPE html>");
           out.println("<html>");
           out.println("<head>");
           out.println("<title>Servlet MyServlet</title>");
           out.println("</head>");
           out.println("<body>");
           String toParse = request.getParameter("String");
           String jndiUrl = "java:global/mavenproject-ear-1.0-SNAPSHOT/mavenproject-ejb-1.0-SNAPSHOT/MySessionBean";
           //String jndiUrl = prefix + ejbRefName;    //The JNDI URL should be "java:comp/env/SessionFacade2RefName"

           Map<Character, Integer> resultMap = sessionBean.parseString(toParse);
           if (toParse != null && toParse.length() > 0) {
               out.println("<table border='1'>");
               out.println("<tr>");
               out.println("<th>");
               out.println("Буква");
               out.println("</th>");
               out.println("<th>");
               out.println("Кол-во");
               out.println("</tr>");
               out.println("</th>");
               for (Map.Entry<Character, Integer> entry : resultMap.entrySet()) {
                   out.println("<tr>");
                   out.println("<td>");
                   out.println(entry.getKey());
                   out.println("</td>");
                   out.println("<td>");
                   out.println(entry.getValue());
                   out.println("</td>");
                   out.println("</tr>");
               }
               out.println("</table>");
           }
           out.println("<form action='myservlet' method='POST' >");
           out.println("<textarea name='String' ></textarea>");
           out.println("<input type='submit'/>");
           out.println("</form>");

           out.println("</body>");
           out.println("</html>");


           //request.getParameter("String");
       }

   }
    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
