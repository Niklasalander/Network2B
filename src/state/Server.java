/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package state;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author fno
 */
public class Server {
     public static void main(String[] args){
         try {
             InetAddress addr = InetAddress.getByName("localhost");
             ServerSocket s = new ServerSocket(9912, 1, addr);
             
             System.out.println("Server started... ");
         } catch (IOException ex) {
             Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
         }
     }
}
