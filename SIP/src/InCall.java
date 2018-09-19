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
    
    public InCall(){
        
    }
    public SIPState doBYE(){
        return new Exiting();
    }
    public SIPState gotBye(){
        return new Idle();
    }
    
}
