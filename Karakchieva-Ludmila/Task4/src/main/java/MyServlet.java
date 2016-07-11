package main.java;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;


@WebServlet(name = "MyServlet")
public class MyServlet extends HttpServlet {
   public MySessionBean sbean = new MySessionBean();
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Символы в запросе</title>");
            out.println("</head>");
            out.println("<body>");
            String string = request.getParameter("Text");
            Map<Character,Integer> myMap = sbean.parserString(string);
            if((string!=null) && (string.length()>0)) {
                out.println("<table border='1'>");
                out.println("<tr>");
                out.println("<th>");
                out.println("Символ");
                out.println("</th>");
                out.println("<th>");
                out.println("Кол-во");
                out.println("</tr>");
                out.println("</th>");
                for (Map.Entry<Character, Integer> entry : myMap.entrySet()) {
                    out.println("<tr>");
                    out.println("<td>");
                    out.println(entry.getKey());
                    out.println("</td>");
                    out.println("<td>");
                    out.println(entry.getValue()-1);
                    out.println("</td>");
                    out.println("</tr>");
                }
                out.println("</table>");
            }
            out.println("</body>");
            out.println("</html>");
        }
    }
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
