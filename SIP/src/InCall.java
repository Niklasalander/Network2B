
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
         AudioStreamUDP  audio= user.getLocalUser().getAudioStream();
        try {
            audio.connectTo(user.getLocalUser().getAddress(), user.getLocalUser().getRemotePortNumber());
            audio.startStreaming();
        } catch (IOException ex) {
            Logger.getLogger(InCall.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    
}
