
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
public class IsInviting extends CanTimeout {

//    public IsInviting() {
//    }

    public IsInviting(RemoteUser user) {
        super(user);
    }

    public SIPState gotTRO(RemoteUser user) {
        System.out.println("this method");
        if (isSameUser(user)) {
            cancelTimer();
            System.out.println("IN GOT TRO " + user.getLocalUser().getAddress());
            sendDataWithIntegers(SIPEvent.ACK,user.getLocalUsersPort(),user.getLocalUser().getAddress().getHostName());
            System.out.println("Got TRO now we send ACK...");
            return new InCall(user);
        } 
        else {
            sendBusyAndCloseWriter(user);
            return (this);
        }
    }
    
    public SIPState gotBUSY(RemoteUser user) {
        if (isSameUser(user)) {
            cancelTimer();
            if (user != null) {
                user.endConnection();
//                user.getOut().close();
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
