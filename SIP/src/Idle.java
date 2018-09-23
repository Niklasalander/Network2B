
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Timer;
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
public class Idle extends SIPState {

    public Idle() {

    }
    
    public SIPState inviting(Socket socket) {
        try {
            out = new PrintWriter(new ObjectOutputStream(socket.getOutputStream()));
            System.out.println("Sending INVITE, waiting for TRO");
            SIPHandler.acceptCall(socket);
            sendDataPrimary(SIPEvent.INVITE);
            return new IsInviting(out);
        } catch (IOException ex) {
            ex.printStackTrace();
            return new Idle();
        }
    }

    public SIPState inviting(PrintWriter out) {
         System.out.println("in inviting...");
        this.out = out;
        sendDataPrimary(SIPEvent.INVITE);
        System.out.println("Sending INVITE, waiting for TRO");
        return new IsInviting(out);
    }
      public SIPState inviting(UserInfo u) {
          System.out.println("in inviting");
        //this.out = out;
        u.init();
      
        sendDataAlt(SIPEvent.INVITE,u);
        System.out.println("Sending INVITE, waiting for TRO");
        return new IsInviting(out);
    }
    
    public SIPState invited(Socket socket) {
        try {
            out = new PrintWriter(new ObjectOutputStream(socket.getOutputStream()));
            System.out.println("Sending TRO waiting for ACK");
            SIPHandler.acceptCall(socket);
//            out.print(SIPEvent.TRO);
            sendDataPrimary(SIPEvent.TRO);
            return new WasInvited(out);
        } catch (IOException ex) {
            ex.printStackTrace();
            return new Idle();
        }
    }
     public SIPState invited(UserInfo u) {
         System.out.println("in here now");
        ///ry {
           // u.setOut(new PrintWriter(new ObjectOutputStream(u.getSocket().getOutputStream())));
            System.out.println("Sending TRO waiting for ACK");
           // SIPHandler.acceptCall(socket);
//            out.print(SIPEvent.TRO);
            sendDataAlt(SIPEvent.TRO,u);
            return new WasInvited(out);
       /* } catch (IOException ex) {
            ex.printStackTrace();
            return new Idle();
        }*/
    }

    public SIPState invited(PrintWriter out) {
        this.out = out;
        sendDataPrimary(SIPEvent.TRO);
        System.out.println("Received INVITE, sending TRO");
        return new WasInvited(out);
    }
    
     public SIPState invited(PrintWriter out, int thisAudioPort) {
        this.out = out;
        sendDataTertiary(SIPEvent.TRO,thisAudioPort);// thisAudioPort);
        System.out.println("Received INVITE, sending TRO in tertiary");
        return new WasInvited(out);
    }

    public void printState() {
        System.out.println("You are now in an idle state...");
    }
}
