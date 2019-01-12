
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
    
    public CanTimeout(User user) {
        super(user);
        rsTimer = new Timer();
        rsTimer.schedule(new ResponsiveServerTimer(user), 1500000000);
    }
    
    public CanTimeout(User user, int timeout) {
        super(user);
        rsTimer = new Timer();
        rsTimer.schedule(new ResponsiveServerTimer(user), timeout);
    }
    
    public SIPState timeoutReached(User user) {
        sendDataPrimary(SIPEvent.BYE);
        System.out.println("CanTimeout sent BYE");
        cancelTimer();
        user.endConnection();
        return new Idle();
    }
    
    public SIPState sendBYE(){
        System.out.println("Seding BYE waiting for OK");
        cancelTimer();
        sendDataPrimary(SIPEvent.BYE);
        return new Exiting(user);
    }
    
    public SIPState gotBUSY(User user) {
        System.out.println("Got busy");
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
    
    public SIPState gotBYE(User user) {
        if (isSameUser(user)) {
            cancelTimer();
            System.out.println("Got BYE sending OK");
            sendDataPrimary(SIPEvent.OK);
            user.endConnection();
            return new Idle();
        } else {
            sendBusyAndDisconnectUser(user);
            return this;
        }
    }
    
    public SIPState lostConnection(User user) {
        if (isSameUser(user)) {
            System.out.println("Got LOST_CONNECTION returning to Idle");
            cancelTimer();
            sendDataPrimary(SIPEvent.BYE);
            user.endConnection();
            return new Idle();
        } else {
            sendBusyAndDisconnectUser(user);
            return this;
        }
    }
    
    public SIPState makeSureIdle(User user) {
        if (isSameUser(user)) {
            System.out.println("Making sure we state machine doesn't get stuck outside of Idle");
            cancelTimer();
            user.endConnection();
            return new Idle();
        }
        else {
            sendBusyAndDisconnectUser(user);
            return this;
        }
    }
    
    protected void cancelTimer() {
        rsTimer.cancel();
    }
}
