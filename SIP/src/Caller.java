
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
         
        }
        while(choice!=0);
    }
}
