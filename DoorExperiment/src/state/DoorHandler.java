/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package state;

/**
 *
 * @author fno
 */
public class DoorHandler{
    public enum DoorEvent {CLOSE_BUTTON, OPEN_BUTTON,ARRIVED,OBSTACLE_ENCOUNTERED,LOCK,UNLOCK};
   // Do
    
    public DoorHandler(){
       currentState = new DoorStateClosed();
    }
    public void processNextEvent (DoorEvent event){
        switch(event){
            case CLOSE_BUTTON:currentState = currentState.closeButtonPressed(); break;
            case OPEN_BUTTON: currentState = currentState.openButtonPressed(); break;
            case ARRIVED: currentState = currentState.arrived(); break;
            case OBSTACLE_ENCOUNTERED: currentState = currentState.obstacleEncountered(); break; case LOCK: currentState = currentState.lock(); break;
            case UNLOCK: currentState = currentState.unlock(); break;
        }
    }
    private DoorState currentState;
    public void printState() { currentState.printState();

    /**
     *
     * @return
     */
    
}
    
    public String returnStates(){
        return currentState.getClass().getSimpleName();
    }
}
