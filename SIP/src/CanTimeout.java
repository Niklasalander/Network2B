
import java.util.Timer;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Niklas
 */
public class CanTimeout extends Busy {
    private Timer rsTimer;
    
    public CanTimeout(User user) {
        super(user);
        rsTimer = new Timer();
        rsTimer.schedule(new ResponsiveServerTimer(user), 5000);
    }
    
    public SIPState timeoutReached(User user) {
        sendDataPrimary(SIPEvent.BYE);
        System.out.println("CanTimeout sent BYE");
        return new Idle();
    }
    
    protected void cancelTimer() {
        rsTimer.cancel();
    }
}