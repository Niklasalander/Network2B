
import java.io.PrintWriter;
import java.util.Timer;
import java.util.TimerTask;

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
    //Some timer class
    //when timer
    Timer timer;
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
                System.out.println("Busy in IsInviting");
                out.println(SIPEvent.BUSY);
                out.flush();
                // close out(?)
            }
            return (this);
        }
    }
    
    public SIPState sendTRO() {
        sendDataPrimary(SIPEvent.TRO);
        return this;
    }
    
    public SIPState gotBUSY(PrintWriter out) {
        if (isSameUser(out)) {
            if (out != null) {
                out.close();
            }
            return new Idle();
        }
        else {
            sendBusyAndCloseWriter(out);
            return this;
        }
    }

    public void printState() {
        System.out.println("You are now in the 'was invited' state...");
    }
}
