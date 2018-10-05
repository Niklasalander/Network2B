
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
    private static final String E = "E";
    private static final String CALL = "CALL";
    private static final String ACCEPT = "ACCEPT";
    private static final String HANGUP = "HANGUP";
    
    private static final String NOW = "NOW";
    private static final String FAULTY = "FAULTY";
    private static final String CURCON = "CURCON";
    private static final String NEWCON = "NEWCON";
    private static final String CALLE = "CALLE";
    private static final String ACCEPTE = "ACCEPTE";
    private static final String HANGUPE = "HANGUPE";

    private User user;
    private InetAddress localAddress;
    private int localPort;
    public InputHandler(InetAddress localAddress, int localPort) {
        this.localAddress = localAddress;
        this.localPort = localPort;
        this.setName("Input Handler: " + this.getId());
    }
    
    @Override 
    public void run() {
        Scanner sc = new Scanner(System.in);
        String command = "";
        String ipString = "";
        while (!command.equals(EXIT) && !command.equals(E)) {
            try {
                String input = sc.nextLine().trim().toUpperCase();
                String[] received = input.split(" ");
                command = received[0].trim().toUpperCase();
                switch(command) {
                    case EXIT : SIPHandler.processNextEvent(SIPEvent.SEND_BYE); NetworkServer.killme(); break;
                    case E : SIPHandler.processNextEvent(SIPEvent.SEND_BYE); NetworkServer.killme(); break;
                    case CALL : 
                        if (received[1].trim().toUpperCase().equals(localAddress.getHostName().toUpperCase()) && Integer.parseInt(received[2].trim()) == localPort) {
                            System.out.println("You cannot call yourself...");
                            break;
                        }
                        initSocket(received);
                        SIPHandler.processNextEvent(SIPEvent.SEND_INVITE, this.user, this);
                        break;
                    case ACCEPT :  
                        SIPHandler.processNextEvent(SIPEvent.SEND_TRO);
                        break;
                    case "A" :  
                        SIPHandler.processNextEvent(SIPEvent.SEND_TRO);
                        break;
                    case HANGUP : SIPHandler.processNextEvent(SIPEvent.SEND_BYE); break;
                    case "H" : SIPHandler.processNextEvent(SIPEvent.SEND_BYE); break;
                    
                    
                    /** For testing **/
                    case NOW : System.out.println("Now in state: " + SIPHandler.getCurrentState()); break;
                    
                    /*** Faulty commands ***/
                    case CURCON : 
                        String s = received[1].trim().toUpperCase();
                        int iterations = 1;
                        if (received.length > 2)
                            iterations = Integer.parseInt(received[2].trim());
                        for (int i = 0; i < iterations; i++) {
                            SIPHandler.faultySendAny(s);
                        }
                        break;
                    
                    case NEWCON : 
                        s = received[3].trim().toUpperCase();
                        iterations = 1;
                        if (received.length > 4)
                            iterations = Integer.parseInt(received[4].trim());
                        for (int i = 0; i < iterations; i++) {
                            initSocket(received);
                            this.user.getOut().println(s);
                            this.user.getOut().flush();
                        }
                        break;
                        
                    case CALLE : 
                        initSocket(received);
                        for (int i = 0; i < Integer.parseInt(received[3].trim()); i++)
                            SIPHandler.processNextEvent(SIPEvent.SEND_INVITE, this.user, this);
                        break;
                    case ACCEPTE : 
                        for (int i = 0; i < Integer.parseInt(received[1].trim()); i++)
                            SIPHandler.processNextEvent(SIPEvent.SEND_TRO);
                        break;
                    case HANGUPE : 
                        for (int i = 0; i < Integer.parseInt(received[1].trim()); i++)
                            SIPHandler.processNextEvent(SIPEvent.SEND_BYE);
                        break;
                    case FAULTY : 
                        System.out.println("CURCON  [String, nrOfMessages] (does not change this state)");
                        System.out.println("NEWCON  [Address, Port, String, NrOfMessages] (does not change this state)");
                        System.out.println("CALLE   [Address, Port, NrOfMessages]");
                        System.out.println("ACCEPTE [NrOfMessages]");
                        System.out.println("HANGUPE [NrOfMessages]");
                        break;
                    default : 
                        System.out.println("Not a valid command\n"
                        + "Use: CALL, ACCEPT, HANGUP and EXIT");
                }
            } catch (UnknownHostException ex) {
                System.out.println("Cannot find host: " + ipString);
                ex.printStackTrace();
            } catch (IOException ex) {
                System.out.println("Could not create a connection");
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
        InetAddress remoteAddress = InetAddress.getByName(ipString);
        int remotePort = Integer.parseInt(received[2].trim());
        this.user = new User(localAddress, localPort, remoteAddress, remotePort); 
        this.user.setIsConnected(true);
        NetworkServer.initReceiver(this.user, new Socket(remoteAddress, remotePort));
        NetworkServer.beginSocketReader(this.user);
    }
    
}
