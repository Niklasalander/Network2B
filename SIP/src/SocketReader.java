
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
    private RemoteUser u;
  //  private User localUser;
    private boolean isConnected;
    private LocalUser lUser;
    
     public SocketReader(RemoteUser user, LocalUser localUser){
        this.u = user;
        this.socket = user.getSocket();
        this.in = user.getIn();
        this.out = user.getOut();
        isConnected = true;
        System.out.println("Initiated");
        this.lUser = localUser;
        this.setName("Socket Reader: " + this.getId());
    }
    

    
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
                    System.out.println("gotstr: " + str + " id " +u.getId() + " local : " + this.lUser.getAudioPort() );
                    
//                    event = (SIPEvent) in.read();
                    switch(command) {
                        case "SEND_INVITE" : SIPHandler.processNextEvent(SIPEvent.SEND_INVITE, u);break;
                        case "INVITE" : 
                            SIPHandler.processNextEvent(SIPEvent.INVITE, u);
                          
                        break;
                        case "TRO" : 
                           //   this.localUser.setRemotePortNumber(Integer.parseInt(received[1]));
                            //  u.setRemotePortNumber(this.localUser.getLocalPortNumber());
                            //  u.setRemotePortNumber(this.localUser.getLocalPortNumber());
                              u.setLocalUsersPort(this.lUser.getAudioPort());
          
                              u.setRemoteUserPort(Integer.parseInt(received[1]));
                              u.setLocalUser(lUser);
                              
                              SIPHandler.processNextEvent(SIPEvent.TRO, u);break;
                        case "ACK" : 
                             u.setRemoteUserPort(Integer.parseInt(received[1]));
                             //this.localUser.setRemotePortNumber(Integer.parseInt(received[1]));
                             System.out.println("this users remote port: " + this.u.getLocalUsersPort() + " me: " +this.u.getRemoteUserPort());
                            u.setLocalUser(lUser);
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
                SIPHandler.processNextEvent(SIPEvent.LOST_CONNECTION, u);
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
