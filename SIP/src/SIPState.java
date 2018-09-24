
import java.io.BufferedReader;
import java.io.ObjectOutputStream;
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
public abstract class SIPState {

//    protected Socket socket;
//    protected BufferedReader in;
    protected static PrintWriter out;
//    protected ObjectOutputStream os;
//    protected InetAddress otherIdAddress;
//    protected int otherPort;

    public SIPState() {
        System.out.println("Creating new SIP state without printwriter");
    }

    public SIPState(PrintWriter out) {
        System.out.println("Creating new SIP state with printwriter");
        this.out = out;
    }
    
    protected boolean isSameUser(PrintWriter otherOut) {
        if (out.equals(otherOut)) {
            System.out.println("It is the same user");
            return true;
        }
        else {
            System.out.println("Not the same user");
            return false;
        }
    }
    

    
    public SIPState inviting(Socket socket) {
        return this;
    }
    public SIPState invited(Socket socket) {
        return this;
    }
    public SIPState gotTRO(Socket socket) {
        return this;
    }
    public SIPState gotACK(Socket socket) {
        return this;
    }

    public SIPState inviting(PrintWriter out) {
        return this;
    }

    public SIPState invited(PrintWriter out) {
        return this;
    }

    public SIPState gotTRO(PrintWriter out) {
        return this;
    }

    public SIPState gotACK(PrintWriter out) {
        return this;
    }

    public SIPState gotOK() {
        return this;
    }

    public SIPState doBYE() {
        return this;
    }

    public SIPState gotBYE() {
        return this;
    }

    public void printState() {
        System.out.println("current state : " + this.getClass().getSimpleName());
    }

    
     public String returnStates() {
        return "current state : " + this.getClass().getSimpleName().toString();
    }
     
    protected void sendDataPrimary(SIPEvent event) {
        out.println(event);
        out.flush();
    }
    
    protected void sendDataSecondary(Socket socket, SIPEvent event) {
        
    }
    
}
