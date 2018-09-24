
import java.io.BufferedReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;

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
    private InetAddress address;
    private int port;
    private BufferedReader in;
    private PrintWriter out;

    public User(InetAddress addr, int port) {
       //To change body of generated methods, choose Tools | Templates.
       this.address = addr;
       this.port = port;
    }

    public User(BufferedReader in, PrintWriter out) {
        this.in = in;
        this.out = out;
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
    
    
    
}
