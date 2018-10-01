
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
//            ex.printStackTrace();
        } finally {
                try {
                    if (ss != null) 
                        ss.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    public static void killme() {
        
        try {
            if (ss != null) 
                ss.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    // THIS IS B
    public static synchronized void beginSocketReader(Socket socketInstance) {
        try {
            User user = new User(socketInstance, localAddress, localPort);
            beginSocketReaderIfIdle(user);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    // THIS IS B
    // Only start new thread if it is going to be used 
    public static synchronized void beginSocketReaderIfIdle(User user) {
        try {
            String str = user.getIn().readLine();
            String[] received = str.split(" ");
            String command = received[0].trim().toUpperCase();
            if (command.equals("INVITE"))
                SIPHandler.processNextEvent(SIPEvent.INVITE, user);
            if (user.getIsConnected()) {
                beginSocketReader(user);
            }
            
        } catch (Exception ex) {
            user.setIsConnected(false);
            ex.printStackTrace();
        }
    }
    
    
    
    
    // THIS IS A
    public static synchronized void beginSocketReader(User user) {
        SocketReader sr = new SocketReader(user);
        sr.start();
    }

    //THIS IS A
    public static synchronized void initReceiver(User user, Socket se) {
        try {
            user.setSocket(se);
            user.setIn(new BufferedReader(new InputStreamReader(se.getInputStream())));
            user.setOut(new PrintWriter(new OutputStreamWriter(se.getOutputStream())));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
}



