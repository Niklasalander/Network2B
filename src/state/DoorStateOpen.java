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
public class DoorStateOpen extends DoorStateUnlockable
{
public DoorStateOpen(){;}
 public DoorState closeButtonPressed(){
  return new DoorStateClosing();
}
public void printState(){
   System.out.println("State: Door is open");
} 
}