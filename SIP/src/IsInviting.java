
import java.io.PrintWriter;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author fno
 */
public class IsInviting extends Busy {

    public IsInviting() {
    }

    public IsInviting(PrintWriter out) {
        super(out);
    }

    public SIPState gotTRO(PrintWriter out) {
        System.out.println("in got tro");
        if (isSameUser(out)) {
            System.out.println("meh");
            sendDataPrimary(SIPEvent.ACK);
            System.out.println("Got TRO now we send ACK...");
            return new InCall(out);
        } 
        else {
          
            if (out != null) {
                System.out.println("Busy in IsInviting");
                out.println(SIPEvent.BUSY);
                out.flush();
            }
            return (this);

        }

    }
    public SIPState gotTRO(UserInfo u) {
        System.out.println("in got tro");
     //   if (isSameUser(out)) {
            System.out.println("meh");
            sendDataAlt(SIPEvent.ACK,u);
            System.out.println("Got TRO now we send ACK...");
            return new InCall(out);
        //} 
        //else {
          
           /* if (out != null) {
                System.out.println("Busy in IsInviting");
                out.println(SIPEvent.BUSY);
                out.flush();
            }
            return (this);*/

        

    }

    public void printState() {
        System.out.println("You are now in inviting state...");
    }
}
