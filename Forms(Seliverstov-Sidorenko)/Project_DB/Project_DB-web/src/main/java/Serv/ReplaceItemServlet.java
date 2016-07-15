/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Serv;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author a1
 */
public class ReplaceItemServlet extends HttpServlet {
    private SQLProcess object = new SQLProcess();

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            
            int eventId = 0;
            String itemname = "";
            String desc = "";
            String[] tagAndType = new String[2];
            String[] answers = new String[0];
            int parameterCounter = 0;
            
            tagAndType = request.getParameter("select").split("!");
            
            Enumeration<String> parameterNames = request.getParameterNames();

            while ((parameterNames.hasMoreElements()) && (parameterCounter<5)) {
                switch(parameterNames.nextElement()){
                    case "event":
                        eventId = Integer.parseInt(request.getParameter("event"));
                        parameterCounter++;
                        break;
                    case "item":
                        itemname = request.getParameter("item");
                        parameterCounter++;
                        break;
                    case "desc":
                        desc = request.getParameter("desc");
                        parameterCounter++;
                        break;
                    case "select":
                        tagAndType = request.getParameter("select").split("!");
                        parameterCounter++;
                        break;
                    case "answers[]":
                        parameterCounter++;
                        answers = new String[request.getParameterValues("answers[]").length];
                        answers = request.getParameterValues("answers[]");
                }
            }

            object.updateQuestionInDB(eventId, itemname, tagAndType[0], tagAndType[1],desc, answers);
            
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Добавлен</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1> Пункт " + itemname+" обновлен</h1>");
            out.println("<p><a href=\"formview.html?event="+eventId+
                    "-"+object.getEventTitle(eventId)+
                    "\"><-Обратно</a></p>");
            out.println("</body>");
            out.println("</html>");
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
