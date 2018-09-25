
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.Timer;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author fno
 */
public class SIPHandler extends Thread {
//    protected Socket socket;
//    protected BufferedReader in;
//    protected static PrintWriter out;
    
    private static SIPState currentState = new Idle();
    private static PrintWriter out;
    public SIPHandler(){};

    
    public void reportCurrentState(){
        currentState.printState();
    }
    public String reportStates(){
        return currentState.returnStates();
    }
    public static void processNextEvent (SIPEvent event, PrintWriter out) {
        if (currentState == null)
            System.out.println("currentState IS NULL!!!!");
        System.out.println("in next event " + currentState.returnStates());
        switch(event){
            case SEND_INVITE : currentState= currentState.inviting(out);break; // caller
            case INVITE : currentState= currentState.invited(out);break; // callee
            case TRO : currentState = currentState.gotTRO(out);break; // callee -> caller
            case ACK : currentState = currentState.gotACK(out);break;// caller -> callee
            case BYE : currentState = currentState.gotBYE(out);break; //callee -> caller (wants to exit)
            case OK  : currentState = currentState.gotOK(out);break; // callee -> caller 
            case BUSY : currentState = currentState.gotBUSY(out);break;
        }
        System.out.print("Printing current state: ");currentState.printState();
    }
    
    public static void processNextEvent (SIPEvent event) {
        if (currentState == null)
            System.out.println("currentState IS NULL!!!!");
        switch(event){
            case SEND_BYE : currentState = currentState.sendBYE();break; // caller -> callee (caller wants to leave)
            case SEND_TRO : currentState = currentState.sendTRO();break;
        }
        System.out.print("Printing current state: ");currentState.printState();
    }
    
    
//    public static void startCall(InetAddress ipAddress, int port) {
//        try {
//            Socket socket = new Socket(ipAddress, port);
//            out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
//            SocketReader sr = new SocketReader(socket, out);
//            sr.start();
//            processNextEvent(SIPEvent.SEND_INVITE, out);
//        } catch (IOException ex) {
//            System.out.println("Could not create Printwriter out when creating call");
//            ex.printStackTrace();
//        }
//    }
    
    
    
//    public static void acceptCall(Socket socket) {
//        try {
//            out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
//        } catch (IOException ex) {
//            System.out.println("Could not create Printwriter out when accepting call");
//            ex.printStackTrace();
//        }
//    }
    
    public static SIPState getCurrentState() {
        return currentState;
    }
    
    public static void sendInv() {
        out.println("INVITE");
        out.flush();
        
    }
    

}
