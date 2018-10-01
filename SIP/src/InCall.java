
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
    public InCall(){};
    
    public InCall(User user){
        super(user);
//         AudioStreamUDP  audio= user.getLocalUser().getAudioStream();
        try {
            System.out.println("I am user " + user.getLocalAudioPort() + " Address: " + user.getLocalAddress().getHostName() + " " +
                    " and will connect with " + +user.getRemoteAudioPort() + "Address " + user.getRemoteAddress().getHostName() + " " );
            user.connectAudioStream();
//            audio.connectTo(user.getRe,   user.getUserPuser.ort());
//            System.out.println("Starting audio stream");
            user.startAudioStream();
//            audio.startStreaming();
//            System.out.println("Audio streaming is running");
        } catch (IOException ex) {
            Logger.getLogger(InCall.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    
}
