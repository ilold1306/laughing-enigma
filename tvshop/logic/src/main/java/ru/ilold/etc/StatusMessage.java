package ru.ilold.etc;

import ru.ilold.UserEntities.User;

public class StatusMessage {
    private boolean status = false;
    private String message;
    private Object object;

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }

    public StatusMessage(boolean status, String message, Object object) {
        this.status = status;
        this.message = message;
        this.object = object;
    }

    public StatusMessage(boolean status, String message) {
        this.status = status;
        this.message = message;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
