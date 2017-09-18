package chat.server;

import java.io.*;
import java.net.*;
import chat.Message;
import chat.ClientList;
import chat.ClientDis;
import chat.server.ClientThread;


class ThreadServer {

  private static ClientList userlist = new ClientList();

  public synchronized static ClientList getUserList() {
    return userlist;
  }

  public static void main(String[] args) {
    System.out.println("=== Server v2.0 ===");
    ClientList clientList = new ClientList();
    try(ServerSocket server = new ServerSocket(4444)) {
      //Server loop
      while (true) {
        Socket client = null;
        while (client == null) {
          client = server.accept();
        }
        new ClientThread(client);
      }
    } catch (IOException e) {
        System.out.println("I/O error occured");
        e.printStackTrace();
    }
  }

}
