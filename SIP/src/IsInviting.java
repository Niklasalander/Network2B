
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
public class IsInviting extends Busy {

    public IsInviting() {
    }

    public IsInviting(User user) {
        super(user);
    }

    public SIPState gotTRO(User user) {
        if (isSameUser(user)) {
            sendDataPrimary(SIPEvent.ACK);
            System.out.println("Got TRO now we send ACK...");
            return new InCall(user);
        } 
        else {
            sendBusyAndCloseWriter(user);
            return (this);
        }
    }
    
    public SIPState gotBUSY(User user) {
        if (isSameUser(user)) {
            if (user != null) {
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
        System.out.println("You are now in inviting state...");
    }
}
