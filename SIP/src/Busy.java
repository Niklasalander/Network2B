
import java.io.PrintWriter;
import java.net.Socket;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author fno
 */
public abstract class Busy extends SIPState {

    public Busy() {
    }

    ;
    public Busy(PrintWriter out) {
        super(out);
    }

    public SIPState inviting(PrintWriter out) {
        System.out.println("Exit current call before starting a new one");
        return this;
    }

    public SIPState invited(PrintWriter out) {
        System.out.println("this node is busy, can't be invited");
        if (isSameUser(out)) {
            // do something(?)
        }
        else {
            sendBusyAndCloseWriter(out);
            return this;
        }
        return this;
    }

    public SIPState gotTRO(PrintWriter out) {
        System.out.println("this node is busy, can't receive T.R.O");
        if (isSameUser(out)) {
            // do something(?)
        }
        else {
            sendBusyAndCloseWriter(out);
            return this;
        }
        return this;
    }

    public SIPState gotACK(PrintWriter out) {
        System.out.println("this node is busy, can't receive ACK");
        if (isSameUser(out)) {
            // do something(?)
        }
        else {
            sendBusyAndCloseWriter(out);
            return this;
        }
        return this;
    }

    public SIPState gotOK(PrintWriter out) {
        System.out.println("this node is busy, can't accept OK");
        if (isSameUser(out)) {
            // do something(?)
        }
        else {
            sendBusyAndCloseWriter(out);
            return this;
        }
        return this;
    }

    public SIPState sendBYE(){
        System.out.println("Seding BYE waiting for OK");
        sendDataPrimary(SIPEvent.BYE);
        return new Exiting(out);
    }

    public SIPState gotBYE(PrintWriter out) {
        System.out.println("doing bye");
        if (isSameUser(out)) {
            System.out.println("Got BYE sending OK");
            sendDataPrimary(SIPEvent.OK);
            out.close();
            return new Idle();
        } else {
            sendBusyAndCloseWriter(out);
            return this;
        }
    }

    protected void sendBusyAndCloseWriter(PrintWriter out) {
        System.out.println("I am BUSY");
        try {
            if (out != null) {
                out.println(SIPEvent.BUSY);
                out.flush();
                out.close();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
