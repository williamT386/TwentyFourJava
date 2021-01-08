//DONE
/**
 * William Tang
 * ReadAnswerFile.java
 * 5/21/2020
 *
 * This class gets all the different numbers in the "24 solutions file.txt.txt" and "level number to set of numbers.txt" files and stores them. When a separate
 * class sends in a difficulty or a level, but not both, the corresponding set of 4 integers is returned back. If a difficulty is passed in, then the set of
 * 4 arrays returned will vary depending on the Math.random() method.
 **/

//imports File, Scanner, and FileNotFoundException in order to read the file
import java.io.File;
import java.util.Scanner;
import java.io.FileNotFoundException;

public class ReadAnswerFile
{
    private String[] possibleValues0, possibleValues1, possibleValues2, possibleValues3;
    private String[] percentage;
    private int[] convertLevelToSetOfNumbers;

    private final int level0Max = 681;
    private final int level1Max = 1058;
    private final int level2Max = 1262;
    private final int level3Max = 1364;

    private boolean isDifficulty = false;

    //constructor
    public ReadAnswerFile()
    {
        //creates arrays of length 1363. This is the number of lines in the file to be read and therefore the number of possible combinations.
        possibleValues0 = new String[1363];
        possibleValues1 = new String[1363];
        possibleValues2 = new String[1363];
        possibleValues3 = new String[1363];
        percentage = new String[1363];

        //creates an array of length 38 because there are only 38 levels
        convertLevelToSetOfNumbers = new int[38];

    }

    public void getDemoFile()
    {
        //gets the "24 solutions file.txt.txt" file to read
        Scanner inFile = null;
        String fileName = "24 solutions file.txt";
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

        //gets the next number and stores it into the correct array since there are 4 different arrays
        int counterA = 0;
        int counterB = 0;
        while(inFile.hasNext())
        {
            if(counterA == 0)
            {
                possibleValues0[counterB] = inFile.next();
                counterA++;
            }
            else if(counterA == 1)
            {
                possibleValues1[counterB] = inFile.next();
                counterA++;
            }
            else if(counterA == 2)
            {
                possibleValues2[counterB] = inFile.next();
                counterA++;
            }
            else if(counterA == 3)
            {
                possibleValues3[counterB] = inFile.next();
                counterA++;
            }
            else if(counterA == 4)
            {
                percentage[counterB] = inFile.nextLine().substring(1);
                counterB++;
                counterA = 0;
            }
        }

        //closes this file
        inFile.close();

        //gets the "level number to set of numbers.txt" file to read
        Scanner inFile2 = null;
        String fileName2 = "level number to set of numbers.txt";
        File inputFile2 = new File(fileName2);
        try
        {
            inFile2 = new Scanner(inputFile2);
        }
        catch(FileNotFoundException e)
        {
            System.out.println(e);
            System.exit(1);
        }

        //adds the next integer into the convertLevelToSetOfNumbers array at the correct index
        int counter = 0;
        while(inFile2.hasNext())
        {
            convertLevelToSetOfNumbers[counter] = inFile2.nextInt();
            counter++;
        }

        //closes this file
        inFile2.close();
    }

    //decides what to do if the gamemode sends only a difficulty
    public int[] setCardsDifficulty(int difficulty)
    {
        //this means it is on difficulty calculations
        isDifficulty = true;

        //determines a random level within the correct difficulty
        int[] eachLevelsCards = new int[] {(int)(Math.random()*level0Max), (int)(Math.random()*(level1Max-level0Max) + level0Max), (int)(Math.random()*(level2Max-level1Max) + level1Max), (int)(Math.random()*(level3Max-level2Max) + level2Max)};
        int level = eachLevelsCards[difficulty];

        //calls setCardsLevel because the level is gained, so the 4 integers for the card values are returned into numbersNoSubtract
        int[] numbersNoSubtract = setCardsLevel(level);

        //the Game instance runs with all the values having 1 subtracted from them, so this is done
        int[] numbersWithSubtract = new int[4];
        for(int i = 0; i < 4; i++)
        {
            numbersWithSubtract[i] = numbersNoSubtract[i] - 1;
        }
        return numbersWithSubtract;
    }

    //this determines what to do if only a level is given
    public int[] setCardsLevel(int level)
    {
        int value0, value1, value2, value3;

        int convertedLevel;
        //if isDifficulty is false, then 1 should be subtraced because that is how the Game instance runs
        if(!isDifficulty)
        {
            convertedLevel = convertLevelToSetOfNumbers[level] - 1;
        }
        //if isDifficulty is true, then this will be subtracted later
        else
        {
            convertedLevel = level;
        }

        isDifficulty = false;

        //since the values are saved as strings, the integer is gained and stored into the correct value variable
        value0 = calculateIntFromString(possibleValues0, convertedLevel);
        value1 = calculateIntFromString(possibleValues1, convertedLevel);
        value2 = calculateIntFromString(possibleValues2, convertedLevel);
        value3 = calculateIntFromString(possibleValues3, convertedLevel);

        //returns the array of 4 integers of the card values
        return new int[] {value0, value1, value2, value3};
    }

    //determines the int from the String array taken in and the index
    public int calculateIntFromString(String[] stringIn, int convertedLevel)
    {
        int value;
        if(stringIn[convertedLevel].length() == 1)
        {
            value = stringIn[convertedLevel].charAt(0) - 48;
        }
        else
        {
            value = (stringIn[convertedLevel].charAt(0) - 48)*10;
            value += stringIn[convertedLevel].charAt(1) - 48;
        }

        return value;
    }
}
