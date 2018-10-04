
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
        System.out.println("In next event " + currentState.returnStates() + " "  +event);
        if (event != null) {
            switch(event){
                case INVITE : currentState= currentState.invited(user);break; // callee
                case TRO : currentState = currentState.gotTRO(user);break; // callee -> caller
                case ACK : currentState = currentState.gotACK(user);break; // caller -> callee
                case BYE : currentState = currentState.gotBYE(user);break;
                case OK  : currentState = currentState.gotOK(user);break;
                case BUSY : currentState = currentState.gotBUSY(user);break;
                case TIMEOUT : currentState = currentState.timeoutReached(user);break;
                case LOST_CONNECTION : currentState = currentState.lostConnection(user);break;
                case MAKE_SURE_IDLE : currentState = currentState.makeSureIdle(user);break;
            }
        }
        else {
            if (user.getOut() != null) {
                user.getOut().println(SIPEvent.BYE);
                user.getOut().flush();
                user.endConnection();
            }
        }
        System.out.print("Printing current state: ");currentState.printState();
    }
    
    public static void processNextEvent (SIPEvent event) {
        System.out.println("In next event " + currentState.returnStates() + " "  +event);
        switch(event){
            case SEND_BYE : currentState = currentState.sendBYE();break; 
            case SEND_TRO : currentState = currentState.sendTRO();break;
        }
        System.out.print("Printing current state: ");currentState.printState();
    }
    
    public static void processNextEvent (SIPEvent event, User user, String str) {
        System.out.println("In next event " + currentState.returnStates() + " "  +event);
        switch(event){
            case SEND_INVITE : currentState= currentState.inviting(user);break; // caller
        }
        System.out.print("Printing current state: ");currentState.printState();
    }
    
    public static String getCurrentState() {
        return currentState.getClass().getSimpleName();
    }
}
