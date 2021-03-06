
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
public class IsInviting extends CanTimeout {

    public IsInviting(User user) {
        super(user);
    }

    public SIPState gotTRO(User user) {
        if (isSameUser(user)) {
            try {
                cancelTimer();
                int localAudioPort = user.initAudioStream();
                sendDataWithIntegers(SIPEvent.ACK, localAudioPort, user.getLocalAddress().getHostName());
                System.out.println("Got TRO now we send ACK...");
                return new InCall(user);
            } catch (IOException ex) {
                return new Idle();
            }
        } 
        else {
            sendBusyAndDisconnectUser(user);
            return (this);
        }
    }
    
    public SIPState gotBUSY(User user) {
        if (isSameUser(user)) {
            cancelTimer();
            if (user != null) {
                user.endConnection();
            }
            return new Idle();
        }
        else {
            sendBusyAndDisconnectUser(user);
            return this;
        }
    }

    public void printState() {
        System.out.println("You are now in IsInviting state...");
    }
}
