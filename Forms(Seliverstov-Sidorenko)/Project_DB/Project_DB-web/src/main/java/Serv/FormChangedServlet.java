/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Serv;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.*;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

/**
 *
 * @author a1
 */
public class FormChangedServlet extends HttpServlet {
    private SQLProcess object = new SQLProcess();
    private static final String SAVE_DIR = "uploadFiles";
    private static final int MAX_FILESIZE = 20848820;
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
        int eventId = Integer.parseInt(request.getParameter("event"));
        //int eventId = 26;
            Part filePart = request.getPart("image");
            
            if (filePart.getSize() != 0) {      
        
                byte[] img = new byte[
                        (int) filePart.getSize()
                        ];

                InputStream fileIn = filePart.getInputStream();

                int size = fileIn.read(img);
                
                object.changeImage(eventId, img);
            }
            
            String[] itemParameters = request.getParameterValues("items[]");
            ArrayList<String> items = new ArrayList<String>(Arrays.asList(itemParameters));
            
            object.changeActive(eventId, items);
            
        try (PrintWriter out = response.getWriter()) {          
            
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Изменения приняты</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Форма изменена</h1>");
            out.println("<p><a href=\"change.html\"><-К выбору мероприятий</a></p>");
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
