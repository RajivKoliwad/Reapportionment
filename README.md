# Apportionment Project


## About
This piece of code apportions electoral college votes based on two diferent apportionment algorithms (Hamiltonian and Huntington Hill).
More informaiton about these methods can be found here:

 - Huntington Hill: https://en.wikipedia.org/wiki/Huntington%E2%80%93Hill_method
 - Hamilton: https://en.wikipedia.org/wiki/Hare_quota 


## How to Run
This project is made with gradle, which includes the dependencies for this code and creates a neat output (Apportionment.jar)

To run, you should first run `./gradlew clean build` in order to create the Jar file.

From there, you can run somthing like: `java -jar build/libs/apportionment.jar testStates.csv --huntingtonHill`

You can choose CSV files (example given) or .XLSX files. The program defaults to huntingtonHill, but use hamilton if the hamilton flag is supplied

Note - Sometimes, the Huntington Hill and Hamlilton methods have the same output
