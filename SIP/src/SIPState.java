
import java.io.BufferedReader;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author fno
 */
public abstract class SIPState {

    protected static RemoteUser user;
    //protected static LocalUser localUser;

    public SIPState() {
        System.out.println("Creating new SIP state withuser printwriter");
    }

    public SIPState(RemoteUser user) {
        System.out.println("Creating new SIP state with printwriter");
        this.user = user;
    }
    
    public SIPState inviting(RemoteUser user) {
        return this;
    }

    public SIPState invited(RemoteUser user) {
        return this;
    }

    public SIPState gotTRO(RemoteUser user) {
        return this;
    }

    public SIPState gotACK(RemoteUser user) {
        return this;
    }

    public SIPState gotOK(RemoteUser user) {
        return this;
    }

    public SIPState gotBYE(RemoteUser user) {
        return this;
    }
    
    public SIPState gotBUSY(RemoteUser user) {
        return this;
    }
    
    public SIPState sendBYE() {
        return this;
    }
    
    public SIPState sendTRO() {
        return this;
    }
      public SIPState sendTRO(RemoteUser user) {
          return this;
      }
    
    public SIPState lostConnection(RemoteUser user) {
        return this;
    }
    
    public SIPState timeoutReached(RemoteUser user) {
        return this;
    }

    public void printState() {
        System.out.println("current state : " + this.getClass().getSimpleName());
    }

    
     public String returnStates() {
        return "current state : " + this.getClass().getSimpleName().toString();
    }
     
    protected boolean isSameUser(RemoteUser otherUser) {
        System.out.println("This  user id: " + this.user.getId());
        System.out.println("Other user id: " + otherUser.getId());
        if (user.getId() == otherUser.getId()) {
            System.out.println("It is the same user");
            return true;
        }
        else {
            System.out.println("Not the same user");
            return false;
        }
    }
     
    protected void sendDataPrimary(SIPEvent event) {
        try {
            user.getOut().println(event);
            user.getOut().flush();
        } catch (Exception ex) {
            System.out.println("SIPState could not send data");
            ex.printStackTrace();
        }
    }
     protected void sendDataWithInteger(SIPEvent event, int number) {
        try {
            user.getOut().println(event + " " + number);
            user.getOut().flush();
        } catch (Exception ex) {
            System.out.println("SIPState could not send data");
            ex.printStackTrace();
        }
    } 
}
