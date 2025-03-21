package org.example;
import java.util.Collections;
import java.util.ArrayList;

public class StateList {
    ArrayList<US_State> states;
    int TOTAL_POP;

    int TOTAL_REP_COUNT;
    int AVG_POP_PER_REP;

    int unassignedReps;

    boolean hhMethod;


    //statelist constructor
    StateList(ArrayList<US_State> states, int totalReps){
        this.states = states;
        this.TOTAL_REP_COUNT = totalReps;
        this.unassignedReps = totalReps;
        for (US_State s : states){
            TOTAL_POP+=s.getPOPULATION();
        }
        AVG_POP_PER_REP = TOTAL_POP/TOTAL_REP_COUNT;
        this.hhMethod = states.get(0).hhMethod;//set if it is taking hh method (true) or hamilton(false)
    }


    //
    public void assignReps(){
        ArrayList<US_State> originalOrderList = new ArrayList<>();
        for (US_State s : states) {//creates a deep copy of the list which will be in the correct order
            originalOrderList.add(s);
        }
        if (this.hhMethod) {
            huntingtonHill();
        }
        else {
            hamilton();
        }

        states = originalOrderList; //reorders according to original order
    }
    public void hamilton(){
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
    }

    public void huntingtonHill(){
        if (TOTAL_REP_COUNT<states.size()){
            throw new RuntimeException("Error: HuntingtonHill requires at least as many representatives as states");
        }
        for (US_State s : states){
            s.setRepresentatives(1); //give each state 1 starter tep
            unassignedReps-=1; //take away one rep from total
            s.setPriority();

        }
        Collections.sort(states); //sort according to priority score
        for (int i = 0; i<unassignedReps; i++) {//while there are remaining unassigned reps
            states.get(0).representatives+=1;//add 1 rep
            states.get(0).setPriority();//set new priority for top state
            Collections.sort(states); //sort according to priority score
        }

    }
    public String toString(){
        String a = "";
        for (US_State s : states) {
            a+=(s.getNAME()+" - "+s.getRepresentatives()+"\n");
        }
        return a;
    }

}
