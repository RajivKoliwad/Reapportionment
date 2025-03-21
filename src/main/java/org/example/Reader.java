package org.example;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import org.apache.poi.xssf.usermodel.*;
import org.apache.poi.ss.usermodel.*;


public class Reader {
    static int poplocation; //tells us which column popolation is stored in
    static int statelocation; //tells us which column the state is stored in
    static boolean hhMethod = true; //default huntington-hill method if nothing else is specified
    static int representativeCount = 435; //default reps

    static boolean csv;
    static boolean xlsx;


    //readFile decides which apportionment method to use given user input. Defaults to Huntington Hill if not specified
    static StateList readFile(String[] userIn){
        for (int i = 0; i < userIn.length; i++){
            if (userIn[i].equals("--hamilton")||userIn[i].equals("-hamilton")){
                hhMethod=false;
            }
            try {
                representativeCount = Integer.parseInt(userIn[i]);
            }

            catch(NumberFormatException e) {
                //file format error
            }
            if (userIn[i].length()> 4){
                if ((userIn[i].substring(userIn[i].length()-4, userIn[i].length())).equals(".csv")){
                    csv=true;
                }
            }
            if (userIn[i].length()> 5){
                if ((userIn[i].substring(userIn[i].length()-5, userIn[i].length())).equals((".xlsx"))){
                    xlsx=true;
                }
            }

        }
        if (csv){
            return readCSVFile(userIn);
        }
        if (xlsx){
            return readXLSXFile(userIn);
        }
        else{
            throw new RuntimeException("Program must take a file argument ending in .csv or .xlsx");
        }

    }


    //readCSV file reads in CSVs, called when we know user used CSV
    static StateList readCSVFile(String[] userIn){
        String fileName = userIn[0]; //file name
        boolean validFile = false; //default value for the file
        try {
            //tUnderstand file format before creating StateList
            BufferedReader brtest = new BufferedReader(new FileReader(fileName));
            String[] headerline = brtest.readLine().split(",");
            for (int i = 0; i < headerline.length; i++) {
                if ((headerline[i].trim()).equals("Population")) {
                    poplocation = i;
                }
                if ((headerline[i].trim()).equals("State")) {
                    statelocation = i;
                }
            }
            brtest.close();
        }
        catch(FileNotFoundException e){
            throw new RuntimeException("issue with file: " + e);
        }
        catch(IOException e){

        }
        ArrayList<US_State> theStates = new ArrayList<>(); //create empty arraylist for states
        try{ // code which will transfer data to arraylist
            //learned more about BufferedReader here:  https://docs.oracle.com/javase/8/docs/api/java/io/BufferedReader.html
            BufferedReader br = new BufferedReader(new FileReader(fileName));
            String line;
            br.readLine(); //account for header, skips to second row
            while ((line = br.readLine()) != null) { // process each line of data
                line = line.trim(); // Remove any extra leading/trailing whitespace
                if (!line.isEmpty()) {
                    String[] data = line.split(","); // split into array of strings (CSV entries delimiter is the comma)
                    if (reviewLineCsv(data)) { // if line is acceptable
                       validFile = true;
                        try {
                            // Create a new state with name and population, using 'hhMethod' as the third parameter
                            US_State state = new US_State(data[statelocation].trim(), Integer.parseInt(data[poplocation].trim()), hhMethod);
                            theStates.add(state);
                        } catch (NumberFormatException e) {
                            // Skip the line if population is not valid (could be empty or malformed)
                            System.err.println("Skipping invalid line: " + line);
                        }
                    }
                }
            }
            br.close();
        }
        catch(IOException e){
            throw new RuntimeException("Error:" + e);
        }
        if (!validFile){
            throw new RuntimeException("Invalid file: there are no readable rows of data in the file");
        }
        return new StateList(theStates, representativeCount); //create new StateList object with arraylist of State objects and the no of reps
    }


    static StateList readXLSXFile(String[] userIn){
        String Nameoffile = userIn[0]; //file name
        boolean validFile = false; //default value for the file
        ArrayList<US_State> theStates = new ArrayList<>();
        try {
            //next 14 lines of code try to understand file structure to then populate StateList
            FileInputStream inputStream = new FileInputStream(Nameoffile);
            Workbook workbook =  new XSSFWorkbook(inputStream);
            Sheet worksheet = workbook.getSheetAt(0);
            Row row = worksheet.getRow(0);
            for(int i=0; i < row.getLastCellNum(); i++) {
                if (row.getCell(i).getStringCellValue().equals("Population"))
                {
                    poplocation = i;
                }
                if (row.getCell(i).getStringCellValue().equals("State")) {
                    statelocation = i;

                }

            }
            //populate StateList
            int b = 1;
            while ((worksheet.getRow(b)) != null) { //while there are lines to be dealt with
                Row data = worksheet.getRow(b); //split into array of string
                if (reveiwLineXLSX(data) ) {     //if line is acceptable
                    validFile = true;
                    US_State state = new US_State(data.getCell(statelocation).getStringCellValue(), (int) (data.getCell(poplocation).getNumericCellValue()), hhMethod); //new state with name and population
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


    static boolean reviewLineCsv(String[] inLine){
        String state = inLine[statelocation].trim();
        String populationStr = inLine[poplocation].trim();

        if (state.isEmpty() || populationStr.isEmpty()) {
            System.out.println("State or population is empty: " + Arrays.toString(inLine)); // Log invalid line
            return false;
        }
        try {
            // Check if population is a valid integer and greater than -1
            int population = Integer.parseInt(populationStr);
            if (population < 0) {
                System.out.println("Invalid population (negative value): " + populationStr + " in line: " + Arrays.toString(inLine)); // Log invalid line
                return false;
            }
        } catch (NumberFormatException e) {
            // Handle cases where population is not a valid integer
            System.out.println("Invalid population format: " + populationStr + " in line: " + Arrays.toString(inLine)); // Log invalid line
            return false;
        }
        return true;
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


