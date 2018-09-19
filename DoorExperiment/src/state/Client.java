/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package state;

import java.net.InetAddress;
import java.util.Scanner;

/**
 *
 * @author fno
 */
public class Client {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        int choice = 0;
        DoorHandler dh = new DoorHandler();
        InetAddress addr;
        
        Scanner scan = new Scanner(System.in);
        do{
           dh.printState();
           System.out.println();
           System.out.println("What happens to the door?"); 
           System.out.println("1. Close-button is pressed.");
           System.out.println("2. Open-button is pressed."); 
           System.out.println("3. It has arrived.");
           System.out.println("4. An obstacle is encountered."); 
           System.out.println("5. Lock the door.");
           System.out.println("6. Unlock the door.");
           System.out.println("0. I wanna go home."); 
           choice = scan.nextInt();
         /*  switch(choice)
           {
               case 1: dh.processNextEvent(DoorHandler.DoorEvent.CLOSE_BUTTON); break;
               case 2: dh.processNextEvent(DoorHandler.DoorEvent.OPEN_BUTTON); break;
               case 3: dh.processNextEvent(DoorHandler.DoorEvent.ARRIVED); break;
               case 4: dh.processNextEvent(DoorHandler.DoorEvent.OBSTACLE_ENCOUNTERED);break;   
               case 5: dh.processNextEvent(DoorHandler.DoorEvent.LOCK);break;
               case 6: dh.processNextEvent(DoorHandler.DoorEvent.UNLOCK);break;
           }*/
           System.out.println("");
        }while(choice != 0);
    }
    
}
