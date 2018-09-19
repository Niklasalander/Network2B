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
public class DoorStateClosing extends DoorStateUnlockable {
    public DoorStateClosing() {;}
    public DoorState arrived() {
        return new DoorStateClosed();
    }
    public DoorState obstacleEncountered() {
        return new DoorStateOpening();
    } public void printState() {
        System.out.println("State: Door is closing");
    }
}