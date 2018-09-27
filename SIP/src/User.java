
import java.io.BufferedReader;
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
public class User {
    private BufferedReader in;
    private PrintWriter out;
    private Socket socket;
    private boolean isConnected;
    private int id;

    public User() {
       //To change body of generated methods, choose Tools | Templates.
       this.id = NetworkServer.getNewUserId();
       this.isConnected = true;
    }

    public User(Socket socket, BufferedReader in, PrintWriter out) {
        this.socket = socket;
        this.in = in;
        this.out = out;
        this.id = NetworkServer.getNewUserId();
        this.isConnected = true;
    }
    
    public void endConnection() {
        isConnected = false;
    }
    
    public boolean getIsConnected() {
        return isConnected;
    }

    public int getId() {
        return id;
    }
    
    public BufferedReader getIn() {
        return in;
    }

    public void setIn(BufferedReader in) {
        this.in = in;
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
    
    
    
    
    
}
