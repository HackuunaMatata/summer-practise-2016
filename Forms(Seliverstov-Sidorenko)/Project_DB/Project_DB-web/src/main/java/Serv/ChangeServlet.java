/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Serv;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONArray;


/**
 *
 * @author a1
 */
public class ChangeServlet extends HttpServlet {
    
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
            object.clean();
            object.eventsFromDB();
            out.println(new JSONArray(object.getEv()).toString());
            
//            JSONArray jsa = new JSONArray(object.getEv());
//            System.out.println(jsa.toString());
//            out.println("<!DOCTYPE html>");
//            out.println("<html>");
//            out.println("<head>");
//            out.println("<title>Выбор мероприятия</title>");   
//            out.println("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">");
//            out.println("<script>");
//            out.println("function add(){");
//                out.println("location.href = index.html;");
//            out.println("}");
//            out.println("</script>");
//            out.println("</head>");
//            out.println("<body>");
//            out.println("<fieldset>");
//            out.println("<legend><b>Мероприятия</b></legend>");
//            out.println("<form method=\"POST\" action=\"changeform\">");
//            out.println("Выбор мероприятия:");
//            out.println("<select name=\"select\" id=\"select\">");
//            
//            for(int i = 0; i < object.getEv().size(); i++){
//                out.println("<option value="+object.getEv().get(i).getIdevent()+">"+object.getEv().get(i).getTitle()+"</option>");
//            }
//
//            out.println("</select> ");
//            out.println("<input type=\"submit\">");
//            out.println("<p><input type=\"button\" value=\"Добавить мероприятия\" onclick=\"add()\"></p>");
//            out.println("</form>");
//            out.println("</fieldset>");
//            out.println("</body>");
//            out.println("</html>");
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
