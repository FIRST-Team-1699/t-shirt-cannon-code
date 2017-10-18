package edu.wpi.first.wpilibj.templates;

public class StateMachine{

  private int currentState;
  private int maxStates;

  public StateMachine(int maxStates){
    this.maxStates = maxStates;
    currentState = 0;
  }
  
  public void incrementState(){
    if(currentState == maxStates){
      currentState = 0;
     }else{
      currentState++;
     }     
  }
  
  public void setCurrentState(int state){
    this.currentState = state;
  }
  
  public int getCurrentState(){
    return currentState;
  }

}
