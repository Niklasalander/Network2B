/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author fno
 */
public class SIPHandler {
    
    private SIPState currentState;
    public SIPHandler(){
        currentState = new Idle();
    }
    
    public void reportCurrentState(){
        currentState.printState();
    }
    public String reportStates(){
        return currentState.returnStates();
    }
    public void processNextEvent (SIPEvent event) {
        switch(event){
            case SEND_INVITE : currentState= currentState.inviting();break; // caller
            case INVITE : currentState= currentState.invited();break; // callee
            case TRO : currentState = currentState.gotTRO();break; // callee -> caller
            //case OK  : currentState = currentState.gotOK();break;
            case ACK : currentState = currentState.gotACK();break;// caller -> callee
            case SEND_BYE : currentState = currentState.doBYE();break; // caller -> callee (caller wants to leave)
            case BYE : currentState = currentState.gotBYE();break; //callee -> caller (wants to exit)
            case OK  : currentState = currentState.gotOK();break; // callee -> caller 
        }
    }
}
