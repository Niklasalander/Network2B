/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package state;

/**
 *
 * @author fno
 */
public class DoorStateClosed extends DoorState
{
   public DoorStateClosed(){;}
   public DoorState openButtonPressed(){

    return new DoorStateOpening();
}
public DoorState lock(){
//This is an outgoing signal: 
    System.out.println("Signal: Door is now locking."); 
    return new DoorStateLocked();
}
public DoorState unlock(){
//This is an outgoing signal: 
   System.out.println("Signal: Door is unlocked.");
   return this;
}
public void printState(){
System.out.println("State: Door is closed");
} 
}
