
import java.io.PrintWriter;
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
public class WasInvited extends CanTimeout {
//    public WasInvited() {
//        System.out.println("was invited");
//    }

    public WasInvited(RemoteUser user) {
       
        super(user);
        System.out.println("was invited +");
    }

    public SIPState gotACK(RemoteUser user) {
        cancelTimer();
        if (isSameUser(user)) {
            // Start audio stream
            System.out.println("Got ACK going to InCall... ");
            return new InCall(user);
        }
        else {
            if (user != null) {
                System.out.println("Busy in IsInviting");
                sendBusyAndCloseWriter(user);
            }
            return (this);
        }
    }
    
    public SIPState sendTRO() {
        cancelTimer();
        sendDataPrimary(SIPEvent.TRO);
        
        return this;
    }
     public SIPState sendTRO(RemoteUser user) {
       cancelTimer();
       sendDataWithInteger(SIPEvent.TRO,user.getLocalUsersPort());
       return this;
    }

    
    public SIPState gotBUSY(RemoteUser user) {
        cancelTimer();
        System.out.println("Got busy");
        if (isSameUser(user)) {
            if (user.getOut() != null) {
                user.getOut().close();
            }
            return new Idle();
        }
        else {
            sendBusyAndCloseWriter(user);
            return this;
        }
    }
    
     public SIPState timeoutReached(RemoteUser user) {
        sendDataPrimary(SIPEvent.BUSY);
        System.out.println("Timeout Was Invited sent BUSY");
        return new Idle();
    }

    public void printState() {
        System.out.println("You are now in the 'was invited' state...");
    }
    
  
    
}
