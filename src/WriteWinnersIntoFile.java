//DONE
/**
 * William Tang
 * WriteWinnersIntoFile.java
 * 5/21/2020
 *
 * This class gets the file and counts how long the array needs to be. Then, it runs the file again and saves all the information into arrays of the length
 * measured previously. This class then adds in the new score that the user got. Afterwards, the file is read and counted, and then the information is saved
 * into arrays again.
 */

//imports File, Scanner and FileNotFoundException for reading files. imports PrintWriter and IOException for writing files
import java.io.File;
import java.util.Scanner;
import java.io.PrintWriter;
import java.io.FileNotFoundException;
import java.io.IOException;

public class WriteWinnersIntoFile
{
    private int counterForIndexLength = 0;
    private String[] nameArray;
    private int[] minuteArray;
    private int[] secondArray;

    private String name;
    private int minute;
    private int second;

    private int place;

    //constructor
    public WriteWinnersIntoFile(String nameIn, int minuteIn, int secondIn)
    {
        //sets the name to nameIn
        name = nameIn;
        //sets the minute to minuteIn
        minute = minuteIn;
        //sets the second to secondIn
        second = secondIn;

        //reads the file
        readFile();
        //writes into the file
        writeFile();
        //reads the file again
        readFile();
    }

    public void readFile()
    {
        //reads the "Timed Trials Winners.txt" file
        Scanner inFile = null;
        String fileName = "Timed Trials Winners.txt";
        File inputFile = new File(fileName);
        try
        {
            inFile = new Scanner(inputFile);
        }
        catch(FileNotFoundException e)
        {
            System.out.println(e);
            System.exit(1);
        }

        //counts how the long the array need to be
        counterForIndexLength = 0;
        while(inFile.hasNext())
        {
            inFile.nextLine();
            counterForIndexLength++;
        }
        inFile.close();

        //makes the arrays the length of counterForIndexLength
        nameArray = new String[counterForIndexLength];
        minuteArray = new int[counterForIndexLength];
        secondArray = new int[counterForIndexLength];
        try
        {
            inFile = new Scanner(inputFile);
        }
        catch(FileNotFoundException e)
        {
            System.out.println(e);
            System.exit(2);
        }

        //adds the different inputs to the array
        int counter = 0;
        while(inFile.hasNext())
        {
            nameArray[counter] = inFile.next();
            minuteArray[counter] = inFile.nextInt();
            secondArray[counter] = inFile.nextInt();
            counter++;
        }
    }

    public void writeFile()
    {
        //gets the file "Timed Trials Winners.txt"
        PrintWriter outFile = null;
        String fileName = "Timed Trials Winners.txt";
        File outputFile = new File(fileName);
        try
        {
            outFile = new PrintWriter(outputFile);
        }
        catch(IOException e)
        {
            System.out.println(e);
            System.exit(3);
        }

        //determines what to print into the file
        boolean needToPrint = true;
        if(minute < minuteArray[0] || (minute == minuteArray[0] && second <= secondArray[0]))
        {
            outFile.println(name + " " + minute + " " + second);
            needToPrint = false;
            place = 1;
        }

        for(int i = 0; i < counterForIndexLength; i++)
        {
            outFile.println(nameArray[i] + " " + minuteArray[i] + " " + secondArray[i]);
            if((needToPrint) && (i < counterForIndexLength - 1) && (minute > minuteArray[i] || (minute == minuteArray[i] && second >= secondArray[i])) && (minute < minuteArray[i + 1] || (minute == minuteArray[i + 1] && second <= secondArray[i + 1])))
            {
                outFile.println(name + " " + minute + " " + second);
                needToPrint = false;
                place = i + 2;
            }

        }
        if(needToPrint)
        {
            outFile.println(name + " " + minute + " " + second);
            place = counterForIndexLength;
        }

        //closes the file
        outFile.close();
    }

    //this returns the leaderboard as a string array
    public String[] getLeaderboard()
    {
        String[] leaderboard = new String[counterForIndexLength];
        for(int i = 0; i < counterForIndexLength; i++)
        {
            if(secondArray[i] < 10)
            {
                leaderboard[i] = "" + nameArray[i] + " " + minuteArray[i] + ":0" + secondArray[i];
            }
            else
            {
                leaderboard[i] = "" + nameArray[i] + " " + minuteArray[i] + ":" + secondArray[i];
            }
        }

        return leaderboard;

    }

    //getter for place
    public int getPlace()
    {
        return place;
    }
}
