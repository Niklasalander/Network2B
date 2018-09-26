
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
public class WasInvited extends Busy {
    private Timer rsTimer;
    public WasInvited() {
        System.out.println("was invited");
    }

    public WasInvited(User user) {
       
        super(user);
        System.out.println("was invited +");
        rsTimer = new Timer();
        rsTimer.schedule(new ResponsiveServerTimer(user), 5000);
    }

    public SIPState gotACK(User user) {
         rsTimer.cancel();
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
        rsTimer.cancel();
        sendDataPrimary(SIPEvent.TRO);
        
        return this;
    }
    
    public SIPState gotBUSY(User user) {
         rsTimer.cancel();
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
    
     public SIPState sendBYE(User user) {
        sendDataPrimary(SIPEvent.BUSY);
        System.out.println("Sent");
        return new Idle();
    }

    public void printState() {
        System.out.println("You are now in the 'was invited' state...");
    }
    
  
    
}
