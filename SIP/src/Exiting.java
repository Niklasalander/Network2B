
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
public class Exiting extends Busy{
    
    public Exiting(){
        
    }
    
    public Exiting(PrintWriter out) {
        super(out);
    }
    
    public SIPState gotOK(PrintWriter out) {
        if (isSameUser(out)) {
            //clean up
            if (out != null)
                out.close();
            return new Idle();
        }
        else {
            sendBusyAndCloseWriter(out);
            return this;
        }
        
    }
    
}
