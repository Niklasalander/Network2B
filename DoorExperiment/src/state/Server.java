/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package state;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author fno
 */
public class Server {
    
     protected static BufferedReader in;
     protected static PrintWriter out;
     public static void main(String[] args){
         try {
             InetAddress addr = InetAddress.getByName("localhost");
             ServerSocket ss = new ServerSocket(9912, 1, addr);
            
             Socket s = ss.accept();
             DoorHandler dh = new DoorHandler();
             System.out.println("Server started... ");
             try {
                in = new BufferedReader(new InputStreamReader(s.getInputStream()));
                out = new PrintWriter(new OutputStreamWriter(s.getOutputStream()));

            } catch (IOException ex) {
                try {
                    if (in != null)
                        in.close();
                    if (out != null)
                        out.close();
                } catch (IOException e) {
                }
                System.out.println("Could not establish connection to client");
            }
             while(true){
                 Integer choice = Integer.parseInt(in.readLine());
             switch(choice)
           {
               case 1: dh.processNextEvent(DoorHandler.DoorEvent.CLOSE_BUTTON); break;
               case 2: dh.processNextEvent(DoorHandler.DoorEvent.OPEN_BUTTON); break;
               case 3: dh.processNextEvent(DoorHandler.DoorEvent.ARRIVED); break;
               case 4: dh.processNextEvent(DoorHandler.DoorEvent.OBSTACLE_ENCOUNTERED);break;   
               case 5: dh.processNextEvent(DoorHandler.DoorEvent.LOCK);break;
               case 6: dh.processNextEvent(DoorHandler.DoorEvent.UNLOCK);break;
              }
             String sendBack = dh.returnStates();
             out.println(sendBack);
             out.flush();
             }
         } catch (IOException ex) {
             Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
         }
     }
}
