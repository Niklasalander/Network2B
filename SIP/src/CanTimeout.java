
import java.util.Timer;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Niklas
 */
public class CanTimeout extends Busy {
    private Timer rsTimer;
    
    public CanTimeout(RemoteUser user) {
        super(user);
        rsTimer = new Timer();
        rsTimer.schedule(new ResponsiveServerTimer(user), 15000);
    }
    
    public CanTimeout(RemoteUser user, int timeout) {
        super(user);
        rsTimer = new Timer();
        rsTimer.schedule(new ResponsiveServerTimer(user), timeout);
    }
    
    public SIPState timeoutReached(RemoteUser user) {
        sendDataPrimary(SIPEvent.BYE);
        System.out.println("CanTimeout sent BYE");
        cancelTimer();
        user.endConnection();
        return new Idle();
    }
    
    /* may break things */
    public SIPState gotBUSY(RemoteUser user) {
        System.out.println("Got busy");
        if (isSameUser(user)) {
            cancelTimer();
            user.endConnection();
            return new Idle();
        }
        else {
            sendBusyAndCloseWriter(user);
            return this;
        }
    }
    
    public SIPState gotBYE(RemoteUser user) {
        System.out.println("doing bye");
        if (isSameUser(user)) {
            cancelTimer();
            System.out.println("Got BYE sending OK");
            sendDataPrimary(SIPEvent.OK);
            user.endConnection();
            return new Idle();
        } else {
            sendBusyAndCloseWriter(user);
            return this;
        }
    }
    
    protected void cancelTimer() {
        rsTimer.cancel();
    }
}
