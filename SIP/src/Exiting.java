
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
public class Exiting extends CanTimeout {
    
//    public Exiting(){
//        
//    }
    
    public Exiting(RemoteUser user) {
        super(user);
    }
    
    public SIPState gotOK(RemoteUser user) {
        if (isSameUser(user)) {
            cancelTimer();
            user.getLocalUser().getAudioStream().close();
            //clean up
            if (user.getOut() != null)
                user.getOut().close();
            return new Idle();
        }
        else {
            sendBusyAndCloseWriter(user);
            return this;
        }
    }
    
}
