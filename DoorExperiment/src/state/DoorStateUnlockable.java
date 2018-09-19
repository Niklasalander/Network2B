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
public abstract class DoorStateUnlockable extends DoorState
{
public DoorState lock(){
//This is an outgoing signal: 
System.out.println("Signal: Cannot Lock Now."); return this;
}
public DoorState unlock(){
//This is an outgoing signal: 
System.out.println("Signal: Door Unlocked."); return this;
} 
}