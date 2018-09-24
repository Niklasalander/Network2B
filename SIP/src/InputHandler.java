
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
    private User target;
    private Socket s;

    public InputHandler() {
        
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

                switch(command) {
                    case EXIT : break;
                    case CALL : 
                        ipString = received[1].trim();
                        InetAddress ipAddress = InetAddress.getByName(ipString);
                        port = Integer.parseInt(received[2].trim());
                        this.target = new User(ipAddress,port); // to and from B
                        NetworkServer.initReceiver(target,new Socket(ipAddress, port));
                        NetworkServer.beginSocketReader(this.target);
                        SIPHandler.processNextEvent(SIPEvent.SEND_INVITE,this.target.getOut());
                        //host starts connection
                       // SIPHandler.startCall(ipAddress, port);
//                        Socket socket = new Socket(ipAddress, port);
//                        SocketReader sr = new SocketReader(socket);
//                        sr.sendMessage(SIPEvent.SEND_INVITE);
//                        sr.start();
                        break;
                    case ACCEPT : 
                        //SEND TRO
                        SIPHandler.processNextEvent(SIPEvent.SEND_TRO);
                        break;
                    case HANGUP : 
                        SIPHandler.processNextEvent(SIPEvent.SEND_BYE);
                        break;
                    case "INV" : 
                        System.out.println("sending more invites");
                        SIPHandler.sendInv();
                        break;
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
    
}