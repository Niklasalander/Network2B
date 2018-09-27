
import java.io.BufferedReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
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
    private InetAddress address;
    private int port;
    private int localAudioPort;
    private int remoteAudioPort;
    private BufferedReader in;
    private PrintWriter out;
    private int id;
    private AudioStreamUDP audioStream;
    private User localUser;
    
    //A and B to themselves
    public User(){
        try {
            audioStream = new AudioStreamUDP();
            address = InetAddress.getByName("localhost");
        } catch (IOException ex) {
            Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public User(int remoteAudio){
        try {
            this.remoteAudioPort = remoteAudio;
            address = InetAddress.getByName("localhost");
        } catch (UnknownHostException ex) {
            Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    // A wants to contact B, This is B to A
    public User(InetAddress addr, int port) {
       //To change body of generated methods, choose Tools | Templates.
       this.address = addr;
       this.port = port;
       this.id = NetworkServer.getNewUserId();
    }
    //B is contacted by A, this is A to B
    public User(BufferedReader in, PrintWriter out) {
        this.in = in;
        this.out = out;
        this.id = NetworkServer.getNewUserId();
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
