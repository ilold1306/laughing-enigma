package chat;

import java.io.Serializable;

public class Message implements Serializable{
  private String message;
  private String login;
  private String[] users;

  public Message(String message, String login) {
    this.message = message;
    this.login = login;
  }

  public Message(String message, String login, String[] users) {
    this.message = message;
    this.login = login;
    this.users = users;
  }

  public String getMessage() {
    return message;
  }

  public String getLogin() {
    return login;
  }

  public String[] getUsers() {
    return users;
  }

}
