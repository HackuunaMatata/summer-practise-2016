package Serv;

import com.thoughtworks.xstream.XStream;
import database.Answers;
import database.Blobs;
import database.BlobsId;
import database.Dates;
import database.DatesId;
import database.Events;
import database.HibernateUtil;
import database.Numbers;
import database.NumbersId;
import database.Questions;
import database.Strings;
import database.StringsId;
import database.Users;
import database.UsersId;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.nio.charset.Charset;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Set;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.stream.XMLEventFactory;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLEventWriter;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.XMLEvent;
import javax.xml.transform.stream.StreamSource;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.json.JSONArray;
import org.json.JSONObject;

public class ReqProc {

    public String getEvents(Session session) throws JAXBException, XMLStreamException, IOException, ClassNotFoundException {

        String sql = "SELECT * FROM Events";
        SQLQuery query = session.createSQLQuery(sql);
        List<Events> results = query.addEntity(Events.class).list();

        String path = null;
        ImageProc img = new ImageProc();
        Events[] ev = new Events[results.size()];
        for (int i = 0; i < results.size(); i++) {
            ev[i] = new Events();
            ev[i].setIdevent(results.get(i).getIdevent());
            ev[i].setTitle(results.get(i).getTitle());
            path = "C:\\Users\\mylll\\Desktop\\imgs\\img" + i + ".jpg";
            ev[i].setImage(path.getBytes());
            img.saveBytesToFile(path, ev[i].getImage());
        }

        //System.out.println(ev[0].getImage().toString());
        return new JSONArray(ev).toString();
    }

    public String GetQuestions(Session session) {
        String sql = "SELECT * FROM Events JOIN Questions ON Events.idevent=Questions.idevent";
        SQLQuery query = session.createSQLQuery(sql);
        List<Events> eRes = query.addEntity(Events.class).list();
        List<Questions> qRes = query.addEntity(Questions.class).list();
        Events[] e = new Events[eRes.size()];
        Questions[] q = new Questions[qRes.size()];
        for (int i = 0; i < eRes.size(); i++) {
            e[i] = new Events();
            q[i] = new Questions();

            e[i].setIdevent(eRes.get(i).getIdevent());
            e[i].setImage(eRes.get(i).getImage());
            e[i].setTitle(eRes.get(i).getTitle());
            q[i].setEvents(e[i]);
            q[i].setIditem(qRes.get(i).getIditem());
            q[i].setItemname(qRes.get(i).getItemname());
            q[i].setTag(qRes.get(i).getTag());
            q[i].setType(qRes.get(i).getType());
            q[i].setDescription(qRes.get(i).getDescription());
        }
        return new JSONArray(q).toString();
    }

    public String GetAnswers(Session session) {
        String sql = "SELECT * FROM Answers";
        SQLQuery query = session.createSQLQuery(sql);
        List<Answers> results = query.addEntity(Answers.class).list();

        Answers[] a = new Answers[results.size()];
        for (int i = 0; i < results.size(); i++) {
            a[i] = new Answers();
            a[i].setId(results.get(i).getId());
        }
        return new JSONArray(a).toString();
    }

    public String GetInstructions(Session session) {

        String sql = "SELECT * FROM Answers JOIN (Events JOIN Questions ON Events.idevent=Questions.idevent) ON Answers.iditem=Questions.iditem";
        SQLQuery query = session.createSQLQuery(sql);
        List<Events> results1 = query.addEntity(Events.class).list();
        List<Questions> results2 = query.addEntity(Questions.class).list();
        List<Answers> results4 = query.addEntity(Answers.class).list();
        Events[] e = new Events[results1.size()];
        for (int i = 0; i < results1.size(); i++) {
            e[i] = new Events();
            e[i].setIdevent(results1.get(i).getIdevent());
            e[i].setImage(results1.get(i).getImage());
            e[i].setTitle(results1.get(i).getTitle());
        }
        Questions[] q = new Questions[results2.size()];
        for (int i = 0; i < results2.size(); i++) {
            q[i] = new Questions();
            q[i].setEvents(e[i]);
            q[i].setIditem(results2.get(i).getIditem());
            q[i].setItemname(results2.get(i).getItemname());
            q[i].setTag(results2.get(i).getTag());
            q[i].setType(results2.get(i).getType());
        }
        Answers[] a = new Answers[results4.size()];
        for (int i = 0; i < results4.size(); i++) {
            a[i] = new Answers();
            a[i].setId(results4.get(i).getId());
            a[i].setQuestions(q[i]);
        }
        return new JSONArray(a).toString();
    }

