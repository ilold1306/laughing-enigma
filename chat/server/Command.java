package chat.server;

import java.io.*;
import java.net.*;
import chat.Message;

public class Command {
  private static boolean flag;
  private static Socket socket;

  public static boolean check(String com, Socket scket) {
    socket = scket;
    flag = true;

    return flag;
  }

}
