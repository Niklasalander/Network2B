

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

    public Busy() {
    }

    public Busy(User user) {
        super(user);
    }

    public SIPState inviting(User user) {
        System.out.println("Exit current call before starting a new one");
        if (!isSameUser(user) && user.getOut() != null) {
            user.getOut().println(SIPEvent.BYE);
            user.getOut().flush();
            user.getOut().close();
        }
        return this;
    }

    public SIPState invited(User user) {
        System.out.println("this node is busy, can't be invited");
        if (isSameUser(user)) {
            // do something(?)
        }
        else {
            sendBusyAndCloseWriter(user);
            return this;
        }
        return this;
    }

    public SIPState gotTRO(User user) {
        System.out.println("this node is busy, can't receive T.R.O");
        if (isSameUser(user)) {
            // do something(?)
        }
        else {
            sendBusyAndCloseWriter(user);
            return this;
        }
        return this;
    }

    public SIPState gotACK(User user) {
        System.out.println("this node is busy, can't receive ACK");
        if (isSameUser(user)) {
            // do something(?)
        }
        else {
            sendBusyAndCloseWriter(user);
            return this;
        }
        return this;
    }

    public SIPState gotOK(User user) {
        System.out.println("this node is busy, can't accept OK");
        if (isSameUser(user)) {
            // do something(?)
        }
        else {
            sendBusyAndCloseWriter(user);
            return this;
        }
        return this;
    }

    public SIPState sendBYE(){
        System.out.println("Seding BYE waiting for OK");
        sendDataPrimary(SIPEvent.BYE);
        return new Exiting(user);
    }

    public SIPState gotBYE(User user) {
        System.out.println("doing bye");
        if (isSameUser(user)) {
            System.out.println("Got BYE sending OK");
            if (user != null) {
                user.stopAudioStream();
                user.closeAudioStream();
            }
            sendDataPrimary(SIPEvent.OK);
            user.endConnection();
//            user.getOut().close();
            // instead of closing out maybe add boolean isConnected?
            return new Idle();
        } else {
            sendBusyAndCloseWriter(user);
            return this;
        }
    }
    
    public SIPState lostConnection(User user) {
        if (isSameUser(user)) {
            System.out.println("Got LOST_CONNECTION returning to Idle");
            sendDataPrimary(SIPEvent.BYE);
            return new Idle();
        } else {
            sendBusyAndCloseWriter(user);
            return this;
        }
    }

    protected void sendBusyAndCloseWriter(User user) {
        System.out.println("I am BUSY");
        try {
            if (user.getOut() != null) {
                user.getOut().println(SIPEvent.BUSY);
                user.getOut().flush();
                user.getOut().close();
                user.endConnection();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
