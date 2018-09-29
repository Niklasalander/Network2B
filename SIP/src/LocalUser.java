
import java.io.IOException;
import java.net.InetAddress;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author fno
 */
public class LocalUser {
     private InetAddress address;
     private int id;
     private int audioPort;
     private AudioStreamUDP audioStream;
     

    public LocalUser(InetAddress address) throws IOException {
        this.address = address;
        this.audioStream = new AudioStreamUDP();
        this.audioPort = audioStream.getLocalPort();
        setId(NetworkServer.getNewUserId());
    }

    public int getAudioPort() {
        return audioPort;
    }

    public void setAudioPort(int audioPort) {
        this.audioPort = audioPort;
    }

    public AudioStreamUDP getAudioStream() {
        return audioStream;
    }

    public void setAudioStream(AudioStreamUDP audioStream) {
        this.audioStream = audioStream;
    }
    
    

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
     

    public InetAddress getAddress() {
        return address;
    }

    public void setAddress(InetAddress address) {
        this.address = address;
    }
     
     
}
