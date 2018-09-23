
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

    public static void main(String[] args) {
        try {
            int port = 9912;
            if (args.length == 1) 
                port = Integer.parseInt(args[0]);
            System.out.println("port: " + port);
            InetAddress addr = InetAddress.getByName("localhost");
            ServerSocket ss = new ServerSocket(port, 1, addr);
//            SIPHandler dh = new SIPHandler();
            InputHandler ih = new InputHandler(new UserInfo(port,addr));
            ih.start();
            System.out.println("Server started... ");
            while (true) {
                System.out.println("Waiting for connection...");
                Socket s = ss.accept();
                SocketReader sr = new SocketReader(s,port,addr);
                //SocketReader sr = new SocketReader(s);
               // sr.acceptCall();
                sr.start();
                
            }
            
//            while (true) {
//                Integer choice = Integer.parseInt(in.readLine());
//                switch (choice) {
//                    case 1:
//                        dh.processNextEvent(SIPEvent.SEND_INVITE);
//                        break;
//                    case 2:
//                        dh.processNextEvent(SIPEvent.INVITE);
//                        break;
//                    case 3:
//                        dh.processNextEvent(SIPEvent.TRO);
//                        break;
//                    case 4:
//                        dh.processNextEvent(SIPEvent.ACK);
//                        break;
//                    case 5:
//                        dh.processNextEvent(SIPEvent.SEND_BYE);
//                        break;
//                    case 6:
//                        dh.processNextEvent(SIPEvent.BYE);
//                        break;
//                    case 7:
//                        dh.processNextEvent(SIPEvent.OK);
//                        break;
//                }
//                String sendBack = dh.reportStates();
//                out.println(sendBack);
//                out.flush();
//            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
