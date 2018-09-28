
import java.util.TimerTask;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author fno
 */
public class ResponsiveServerTimer extends TimerTask{
    private RemoteUser u;
    public ResponsiveServerTimer(RemoteUser user){
        this.u = user;
    }
    @Override
    public void run() {
        System.out.println("Timer ran out");
        SIPHandler.processNextEvent(SIPEvent.TIMEOUT,u);
       }
    
    
}
