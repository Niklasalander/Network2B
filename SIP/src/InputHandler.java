
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Niklas
 */
public class InputHandler extends Thread {
    private static final String EXIT = "EXIT";
    private static final String CALL = "CALL";
    private static final String ACCEPT = "ACCEPT";
    private static final String HANGUP = "HANGUP";
//    private static int idProvider = 0;
    private User target;
    private User thisOne;
    public InputHandler() {
         thisOne = new User();   
         System.out.println("new this user created " + thisOne.getLocalPortNumber());
         
    }
    
    @Override 
    public void run() {
        Scanner sc = new Scanner(System.in);
        String command = "";
        String ipString = "";
        int port;
        while (!command.equals(EXIT)) {
            try {
                String input = sc.nextLine().trim().toUpperCase();
                String[] received = input.split(" ");
                command = received[0].trim().toUpperCase();
                System.out.println("THIS USER " + thisOne.getLocalPortNumber());
                switch(command) {
                    case EXIT : break;
                    case CALL : 
                        ipString = received[1].trim();
                        InetAddress ipAddress = InetAddress.getByName(ipString);
                        port = Integer.parseInt(received[2].trim());
                        this.target = new User(ipAddress, port); // to and from B
                        NetworkServer.initReceiver(target,new Socket(ipAddress, port));
                        NetworkServer.beginSocketReader(this.target);
                        SIPHandler.processNextEvent(SIPEvent.SEND_INVITE, this.target);
                        break;
                    case ACCEPT : System.out.println("in accept");
                       if(this.thisOne.getLocalPortNumber()!=0){
                           System.out.println("condition accept");
                           this.target = new User(this.thisOne.getLocalPortNumber());
                           System.out.println("HERERE");
                           SIPHandler.processNextEvent(SIPEvent.SEND_TRO,this.target);break;
                    }
                      //  System.out.println("Something went wrong");
                   // break;
                        //SEND TRO  
                    case "A" : SIPHandler.processNextEvent(SIPEvent.SEND_TRO);break;
                    case HANGUP : 
                        SIPHandler.processNextEvent(SIPEvent.SEND_BYE);break;
                    case "H" : SIPHandler.processNextEvent(SIPEvent.SEND_BYE);break;
                    /** For testing **/
                    case "INV" : 
                        System.out.println("sending more invites");
                        SIPHandler.sendInv();
                        break;
                    /** For testing **/
                    case "TRO" : 
                        SIPHandler.processNextEvent(SIPEvent.TRO);
                        break;
                    case "NOW" : System.out.println(SIPHandler.getCurrentState()); 
                    default : 
                        System.out.println("Not a valid command\n"
                        + "Use: CALL, ACCEPT, HANGUP and EXIT");
                }
            } catch (UnknownHostException ex) {
                System.out.println("Cannot find host: " + ipString);
            } catch (Exception ex) {
                System.out.println("could not create socket");
            }
        } 
    }
    private boolean checkIfPortIsDigit(String portNumber){
          if(portNumber.chars().allMatch(Character::isDigit)){
              return true;
          }
          return false;
       
   }
    
}
