
import java.io.BufferedReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
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
public class User {
    private BufferedReader in;
    private PrintWriter out;
    private Socket socket;
    private boolean isConnected;
    private int id;
    
    private InetAddress address;
    private int localAudioPort;
    private int remoteAudioPort;
    private AudioStreamUDP audioStream;
    private User localUser;

    public User() {
       //To change body of generated methods, choose Tools | Templates.
       this.id = NetworkServer.getNewUserId();
       this.isConnected = true;
         try {
            audioStream = new AudioStreamUDP();
            address = InetAddress.getByName("localhost");
        } catch (IOException ex) {
            Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public User(InetAddress address){
        this.id = NetworkServer.getNewUserId();
        this.address = address;
        this.isConnected = true;
        try {
            this.audioStream = new AudioStreamUDP();
            address = InetAddress.getByName("localhost");
        } catch (IOException ex) {
            Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public User(int remoteAudio){
        this.id = NetworkServer.getNewUserId();
        try {
            this.remoteAudioPort = remoteAudio;
            address = InetAddress.getByName("localhost");
            this.isConnected = true;
        } catch (UnknownHostException ex) {
            Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public User(Socket socket, BufferedReader in, PrintWriter out) {
        this.socket = socket;
        this.in = in;
        this.out = out;
        this.id = NetworkServer.getNewUserId();
        this.isConnected = true;
    }
     public User(Socket socket, BufferedReader in, PrintWriter out, InetAddress address) {
        this.id = NetworkServer.getNewUserId();
        this.socket = socket;
        this.in = in;
        this.out = out;
        this.id = NetworkServer.getNewUserId();
        this.isConnected = true;
        this.address = address;
        try {
            this.audioStream = new AudioStreamUDP();
            address = InetAddress.getByName("localhost");
        } catch (IOException ex) {
            Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void endConnection() {
        isConnected = false;
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
    
     public int getLocalPortNumber(){
        return audioStream.getLocalPort();
    }
    public int getRemotePortNumber(){
        return this.remoteAudioPort;
    }
    public void setRemotePortNumber(int remotePort){
        System.out.println("setting new remote port");
        this.remoteAudioPort = remotePort;
        System.out.println("set new remote port");
    }

    public AudioStreamUDP getAudioStream() {
        return audioStream;
    }

    public void setAudioStream(AudioStreamUDP audioStream) {
        this.audioStream = audioStream;
    }

    public User getLocalUser() {
        return localUser;
    }

    public void setLocalUser(User localUser) {
        this.localUser = localUser;
    }

    public InetAddress getAddress() {
        return address;
    }
    
    
    
    
}
