
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
//    public SIPHandler(Socket socket, SIPState currentState){
//        this.socket = socket;
////        sendAlive = new Timer();
//        if (socket != null) {
//            try {
//                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
//                out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
//
//            } catch (IOException ex) {
//                try {
//                    if (in != null)
//                        in.close();
//                    if (out != null)
//                        out.close();
//                } catch (IOException e) {
//                }
//                System.out.println("Could not establish connection to client");
//            }
//        }
//    }
    
    public void reportCurrentState(){
        currentState.printState();
    }
    public String reportStates(){
        return currentState.returnStates();
    }
    public static void processNextEvent (SIPEvent event, PrintWriter out) {
        if (currentState == null)
            System.out.println("currentState IS NULL!!!!");
        switch(event){
            case SEND_INVITE : currentState= currentState.inviting(out);break; // caller
            case INVITE : currentState= currentState.invited(out);break; // callee
            case TRO : currentState = currentState.gotTRO(out);break; // callee -> caller
            case ACK : currentState = currentState.gotACK(out);break;// caller -> callee
            case SEND_BYE : currentState = currentState.doBYE();break; // caller -> callee (caller wants to leave)
            case BYE : currentState = currentState.gotBYE();break; //callee -> caller (wants to exit)
            case OK  : currentState = currentState.gotOK();break; // callee -> caller 
            // case BUSY : close socket and exit
        }
        System.out.print("Printing current state: ");currentState.printState();
    }
    
    public static void processNextEvent (SIPEvent event) {
        if (currentState == null)
            System.out.println("currentState IS NULL!!!!");
        switch(event){
            case SEND_INVITE : currentState= currentState.inviting(out);break; // caller
            case INVITE : currentState= currentState.invited(out);break; // callee
            case TRO : currentState = currentState.gotTRO(out);break; // callee -> caller
            case ACK : currentState = currentState.gotACK(out);break;// caller -> callee
            case SEND_BYE : currentState = currentState.doBYE();break; // caller -> callee (caller wants to leave)
            case BYE : currentState = currentState.gotBYE();break; //callee -> caller (wants to exit)
            case OK  : currentState = currentState.gotOK();break; // callee -> caller 
            // case BUSY : close socket and exit
        }
        System.out.print("Printing current state: ");currentState.printState();
    }
    
    
    public static void startCall(InetAddress ipAddress, int port) {
        try {
            Socket socket = new Socket(ipAddress, port);
            out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
            SocketReader sr = new SocketReader(socket, out);
            sr.start();
            processNextEvent(SIPEvent.SEND_INVITE, out);
        } catch (IOException ex) {
            System.out.println("Could not create Printwriter out when creating call");
            ex.printStackTrace();
        }
    }
    
    
    
    public static void acceptCall(Socket socket) {
        try {
            out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
        } catch (IOException ex) {
            System.out.println("Could not create Printwriter out when accepting call");
            ex.printStackTrace();
        }
    }
    
    public static SIPState getCurrentState() {
        return currentState;
    }
    
    public static void sendInv() {
        out.println("INVITE");
        out.flush();
        
    }
    
//    @Override
//    public void run() {
//        if (in != null && out != null) {
//            String str = "";
//            SIPEvent event;
//            try {
//                while (true) {
//                    str = in.readLine();
//                    System.out.println("gotstr: " + str);
////                    event = (SIPEvent) in.read();
//                    switch(str) {
//                        case "SEND_INVITE" : processNextEvent(SIPEvent.SEND_INVITE);break;
//                        case "INVITE" : processNextEvent(SIPEvent.INVITE);break;
//                        case "TRO" : processNextEvent(SIPEvent.TRO);break;
//                        case "ACK" : processNextEvent(SIPEvent.ACK);break;
//                        case "SEND_BYE" : processNextEvent(SIPEvent.SEND_BYE);break;
//                        case "BYE" : processNextEvent(SIPEvent.BYE);break;
//                        case "OK" : processNextEvent(SIPEvent.OK);break;
//                        case "BUSY" : processNextEvent(SIPEvent.BUSY);break;
//                        default :
//                            // close connection
//                    }
//                }
//
//            } catch (SocketTimeoutException ex) {
//                System.out.println("A client timed out");
//            } catch (NullPointerException ex) {
//                System.out.println("Connection with client abrupty lost");
//            } catch (SocketException se) {
//                System.out.println("Could not read from socket handeling client");
//            } catch (IOException ex) {
//                System.out.println("Client handler run method");
//            } finally {
//                try {
//                    System.out.println("Removing client");
//                    if (socket != null) {
//                        socket.close();
//                    }
//                    if (in != null) {
//                        in.close();
//                    }
//                    if (out != null) {
//                        out.close();
//                    }
//                } catch (IOException ex) {
//                    System.out.println("could not close in stream in client handler");
//                } catch (java.util.ConcurrentModificationException ex) {
//                    System.out.println("Too many clients disconnecting simutainiously causing concurrency issues, "
//                            + "thread will close in a few minutes.");;
//                }
//            }
//        }
//    }
}
