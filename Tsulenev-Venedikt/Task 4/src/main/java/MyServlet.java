import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("MyServlet")
public class MyServlet extends HttpServlet {
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String text = request.getParameter("text");
        if (text == null)
            response.sendRedirect("index.html");
        else {
            response.setContentType("text/html;charset=UTF-8");
            PrintWriter writer = response.getWriter();
            writer.println("<html><body>");
            writer.println("Message: " + text);
            writer.println("Length: " + text.length());
            writer.println("</body></html>");
        }
    }
}
