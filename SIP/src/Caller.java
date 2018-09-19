
import java.util.Scanner;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author fno
 */
public class Caller {
    public static void main(String[] args){
        Scanner scan = new Scanner(System.in);
        SIPHandler sHandler = new SIPHandler();
        int choice;
        do{
            sHandler.reportCurrentState();
            
            choice = Integer.parseInt(scan.nextLine());
            
           /* switch(choice){
                case 1 : sHandler.processNextEvent(SIPEvent.SEND_INVITE);break;
                case 2 : sHandler.processNextEvent(SIPEvent.INVITE);break;
                case 3 : sHandler.processNextEvent(SIPEvent.TRO);break;
                case 4 : sHandler.processNextEvent(SIPEvent.ACK);break;
                case 5 : sHandler.processNextEvent(SIPEvent.BYE);break;
                case 6 : sHandler.processNextEvent(SIPEvent.OK);break;
            }*/
        }
        while(choice!=0);
    }
}
