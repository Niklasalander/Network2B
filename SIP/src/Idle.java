
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
        this.user = user;
        System.out.println("Sending INVITE, waiting for TRO");
        user.setIsConnected(true);
        sendDataPrimary(SIPEvent.INVITE);
        return new IsInviting(user);
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
