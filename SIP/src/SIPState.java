/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author fno
 */
public abstract class SIPState {
     public SIPState inviting(){return this;}
     public SIPState invited(){return this;}
     public SIPState gotTRO(){return this;}
     public SIPState gotACK(){return this;}
     public SIPState gotOK(){return this;}
     public SIPState doBYE(){return this;}
     public SIPState gotBYE(){return this;}
     public void printState(){System.out.println("current state : " + this.getClass().getSimpleName());};
     public String returnStates(){ 
         return "current state : " + this.getClass().getSimpleName().toString();
     }
}
