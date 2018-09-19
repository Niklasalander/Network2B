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
public class DoorStateOpening extends DoorStateUnlockable {
    public DoorStateOpening() {;}

    
    public DoorState arrived() {
        return new DoorStateOpen();
    }
public void printState() {
    System.out.println("State: Door is opening");}
}
