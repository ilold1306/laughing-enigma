package chat.client;

import java.io.*;
import java.net.*;
import chat.Message;
import chat.ClientDis;
import chat.client.MessageThread;



class Client {
  private static String login;
  private static Message message;

  public static void main(String[] args) throws UnknownHostException, IOException, NullPointerException, ClassNotFoundException {
    Runtime.getRuntime().exec("clear");
    System.out.println("=== Client v4.0 ===");
    Socket socket = new Socket("localhost", 4444);
    System.out.println("Connected");

    try(ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
          BufferedReader inu = new BufferedReader(new InputStreamReader(System.in))) {
      String output;
      System.out.print("Enter your username: ");
      login = inu.readLine();
      out.writeObject(new Message("", login));
      new MessageThread(socket);
      while(true) {
        System.out.print("> ");
        output = inu.readLine();
        if(output.equalsIgnoreCase("/close")) break;
        if(output.equalsIgnoreCase("/help")) ShowHelp();
        message = new Message(output, login);
        out.writeObject(message);
      }
      out.close();
      socket.close();
    } catch (ClassNotFoundException e) {
          System.out.println("Class not found in Client body");
    } catch (NullPointerException e) {
        System.out.println("Null pointer in Client");
    } catch (UnknownHostException e) {
        System.out.println("Unknown host");
    } catch (IOException e) {
        System.out.println("I/O error in Client");
    }
  }

  public synchronized static void ShowHelp() {
    try {
      FileInputStream filein = new FileInputStream("chat/client/help.txt");
      int i = -1;
      System.out.println();
      while((i = filein.read()) != -1) {
        System.out.print((char) i);
      }
      System.out.println();
    } catch (FileNotFoundException e) {
        System.out.println("FileNotFoundException");
    } catch (IOException e) {
        System.out.println("IOException");
    }
  }

  public synchronized static String getLogin() {
    return login;
  }
}
