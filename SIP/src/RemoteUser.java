
import java.io.BufferedReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author fno
 */
public class RemoteUser {
    private BufferedReader in;
    private PrintWriter out;
    private Socket socket;
    private boolean isConnected;
    private int id;
    
    private InetAddress address;
    private InetAddress localAddress;
    private int localUsersPort;
    private int remoteUserPort;
    private AudioStreamUDP audioStream;
    private LocalUser localUser;
    
 
    public RemoteUser(InetAddress address){
        this.id = NetworkServer.getNewUserId();
        this.address = address;
        this.isConnected = false;
      
    }
    
    public RemoteUser(int remoteAudio){
        this.id = NetworkServer.getNewUserId();
        try {
            this.localUsersPort = remoteAudio;
            address = InetAddress.getByName("localhost");
            this.isConnected = false;
        } catch (UnknownHostException ex) {
            Logger.getLogger(RemoteUser.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public RemoteUser(Socket socket, BufferedReader in, PrintWriter out) {
        this.socket = socket;
        this.in = in;
        this.out = out;
        this.id = NetworkServer.getNewUserId();
        this.isConnected = false;
    }
     public RemoteUser(Socket socket, BufferedReader in, PrintWriter out, InetAddress address) {
        this.id = NetworkServer.getNewUserId();
        this.socket = socket;
        this.in = in;
        this.out = out;
        this.id = NetworkServer.getNewUserId();
        this.isConnected = false;
        this.address = address;
       
    }
    
    
    public void endConnection() {
        isConnected = false;
    }
    
    public void setIsConnected(boolean isConnected) {
        this.isConnected = isConnected;
    }
    
    public boolean getIsConnected() {
        return isConnected;
    }

    public int getId() {
        return id;
    }
    
    public BufferedReader getIn() {
        return in;
    }

    public void setIn(BufferedReader in) {
        this.in = in;
    }

    public PrintWriter getOut() {
        return out;
    }

    public void setOut(PrintWriter out) {
        this.out = out;
    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }
 

    public LocalUser getLocalUser() {
        return localUser;
    }

    public void setLocalUser(LocalUser localUser) {
        this.localUser = localUser;
    }

    public InetAddress getAddress() {
        return address;
    }
    public void setAddress(InetAddress addr){
        this.address = addr;
    }

    public int getLocalUsersPort() {
        return localUsersPort;
    }

    public void setLocalUsersPort(int localUsersPort) {
        this.localUsersPort = localUsersPort;
    }

    public int getRemoteUserPort() {
        return remoteUserPort;
    }

    public void setRemoteUserPort(int remoteUserPort) {
        this.remoteUserPort = remoteUserPort;
    }

    public InetAddress getLocalAddress() {
        return localAddress;
    }

    public void setLocalAddress(InetAddress localAddress) {
        this.localAddress = localAddress;
    }
    
    
    
    
}
