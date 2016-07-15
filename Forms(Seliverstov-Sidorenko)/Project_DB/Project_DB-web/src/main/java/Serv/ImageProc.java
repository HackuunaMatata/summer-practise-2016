package Serv;

import database.Events;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.SQLException;
import javax.imageio.ImageIO;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

public class ImageProc {

    private static ServiceRegistry serviceRegistry;
    private static Session session;

    public ImageProc() {
    }

    public void readPhotoOfPerson(int idevent, String photoFilePath) throws IOException, SQLException {
        Events e = (Events) session.get(Events.class, idevent);
        byte[] blob = e.getImage();
        saveBytesToFile(photoFilePath, blob);
    }

    public void saveBytesToFile(String filePath, byte[] fileBytes) throws IOException {
        FileOutputStream fos = new FileOutputStream(filePath); 
        fos.write(fileBytes);
        fos.close();
    }

    public void initSession() {
        Configuration configuration = new Configuration().configure();
        serviceRegistry = new StandardServiceRegistryBuilder()
                .applySettings(configuration.getProperties()).build();

        SessionFactory sessionFactory = configuration.buildSessionFactory(serviceRegistry);

        session = sessionFactory.openSession();
        session.beginTransaction();
    }

    public void endSession() {
        session.getTransaction().commit();
        session.close();

        StandardServiceRegistryBuilder.destroy(serviceRegistry);
    }
}
