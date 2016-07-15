package Serv;

import database.Events;
import database.HibernateUtil;
import database.Users;
import database.UsersId;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import javax.xml.bind.JAXBException;
import javax.xml.stream.XMLStreamException;
import org.hibernate.Session;
import org.json.JSONArray;
import org.w3c.dom.Document;

@MultipartConfig
public class Servlet extends HttpServlet {

    ReqProc x = new ReqProc();
    static Users u = new Users();
    static boolean continueFlag = true;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        Enumeration<String> parameterNames = request.getParameterNames();
        Session session = HibernateUtil.getSessionFactory().openSession();
        String param;
        while (parameterNames.hasMoreElements()) {
            switch (param = parameterNames.nextElement()) {
                case "event":
                    String ev;
                    try {
                        ev = x.getEvents(session);
                        out.println(ev);
                    } catch (JAXBException ex) {
                        Logger.getLogger(Servlet.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (XMLStreamException ex) {
                        Logger.getLogger(Servlet.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (ClassNotFoundException ex) {
                        Logger.getLogger(Servlet.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    break;
                case "form":
                    String t = x.GetAnswersById(session, Integer.parseInt(request.getParameterValues(param)[0]));
                    out.print(t);
                    break;
            }
        }
        session.close();
        out.close();
    }

    private static final String SAVE_DIR = "uploadFiles";

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        Session session = HibernateUtil.getSessionFactory().openSession();
        String t;
        boolean flag = false;
        ArrayList<Integer> num = new ArrayList<Integer>();
        ArrayList<String> str = new ArrayList<String>();
        Enumeration<String> parameterNames = request.getParameterNames();

        byte[] img;

        t = parameterNames.nextElement();
        int idevent = Integer.parseInt(request.getParameterValues(t)[0].trim());
        SetNewUser(session, idevent);
        if (continueFlag == true) {
            while (parameterNames.hasMoreElements()) {
                t = parameterNames.nextElement();
                System.out.println(t);
                num.add(Integer.parseInt(t.trim()));
                for (int i = 0; i < request.getParameterValues(t).length; i++) {
                    System.out.println(request.getParameterValues(t)[i]);
                    str.add(request.getParameterValues(t)[i]);
                }
                if (continueFlag == false) {
                    break;
                }
                try {
                    x.FillTable(session, num, str, idevent);
                } catch (ParseException ex) {
                    Logger.getLogger(Servlet.class.getName()).log(Level.SEVERE, null, ex);
                }
                flag = true;
                num.clear();
                str.clear();
                out.println();
            }
            if (continueFlag == true) {
                if (request.getParameter("41") == null) {
                    if (request.getParts().isEmpty()) {
                        Part filePart = request.getPart("41");
                        String fileName = filePart.getSubmittedFileName();
                        InputStream fileContent = filePart.getInputStream();
                        img = getBytes(fileContent);
                        x.SetImage(session, img, idevent);
                    }
                }
                System.out.println(u.getId().getIduser() + " " + u.getId().getIdevent() + " " + u.getName() + " " + u.getSurname());
                session.close();
                out.println("<h1>" + "User addition is successful" + "</h1>");
                continueFlag = false;
            } else {
                out.println("<h1>" + "User already exists" + "</h1>");
                continueFlag = true;
            }
        } else {
            out.println("<h1>" + "User already exists" + "</h1>");
            continueFlag = true;
        }

    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }

    public void SetNewUser(Session session, int idevent) {
        int maxId = x.GetMaxId(session, idevent);
        UsersId uid = new UsersId();
        uid.setIdevent(idevent);
        uid.setIduser(maxId + 1);
        u.setId(uid);
        System.out.println(u.getId().getIduser());
    }

    public static byte[] getBytes(InputStream is) throws IOException {

        int len;
        int size = 1024;
        byte[] buf;

        if (is instanceof ByteArrayInputStream) {
            size = is.available();
            buf = new byte[size];
            len = is.read(buf, 0, size);
        } else {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            buf = new byte[size];
            while ((len = is.read(buf, 0, size)) != -1) {
                bos.write(buf, 0, len);
            }
            buf = bos.toByteArray();
        }
        return buf;
    }

}
