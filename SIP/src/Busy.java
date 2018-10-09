

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author fno
 */
public abstract class Busy extends SIPState {

    public Busy(User user) {
        super(user);
    }

    public SIPState inviting(User user) {
        System.out.println("Exit current call before starting a new one");
        if (!isSameUser(user) && user.getOut() != null) {
            sendBusyAndDisconnectUser(user);
        }
        return new Idle();
    }

    public SIPState invited(User user) {
        System.out.println("This node is busy, can't be invited");
        if (isSameUser(user)) {
            sendDataPrimary(SIPEvent.BYE);
            return new Idle();
        }
        else {
            sendBusyAndDisconnectUser(user);
            return this;
        }
       
    }

    public SIPState gotTRO(User user) {
        System.out.println("This node is busy, can't receive T.R.O");
       if (isSameUser(user)) {
            sendDataPrimary(SIPEvent.BYE);
            return new Idle();
        }
        else {
            sendBusyAndDisconnectUser(user);
            return this;
        }
       
    }

    public SIPState gotACK(User user) {
        System.out.println("This node is busy, can't receive ACK");
        if (isSameUser(user)) {
            sendDataPrimary(SIPEvent.BYE);
            return new Idle();
        }
        else {
            sendBusyAndDisconnectUser(user);
            return this;
        }
    }

    public SIPState gotOK(User user) {
        System.out.println("This node is busy, can't accept OK");
        if (!isSameUser(user)) {
            sendBusyAndDisconnectUser(user);
        }
        sendDataPrimary(SIPEvent.BYE);
        return new Idle();
    }

    public SIPState sendBYE(){
        System.out.println("Seding BYE waiting for OK");
        sendDataPrimary(SIPEvent.BYE);
        return new Exiting(user);
    }

    public SIPState gotBYE(User user) {
        if (isSameUser(user)) {
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
            sendDataPrimary(SIPEvent.BYE);
            return new Idle();
        } else {
            sendBusyAndDisconnectUser(user);
            return this;
        }
    }
    
    public SIPState makeSureIdle(User user) {
        if (isSameUser(user)) {
            System.out.println("Making sure this state machine doesn't get stuck outside of Idle");
            user.endConnection();
            return new Idle();
        }
        else {
            sendBusyAndDisconnectUser(user);
            return this;
        }
    }

    protected void sendBusyAndDisconnectUser(User user) {
        System.out.println("I am BUSY");
        try {
            user.endConnection();
            if (user.getOut() != null) {
                user.getOut().println(SIPEvent.BUSY);
                user.getOut().flush();
            }
        } catch (Exception ex) {
            System.out.println("Could not send BUSY message");
        }
    }

}
