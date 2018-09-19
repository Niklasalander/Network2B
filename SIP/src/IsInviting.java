/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author fno
 */
public class IsInviting extends Busy{
    public IsInviting(){
        
    }
    public SIPState gotTRO(){
       
        System.out.println("Got TRO now we send ok...");
        return new InCall();
    }
    public void printState(){
        System.out.println("You are now in inviting state...");
    }
}
