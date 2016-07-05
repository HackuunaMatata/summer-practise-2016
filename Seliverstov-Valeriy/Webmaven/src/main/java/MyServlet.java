import forEjb.MyEJBean;
import forEjb.SymbolNumber;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;

@WebServlet(urlPatterns = {"/MyServlet"})
public class MyServlet extends HttpServlet {

    @EJB
    MyEJBean obj;
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        PrintWriter out = response.getWriter();

        obj.fillIt(request.getParameter("Message"));

        out.println("<table border>");

        Iterator iterator = obj.getSymnum().iterator();
        SymbolNumber symnum = null;

        while(iterator.hasNext()){
            symnum = (SymbolNumber) iterator.next();

            out.println("<tr>");

            out.println("<td>");
            out.println(symnum.getSymbol());
            out.println("</td>");

            out.println("<td>");
            out.println(symnum.getNumber());
            out.println("</td>");

            out.println("</tr>");
        }

        out.println("</table>");

    }
}