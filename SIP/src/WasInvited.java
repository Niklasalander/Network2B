
import java.io.IOException;

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

    public WasInvited(User user) {
        super(user);
    }

    public SIPState gotACK(User user) {
        if (isSameUser(user)) {
            System.out.println("Got ACK going to InCall... ");
            cancelTimer();
            return new InCall(user);
        }
        else {
            if (user != null) {
                System.out.println("Busy in IsInviting");
                sendBusyAndDisconnectUser(user);
            }
            return (this);
        }
    }
    
    public SIPState sendTRO() {
        try {
            cancelTimer();
            int localAudioPort = user.initAudioStream();
            sendDataWithInteger(SIPEvent.TRO, localAudioPort);
            return new WasInvited(this.user);
        } catch (IOException ex) {
            ex.printStackTrace();
            sendBusyAndDisconnectUser(user);
            return new Idle();
        }
    }
    
    public SIPState gotBUSY(User user) {
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
    
     public SIPState timeoutReached(User user) {
        try {
            sendDataPrimary(SIPEvent.BUSY);
            System.out.println("Timeout Was Invited sent BUSY");
            cancelTimer();
            user.endConnection();
        } catch (Exception ex) {
        }
        return new Idle();
    }

    public void printState() {
        System.out.println("You are now in the 'Was Invited' state...");
    }
    
}
