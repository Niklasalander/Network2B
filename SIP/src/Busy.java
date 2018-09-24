
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
public abstract class Busy extends SIPState{
    public Busy(){};
    public Busy(PrintWriter out) {
        super(out);
    }
    
     public SIPState inviting(){
         System.out.println("this node is busy, can't invite");
         return this;
     }
     public SIPState invited(){
         System.out.println("this node is busy, can't be invited");
         return this;
     }
     public SIPState invited(PrintWriter out){
         System.out.println("this node is busy, can't be invited");
         sendDataPrimary(SIPEvent.BUSY);
         return this;
     }
       public SIPState gotTRO(){
         System.out.println("this node is busy, can't receive T.R.O");
         return this;
     }
        public SIPState gotACK(){
         System.out.println("this node is busy, can't receive ACK");
         return this;
     }
         public SIPState gotOK(){
         System.out.println("this node is busy, can't accept OK");
         return this;
     }
       public SIPState doBYE(){
         System.out.println("this node is busy, can't send BYE");
         return this;
     }
       public SIPState gotBYE(){
         System.out.println("this node is busy, can't receive BYE");
         return this;
     }
    
}