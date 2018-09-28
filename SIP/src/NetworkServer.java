
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
    private static int idProvider = 0;

    // THIS IS B
    public static synchronized void beginSocketReader(Socket socketInstance, User newUser) {
        try {
            /*User receiverUser = new User(socketInstance, new BufferedReader(new InputStreamReader(socketInstance.getInputStream())),
                    new PrintWriter(new OutputStreamWriter(socketInstance.getOutputStream())));*/
            newUser.setSocket(socketInstance);
            newUser.setIn(new BufferedReader(new InputStreamReader(socketInstance.getInputStream())));
            newUser.setOut(new PrintWriter(new OutputStreamWriter(socketInstance.getOutputStream())));
            beginSocketReader(newUser);
        } catch (IOException ex) {
            Logger.getLogger(NetworkServer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    // THIS IS A
    public static synchronized void beginSocketReader(User user) {
        SocketReader sr = new SocketReader(user);
        sr.start();
    }

    //THIS IS A
    public static synchronized void initReceiver(User foreigner, Socket se) {
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
        try {
            int port = 9912;
            String address = "localhost";
            if (args.length > 0) {
                port = Integer.parseInt(args[0]);
            }
            if (args.length > 1) {
                address = args[1];
            }
            System.out.println("port: " + port);
            InetAddress addr = InetAddress.getByName(address);
            ServerSocket ss = new ServerSocket(port, 1, addr);
            User localUser = new User(addr);
            inHandler = new InputHandler(localUser);
            inHandler.start();
            System.out.println("Server started... ");
            while (true) {
                System.out.println("Waiting for connection...");
                Socket s = ss.accept();
                beginSocketReader(s,localUser);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

}
