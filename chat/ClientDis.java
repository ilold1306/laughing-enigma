package chat;

import java.net.*;
import java.io.*;

public class ClientDis {
  private ObjectOutputStream outs;
  private ObjectInputStream ins;
  private Socket socket;

  public ClientDis(Socket socket, ObjectInputStream ins, ObjectOutputStream outs) {
    this.socket = socket;
    this.ins = ins;
    this.outs = outs;
  }

  public ObjectOutputStream getObOutputStream() {
    return this.outs;
  }

  public ObjectInputStream getObInputStream() {
    return this.ins;
  }

  public void setObOutputStream(ObjectOutputStream outs) {
    this.outs = outs;
  }
  public void setObInputStream(ObjectInputStream ins) {
    this.ins = ins;
  }
}
