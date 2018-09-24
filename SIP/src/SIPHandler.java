
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
public class SIPHandler extends Thread {
    
    private static SIPState currentState = new Idle();
    private static PrintWriter out;
//    public SIPHandler(){};
    
    public void reportCurrentState(){
        currentState.printState();
    }
    public String reportStates(){
        return currentState.returnStates();
    }
    public synchronized static void processNextEvent (SIPEvent event, PrintWriter out) {
        if (currentState == null)
            System.out.println("currentState IS NULL!!!!");
        switch(event){
            case SEND_INVITE : currentState= currentState.inviting(out);break; // caller
            case INVITE : currentState= currentState.invited(out);break; // callee
            case TRO : currentState = currentState.gotTRO(out);break; // callee -> caller
            case SEND_TRO : currentState = currentState.sendTRO();break;
            case ACK : currentState = currentState.gotACK(out);break;// caller -> callee
            case SEND_BYE : currentState = currentState.doBYE();break; // caller -> callee (caller wants to leave)
            case BYE : currentState = currentState.gotBYE(out);break; //callee -> caller (wants to exit)
            case OK  : currentState = currentState.gotOK(out);break; // callee -> caller 
            case BUSY : currentState = currentState.gotBUSY(out);break;
        }
        System.out.print("Printing current state: ");currentState.printState();
    }
    
    public synchronized static void processNextEvent(SIPEvent event) {
        processNextEvent(event, out);
    }
    
    public static void startCallCaller(InetAddress ipAddress, int port) {
        try {
            Socket socket = new Socket(ipAddress, port);
            out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
            SocketReader sr = new SocketReader(socket, out);
            sr.start();
            processNextEvent(SIPEvent.SEND_INVITE, out);
        } catch (IOException ex) {
            System.out.println("Could not create connection to other party");
            ex.printStackTrace();
        }
    }
    
    public static void startCallCallee(Socket socket) {
        try {
            out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
            SocketReader sr = new SocketReader(socket, out);
            sr.start();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
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
