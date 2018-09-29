

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
        super(user, 5000);
    }

    public SIPState gotTRO(RemoteUser user) {
        if (isSameUser(user)) {
            cancelTimer();
            sendDataWithInteger(SIPEvent.ACK,user.getLocalUsersPort());

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
