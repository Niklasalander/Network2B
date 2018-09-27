
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
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
    private boolean isConnected;
    private User thisUser;
    public SocketReader(User user){
        this.u = user;
        isConnected = true;
        System.out.println("Initiated"); 
        this.thisUser = new User();
    }
    public SocketReader(Socket socket){
        
        this.socket = socket;
//        sendAlive = new Timer();
        if (socket != null) {
            try {
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
                isConnected = true;

            } catch (IOException ex) {
                isConnected = false;
                try {
                    if (in != null)
                        in.close();
                    if (out != null)
                        out.close();
                } catch (IOException e) {
                }
                System.out.println("Could not establish connection to client");
            }
        }
    }
    
    public SocketReader(Socket socket, PrintWriter out){
        this.socket = socket;
//        sendAlive = new Timer();
        if (socket != null) {
            try {
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                this.out = out;
                isConnected = true;

            } catch (IOException ex) {
                isConnected = false;
                try {
                    if (in != null)
                        in.close();
                    if (out != null)
                        out.close();
                } catch (IOException e) {
                }
                System.out.println("Could not establish connection to client");
            }
        }
    }
    
    public synchronized void sendMessage(SIPEvent event) {
        if (true)
            return;
        out.print(event);
        out.flush();
        System.out.println("Message sent");
    }
    
    @Override
    public void run() {
        System.out.println("in run");
        if (this.u.getIn() != null && this.u.getOut() != null) {
            String str = "";
            SIPEvent event;
             System.out.println("in if");
            try {
                while (this.u.getOut() != null) {
                    System.out.println("SR waiting for string...");
                    str = u.getIn().readLine();
                    String[] received = str.split(" ");
                    String command = received[0].trim().toUpperCase();
                    System.out.println("gotstr: " + str + " id " +u.getId() );
//                   event = (SIPEvent) in.read();
                    switch(command) {
                        case "SEND_INVITE" : SIPHandler.processNextEvent(SIPEvent.SEND_INVITE, u);break;
                        case "INVITE" : 
                            SIPHandler.processNextEvent(SIPEvent.INVITE, u);
                            
                        break;
                        case "TRO" : 
                            this.thisUser.setRemotePortNumber(Integer.parseInt(received[1]));
                            u.setRemotePortNumber(this.thisUser.getLocalPortNumber());
                            System.out.println("remote port is " + this.thisUser.getLocalPortNumber() + " " + this.thisUser.getRemotePortNumber());
                            u.setLocalUser(thisUser);
                            SIPHandler.processNextEvent(SIPEvent.TRO,u);break;
                            
                        case "ACK" : 
                            this.thisUser.setRemotePortNumber(Integer.parseInt(received[1]));
                            System.out.println("this users remote port: " + this.thisUser.getRemotePortNumber());
                            u.setLocalUser(thisUser);
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
            } catch (SocketException se) {
                System.out.println("Could not read from socket handeling client");
            } catch (IOException ex) {
                System.out.println("Client handler run method");
            }  finally {
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
