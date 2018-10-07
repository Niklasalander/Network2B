
import java.io.BufferedReader;
import java.io.IOException;
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
    private User u;
     
     public SocketReader(User user){
        this.u = user;
        this.socket = user.getSocket();
        this.in = user.getIn();
        this.out = user.getOut();
        this.setName("Socket Reader: " + this.getId());
    }
    
    @Override
    public void run() {
        if (this.u.getIn() != null && this.u.getOut() != null) {
            int counter = 0;
            String str = "";
            try {
                while (u.getIsConnected()) {
                    try {
                        System.out.println("SR waiting for string...");
                        str = u.getIn().readLine();
                        if (str == null) {
                            counter++;
                            if (counter == 10) {
                                System.out.println("Connection was lost");
                                u.endConnection();
                            }
                        }
                        else
                            counter = 0;
                        
                        try { // Simulate network delay
                            Thread.sleep(10);
                        } catch (InterruptedException ex) {
                        }
                        String[] received = str.split(" ");
                        String command = received[0].trim().toUpperCase();
    //                    System.out.println("gotstr: " + str + " id " +u.getId() + " local : " + this.lUser.getAudioPort() + " "  );

                        switch(command) {
                            case "SEND_INVITE" : SIPHandler.processNextEvent(SIPEvent.SEND_INVITE, u);break;
                            case "INVITE" : 
                                SIPHandler.processNextEvent(SIPEvent.INVITE, u);

                            break;
                            case "TRO" : 
                                u.setRemoteAudioPort(Integer.parseInt(received[1]));
                                SIPHandler.processNextEvent(SIPEvent.TRO, u);
                                break;
                            case "ACK" : 
                                u.setRemoteAudioPort(Integer.parseInt(received[1]));
                                u.setRemoteAddress(InetAddress.getByName(received[2].trim()));
                                SIPHandler.processNextEvent(SIPEvent.ACK, u);
                                break;
                            case "BYE" : SIPHandler.processNextEvent(SIPEvent.BYE, u);break;
                            case "OK" : SIPHandler.processNextEvent(SIPEvent.OK, u);break;
                            case "BUSY" : SIPHandler.processNextEvent(SIPEvent.BUSY, u);break;
                        }
                    } catch (ArrayIndexOutOfBoundsException ex) {
                        System.out.println("Could not parse arguments");
                    } catch (NullPointerException ex) {
                        
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
                // make sure state is idle
                SIPHandler.processNextEvent(SIPEvent.MAKE_SURE_IDLE, u);
                try {
                    System.out.println("Removing client");
                    if (in != null) {
                        in.close();
                    }
                    if (out != null) {
                        out.close();
                    }
                    if (socket != null) {
                        socket.close();
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
                if (in != null) {
                    in.close();
                }
                if (out != null) {
                    out.close();
                }
                if (socket != null) {
                    socket.close();
                }
            } catch (IOException ex) {
                System.out.println("could not close in stream in client handler");
            }
        }
    }
    
}
