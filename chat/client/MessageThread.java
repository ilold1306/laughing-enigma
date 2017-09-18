package chat.client;

import java.io.*;
import java.net.*;
import chat.client.Client;
import chat.Message;


public class MessageThread extends Thread{
  private Message message;
  private Socket socket;

  public MessageThread(Socket socket) {
    this.socket = socket;
    this.start();
  }

  public void run() {
    try(ObjectInputStream in = new ObjectInputStream(socket.getInputStream())) {
      while(true) {
        message = (Message) in.readObject();
        if(!Client.getLogin().equals(message.getLogin())) {
          System.out.print(message.getLogin() + ": " + message.getMessage() + "\n> ");
        }
      }
    } catch (IOException e) {
        System.out.println("Client-Bot: You are disconnected I/O");
    } catch (ClassNotFoundException e) {
        System.out.println("Client-Bot: ClassNotFoundException");
    }
  }

}
