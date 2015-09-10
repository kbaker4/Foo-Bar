package com.company;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

import static java.lang.Double.parseDouble;
import static oracle.jrockit.jfr.events.Bits.intValue;

public class Main {

    public static void main(String[] args)
    throws FileNotFoundException {

        List<Double> x = new ArrayList<>();
        List<Double> y = new ArrayList<>();
        int percentImprovement;

        // Prompt for the input text file name, check to make sure it exists
        Scanner sc = new Scanner(System.in);
        BufferedReader firstResults, secondResults = null;


        System.out.println("Enter filename of first set of test results: ");
        firstResults = getTestResultsFile();
        System.out.println("Enter filename of second set of test results: ");
        secondResults = getTestResultsFile();

        //convert file to List<Double>
        x = createListFromFile(firstResults);
        y = createListFromFile(secondResults);

        //sort the List smallest to largest
        x = sortList(x);
        y = sortList(y);

        //calculate the improvement % from the two lists
        System.out.println(x);
        System.out.println(y);
        percentImprovement = answer(x, y);

        System.out.println("The percent improvement for this rabbit is: " + percentImprovement + "%.");
    }

    public static BufferedReader getTestResultsFile(){
        BufferedReader file = null;
        Scanner sc = new Scanner(System.in);
        int validFileName;
        do {
            String inputFile = sc.next();
            try {
                    file = new BufferedReader(new FileReader(inputFile));
                validFileName = 1;
            } catch (FileNotFoundException e) {
                System.out.println("File not found, try again: ");
                validFileName = 0;
            }
        } while (validFileName == 0);

        return file;
    }

    public static List<Double> createListFromFile(BufferedReader file){
        List<Double> x = new ArrayList<>();

        Scanner sc = new Scanner(file).useDelimiter(", ");
        while(sc.hasNext()){
            x.add(parseDouble(sc.next()));
        }

        return x;
    }

    public static List<Double> sortList(List<Double> x){
        Collections.sort(x);

        return x;
    }

    public static int answer(List<Double> x, List<Double> y){
        int percent = 0;
        int limit = x.size();
        Double[] xAsArray = x.toArray(new Double[x.size()]);
        Double[] yAsArray = y.toArray(new Double[y.size()]);

        //first find the difference between the two times
        double difference = xAsArray[0] - yAsArray[0];
        //then divide the first number by the difference
        double divide = difference / xAsArray[0];
        //then multiply by 100
        double multiply = 100 * divide;

        //I want an integer as the percent to the nearest int
        percent = intValue(Math.round(multiply));

        return percent;
    }

}
