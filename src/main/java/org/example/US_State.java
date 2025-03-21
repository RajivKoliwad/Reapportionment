package org.example;
import java.lang.Math;

public class US_State implements Comparable<US_State> {
    public String getNAME() {
        return NAME;
    }

    public int getPOPULATION() {
        return POPULATION;
    }

    public int getRemainderPop() {
        return remainderPop;
    }

    public int getRepresentatives() {
        return representatives;
    }


    public void setRemainderPop(int remainderPop) {
        this.remainderPop = remainderPop;
    }

    public void setRepresentatives(int representatives) {
        this.representatives = representatives;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority() {
        this.priority = (int) ( (POPULATION)/(Math.sqrt(representatives*(representatives+1))));
    }

    String NAME;
    int POPULATION;
    int remainderPop;
    int representatives;

    int priority;

    boolean hhMethod;



    public US_State(String name, int population, boolean hhMethod){
        this.NAME=name;
        this.POPULATION=population;
        this.hhMethod=hhMethod;
    }

    public int compareTo(US_State other) {

        if (hhMethod){
            return Integer.compare(other.getPriority(), this.getPriority());

        }
        else{
            return Integer.compare(other.getRemainderPop(), this.getRemainderPop());
        }

    }


}
