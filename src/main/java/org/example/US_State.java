package org.example;

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
    String NAME;
    int POPULATION;
    int remainderPop;
    int representatives;

    public US_State(String name, int population){
        this.NAME=name;
        this.POPULATION=population;
    }

    public int compareTo(US_State other) {
        return Integer.compare(other.getRemainderPop(), this.getRemainderPop());
    }


}
