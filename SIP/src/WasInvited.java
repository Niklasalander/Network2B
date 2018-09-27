
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
public class WasInvited extends Busy {

    public WasInvited() {

    }

    public WasInvited(User user) {
        super(user);
    }

    public SIPState gotACK(User user) {
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
            return this;
        }
    }
    
    public SIPState sendTRO() {
        sendDataPrimary(SIPEvent.TRO);
        return this;
    }
    
    public SIPState gotBUSY(User user) {
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

    public void printState() {
        System.out.println("You are now in the 'was invited' state...");
    }
}
