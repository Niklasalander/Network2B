
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

    public WasInvited(PrintWriter out) {
        super(out);
    }

    public SIPState gotACK(PrintWriter out) {
        
        if (isSameUser(out)) {
            // Start audio stream
            System.out.println("Got ACK going to InCall... ");
            return new InCall(out);
        }
        else {
            if (out != null) {
                sendDataPrimary(SIPEvent.BUSY);
            }
            System.out.println("WasWaiting is Busy");
            return (this);
        }
    }

    public void printState() {
        System.out.println("You are now in the 'was invited' state...");
    }
}
