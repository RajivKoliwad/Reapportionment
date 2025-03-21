package org.example;
import javax.swing.plaf.synth.SynthLookAndFeel;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;


import org.apache.poi.xssf.usermodel.*;
import org.apache.poi.ss.usermodel.*;


public class Reader {
    static int poplocation;
    static int statelocation;




    static StateList readCSVFile(String[] userIn){
        String fileName = userIn[0]; //file name
        boolean validFile = false; //default value for the file
        int representativeCount =435; //default reps
        if (userIn.length>1){
            try {
                representativeCount = Integer.parseInt(userIn[1]); //store num of reps
            }
            catch(NumberFormatException e){
                throw new RuntimeException("Invalid input: non-integer value entered for representatives" + e.getMessage());
            }
        }
        if (representativeCount<1){
            throw new RuntimeException("Invalid input: cannot have less than 1 representative");
        }

        //here I try to parse out the code to see where the different columns are/ see if there is an issue with the colummn
        //error checking and column finding

        try {
            BufferedReader brtest = new BufferedReader(new FileReader(fileName));
            String[] headerline = brtest.readLine().split(", ");
            for (int i = 0; i < headerline.length; i++) {
                if (headerline[i].equals("Population")) {
                    poplocation = i;
                }
                if (headerline[i].equals("State")) {
                    statelocation = i;
                }
            }
            brtest.close();
        }
        catch(FileNotFoundException e){

        }
        catch(IOException e){

        }
        ArrayList<US_State> theStates = new ArrayList<>(); //create empty arraylist for states
        try{ // code which will transfer data to arraylist
            //learned more about BufferedReader here:  https://docs.oracle.com/javase/8/docs/api/java/io/BufferedReader.html
            BufferedReader br = new BufferedReader(new FileReader(fileName));
            String line;
            br.readLine(); //account for header > skips to second row
            while ((line = br.readLine()) != null) { //while there are lines to be dealt with (also moves on to the next line)
                String[] data = line.split(", "); //split into array of string
                if (reviewLine(data) ) {     //if line is acceptable
                    validFile=true;
                    US_State state = new US_State(data[statelocation], Integer.parseInt(data[poplocation]) ); //new state with name and population
                    //false if hamilton for a third boolean true if huntington-hale
                    theStates.add(state);
                }
            }
            br.close();
        }
        catch(IOException e){

        }
        if (!validFile){
            throw new RuntimeException("Invalid file: there are no readable rows of data in the file");
        }
        return new StateList(theStates, representativeCount); //create new StateList object with arraylist of State objects and the no of reps
    }


    static StateList readXLSXFile(String[] userIn){
        String Nameoffile = userIn[0]; //file name
        boolean validFile = false; //default value for the file
        int representativeCount =435; //default reps
        if (userIn.length>1){
            try {
                representativeCount = Integer.parseInt(userIn[1]); //store num of reps
            }
            catch(NumberFormatException e){
                throw new RuntimeException("Invalid input: non-integer value entered for representatives" + e.getMessage());
            }
        }
        if (representativeCount<1){
            throw new RuntimeException("Invalid input: cannot have less than 1 representative");
        }
        ArrayList<US_State> theStates = new ArrayList<>();
        try {
            FileInputStream inputStream = new FileInputStream(Nameoffile);
            Workbook workbook =  new XSSFWorkbook(inputStream);
            Sheet worksheet = workbook.getSheetAt(0);
            Row row = worksheet.getRow(0);
            for(int i=0; i < row.getLastCellNum(); i++) {
                if (row.getCell(0).equals("Population")) ;
                {
                    poplocation = i;
                }
                if (row.getCell(0).equals("State")) {
                    statelocation = i;
                }

            }
            int b = 1;
            while ((worksheet.getRow(b)) != null) { //while there are lines to be dealt with (also moves on to the next line)
                Row data = worksheet.getRow(b); //split into array of string
                if (reveiwLineXLSX(data) ) {     //if line is acceptable
                    validFile = true;
                    US_State state = new US_State(data.getCell(statelocation).getStringCellValue(), (int) (data.getCell(poplocation).getNumericCellValue())); //new state with name and population
                    //false if hamilton for a third boolean true if huntington-hale
                    theStates.add(state);
                }
                b += 1;
            }


        } catch (FileNotFoundException e) {
            throw new RuntimeException("Error: File " + Nameoffile+ " not found");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return new StateList(theStates, representativeCount);

    }


    static boolean reviewLine(String[] inLine){
        try {
            if ((inLine.length>1)&&(Integer.parseInt(inLine[poplocation])>-1)){
                return true;
            }
        } catch (NumberFormatException e) {
            return false;
        }
        return false;
    }

    static boolean reveiwLineXLSX(Row inLine){
        try {
            if ((inLine.getLastCellNum()>1)&&     (((inLine.getCell(poplocation).getNumericCellValue())>-1))){
                return true;
            }
        } catch (NumberFormatException e) {
            return false;
        }
        return false;


    }
}



