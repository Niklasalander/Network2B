
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
public class Exiting extends CanTimeout {
    
    public Exiting(User user) {
        super(user);
    }
    
    public SIPState gotOK(User user) {
        if (isSameUser(user)) {
            cancelTimer();
            user.endConnection();
            return new Idle();
        }
        else {
            sendBusyAndDisconnectUser(user);
            return this;
        }
    }
    
    public void printState() {
        System.out.println("You are now in the Exiting state...");
    }
    
}
