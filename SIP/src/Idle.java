
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
    
    public SIPState inviting(PrintWriter out) {
        this.out = out;
        sendDataPrimary(SIPEvent.INVITE);
        System.out.println("Sending INVITE, waiting for TRO");
        return new IsInviting(out);
    }
    
    public SIPState invited(PrintWriter out) {
        this.out = out;
        System.out.println("Incoming call, type accept to answer");
//        SIPHandler.setOut(out);
//        sendDataPrimary(SIPEvent.TRO);
//        System.out.println("Received INVITE, sending TRO");
        return new WasInvited(out);
    }

    public void printState() {
        System.out.println("You are now in an idle state...");
    }
}
