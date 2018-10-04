

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

    protected static User user;

    public SIPState() {
        
    }
    
    public SIPState(User user) {
        this.user = user;
    }
    
    public SIPState inviting(User user) {
        return this;
    }

    public SIPState invited(User user) {
        return this;
    }

    public SIPState gotTRO(User user) {
        return this;
    }

    public SIPState gotACK(User user) {
        return this;
    }

    public SIPState gotOK(User user) {
        return this;
    }

    public SIPState gotBYE(User user) {
        return this;
    }
    
    public SIPState gotBUSY(User user) {
        return this;
    }
    
    public SIPState sendBYE() {
        return this;
    }
    
    public SIPState sendTRO() {
        return this;
    }
      public SIPState sendTRO(User user) {
          return this;
      }
    
    public SIPState lostConnection(User user) {
        return this;
    }
    
    public SIPState timeoutReached(User user) {
        return this;
    }
    
    public SIPState makeSureIdle(User user) {
        return this;
    }

    public void printState() {
        System.out.println("current state : " + this.getClass().getSimpleName());
    }

    
     public String returnStates() {
        return "current state : " + this.getClass().getSimpleName().toString();
    }
     
    protected boolean isSameUser(User otherUser) {
//        System.out.println("This  user id: " + this.user.getId());
//        System.out.println("Other user id: " + otherUser.getId());
        if (user.getId() == otherUser.getId()) {
//            System.out.println("It is the same user");
            return true;
        }
        else {
//            System.out.println("Not the same user");
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
            String s = String.valueOf(number);
            user.getOut().println(event + " " + s);
            user.getOut().flush();
        } catch (Exception ex) {
            System.out.println("SIPState could not send data");
            ex.printStackTrace();
        }
    } 
       protected void sendDataWithIntegers(SIPEvent event, int number,String ipAddress) {
        try {
           
            user.getOut().println(event + " " + number + " " + ipAddress);
            user.getOut().flush();
        } catch (Exception ex) {
            System.out.println("SIPState could not send data");
            ex.printStackTrace();
        }
    } 
}
