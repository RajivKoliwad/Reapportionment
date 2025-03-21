package org.example;


public class Main {
    public static void main(String[] args) {
        StateList testList = Reader.readFile(args);//call file reader
        testList.assignReps(); //given list of states, assign representatives
        System.out.println(testList); //prints states with number of reps for given apportionment algorithm
    }

}