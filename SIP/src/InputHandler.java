
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Arrays;
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
    

    public InputHandler() {
        //AudioStreamUDP u;
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
                System.out.println("command " + input);
                String[] received = input.split(" ");
                command = received[0].trim().toUpperCase();
                
                switch(command) {
                    case EXIT : break;
                    case CALL : 
                        System.out.println("PAYCHECK");
                        ipString = received[1].trim();
                        InetAddress ipAddress = InetAddress.getByName(ipString);
                        port = Integer.parseInt(received[2].trim());
                        SIPHandler.startCall(ipAddress, port);
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
