/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author fno
 */
public class WasInvited extends Busy{
     public WasInvited(){
         
     }
    
     public SIPState gotACK(){
         return new InCall();
     }
     
     public void printState(){
        System.out.println("You are now in the 'was invited' state...");
    }
}
