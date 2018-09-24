
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
    protected static User thisUser;
    protected static User receiverUser;
    protected static InputHandler inHandler;
    protected static SocketReader sReader;
    // THIS IS B
    public static synchronized void beginSocketReader(Socket socketInstance){
        try {
            receiverUser = new User(new BufferedReader(new InputStreamReader(socketInstance.getInputStream())),new PrintWriter(new OutputStreamWriter(socketInstance.getOutputStream())));
            beginSocketReader(receiverUser);
            // SocketReader sr = new SocketReader();
        } catch (IOException ex) {
            Logger.getLogger(NetworkServer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    // THIS IS A
    public static synchronized void beginSocketReader(User user){
       // SocketReader sr = new SocketReader();
       sReader = new SocketReader(user);
       sReader.start();
    }
    //THIS IS A
     public static synchronized void initReceiver(User foreigner, Socket se){
       
       receiverUser = foreigner;
        try {
            receiverUser.setIn( new BufferedReader(new InputStreamReader(se.getInputStream())));
            receiverUser.setOut(new PrintWriter(new OutputStreamWriter(se.getOutputStream())));
        } catch (IOException ex) {
            Logger.getLogger(NetworkServer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public static void main(String[] args) {
        try {
            int port = 9912;
            if (args.length == 1) 
                port = Integer.parseInt(args[0]);
            System.out.println("port: " + port);
            InetAddress addr = InetAddress.getByName("localhost");
            thisUser = new User(addr,port);
            ServerSocket ss = new ServerSocket(port, 1, addr);
//            SIPHandler dh = new SIPHandler();
            inHandler = new InputHandler();
            inHandler.start();
            System.out.println("Server started... ");
            while (true) {
                System.out.println("Waiting for connection...");
                Socket s = ss.accept();
                beginSocketReader(s);
                
              /*  sr.acceptCall();
                sr.start(); */  
            }
            

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
