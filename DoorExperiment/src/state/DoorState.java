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
public class DoorState {
    public DoorState closeButtonPressed() {return this;} 
    public DoorState openButtonPressed() {return this;}
    public DoorState arrived(){return this;}
    public DoorState obstacleEncountered(){return this;}
    public DoorState lock(){return this;}
    public DoorState unlock(){return this;}
    public void printState(){;}
}
