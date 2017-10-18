package edu.wpi.first.wpilibj.templates;

public class StateMachine{

    //Instance Vars
    private int currentState;
    private int maxStates;

    public StateMachine(int maxStates){
        this.maxStates = maxStates;
        currentState = 0;
    }

    //Moves to next state
    public void incrementState(){
        if(currentState == maxStates){
            currentState = 0;
        }else{
            currentState++;
        }
    }

    //Getters/Setters
    public void setCurrentState(int state){
        this.currentState = state;
    }

    public int getCurrentState(){
        return currentState;
    }

}
