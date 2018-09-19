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
    public SIPState gotOK(){
        return new Idle();
    }
    
}
