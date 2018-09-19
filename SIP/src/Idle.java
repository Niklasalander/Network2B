/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author fno
 */
public class Idle extends SIPState{
    public Idle(){
        
    }
    public SIPState inviting(){
        System.out.println("Sending INVITE, waiting for TRO");
        return new IsInviting();
    }
     public SIPState invited(){
         System.out.println("Received INVITE, sending TRO");
         return new WasInvited();
     }
    public void printState(){
        System.out.println("You are now in an idle state...");
    } 
}
