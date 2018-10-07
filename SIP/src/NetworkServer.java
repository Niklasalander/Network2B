
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

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Niklas
 */
public class NetworkServer {

    protected static BufferedReader in;
    protected static PrintWriter out;
    protected static InputHandler inHandler;
    protected static SocketReader sReader;
    private static InetAddress localAddress;
    private static int localPort;
    private static ServerSocket ss;
    private static int idProvider = 0;


    public static int getNewUserId() {
        return idProvider++;
    }

    public static void main(String[] args) {
        try {
            int localPort = 9912;
            InetAddress socketAddress = InetAddress.getByName("localhost");
            if (args.length > 0) {
                localPort = Integer.parseInt(args[0]);
                localAddress = InetAddress.getByName("localhost");
                socketAddress = InetAddress.getByName("localhost");
            }
            if (args.length == 2) {
                  localPort = Integer.parseInt(args[0]);
                  localAddress = InetAddress.getByName((args[1]));
                  socketAddress = InetAddress.getByName((args[1]));
            }
            if (args.length == 3) {
                  localPort = Integer.parseInt(args[0]);
                  localAddress = InetAddress.getByName((args[1]));
                  socketAddress = InetAddress.getByName((args[2]));
            }
            System.out.println("port: " + localPort + " " + localAddress.getHostAddress());
          
            ss = new ServerSocket(localPort, 1, socketAddress);
            inHandler = new InputHandler(localAddress, localPort);
            inHandler.start();
            System.out.println("Server started... ");
            while (true) {
                System.out.println("Waiting for connection...");
                Socket s = ss.accept();
                beginSocketReader(s);
            }
        } catch (IOException ex) {
            System.out.println("Main exiting");
        } finally {
                try {
                    if (ss != null) 
                        ss.close();
            } catch (IOException ex) {
            }
        }
    }

    public static void killme() {
        try {
            if (ss != null) 
                ss.close();
        } catch (IOException ex) {
        }
    }
    
    // THIS IS B
    public static synchronized void beginSocketReader(Socket socketInstance) {
        try {
            User user = new User(socketInstance, localAddress, localPort);
            beginSocketReaderIfIdle(user);
        } catch (IOException ex) {
        }
    }
    
    // THIS IS B
    // Only start new thread if it is going to be used 
    public static synchronized void beginSocketReaderIfIdle(User user) {
        try {
            String str = user.getIn().readLine();
            String[] received = str.split(" ");
            String command = received[0].trim().toUpperCase();
            SIPEvent event = getEventUsingString(command);
            SIPHandler.processNextEvent(event, user);
            if (user.getIsConnected())
                beginSocketReader(user);
        } catch (Exception ex) {
            user.setIsConnected(false);
        }
    }
    
    
    
    
    // THIS IS A (caller)
    public static synchronized void beginSocketReader(User user) {
        SocketReader sr = new SocketReader(user);
        sr.start();
    }

    // THIS IS A (caller)
    public static synchronized void initReceiver(User user, Socket se) throws IOException {
        user.setSocket(se);
        user.setIn(new BufferedReader(new InputStreamReader(se.getInputStream())));
        user.setOut(new PrintWriter(new OutputStreamWriter(se.getOutputStream())));
    }
    
    
    
    public static SIPEvent getEventUsingString(String command) {
        switch(command) {
            case "SEND_INVITE" : return SIPEvent.SEND_INVITE;
            case "SEND_TRO" : return SIPEvent.SEND_TRO;
            case "SEND_ACK" : return SIPEvent.SEND_ACK;
            case "SEND_OK" : return SIPEvent.SEND_OK;
            case "SEND_BYE" : return SIPEvent.SEND_BYE;
            case "SEND_BUSY" : return SIPEvent.SEND_BUSY;
            case "INVITE" : return SIPEvent.INVITE;
            case "TRO" : return SIPEvent.TRO;
            case "ACK" : return SIPEvent.ACK;
            case "OK" : return SIPEvent.OK;
            case "BYE" : return SIPEvent.BYE;
            case "BUSY" : return SIPEvent.BUSY;
            case "LOST_CONNECTION" : return SIPEvent.LOST_CONNECTION;
            case "TIMEOUT" : return SIPEvent.TIMEOUT;
            case "MAKE_SURE_IDLE" : return SIPEvent.MAKE_SURE_IDLE;
            default : return null;
        }
    }
    
}



