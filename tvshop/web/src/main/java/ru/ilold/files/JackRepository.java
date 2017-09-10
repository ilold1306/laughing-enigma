package ru.ilold.files;

import org.apache.jackrabbit.commons.JcrUtils;
import org.primefaces.model.UploadedFile;
import ru.ilold.etc.StatusMessage;

import javax.jcr.*;
import java.io.*;
import java.util.Calendar;

public class JackRepository {
    Repository repository = null;
    private File file;

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public JackRepository(File f, String mimeType) throws Exception {
        repository = JcrUtils.getRepository();
        Session session = repository.login(
                new SimpleCredentials("admin", "admin".toCharArray()));
        Node root = session.getRootNode();
        Node contentNode = root.addNode("jcr:content", "nt:resource");
        Binary binary = session.getValueFactory()
                    .createBinary(new FileInputStream(file));
        contentNode.setProperty("jcr:data", binary);
        contentNode.setProperty("jcr:mimeType", mimeType);
        Calendar created = Calendar.getInstance();
        created.setTimeInMillis(f.lastModified());
        contentNode.setProperty("jcr:lastModified", created);
    }

    public static StatusMessage deployFile(byte[] contents, String mimeType) {
        try {
            Repository repository = JcrUtils.getRepository();
            Session session = repository.login(new SimpleCredentials(
                    "admin", "admin".toCharArray()));
            try {
                Node root = session.getRootNode();
                Node resNode = root.addNode("jcr:content", "nt:resource");
                Binary binary = session.getValueFactory()
                        .createBinary(new ByteArrayInputStream(contents));
                resNode.setProperty("jcr:data", binary);
                resNode.setProperty("jcr:mimeType", mimeType);
                Calendar created = Calendar.getInstance();
                resNode.setProperty("jcr:lastModified", created);
                session.save();

                return new StatusMessage(true, resNode.getPath(), resNode);
            } catch (Exception e) {
                return new StatusMessage(false, e.getMessage());
            }
        } catch (RepositoryException e) {

            return new StatusMessage(false, "RepositoryException");
        }
    }



}
