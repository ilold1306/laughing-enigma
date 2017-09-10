package ru.ilold;


import org.apache.jackrabbit.commons.JcrUtils;

import javax.jcr.Node;
import javax.jcr.Repository;
import javax.jcr.Session;
import javax.jcr.SimpleCredentials;

public class JackRepo {
    public static void workWithRepo() throws Exception {
        Repository repository = JcrUtils.getRepository();
        Session session = repository.login(
                new SimpleCredentials("admin", "admin".toCharArray()));
        try {
            Node root = session.getRootNode();
            Node node = root.getNode("hello/world");
            String s = node.getProperty("message").getString();
            s = s + "";
        } catch (Exception e) {
            e.printStackTrace();
            session.logout();
        } finally {
            session.logout();
        }
    }
}