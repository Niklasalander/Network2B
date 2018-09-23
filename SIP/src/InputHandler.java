
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
    private UserInfo u;
    private UserInfo receiverInfo;
    public InputHandler() {
        //AudioStreamUDP u;
    }
    
      public InputHandler(UserInfo userInfo) {
       this.u = userInfo;
    }
    
    @Override 
    public void run() {
        Scanner sc = new Scanner(System.in);
        System.out.println("now performing");
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
                        System.out.println("Now doing call...");
                        ipString = received[1].trim();
                        InetAddress ipAddress = InetAddress.getByName(ipString);
                        port = Integer.parseInt(received[2].trim());
                        receiverInfo = new UserInfo(port,ipAddress);
                        System.out.println("Attempted");
                        //SIPHandler.startCall(ipAddress, port);
//                        Socket socket = new Socket(ipAddress, port);
//                        SocketReader sr = new SocketReader(socket);
                        SIPHandler.processNextEvent(SIPEvent.SEND_INVITE,receiverInfo);
                        //SocketReader sr = new SocketReader(receiverInfo.getSocket(), receiverInfo.getOut());
                        SocketReader sr = new SocketReader(receiverInfo);
                        sr.start();
                         /**
             *    Socket socket = new Socket(ipAddress, port);
            out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
            SocketReader sr = new SocketReader(socket, out);
            sr.start();
            processNextEvent(SIPEvent.SEND_INVITE, out);
             */
//                        sr.start();
                        break;
                    case ACCEPT : 
                         System.out.println("now in accept");
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
                        System.out.println("GOT TRO I THINK");
                        SIPHandler.processNextEvent(SIPEvent.TRO);
                        break;
                    case "NOW" : System.out.print(SIPHandler.getCurrentState());
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
