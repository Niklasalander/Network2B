
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
    private static ServerSocket ss;
    private static int idProvider = 0;

    // THIS IS B
    public static synchronized void beginSocketReader(Socket socketInstance, LocalUser localUser) {
        try {
            RemoteUser newUser = new RemoteUser(socketInstance, new BufferedReader(new InputStreamReader(socketInstance.getInputStream())),
                    new PrintWriter(new OutputStreamWriter(socketInstance.getOutputStream())));
          /*  newUser.setSocket(socketInstance);
            newUser.setIn(new BufferedReader(new InputStreamReader(socketInstance.getInputStream())));
            newUser.setOut(new PrintWriter(new OutputStreamWriter(socketInstance.getOutputStream())));*/
            beginSocketReaderIfIdle(newUser,localUser);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    // THIS IS B
    // Only start new thread if it is going to be used 
    public static synchronized void beginSocketReaderIfIdle(RemoteUser remoteUser, LocalUser localUser) {
        try {
            String str = remoteUser.getIn().readLine();
            String[] received = str.split(" ");
            String command = received[0].trim().toUpperCase();
            if (command.equals("INVITE"))
                SIPHandler.processNextEvent(SIPEvent.INVITE, remoteUser);
            if (remoteUser.getIsConnected()) {
                beginSocketReader(remoteUser, localUser);
            }
            
        } catch (Exception ex) {
            remoteUser.setIsConnected(false);
        }
    }

    // THIS IS A
    public static synchronized void beginSocketReader(RemoteUser user,LocalUser localUser) {
        SocketReader sr = new SocketReader(user,localUser);
        user.setIsConnected(true);
        sr.start();
    }

    //THIS IS A
    public static synchronized void initReceiver(RemoteUser foreigner, Socket se) {
        try {
            foreigner.setSocket(se);
            foreigner.setIn(new BufferedReader(new InputStreamReader(se.getInputStream())));
            foreigner.setOut(new PrintWriter(new OutputStreamWriter(se.getOutputStream())));
        } catch (IOException ex) {
            Logger.getLogger(NetworkServer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static int getNewUserId() {
        return idProvider++;
    }

    public static void main(String[] args) {
         InetAddress addr = null;
        try {
            int port = 9912;
            String address = "localhost";
            if (args.length > 0) {
                port = Integer.parseInt(args[0]);
                addr = InetAddress.getByName("localhost");
            }
             if (args.length == 2) {
                  port = Integer.parseInt(args[0]);
                  addr = InetAddress.getByName((args[1]));
            }
            System.out.println("port: " + port);
          
            ss = new ServerSocket(port, 1, addr);
           // User localUser = new User(addr);
            LocalUser lUser = new LocalUser(addr);
            inHandler = new InputHandler(lUser);
            inHandler.start();
            System.out.println("Server started... ");
            while (true) {
                System.out.println("Waiting for connection...");
                Socket s = ss.accept();
                beginSocketReader(s,lUser);
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
}
