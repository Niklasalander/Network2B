
import java.io.IOException;
import java.util.Timer;
import java.util.logging.Level;
import java.util.logging.Logger;

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
        
        if (isSameUser(user)) {
            // Start audio stream
            System.out.println("Got ACK going to InCall... ");
            cancelTimer();
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
        
        return new WasInvited(user);
    }
     public SIPState sendTRO(RemoteUser user) {
       cancelTimer();
        sendDataWithInteger(SIPEvent.TRO,user.getLocalUsersPort());
        return new WasInvited(this.user);
    }

    
    public SIPState gotBUSY(RemoteUser user) {
        
        System.out.println("Got busy");
        if (isSameUser(user)) {
            cancelTimer();
            user.endConnection();
            return new Idle();
        }
        else {
            sendBusyAndCloseWriter(user);
            return this;
        }
    }
    
     public SIPState timeoutReached(RemoteUser user) {
        try {
            sendDataPrimary(SIPEvent.BUSY);
            System.out.println("Timeout Was Invited sent BUSY");
            cancelTimer();
            user.endConnection();
        } catch (Exception ex) {
            Logger.getLogger(WasInvited.class.getName()).log(Level.SEVERE, null, ex);
        }
        return new Idle();
    }

    public void printState() {
        System.out.println("You are now in the 'was invited' state...");
    }
    
  
    
}
