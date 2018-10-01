
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

    public WasInvited(User user) {
        super(user);
        System.out.println("Created new WasInvited");
    }

    public SIPState gotACK(User user) {
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
        try {
            cancelTimer();
            int localAudioPort = user.initAudioStream();
            System.out.println("audio port: " + localAudioPort);
            sendDataWithInteger(SIPEvent.TRO, localAudioPort);
            return new WasInvited(this.user);
        } catch (IOException ex) {
            ex.printStackTrace();
            sendBusyAndCloseWriter(user);
            return new Idle();
        }
    }
    
    public SIPState gotBUSY(User user) {
        
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
    
     public SIPState timeoutReached(User user) {
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
