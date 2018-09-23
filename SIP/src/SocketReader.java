
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;

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
    protected AudioStreamUDP audiostream;
    private boolean isConnected;
    private int audioPort;
    private int receiveraudioPort;
    private UserInfo thisUser;
    //private UserInfo receiver;
    public SocketReader(Socket socket){
        this.socket = socket;
//        sendAlive = new Timer();
        if (socket != null) {
            System.out.println("starting in/out");
            try {
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
                audiostream = new AudioStreamUDP();
                isConnected = true;
                audioPort = audiostream.getLocalPort();
                
                
                
              
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
    public SocketReader(Socket socket, int port, InetAddress addr){
        this.socket = socket;
//        sendAlive = new Timer();
        if (socket != null) {
            System.out.println("starting in/out");
            try {
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
                audiostream = new AudioStreamUDP();
                isConnected = true;
                audioPort = audiostream.getLocalPort();
                this.thisUser = new UserInfo(port,addr,socket,out,in);
                
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
               // this.thisUser = new UserInfo(socket);

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
    public SocketReader(UserInfo us){
        this.socket = socket;
//        sendAlive = new Timer();
        if (socket != null) {
            try {
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                this.thisUser = us;
                this.thisUser.setIn(in);
                this.out = out;
                isConnected = true;
               // this.thisUser = new UserInfo(socket);

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
    
    public void acceptCall() {
        System.out.println("I'm accepting");
        SIPHandler.processNextEvent(SIPEvent.INVITE, out);
//        if (out == null)
//            return false;
//        return true;
    }
    
    @Override
    public void run() {
        if (in != null && out != null) {
            String str = "";
            SIPEvent event;
              System.out.println("this port number is : " + this.audioPort);
            try {
                while (out != null) {
                    
                    System.out.println("SR waiting for string...");
                    str = in.readLine();
                    String[] receive = str.trim().split(",");
                    System.out.println("gotstr: " + str);
                    if(receive.length>1){
                        if(receive[1].chars().allMatch(Character::isDigit)){
                           this.receiveraudioPort = Integer.parseInt(receive[1]);
                           System.out.println(this.receiveraudioPort);
                        }
                    }
//                    event = (SIPEvent) in.read();
                    System.out.println("gimme " + receive[0]);
                    switch(receive[0]) {
                        case "SEND_INVITE" : SIPHandler.processNextEvent(SIPEvent.SEND_INVITE, out);break;
                       // case "INVITE" : SIPHandler.processNextEvent(SIPEvent.INVITE, out);break;
                         case "INVITE" : SIPHandler.processNextEvent(SIPEvent.INVITE, thisUser);break;
                     //   case "TRO" : SIPHandler.processNextEvent(SIPEvent.TRO, out);break;
                     //   case "TRO" : SIPHandler.processNextEvent(SIPEvent.TRO,out);break;
                         case "TRO" : SIPHandler.processNextEvent(SIPEvent.TRO,thisUser);break;
                        case "ACK" : SIPHandler.processNextEvent(SIPEvent.ACK, out);break;
                        case "SEND_BYE" : SIPHandler.processNextEvent(SIPEvent.SEND_BYE, out);break;
                        case "BYE" : SIPHandler.processNextEvent(SIPEvent.BYE, out);break;
                        case "OK" : SIPHandler.processNextEvent(SIPEvent.OK, out);break;
                        case "BUSY" : SIPHandler.processNextEvent(SIPEvent.BUSY, out);break;
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
