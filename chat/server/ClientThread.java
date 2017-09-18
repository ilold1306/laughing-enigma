package chat.server;

import java.io.*;
import java.net.*;
import java.util.*;
import chat.Message;
import chat.ClientList;
import chat.ClientDis;
import chat.server.ThreadServer;


class ClientThread extends Thread {
  private Socket socket;
  private String login;
  private Message mes;
  private ClientDis user;

  public ClientThread(Socket socket) {
    this.socket = socket;
    this.start();
  }


  private void sendOnline() {
    String[] str = ThreadServer.getUserList().getClients();
    String ms = "\n";
    for(int i = 0; i < str.length; i++) {
      ms = ms + (i + 1) + ". " + str[i] + "\n";
    }
    try {
      user.getObOutputStream().writeObject(new Message(ms, "", str));
    } catch (IOException e) {
        System.out.println("I/O error occured");
    }
  }

  private void sendPrivate() {
    String[] splitMes = mes.getMessage().split(" ");
    if(splitMes.length > 2) {
      String message = "";
      for(int i = 2; i < splitMes.length; i++) {
        message = message + splitMes[i] + " ";
      }
      try {
        ClientDis dis = ThreadServer.getUserList().findUser(splitMes[1]);
        if(dis != null) {
          dis.getObOutputStream().writeObject(new Message("[ private message ] " + message, login));
        } else {
            user.getObOutputStream().writeObject(new Message("No such user in the chat", "Server-Bot"));
        }
      } catch (IOException e) {
          System.out.println("Private message I/O error");
      }
    }
  }

  private boolean checkCommand() {
    boolean flag = false;
    if(mes.getMessage().equals("/online")) {
      flag = true;
      sendOnline();
    }
    if(mes.getMessage().startsWith("/private")) {
      flag = true;
      sendPrivate();
    }
  return flag;
  }


  public void run() {
    try(ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
          ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream())) {
        user = new ClientDis(socket, in, out);
        String input, output;
        mes = (Message) in.readObject();
        login = mes.getLogin();
        user = new ClientDis(socket, in, out);
        System.out.println("User " + login + " connected");
        ThreadServer.getUserList().addClient(login, user);
        out.writeObject(new Message("Print /help for help", "Server-Bot", ThreadServer.getUserList().getClients()));

        while(true) {
          mes = (Message) in.readObject();
          if(!checkCommand()) {
            ClientList tmp = ThreadServer.getUserList();
            ArrayList<ClientDis> disList = tmp.getClientList();
            for(int i = 0; i < disList.size(); i++) {
              ClientDis tmpDis = disList.get(i);
              tmpDis.getObOutputStream().writeObject(mes);
            }
          }
        }

    } catch (ClassNotFoundException e) {
        System.out.println("ClassNotFoundException");
        ThreadServer.getUserList().deleteClient(login);
        e.printStackTrace();
    } catch (NullPointerException e) {
        System.out.println("Disconnected");
        ThreadServer.getUserList().deleteClient(login);
    } catch (IOException e) {
        System.out.println("User " + login + " was disconnected");
        ThreadServer.getUserList().deleteClient(login);
    }

    try {
      socket.close();
    } catch (IOException e) {
        System.out.println("Error while closing Client Socket");
        ThreadServer.getUserList().deleteClient(login);
    }
  }
}
