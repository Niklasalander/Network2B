
import java.io.BufferedReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author fno
 */
public class UserInfo {
    private int port;
    private InetAddress address;
    private int id;
    private PrintWriter out;
    private BufferedReader in;
    private Socket socket;
    
    public UserInfo(int port, InetAddress address) {
        this.port = port;
        this.address = address;
    }
      public UserInfo(int port, InetAddress address, Socket socket, PrintWriter out, BufferedReader in ) {
        this.port = port;
        this.address = address;
        this.socket = socket;
        this.out = out;
        this.in = in;
    }
    public void init(){
          try {
              System.out.println("in init");
            this.socket = new Socket(address, port); // 
            this.out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
          /*  SocketReader sr = new SocketReader(socket, out);
            sr.start();*/
              System.out.println("Socket connvection supposedly done");
            //processNextEvent(SIPEvent.SEND_INVITE, out);
        } catch (IOException ex) {
            System.out.println("Could not create Printwriter out when creating call");
            ex.printStackTrace();
        }
    }
    

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public InetAddress getAddress() {
        return address;
    }

    public void setAddress(InetAddress address) {
        this.address = address;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public PrintWriter getOut() {
        return out;
    }

    public void setOut(PrintWriter out) {
        this.out = out;
    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public BufferedReader getIn() {
        return in;
    }

    public void setIn(BufferedReader in) {
        this.in = in;
    }
    
    
}
