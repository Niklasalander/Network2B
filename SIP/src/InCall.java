
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author fno
 */
public class InCall extends Busy {
    
    public InCall(User user){
        super(user);
        
        try {
//            System.out.println("I am user " + user.getLocalAudioPort() + " Address: " + user.getLocalAddress().getHostName() + " " +
//                    " and will connect with " + +user.getRemoteAudioPort() + "Address " + user.getRemoteAddress().getHostName() + " " );
            user.connectAudioStream();
            user.startAudioStream();
        } catch (IOException ex) {
            sendDataPrimary(SIPEvent.BYE);
            user.endConnection();
        }
    }
    
    public void printState() {
        System.out.println("You are now in the InCall state...");
    }

    
}
