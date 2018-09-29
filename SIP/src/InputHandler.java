
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
    
    private static final String CURCON = "CURCON";
    private static final String NEWCON = "NEWCON";
    private static final String CALLE = "CALLE";
    private static final String ACCEPTE = "ACCEPTE";
    private static final String HANGUPE = "HANGUPE";
//    private static int idProvider = 0;
    private RemoteUser target; 
    private LocalUser localUser;
    public InputHandler(LocalUser newUser) {
        this.localUser  = newUser;
        this.setName("Input Handler: " + this.getId());
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
                System.out.println("user " + localUser.getAudioPort());
                switch(command) {
                    case EXIT : SIPHandler.processNextEvent(SIPEvent.SEND_BYE); NetworkServer.killme(); break;
                    case CALL : 
                        initSocket(received);
                        SIPHandler.processNextEvent(SIPEvent.SEND_INVITE, this.target);
                        break;
                    case ACCEPT :  if(this.localUser.getAudioPort()!=0){
                         this.target = new RemoteUser(this.localUser.getAudioPort());
                         SIPHandler.processNextEvent(SIPEvent.SEND_TRO,this.target);
                         break;
                    }
                    case "A" :  if(this.localUser.getAudioPort()!=0){
                         this.target = new RemoteUser(this.localUser.getAudioPort());
                         SIPHandler.processNextEvent(SIPEvent.SEND_TRO,this.target);
                         break;
                    }
                    case HANGUP : SIPHandler.processNextEvent(SIPEvent.SEND_BYE); break;
                    case "H" : SIPHandler.processNextEvent(SIPEvent.SEND_BYE); break;
                    /** For testing **/
                    case "INV" : 
                        System.out.println("sending more invites");
                        SIPHandler.sendInv();
                        break;
                    /** For testing **/
                    case "TRO" : 
                        SIPHandler.processNextEvent(SIPEvent.TRO);
                        break;
                    case "NOW" : System.out.println("Now in state: " + SIPHandler.getCurrentState()); break;
                    
                    
                    
                    /*** Faulty commands ***/
                    case CURCON : 
                        String s = received[1].trim().toUpperCase();
                        int iterations = 1;
                        if (received.length > 2)
                            iterations = Integer.parseInt(received[2].trim());
                        for (int i = 0; i < iterations; i++) {
                            this.target.getOut().println(s);
                            this.target.getOut().flush();
                        }
                        break;
                    
                    case NEWCON : 
                        initSocket(received);
                        s = received[3].trim().toUpperCase();
                        iterations = 1;
                        if (received.length > 4)
                            iterations = Integer.parseInt(received[4].trim());
                        for (int i = 0; i < iterations; i++) {
                            this.target.getOut().println(s);
                            this.target.getOut().flush();
                        }
                        break;
                        
                    case CALLE : 
                        initSocket(received);
                        for (int i = 0; i < Integer.parseInt(received[3].trim()); i++)
                            SIPHandler.processNextEvent(SIPEvent.SEND_INVITE, this.target);
                        break;
                    case ACCEPTE : 
//                        initSocket(received);
                        for (int i = 0; i < Integer.parseInt(received[1].trim()); i++)
                            SIPHandler.processNextEvent(SIPEvent.SEND_TRO);
                        break;
                    case HANGUPE : 
//                        initSocket(received);
                        for (int i = 0; i < Integer.parseInt(received[1].trim()); i++)
                            SIPHandler.processNextEvent(SIPEvent.SEND_BYE);
                        break;
                    default : 
                        System.out.println("Not a valid command\n"
                        + "Use: CALL, ACCEPT, HANGUP and EXIT");
                }
            } catch (UnknownHostException ex) {
                System.out.println("Cannot find host: " + ipString);
            } catch (IOException ex) {
                ex.printStackTrace();
            } catch (ArrayIndexOutOfBoundsException ex) {
                System.out.println("Too few arguments");
            } catch (NumberFormatException ex) {
                System.out.println("Cannot convert letters to numbers");
            } catch (NullPointerException ex) {
                System.out.println("No connection exists");
            }
        } 
    }
    
    private void initSocket(String[] received) throws UnknownHostException, IOException {
        String ipString = received[1].trim();
        InetAddress ipAddress = InetAddress.getByName(ipString);
        int port = Integer.parseInt(received[2].trim());
        this.target = new RemoteUser(ipAddress); // to and from B
        this.target.setIsConnected(true);
        NetworkServer.initReceiver(this.target, new Socket(ipAddress, port));
        NetworkServer.beginSocketReader(this.target,this.localUser);
        System.out.println("init socket");
    }
    
}
