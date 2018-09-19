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
public class DoorStateLocked extends DoorState {

    public DoorStateLocked() {
    }
    public DoorState unlock(){
    //This is an outgoing signal: 
    System.out.println("Signal: Door is unlocked.");
    return new DoorStateClosed();
   }
    public void printState(){
    System.out.println("State: Door is closed and locked");
}
}