    public void InsertUser(ArrayList<Integer> num, ArrayList<String> str, int event) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        Users u = new Users();
        UsersId uid = new UsersId();
        Numbers n = new Numbers();
        uid.setIdevent(event);
        u.setId(uid);
        u.setName(str.get(0));
        u.setSurname(str.get(1));
        n.setUsers(u);
        session.getTransaction().commit();
        session.close();
    }

    static boolean setUserFlag = false;

    public String FillTable(Session session, ArrayList<Integer> num, ArrayList<String> str, int idevent) throws ParseException {
        Questions q = new Questions();
        Numbers n = new Numbers();
        Strings s = new Strings();
        Dates d = new Dates();
        boolean flag = false;

        System.out.println(idevent);
        String sql = "SELECT * FROM Questions WHERE iditem=" + num.get(0);
        SQLQuery query = session.createSQLQuery(sql);
        List<Questions> qRes = query.addEntity(Questions.class).list();
        if (qRes.size() > 0) {
            switch (qRes.get(0).getItemname()) {
                case "Surname":
                    if (!str.get(0).isEmpty()) {
                        Servlet.u.setSurname(str.get(0));
                        System.out.println(Servlet.u.getName());
                        if (Servlet.u.getName() != null) {
                            Servlet.continueFlag = CheckOnUser(Servlet.u, session);
                        }
                        if (setUserFlag == false && Servlet.continueFlag == true) {
                            session.beginTransaction();
                            session.save(Servlet.u);
                            session.getTransaction().commit();
                            setUserFlag = true;
                            flag = true;
                        } else {
                            session.beginTransaction();
                            session.update(Servlet.u);
                            session.getTransaction().commit();
                            Servlet.continueFlag = true;
                            flag = true;
                        }
                    }
                    break;
                case "Name":
                    if (!str.get(0).isEmpty()) {
                        Servlet.u.setName(str.get(0));
                        if (Servlet.u.getSurname() != null) {
                            Servlet.continueFlag = CheckOnUser(Servlet.u, session);
                        }
                        if (setUserFlag == false && Servlet.continueFlag == true) {
                            session.beginTransaction();
                            session.save(Servlet.u);
                            session.getTransaction().commit();
                            setUserFlag = true;
                            flag = true;
                        } else {
                            session.beginTransaction();
                            session.update(Servlet.u);
                            session.getTransaction().commit();
                            Servlet.continueFlag = true;
                            flag = true;
                        }
                    }
                    break;
            }
            if (!flag) {
                if (Servlet.continueFlag == true) {
                    session.beginTransaction();
                    switch (qRes.get(0).getType()) {
                        case "text":
                            if (!str.get(0).isEmpty()) {
                                s.setId(new StringsId(Servlet.u.getId().getIduser(), num.get(0), idevent, str.get(0)));
                                s.setUsers(Servlet.u);
                                s.setQuestions(qRes.get(0));
                                //s.setValue(str.get(0));
                                session.save(s);
                                System.out.println("text: " + s.getId().getValue());
                            }
                            break;
                        case "number":
                            if (!str.get(0).isEmpty()) {
                                n.setId(new NumbersId(Servlet.u.getId().getIduser(), num.get(0), idevent, Integer.parseInt(str.get(0).trim())));
                                n.setUsers(Servlet.u);
                                n.setQuestions(qRes.get(0));
                                //n.setValue(Integer.parseInt(str.get(0).trim()));
                                session.save(n);
                                System.out.println("number: " + n.getId().getValue());
                            }
                            break;
                        case "date":
                            if (!str.get(0).isEmpty()) {
                                DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                                Date date = format.parse(str.get(0));
                                d.setId(new DatesId(Servlet.u.getId().getIduser(), num.get(0), idevent));
                                d.setUsers(Servlet.u);
                                d.setQuestions(qRes.get(0));
                                d.setValue(date);
                                session.save(d);
                                System.out.println("date: " + d.getValue());
                            }
                            break;
                        case "radio":
                            if (!str.get(0).isEmpty()) {
                                s.setId(new StringsId(Servlet.u.getId().getIduser(), num.get(0), idevent, str.get(0)));
                                s.setUsers(Servlet.u);
                                s.setQuestions(qRes.get(0));
                                //s.setValue(str.get(0));
                                session.save(s);
                                System.out.println("text: " + s.getId().getValue());
                            }
                            break;
                        case "checkbox":
                            //List<Strings> sList = new ArrayList<Strings>(str.size());
                            Strings[] sAr = new Strings[str.size()];
                            System.out.println(str.size() + " " + num.get(0));
                            for (int j = 0; j < str.size(); j++) {
                                sAr[j] = new Strings();
                                sAr[j].setId(new StringsId(Servlet.u.getId().getIduser(), num.get(0), idevent, str.get(j)));
                                sAr[j].setUsers(Servlet.u);
                                sAr[j].setQuestions(qRes.get(0));
                                //sAr[j].setValue(str.get(j));
                                session.save(sAr[j]);
                            }
                            break;
                    }
                    switch (qRes.get(0).getTag()) {
                        case "select":
                            s.setId(new StringsId(Servlet.u.getId().getIduser(), num.get(0), idevent, str.get(0)));
                            s.setUsers(Servlet.u);
                            s.setQuestions(qRes.get(0));
                            //s.setValue(str.get(0));
                            session.save(s);
                            break;
                        case "textarea":
                            s.setId(new StringsId(Servlet.u.getId().getIduser(), num.get(0), idevent, str.get(0)));
                            s.setUsers(Servlet.u);
                            s.setQuestions(qRes.get(0));
                            session.save(s);
                            break;
                    }
                    session.getTransaction().commit();
                }
            }
        }
        return null;
    }

    public void SetImage(Session session, byte[] img, int idevent) {
        session.beginTransaction();
        String sql = "SELECT * FROM Questions WHERE type='file'";
        SQLQuery query = session.createSQLQuery(sql);
        List<Questions> qRes = query.addEntity(Questions.class).list();
        Blobs b = new Blobs();
        b.setId(new BlobsId(Servlet.u.getId().getIduser(), qRes.get(0).getIditem(), idevent));
        b.setUsers(Servlet.u);
        b.setQuestions(qRes.get(0));
        b.setValue(img);
        session.save(b);
        session.getTransaction().commit();
        System.out.println("file: " + b.getValue());
    }

    public String GetAnswersById(Session session, int idevent) {
        String sql = "SELECT idevent, title, image FROM Events WHERE idevent=" + idevent;
        SQLQuery query = session.createSQLQuery(sql);
        List<Events> eRes = query.addEntity(Events.class).list();
        int sz = eRes.get(0).getQuestionses().size();
        Questions[] q = new Questions[sz];
        eRes.get(0).getQuestionses().toArray(q);
        List<Questions> qList = Arrays.asList(q);
        Collections.sort(qList, COMPARE_BY_IDITEM);
        q = (Questions[]) qList.toArray();
        sz = 0;
        List<Answers> a = new ArrayList<Answers>();
        for (int i = 0; i < q.length; i++) {
            if (!q[i].getAnswerses().isEmpty()) {
                sz = q[i].getAnswerses().toArray().length;
                Answers[] atmp = new Answers[sz];
                q[i].getAnswerses().toArray(atmp);
                for (int j = 0; j < sz; j++) {
                    a.add(atmp[j]);
                }
            }
        }
        JSONObject[] aObj = new JSONObject[a.size()];
        for (int i = 0; i < a.size(); i++) {
            aObj[i] = new JSONObject(a.get(i).getId());
        }
        JSONObject[] qObj = new JSONObject[q.length];
        for (int i = 0; i < q.length; i++) {
            qObj[i] = new JSONObject();
            qObj[i].put("iditem", q[i].getIditem());
            qObj[i].put("itemname", q[i].getItemname());
            qObj[i].put("tag", q[i].getTag());
            qObj[i].put("type", q[i].getType());
            qObj[i].put("description", q[i].getDescription());
            qObj[i].put("isActive", q[i].getIsActive());
        }
        JSONArray ar = new JSONArray();
        ar.put(aObj);
        ar.put(qObj);
        JSONObject obj = new JSONObject();
        obj.put("answers", ar.get(0));
        obj.put("questions", ar.get(1));
        //System.out.println(obj);
        return obj.toString();
    }

    public String test2(Session session) {
        String sql = "SELECT idevent FROM Events where title='event1'";
        SQLQuery query = session.createSQLQuery(sql);
        List<Events> eRes = query.addEntity(Events.class).list();
        Events e = (Events) session.get(Events.class, eRes.get(0).getIdevent());

        return null;
    }

    public static Comparator<Questions> COMPARE_BY_IDITEM = new Comparator<Questions>() {
        @Override
        public int compare(Questions one, Questions other) {
            return one.getIditem().compareTo(other.getIditem());
        }
    };

    public int GetMaxId(Session session, int idevent) {
        String sql = "SELECT * FROM Users";
        //List<String> NameSurname = new ArrayList<String>(2);
        SQLQuery query = session.createSQLQuery(sql);
        List<Users> uRes = query.addEntity(Users.class).list();
        int max = 0;
        int id = 0;
        for (int i = 0; i < uRes.size(); i++) {
            if (uRes.get(i).getId().getIduser() > max) {
                max = uRes.get(i).getId().getIduser();
                id = i;
            }
        }
        return max;
    }

    public boolean CheckOnUser(Users u, Session session) {
        String sql = "SELECT * FROM Users";
        SQLQuery query = session.createSQLQuery(sql);
        List<Users> uRes = query.addEntity(Users.class).list();
        for (int i = 0; i < uRes.size(); i++) {
            if (u.getName() != null && u.getSurname() != null) {
                if (u.getName().equals(uRes.get(i).getName()) && u.getSurname().equals(uRes.get(i).getSurname())) {
                    return false;
                }
            }
        }
        return true;
    }

}
