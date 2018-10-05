
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
        super();
    }
    
    public SIPState inviting(User user) {
//        if (user.getLocalAddress().getHostName() == user.getRemoteAddress().getHostName() &&
//                user.getLocalPort() == user.getRemotePort()) {
//            System.out.println("Cannnot call yourself....");
//            user.endConnection();
//            return new Idle();
//        }
//        else {
            this.user = user;
            System.out.println("Sending INVITE, waiting for TRO");
            user.setIsConnected(true);
            sendDataPrimary(SIPEvent.INVITE);
            return new IsInviting(user);
//        }
    }
    
    public SIPState invited(User user) {
        this.user = user;
        System.out.println("Incoming call, type accept to answer");
        user.setIsConnected(true);
        return new WasInvited(user);
    }

    public void printState() {
        System.out.println("You are now in an Idle state...");
    }
}
