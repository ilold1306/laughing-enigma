package chat;

import java.net.*;
import java.util.*;
import chat.ClientDis;

public class ClientList {
  private Map<String, ClientDis> uList = new HashMap<String, ClientDis>();

  public void addClient(String login, ClientDis user) {
    if(!uList.containsKey(login)) {
      uList.put(login, user);
    } else {
      int i = 1;
      while (uList.containsKey(login)) {
        login = login + i;
        i++;
      }
      uList.put(login, user);
    }
  }

  public void deleteClient(String login) {
    uList.remove(login);
  }

  public ClientDis findUser(String login) {
    return uList.get(login);    
  }

  public String[] getClients() {
    return uList.keySet().toArray(new String[0]);
  }

  public ArrayList<ClientDis> getClientList() {
    ArrayList<ClientDis> clientlist = new ArrayList<ClientDis>(uList.entrySet().size());
    String s = "";
    for(Map.Entry<String, ClientDis> entry : uList.entrySet()) {
      clientlist.add(entry.getValue());
      System.out.println(entry.getKey());
      s = s + entry.getKey();
    }
    return clientlist;
  }

}
