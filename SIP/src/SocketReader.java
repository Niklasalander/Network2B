
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.Timer;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Niklas
 */
public class SocketReader extends Thread {
    protected Socket socket;
    protected BufferedReader in;
    protected PrintWriter out;
    private User u;
    private User localUser;
    private boolean isConnected;
    
    public SocketReader(User user){
        this.u = user;
        this.socket = user.getSocket();
        this.in = user.getIn();
        this.out = user.getOut();
        isConnected = true;
        System.out.println("Initiated");
        this.localUser = new User();
    }
    
//    public SocketReader(Socket socket){
//        
//        this.socket = socket;
////        sendAlive = new Timer();
//        if (socket != null) {
//            try {
//                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
//                out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
//                isConnected = true;
//
//            } catch (IOException ex) {
//                isConnected = false;
//                try {
//                    if (in != null)
//                        in.close();
//                    if (out != null)
//                        out.close();
//                } catch (IOException e) {
//                }
//                System.out.println("Could not establish connection to client");
//            }
//        }
//    }
//    
//    public SocketReader(Socket socket, PrintWriter out){
//        this.socket = socket;
////        sendAlive = new Timer();
//        if (socket != null) {
//            try {
//                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
//                this.out = out;
//                isConnected = true;
//
//            } catch (IOException ex) {
//                isConnected = false;
//                try {
//                    if (in != null)
//                        in.close();
//                    if (out != null)
//                        out.close();
//                } catch (IOException e) {
//                }
//                System.out.println("Could not establish connection to client");
//            }
//        }
//    }
    
    @Override
    public void run() {
        if (this.u.getIn() != null && this.u.getOut() != null) {
            String str = "";
            SIPEvent event;
            try {
                while (u.getIsConnected()) { //this.u.getOut() != null) {
                    System.out.println("SR waiting for string...");
                    str = u.getIn().readLine();
                    String[] received = str.split(" ");
                    String command = received[0].trim().toUpperCase();
                    System.out.println("gotstr: " + str + " id " +u.getId() + " local : " + this.localUser.getLocalPortNumber() + this.u.getLocalUser());
                    
//                    event = (SIPEvent) in.read();
                    switch(command) {
                        case "SEND_INVITE" : SIPHandler.processNextEvent(SIPEvent.SEND_INVITE, u);break;
                        case "INVITE" : 
                            SIPHandler.processNextEvent(SIPEvent.INVITE, u);
                           // rsTimer.schedule(new ResponsiveServerTimer(u), 5000);
                        break;
                        case "TRO" : 
                              this.localUser.setRemotePortNumber(Integer.parseInt(received[1]));
                              u.setRemotePortNumber(this.localUser.getLocalPortNumber());
                              u.setLocalUser(localUser);
                              SIPHandler.processNextEvent(SIPEvent.TRO, u);break;
                        case "ACK" : 
                            this.localUser.setRemotePortNumber(Integer.parseInt(received[1]));
                            System.out.println("this users remote port: " + this.localUser.getRemotePortNumber());
                            u.setLocalUser(localUser);
                            SIPHandler.processNextEvent(SIPEvent.ACK, u);break;
                        case "BYE" : SIPHandler.processNextEvent(SIPEvent.BYE, u);break;
                        case "OK" : SIPHandler.processNextEvent(SIPEvent.OK, u);break;
                        case "BUSY" : SIPHandler.processNextEvent(SIPEvent.BUSY, u);break;
                        default :
                            // close connection
                    }
                }

            } catch (SocketTimeoutException ex) {
                System.out.println("A client timed out");
            } catch (NullPointerException ex) {
                System.out.println("Connection with client abrupty lost");
                SIPHandler.processNextEvent(SIPEvent.LOST_CONNECTION, u);
            } catch (SocketException se) {
                System.out.println("Could not read from socket handeling client");
                SIPHandler.processNextEvent(SIPEvent.LOST_CONNECTION, u);
            } catch (IOException ex) {
                System.out.println("Client handler run method");
            }  finally {
                //TODO: make sure state is idle
                try {
                    System.out.println("Removing client");
                    if (socket != null) {
                        socket.close();
                    }
                    if (in != null) {
                        in.close();
                    }
                    if (out != null) {
                        out.close();
                    }
                } catch (IOException ex) {
                    System.out.println("could not close in stream in client handler");
                } catch (java.util.ConcurrentModificationException ex) {
                    System.out.println("Too many clients disconnecting simutainiously causing concurrency issues, "
                            + "thread will close in a few minutes.");;
                }
            }
        }
        else {
            try {
                System.out.println("Removing client");
                if (socket != null) {
                    socket.close();
                }
                if (in != null) {
                    in.close();
                }
                if (out != null) {
                    out.close();
                }
            } catch (IOException ex) {
                System.out.println("could not close in stream in client handler");
            }
        }
    }
    
}
