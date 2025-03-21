package org.example;
//import java.io.BufferedReader;
//import java.io.FileReader;
//import java.io.IOException;
//import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {

        StateList testList = Reader.readXLSXFile(args);//call on csv reader to fill testList
        testList.assignReps(); //call on method which calculates apportionment
        System.out.println(testList); //prints states with number of reps
    }

}