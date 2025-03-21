package org.example;
import java.util.Collections;
import java.util.ArrayList;

public class StateList {
    ArrayList<US_State> states;
    int TOTAL_POP;

    int TOTAL_REP_COUNT;
    int AVG_POP_PER_REP;

    int unassignedReps;

    StateList(ArrayList<US_State> states, int totalReps){
        this.states = states;
        this.TOTAL_REP_COUNT = totalReps;
        this.unassignedReps = totalReps;
        for (US_State s : states){
            TOTAL_POP+=s.getPOPULATION();
        }
        AVG_POP_PER_REP = TOTAL_POP/TOTAL_REP_COUNT;
    }

    public void assignReps(){
        ArrayList<US_State> originalOrderList = new ArrayList<>();
        for (US_State s : states) {//creates a copy of the list which will be in the correct order
            originalOrderList.add(s);
        }
        for (US_State s : states){
            int repsPerState = s.getPOPULATION()/AVG_POP_PER_REP; //minimum no of reps
            s.setRepresentatives(repsPerState); //set the minimum no of reps
            unassignedReps-=repsPerState; //take away unassigned reps from list of unassigned reps
            s.setRemainderPop(s.getPOPULATION()%AVG_POP_PER_REP); //set remainder pop
        }
        Collections.sort(states); //sort according to population remaining

        for (int i = 0; i<unassignedReps; i++){  //while there are remaining unassigned reps
            states.get(i).setRepresentatives(states.get(i).getRepresentatives()+1); //give state one more rep
        }
        states = originalOrderList; //reorders according to original order
    }
    public String toString(){
        String a = "";
        for (US_State s : states) {
            a+=(s.getNAME()+" - "+s.getRepresentatives()+"\n");
        }
        return a;
    }

}
