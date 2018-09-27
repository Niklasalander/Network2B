
import java.io.PrintWriter;

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
    public static void processNextEvent (SIPEvent event, User user) {
        if (currentState == null)
            System.out.println("currentState IS NULL!!!!");
        System.out.println("in next event " + currentState.returnStates() + " "  +event);
        switch(event){
            case SEND_INVITE : currentState= currentState.inviting(user);break; // caller
            case INVITE : currentState= currentState.invited(user);break; // callee
            case TRO : currentState = currentState.gotTRO(user);break; // callee -> caller
            case ACK : currentState = currentState.gotACK(user);break;// caller -> callee
            case BYE : currentState = currentState.gotBYE(user);break; //callee -> caller (wants to exit)
            case OK  : currentState = currentState.gotOK(user);break; // callee -> caller 
            case BUSY : currentState = currentState.gotBUSY(user);break;
            case SEND_BUSY: currentState = currentState.sendBYE(user);break;
            case SEND_TRO : currentState = currentState.sendTRO(user);break;
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
    
    

    
    public static String getCurrentState() {
        return currentState.getClass().getSimpleName();
    }
    
    public static void sendInv() {
        out.println("INVITE");
        out.flush();
        
    }
    
}
