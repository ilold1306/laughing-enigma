package ru.ilold.files;


import org.primefaces.model.UploadedFile;
import ru.ilold.adminbeans.RolesBean;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;

@Named
@SessionScoped
public class FileUpload implements Serializable {
    private UploadedFile file;
    private String product;
    
    public UploadedFile getFile() {
        return file;
    }

    public void setFile(UploadedFile file) {
        this.file = file;
    }



    public void upload() {
        if(file == null) {
            RolesBean.addMessage("Error!", "Set file!");
        } else {
            String savedFileName = "/web/images/" + product + "/" + file.getFileName();
        }
    }
}
