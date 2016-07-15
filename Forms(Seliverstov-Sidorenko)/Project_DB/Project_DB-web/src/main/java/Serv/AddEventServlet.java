/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Serv;

import java.io.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import org.hibernate.exception.ConstraintViolationException;




/**
 *
 * @author a1
 */
public class AddEventServlet extends HttpServlet {
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
            throws ServletException, IOException{

        response.setContentType("text/html;charset=UTF-8");        
        
        String title = request.getParameter("title");
         
        Part filePart = request.getPart("image");        
        
        byte[] img = new byte[
                (int) filePart.getSize()
                ];
        
        InputStream fileIn = filePart.getInputStream();
        
        int size = fileIn.read(img);
        //int size = fileIN.read(img); !!!!
        
        if (!title.isEmpty() && (object.checkUniqueEvent(title))) {
                object.addEventtoDB(title, img);
                try (PrintWriter out = response.getWriter()) {
                /* TODO output your page here. You may use following sample code. */
                printSuccess(out,title);
                }
            }
        else {
            try (PrintWriter out = response.getWriter()) {
                printError(out);
            }
            return;
        }
        
    }

    private void printError(PrintWriter out){
        
                /* TODO output your page here. You may use following sample code. */
        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<head>");
        out.println("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">");

        out.println("<title>Не вышло</title>");            
        out.println("</head>");
        out.println("<body>");
        out.println("<h1>Мероприятие не добавлено, имя неуникально или не получено</h1>");
        out.println("<p><a href=\"addevent.html\"><-Обратно</a></p>");

        out.println("</body>");
        out.println("</html>");

    }
    
    private void printSuccess(PrintWriter out,String title){
        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<head>");
        out.println("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">");

        out.println("<title>Готово!</title>");            
        out.println("</head>");
        out.println("<body>");
        out.println("<h1>Мероприятие " + title + " добавлено</h1>");
        out.println("<p><a href=\"change.html\"><-Обратно</a></p>");
        out.println("</body>");
        out.println("</html>");
    }
    
    /**
     * Extracts file name from HTTP header content-disposition
     */
    private String extractFileName(Part part) {
        String contentDisp = part.getHeader("content-disposition");
        String[] items = contentDisp.split(";");
        for (String s : items) {
            if (s.trim().startsWith("filename")) {
                return s.substring(s.indexOf("=") + 2, s.length()-1);
            }
        }
        return "";
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
