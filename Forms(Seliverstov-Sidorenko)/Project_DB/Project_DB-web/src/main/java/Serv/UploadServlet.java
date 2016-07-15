/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Serv;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import jdk.internal.org.xml.sax.InputSource;

/**
 *
 * @author a1
 */
public class UploadServlet extends HttpServlet {

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
        
        String[] idAndTitle = request.getParameter("select").split("-");
        
        UploadProcess up = new UploadProcess();
        
        try{
            String filePath = up.upload(Integer.parseInt(idAndTitle[0]), idAndTitle[1]);
                    
            response.setContentType("text/html;charset=UTF-8");

            try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            
            InputSource is = new InputSource();
            String rootPath = System.getProperty("catalina.home");
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">");
            out.println("<title>Выгрузка</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1> Файл успешно создан[, но не загружен]</h1>"); //НЕ ЗАГРУЖАЕТСЯ
            response.sendRedirect(rootPath+File.separator+filePath);
            //out.println("<p><a href=\""+filePath+"\">Загрузить</a></p>");

            out.println("</body>");
            out.println("</html>");
            }  
        }catch(IndexOutOfBoundsException ex){
            try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");

            out.println("<title>Error</title>");            
            out.println("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">");

            out.println("</head>");
            out.println("<body>");
            out.println("<h1>No data to upload</h1>");
            out.println("<p><a href=\"upload.html\"><-Back</a></p>");

            out.println("</body>");
            out.println("</html>");
            }
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
