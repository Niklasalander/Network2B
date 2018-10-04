
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Niklas
 */
public class User {
    private BufferedReader in;
    private PrintWriter out;
    private Socket socket;
    private boolean isConnected;
    private int id;
    
    private InetAddress localAddress;
    private int localPort;
    private InetAddress remoteAddress;
    private int remotePort;
    
    private int localAudioPort;
    private int remoteAudioPort;
    private AudioStreamUDP audioStream;

    // Caller constructor
    public User(InetAddress localAddress, int localPort, InetAddress remoteAddress, int remotePort) {
        this.localAddress = localAddress;
        this.localPort = localPort;
        this.remoteAddress = remoteAddress;
        this.remotePort = remotePort;
        this.id = NetworkServer.getNewUserId();
        this.audioStream = null;
    }
    
    // Callee contructor
    public User(Socket s, InetAddress localAddress, int localPort) throws IOException {
        this.socket = s;
        this.in = new BufferedReader(new InputStreamReader(s.getInputStream()));
        this.out = new PrintWriter(new OutputStreamWriter(s.getOutputStream()));
        this.localAddress = localAddress;
        this.localPort = localPort;
        this.id = NetworkServer.getNewUserId();
        this.audioStream = null;
    }
    
    public void endConnection() {
        isConnected = false;
        stopAudioStream();
        closeAudioStream();
    }
    
    public int initAudioStream() throws IOException {
        audioStream = new AudioStreamUDP();
        localAudioPort = audioStream.getLocalPort();
        return localAudioPort;
    }
    
    public void connectAudioStream() throws IOException {
        if (audioStream != null) {
            audioStream.connectTo(remoteAddress, remoteAudioPort);
            System.out.println("Connected to Remote Address: " + remoteAddress.getHostAddress() + ". Remote Port: " + remoteAudioPort + ".");
        }
    }
    
    public void startAudioStream() {
        if (audioStream != null)
            audioStream.startStreaming();
    }
    
    public void stopAudioStream() {
        if (audioStream != null)
            audioStream.stopStreaming();
    }
    
    public void closeAudioStream() {
        if (audioStream != null)
            audioStream.close();
    }

    public InetAddress getLocalAddress() {
        return localAddress;
    }

    public int getLocalPort() {
        return localPort;
    }

    public InetAddress getRemoteAddress() {
        return remoteAddress;
    }
    
    public void setRemoteAddress(InetAddress remoteAddress) {
        this.remoteAddress = remoteAddress;
    }

    public int getRemoteAudioPort() {
        return remoteAudioPort;
    }
    
    public void setRemoteAudioPort(int remoteAudioPort) {
        this.remoteAudioPort = remoteAudioPort;
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

    public boolean getIsConnected() {
        return isConnected;
    }

    public void setIsConnected(boolean isConnected) {
        this.isConnected = isConnected;
    }

    public int getLocalAudioPort() {
        return localAudioPort;
    }
    
}
