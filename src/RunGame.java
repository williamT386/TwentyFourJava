/**
 * William Tang
 * RunGame.java
 * 5/21/2020
 *
 * This class runs the entire game. Its very difficult to describe the entire game in this comment, so I will just include the general structure. First,
 * the gamemode is saved and that will be used to determine how to respond to each situation. The program then gets the 4 card values from ReadAnswerFile
 * and prints it out with a random suit. These 4 card values depend on Math.random() and the type of gamemode. After the user presses an image/box,
 * operation, and then image/box in that exact order, the program resets both of them to 0 and draws in the resulting number as a fraction or as a integer.
 * The win situation is if the user finishes with the first card/box as a 24, and the rest of the boxes with nothing. Depending on the gamemode, different
 * responses wll occur.
 */

//imports Graphics, Color, and Font for drawing
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Font;

//import image to draw images later
import java.awt.Image;

//imports JLabel and JPanel for drawing
import javax.swing.JLabel;
import javax.swing.JPanel;

//imports MouseListener and MouseEvent in order to detect when the user clicks on the screen
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;

//imports ActionListener and ActionEvent to check when the user types on the keyboard
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

//imports the Timer to count how much time has passed for TimedTrials gamemode
import javax.swing.Timer;

public class RunGame extends JPanel implements MouseListener, ActionListener
{
    //variable for storing the large image
    private Image fullImage;

    //sets final ints that for the width and height of each card, and the distance away each card should be from each other in the x and y directions
    private final int  WIDTH_OF_EACH_CARD = 40;
    private final int HEIGHT_OF_EACH_CARD = 60;
    private final int DISTANCE_AWAY_X = 140;
    private final int DISTANCE_AWAY_Y = 180;
    //sets final int for the number of levels beaten to win for TimedTrials
    private final int TIMED_TRIALS_WIN_VALUE = 10;

    //the noChange values are used to store the cards that are generated and will never be changed. this is useful if the game is reset, because the original
    //cards must be maintained. the other 2 arrays are used to store which card (part of the image) to draw, but are set to some non-sense values once they
    //are removed or replaced by rectangles
    private int allXLocationsGenerated[], allYLocationsGenerated[], allXLocationsGeneratedNoChange[], allYLocationsGeneratedNoChange[];

    //alreadyPrintedFirstTime determines if the program has printed yet. changeValueArray determines if valueArray has changed
    private boolean alreadyPrintedFirstTime, changeValueArray;

    //firstPress and secondPress are ints that determine where the user clicked. they are on the scale of 1-4. count is used to determine which location to
    //add a new random number to in the allXLocationsGenerated and allYLocationsGenerated. newDrawPosition is used to determine the newest spot to draw a
    //rectangle from. level shows that level this is on. numOfLevelsCompleted stores the number of levels completed so far
    private int firstPress, secondPress, count, newDrawPosition, level, numOfLevelsCompleted;

    //buttonPressed holds the operation that the user clicked on. answer holds the value after an arithmetic operation buttonPressed is performed on
    //firstPress and secondPress. answer is a String because division has the possiblity of generating fractions, which cannot be sufficiently expressed
    //in decimal format because they can be repetiting decimals. Instead, answer is held in String format in order to accomodate for fractions. type stores
    //what gamemode the game is
    private String buttonPressed, answer, type;

    //newDrawPositionArray determines all the positions where the program should draw a rectangle
    private boolean[] newDrawPositionArray;

    //this stores the values of all the derived numbers
    private String[] valueArray;

    //these are JLabels that are displayed in TimedTrials gamemode
    private JLabel levelsCompleted, timeLeft;
    //these stores the amount of minutes and seconds left on the timer before the user loses for TimedTrials gamemode
    private int timeLeftMinutes, timeLeftSeconds;

    //this stores the difficulty for all the endless levels
    private int endlessDifficulty;

    //this holds the Cards instance
    private Cards card;

    //this holds the timer that is created for timing
    private Timer timer;

    //constructor
    public RunGame(Image imageIn, String typeIn)
    {
        //sets defaults for timeLeftMinutes, timeLeftSeconds, numOfLevelsCompleted, and level
        timeLeftMinutes = 10;
        timeLeftSeconds = 0;
        numOfLevelsCompleted = 0;
        level = -1;
        //sets the fullImage variable to store the image being passed in
        fullImage = imageIn;
        //sets type to typeIn
        type = typeIn;

        //sets the noChange variables to new arrays of length 4. other 2 arrays will be set to nonsense values, so the program knows not to print them, since
        //the cards have not been generated yet
        allXLocationsGeneratedNoChange = new int[4];
        allYLocationsGeneratedNoChange = new int[4];
        allXLocationsGenerated = new int[] {-1, -1, -1, -1};
        allYLocationsGenerated = new int[] {-1, -1, -1, -1};

        //this is the first time the program is being run, so nothing has been printed yet
        alreadyPrintedFirstTime = false;
        //the valueArray is being changed
        changeValueArray = true;

        //firstPress and secondPress are 0 because the user has not clicked on card/box yet. count is 0 because it is used for the index of an array, so
        //setting it to 0 is necessary to make sure it gets the first number in the array
        firstPress = secondPress = count = 0;

        //the user has not clicked any button yet, so buttonPressed is set to an empty string
        buttonPressed = "";

        //there is no answer yet, because no operation was made
        answer = "";

        //no rectangles should be drawn yet
        newDrawPositionArray = new boolean [] {false, false, false, false};

        //the array of values for rectangles is all empty strings because none shoud be drawn
        valueArray = new String[] {"", "", "", ""};

        //this makes a ReadAnswerFile instance which sets files into an array in the ReadAnswerFile instance.
        ReadAnswerFile instance = new ReadAnswerFile();
        instance.getDemoFile();
        //all the choices get the file but in a different format depending on the gamemode
        if(type.equals("Instructions"))
        {
            allXLocationsGenerated = instance.setCardsDifficulty(0);
        }
        else if(type.equals("Timed Trials"))
        {
            allXLocationsGenerated = instance.setCardsDifficulty((int)(Math.random()*2) + 1);
        }
        else if(type.equals("Endless"))
        {
            allXLocationsGenerated = instance.setCardsDifficulty(endlessDifficulty);
        }
        //the background color is set
        setBackground(new Color(232, 241, 242));

        //the MouseListener is added
        addMouseListener(this);

        //if the game is TimedTrials gamemode, then a timer is created and starts
        if(type.equals("Timed Trials"))
        {
            timer = new Timer(1000, this);
//			timer.start();
        }
    }

    //this method calls other methods and passes in Graphics g in order to draw to the screen
    public void paintComponent(Graphics g)
    {
        //paints the background
        super.paintComponent(g);

        //sets the font to a new font
        g.setFont(new Font("Arial", Font.PLAIN, 40));

        //if the user wins for TimedTrials or Endless gamemode, this sets newDrawPositionArray[0] to false so to not draw anything
        if(type.equals("Timed Trials") && valueArray[0].equals("24") && valueArray[1].equals("none") && valueArray[2].equals("none") && valueArray[3].equals("none") && numOfLevelsCompleted < TIMED_TRIALS_WIN_VALUE - 1)
        {
            newDrawPositionArray[0] = false;
        }
        else if(type.equals("Endless") && valueArray[0].equals("24") && valueArray[1].equals("none") && valueArray[2].equals("none") && valueArray[3].equals("none"))
        {
            newDrawPositionArray[0] = false;
        }

        //decides the parameters for drawing the card, because each card has a different location on the image and on the JPanel
        chooseWhichImageToDraw(g);

        highlightBoxPress(g, Color.BLUE, firstPress);
        highlightButtonPress(g);
        highlightBoxPress(g, Color.GREEN, secondPress);

        count = 0;

        drawNewBox(g);

        firstPressButtonPressSecondPress(g);

    }

    //decides the parameters for drawing the card, because each card has a different location on the image and on the JPanel. the first time the code is run,
    //new cards will be generated and drawn. Later, when this is called, this method will draw the same cards as the ones generated the first time.
    public void chooseWhichImageToDraw(Graphics g)
    {
        //determines if it is the first program run
        if(!alreadyPrintedFirstTime)
        {
            //should draw 4 different cards from top left --> top right --> bottom left --> bottom right, so nested for loops are necessary
            for(int y = 0; y < 2; y++)
            {
                for(int x = 0; x < 2; x++)
                {
                    //stores randomly generated numbers generated in getCardX() and getCardY(int xLocation)
                    allYLocationsGenerated[count] = (int)(Math.random()*4);
                    //sets the arrays to the newly generated values
                    setInitialArray();
                    //draws the image
                    drawImageOnBoard(g, x, y, allXLocationsGenerated[count], allYLocationsGenerated[count]);
                    count++;
                }
            }
            //already printed once, so in the future this shouldn't happen
            alreadyPrintedFirstTime = true;
        }
        else
        {
            //should draw 4 different cards from top left --> top right --> bottom left --> bottom right, so nested for loops are necessary
            int arrayIndex = 0;
            for(int y = 0; y < 2; y++)
            {
                for(int x = 0; x < 2; x++)
                {
                    //checks if the allXLocationGenerated index is not a nonsense value before printing
                    if(allXLocationsGenerated[arrayIndex] != -1)
                    {
                        drawImageOnBoard(g, x, y, allXLocationsGenerated[arrayIndex], allYLocationsGenerated[arrayIndex]);
                    }
                    arrayIndex++;
                }
            }
        }
    }

    //this method sets all the different arrays to the values that were generated by getCardX() and getCardY()
    public void setInitialArray()
    {
        //looped in order to initialize all 4 numbers in each array
        for(int i = 0; i < 4; i++)
        {
            valueArray[i] = allXLocationsGenerated[i] + 1 + "";
            allXLocationsGeneratedNoChange[i] = allXLocationsGenerated[i] + 1;
            allYLocationsGeneratedNoChange[i] = allYLocationsGenerated[i] + 1;
        }
    }

    //draws the image with the g.drawImage() method with 9 parameters to specify which part of the image to draw and where. the location being drawn is
    //different for the Instructions gamemode and the rest because Instructions gamemode has to be on the right side to fit
    public void drawImageOnBoard(Graphics g, int x, int y, int xLocationOnImage, int yLocationOnImage)
    {
        if(type.equals("Instructions"))
        {
            g.drawImage(fullImage, 90 + DISTANCE_AWAY_X * x, 100 + DISTANCE_AWAY_Y * y, 200 + DISTANCE_AWAY_X * x, 250 + DISTANCE_AWAY_Y * y, WIDTH_OF_EACH_CARD * xLocationOnImage, HEIGHT_OF_EACH_CARD * yLocationOnImage, WIDTH_OF_EACH_CARD * (xLocationOnImage + 1), HEIGHT_OF_EACH_CARD * (yLocationOnImage + 1), this);
        }
        else if(type.equals("Practice") || (type.equals("Timed Trials")) || (type.equals("Endless")) )
        {
            g.drawImage(fullImage, 330 + DISTANCE_AWAY_X * x, 120 + DISTANCE_AWAY_Y * y, 440 + DISTANCE_AWAY_X * x, 270 + DISTANCE_AWAY_Y * y, WIDTH_OF_EACH_CARD * xLocationOnImage, HEIGHT_OF_EACH_CARD * yLocationOnImage, WIDTH_OF_EACH_CARD * (xLocationOnImage + 1), HEIGHT_OF_EACH_CARD * (yLocationOnImage + 1), this);
        }
    }

    //this draws an outline around the card/box that the user clicks on. g.drawRect() is called twice because the box should be 2px thickness
    public void drawOutline(Graphics g, Color color, int x1, int y1, int width1, int height1)
    {
        g.setColor(color);
        g.drawRect(x1, y1, width1, height1);
        g.drawRect(x1 - 1, y1 - 1, width1 + 2, height1 + 2);
    }

    //this determines the placement and size of the highlight to draw for the card/box by checking which spot the user clicked on. since the user can only
    //choose 1 card/box for each press, and there are 2 separate presses, then this method's for loop ends the moment 1 outline is drawn
    public void highlightBoxPress(Graphics g, Color myColor, int press)
    {
        int[] xForOutline, yForOutline;
        //different positions for the box in Instructions gamemode compared to the rest because Instructions gamemode is moved to the right side
        if(type.equals("Instructions"))
        {
            xForOutline = new int[] {0, 89, 89 + DISTANCE_AWAY_X, 89, 89 + DISTANCE_AWAY_X};
            yForOutline = new int[] {0, 99, 99, 99 + DISTANCE_AWAY_Y, 99 + DISTANCE_AWAY_Y};
        }
        else
        {
            xForOutline = new int[] {0, 329, 329 + DISTANCE_AWAY_X, 329, 329 + DISTANCE_AWAY_X};
            yForOutline = new int[] {0, 119, 119, 119 + DISTANCE_AWAY_Y, 119 + DISTANCE_AWAY_Y};
        }

        boolean keepRunningForLoop = true;
        for(int i = 1; i < 5 && keepRunningForLoop; i++)
        {
            if(press == i)
            {
                drawOutline(g, myColor, xForOutline[i], yForOutline[i], 111, 151);
                keepRunningForLoop = false;
            }
        }
    }

    //this determines the placement and size of the highlight to draw for the button by checking which spot the user clicked on. since the user can only
    //choose 1 operation for the press, then this method's for loop ends the moment 1 outline is drawn
    public void highlightButtonPress(Graphics g)
    {
        //different positions for the box in Instructions gamemode compared to the rest because Instructions gamemode is moved to the right side
        if(type.equals("Instructions"))
        {
            String possibleOperations[] = new String[] {"+", "-", "*", "/"};
            boolean keepRunningForLoop = true;
            for(int i = 0; i < 4 && keepRunningForLoop; i++)
            {
                if(buttonPressed.equals(possibleOperations[i]))
                {
                    drawOutline(g, Color.BLUE, 39, 129 + 50*i, 31, 31);
                    keepRunningForLoop = false;
                }
            }
        }
        else if(type.equals("Practice") || type.equals("Timed Trials") || (type.equals("Endless")))
        {
            String possibleOperations[] = new String[] {"+", "-", "*", "/"};
            boolean keepRunningForLoop = true;
            for(int i = 0; i < 4 && keepRunningForLoop; i++)
            {
                if(buttonPressed.equals(possibleOperations[i]))
                {
                    drawOutline(g, Color.BLUE, 249, 179 + 50*i, 31, 31);
                    keepRunningForLoop = false;
                }
            }
        }
    }

    //this method draws a new box and the string for the answer inside. if the answer is a divide by 0 fraction error, the program prints out "undef."
    //instead of the fraction. if the user got 24 with only 1 card left, that counts as a winning sitution. the approach for that is different for each
    //gamemode
    public void drawNewBox(Graphics g)
    {
        int[] xForBox, yForBox, xForString, yForString;
        //different positions for the box in Instructions gamemode compared to the rest because Instructions gamemode is moved to the right side
        if(type.equals("Instructions"))
        {
            xForBox = new int[] {90, 90 + DISTANCE_AWAY_X, 90, 90 + DISTANCE_AWAY_X};
            yForBox = new int[] {100, 100, 100 + DISTANCE_AWAY_Y, 100 + DISTANCE_AWAY_Y};
            xForString = new int [] {100, 100 + DISTANCE_AWAY_X, 100};
            yForString = new int [] {185, 185, 185 + DISTANCE_AWAY_Y};
        }
        else
        {
            xForBox = new int[] {330, 330 + DISTANCE_AWAY_X, 330, 330 + DISTANCE_AWAY_X};
            yForBox = new int[] {120, 120, 120 + DISTANCE_AWAY_Y, 120 + DISTANCE_AWAY_Y};
            xForString = new int [] {340, 340 + DISTANCE_AWAY_X, 340};
            yForString = new int [] {205, 205, 205 + DISTANCE_AWAY_Y};
        }

        //checks for divide by 0 error. if there is a divide by 0 error, that value printed is just changed to "undef". Otherwise, the actual value is printed
        for(int i = 0; i < 3; i++)
        {
            if(newDrawPositionArray[i])
            {
                g.setColor(Color.BLACK);
                g.drawRect(xForBox[i], yForBox[i], 110, 150);
                if(valueArray[i].indexOf("/0") == -1)
                {
                    g.drawString(valueArray[i], xForString[i], yForString[i]);
                }
                else
                {
                    g.drawString("undef.", xForString[i], yForString[i]);
                }
            }
        }

        //since the new box to be printed always takes place of the lower numerical position of the 2 cards/boxes removed, the ultimate answer will always be
        //"24" in the first position. therefore, by checking that the 0th index is "24" and the rest are "none", I can check when the user wins
        if(valueArray[0].equals("24") && valueArray[1].equals("none") && valueArray[2].equals("none") && valueArray[3].equals("none"))
        {
            //prints out nice if they win in Instructions and Practice gamemode
            if(type.equals("Instructions"))
            {
                g.drawString("NICE!", 150, 300);
            }
            else if(type.equals("Practice"))
            {
                g.drawString("NICE!", 390, 320);
            }
            else if(type.equals("Timed Trials"))
            {
                //if they beat the level but have not won yet, the cards are reset with a new level. the number of levels that the user has completed goes
                //up by 1 and the JLabel levelsCompleted reflects that. repaint() is then called
                if(numOfLevelsCompleted < TIMED_TRIALS_WIN_VALUE - 1)
                {
                    valueArray[0] = "";
                    valueArray[1] = "";
                    valueArray[2] = "";
                    valueArray[3] = "";
                    newDrawPositionArray[0] = false;
                    ReadAnswerFile instance = new ReadAnswerFile();
                    instance.getDemoFile();
                    allXLocationsGenerated = instance.setCardsDifficulty((int)(Math.random()*2) + 2);
                    alreadyPrintedFirstTime = false;
                    numOfLevelsCompleted++;
                    levelsCompleted.setText("Completed: " + numOfLevelsCompleted);
                    repaint();
                }
                //if they won, the levels completed is reset and the text is reset. the minutes and seconds are set into the GetName instance. everything else
                //is reset and a new set of cards are generated
                else
                {
                    numOfLevelsCompleted = 0;
                    levelsCompleted.setText("Completed: " + numOfLevelsCompleted);
                    card.getGetNameInstance().setMinutes(timeLeftMinutes);
                    card.getGetNameInstance().setSeconds(timeLeftSeconds);
                    card.getLayoutOfCards().show(card, "Get Name");
                    firstPress = secondPress = count = 0;
                    buttonPressed = "";
                    answer = "";
                    newDrawPositionArray = new boolean [] {false, false, false, false};
                    valueArray = new String[4];

                    allXLocationsGenerated = new int[] {-1, -1, -1, -1};
                    allYLocationsGenerated = new int[] {-1, -1, -1, -1};

                    for(int i = 0; i < 4; i++)
                    {
                        allXLocationsGenerated[i] = allXLocationsGeneratedNoChange[i] - 1;
                        allYLocationsGenerated[i] = allYLocationsGeneratedNoChange[i] - 1;
                        valueArray[i] = allXLocationsGenerated[i] + 1 + "";
                    }
                    alreadyPrintedFirstTime = false;
                    ReadAnswerFile instance = new ReadAnswerFile();
                    instance.getDemoFile();
                    allXLocationsGenerated = instance.setCardsDifficulty((int)(Math.random()*2) + 1);
                    timer = null;
                    timer = new Timer(1000, this);
                    timeLeftMinutes = 10;
                    timeLeftSeconds = 0;
                    timeLeft.setText("Time Left: " + timeLeftMinutes + ":0" + timeLeftSeconds);
                }
            }
            //the cards are reset with a new level. A new set of cards are generated and repaint() is called
            else if(type.equals("Endless"))
            {
                valueArray[0] = "";
                valueArray[1] = "";
                valueArray[2] = "";
                valueArray[3] = "";
                newDrawPositionArray[0] = false;
                ReadAnswerFile instance = new ReadAnswerFile();
                instance.getDemoFile();
                allXLocationsGenerated = instance.setCardsDifficulty((int)(Math.random()*2) + 2);
                alreadyPrintedFirstTime = false;
                numOfLevelsCompleted++;
                repaint();
            }
        }

    }

    //if the user has chosen the firstPress, buttonPress, and secondPress, this method decides which position to draw the new box and stores that. this method
    //also calls doMathOnNumbers() in order to determine what the answer should be after the operation. this method then resets the firstPress, buttonPress,
    //and secondPress variables for later use, and calls repaint()
    public void firstPressButtonPressSecondPress(Graphics g)
    {
        if(firstPress != 0 && buttonPressed != "" && secondPress != 0)
        {
            if(firstPress < secondPress)
            {
                newDrawPosition = firstPress;
            }
            else
            {
                newDrawPosition = secondPress;
            }
            g.setColor(Color.BLACK);

            doMathOnNumbers();

            newDrawPositionArray[newDrawPosition-1] = true;
            firstPress = secondPress =  0;
            buttonPressed = "";
            repaint();
        }
    }

    //the program should no longer draw the cards at the positions of the presses, so the LocationsGenerated arrays are set to nonsense values of -1.
    //the code also stops drawing boxes for the 2 spots that were chosen. the rest of the method finds the numerators and denominators of each number, if
    //applicable, but if it is not a fraction, the entire value is returned. if the answer is a fraction and the denominator is negative, then the
    //negativeSignMover is called to move the sign to the numerator. if the answer is a fraction, then fractionSimplifier is called. Lastly, the value is added
    //to the valueArray to be drawn in paintComponent()
    public void doMathOnNumbers()
    {
        allXLocationsGenerated[firstPress - 1] = allXLocationsGenerated[secondPress - 1] = -1;
        newDrawPositionArray[firstPress - 1] = newDrawPositionArray[secondPress - 1] = false;

        int firstValue, numeratorIntFirst, denominatorIntFirst, secondValue, numeratorIntSecond, denominatorIntSecond;
        firstValue = numeratorIntFirst = denominatorIntFirst = secondValue = numeratorIntSecond = denominatorIntSecond = 0;
        //if it is not a fraction, the int is just the entire string
        if(valueArray[firstPress - 1].indexOf('/') == -1)
        {
            firstValue = findIntFromString(valueArray[firstPress - 1]);
        }
        //if it is a fraction, the the numerators and denominators are added
        else
        {
            numeratorIntFirst = findIntFromStringNumerator(valueArray[firstPress - 1]);
            denominatorIntFirst = findIntFromStringDenominator(valueArray[firstPress - 1]);
        }

        //if it is not a fraction, the int is just the entire string
        if(valueArray[secondPress - 1].indexOf('/') == -1)
        {
            secondValue = findIntFromString(valueArray[secondPress - 1]);
        }
        //if it is a fraction, the the numerators and denominators are added
        else
        {
            numeratorIntSecond = findIntFromStringNumerator(valueArray[secondPress - 1]);
            denominatorIntSecond = findIntFromStringDenominator(valueArray[secondPress - 1]);
        }

        //resets these values for later use
        valueArray[firstPress - 1] = "none";
        valueArray[secondPress - 1] = "none";

        //doArithmeticValueAll() is called
        doArithmeticValueAll(firstValue, numeratorIntFirst, denominatorIntFirst, secondValue, numeratorIntSecond, denominatorIntSecond);

        //if the number is a fraction with a negative sign in the denominator, negativeSignMover() is called
        if(answer.indexOf("/-") != -1)
        {
            negativeSignMover();
        }
        //if the number is a fraction, fractionSimplifier() will be called
        if(answer.indexOf('/') != -1)
        {
            fractionSimplifier();
        }

        //sets valueArray to the answer and states that the changeValueArray should be false
        valueArray[newDrawPosition-1] = answer;
        changeValueArray = false;
    }

    //this method reacts to the 4 different arithmetic operations. although each operation is very different, they all run the same base style: if neither
    //number of the expression is a fraction, then one event happens. if the first is a fraction and the second is not a fraction, or vise versa, 1 separate
    //if statement is called for each. another situation exists when both are fractions. a special case exists with division: if the user divides the first
    //number by a second number that is undefinied, this method manually sets the answer to be a nonsense value so that "undef." would be printed. for example,
    //for 5 / (2/0), the program without the special case would state 0/10, but mathematically the answer should be "undef."
    public void doArithmeticValueAll(int firstValue, int numeratorIntFirst, int denominatorIntFirst, int secondValue, int numeratorIntSecond, int denominatorIntSecond)
    {
        int answerNumerator = 0;
        int answerDenominator = 0;
        if(buttonPressed.equals("+"))
        {
            if(numeratorIntFirst == 0 && numeratorIntSecond == 0)
            {
                answer = firstValue + secondValue + "";
            }
            else if (numeratorIntFirst != 0 && numeratorIntSecond == 0)
            {
                answerNumerator = numeratorIntFirst + secondValue * denominatorIntFirst;
                answerDenominator = denominatorIntFirst;
                answer = "" + answerNumerator + "/" + answerDenominator;
            }
            else if (numeratorIntFirst == 0 && numeratorIntSecond != 0)
            {
                answerNumerator = firstValue * denominatorIntSecond + numeratorIntSecond;
                answerDenominator = denominatorIntSecond;
                answer = "" + answerNumerator + "/" + answerDenominator;
            }
            else
            {
                numeratorIntFirst *= denominatorIntSecond;
                numeratorIntSecond *= denominatorIntFirst;
                denominatorIntFirst *= denominatorIntSecond;
                answerNumerator = numeratorIntFirst * denominatorIntSecond + numeratorIntSecond;
                answerDenominator = denominatorIntFirst;
                answer = "" + answerNumerator + "/" + answerDenominator;
            }
        }
        else if(buttonPressed.equals("-"))
        {
            if(numeratorIntFirst == 0 && numeratorIntSecond == 0)
            {
                answer = firstValue - secondValue + "";
            }
            else if (numeratorIntFirst != 0 && numeratorIntSecond == 0)
            {
                answerNumerator = numeratorIntFirst - secondValue * denominatorIntFirst;
                answerDenominator = denominatorIntFirst;
                answer = "" + answerNumerator + "/" + answerDenominator;
            }
            else if (numeratorIntFirst == 0 && numeratorIntSecond != 0)
            {
                answerNumerator = firstValue * denominatorIntSecond - numeratorIntSecond;
                answerDenominator = denominatorIntSecond;
                answer = "" + answerNumerator + "/" + answerDenominator;
            }
            else
            {
                numeratorIntFirst *= denominatorIntSecond;
                numeratorIntSecond *= denominatorIntFirst;
                denominatorIntFirst *= denominatorIntSecond;
                answerNumerator = numeratorIntFirst - numeratorIntSecond;
                answerDenominator = denominatorIntFirst;
                answer = "" + answerNumerator + "/" + answerDenominator;
            }
        }
        else if(buttonPressed.equals("*"))
        {
            if(numeratorIntFirst == 0 && numeratorIntSecond == 0)
            {
                answer = firstValue * secondValue + "";
            }
            else if (numeratorIntFirst != 0 && numeratorIntSecond == 0)
            {
                answerNumerator = numeratorIntFirst * secondValue;
                answerDenominator = denominatorIntFirst;
                answer = "" + answerNumerator + "/" + answerDenominator;
            }
            else if (numeratorIntFirst == 0 && numeratorIntSecond != 0)
            {
                answerNumerator = firstValue * numeratorIntSecond;
                answerDenominator = denominatorIntSecond;
                answer = "" + answerNumerator + "/" + answerDenominator;
            }
            else
            {
                answerNumerator = numeratorIntFirst * numeratorIntSecond;
                answerDenominator = denominatorIntFirst * denominatorIntSecond;
                answer = "" + answerNumerator + "/" + answerDenominator;
            }
        }
        else if(buttonPressed.equals("/"))
        {
            if(numeratorIntFirst == 0 && numeratorIntSecond == 0)
            {
                answer = "" + firstValue + "/" + secondValue;
            }
            else if(numeratorIntFirst == 0 && numeratorIntSecond != 0 && denominatorIntSecond == 0)
            {
                answer = "1/0";
            }
            else if (numeratorIntFirst != 0 && numeratorIntSecond == 0)
            {
                answerNumerator = numeratorIntFirst;
                answerDenominator = denominatorIntFirst * secondValue;
                answer = "" + answerNumerator + "/" + answerDenominator;
            }
            else if (numeratorIntFirst == 0 && numeratorIntSecond != 0)
            {
                answerNumerator = firstValue * denominatorIntSecond;
                answerDenominator = numeratorIntSecond;
                answer = "" + answerNumerator + "/" + answerDenominator;
            }
            else
            {
                answerNumerator = numeratorIntFirst * denominatorIntSecond;
                answerDenominator = denominatorIntFirst * numeratorIntSecond;
                answer = "" + answerNumerator + "/" + answerDenominator;
            }
        }
    }

    //this returns the numerator in int format from a string fraction
    public int findIntFromStringNumerator(String value)
    {
        int numeratorInt = 0;
        int placeHolderCount = 1;
        int start = 1;
        if(value.charAt(0) != '-')
        {
            start = 0;
        }

        String numeratorString = value.substring(start, value.indexOf('/'));
        for(int i = numeratorString.length() - 1; i >= 0; i--)
        {
            int characterInIntFormat = (int)(numeratorString.charAt(i) - 48);
            numeratorInt += characterInIntFormat*placeHolderCount;
            placeHolderCount *= 10;
        }

        if(start == 1)
        {
            numeratorInt = -numeratorInt;
        }

        return numeratorInt;
    }

    //this returns the denominator in int format from a string fraction
    public int findIntFromStringDenominator(String value)
    {
        int denominatorInt = 0;
        int placeHolderCount = 1;
        String denominatorString = value.substring(value.indexOf('/') + 1);
        for(int i = denominatorString.length() - 1; i >= 0; i--)
        {
            int characterInIntFormat = (int)(denominatorString.charAt(i) - 48);
            denominatorInt += characterInIntFormat*placeHolderCount;
            placeHolderCount *= 10;
        }
        return denominatorInt;
    }

    //this returns the int format of a string number
    public int findIntFromString(String value)
    {
        int intValue = 0;
        int placeHolderCount = 1;
        if(value.charAt(0) != '-')
        {
            for(int i = value.length() - 1; i >= 0; i--)
            {
                int temp = (int)(value.charAt(i)) - 48;
                intValue += temp*placeHolderCount;
                placeHolderCount *= 10;
            }
        }
        else
        {
            for(int i = value.length() - 1; i > 0; i--)
            {
                int temp = (int)(value.charAt(i)) - 48;
                intValue += temp*placeHolderCount;
                placeHolderCount *= 10;
            }
            intValue = -intValue;
        }
        return intValue;
    }

    //this moves the negative sign to the front of a fraction if it the fraction is in the denominator
    public void negativeSignMover()
    {
        answer = "-" + answer.substring(0,answer.indexOf('/')) + "/" + answer.substring(answer.indexOf('/') + 2);
    }

    //this simplifies a fraction if possible
    public void fractionSimplifier()
    {
        int answerNumerator = findIntFromStringNumerator(answer);
        int answerDenominator = findIntFromStringDenominator(answer);
        boolean breakOut = false;
        boolean changeBreakOut = true;
        if(answerNumerator > answerDenominator)
        {
            while(answerDenominator != 1 && breakOut == false)
            {
                for(int i = 2; i <= answerDenominator; i++)
                {
                    if(1.0 * answerNumerator / i - answerNumerator / i == 0.0 && 1.0 * answerDenominator / i - answerDenominator / i == 0.0)
                    {
                        answerNumerator /= i;
                        answerDenominator /= i;
                        i = answerDenominator;
                        changeBreakOut = false;
                    }
                    else
                    {
                        changeBreakOut = true;
                    }
                }
                if(changeBreakOut)
                {
                    breakOut = true;
                }
            }
            answer = "" + answerNumerator + "/" + answerDenominator;
        }
        //special case if the answerNumerator and answerDenominator are the same and same sign
        else if(answerNumerator == answerDenominator)
        {
            answer = "1";
        }
        //special case if the answerNumerator and answerDenominator are the same but opposite signs
        else if(answerNumerator == -answerDenominator)
        {
            answer = "-1";
        }
        else if(answerNumerator < answerDenominator)
        {
            while(answerDenominator != 1 && breakOut == false)
            {
                for(int i = 2; i <= answerNumerator; i++)
                {
                    if(1.0 * answerNumerator / i - answerNumerator / i == 0.0 && 1.0 * answerDenominator / i - answerDenominator / i == 0.0)
                    {
                        answerNumerator /= i;
                        answerDenominator /= i;
                        i = answerNumerator;
                        changeBreakOut = false;
                    }
                    else
                    {
                        changeBreakOut = true;
                    }
                }
                if(changeBreakOut)
                {
                    breakOut = true;
                }
            }
            answer = "" + answerNumerator + "/" + answerDenominator;
        }
        //if the answerDenominator is 1, its not a longer a fraction
        if(answerDenominator == 1)
        {
            answer = "" + answerNumerator;
        }
    }

    //decides which card the user clicked on. It does this by calculating the different positions for each card. if the click is inside all 4 parameters of
    //the location of the card, then that card is selected
    public void mousePressed(MouseEvent e)
    {
        int[] minX, maxX, minY, maxY;
        //different for the Instructions gamemode and the rest because Instructions gamemode has to be on the right side to fit
        if(type.equals("Instructions"))
        {
            minX = new int[] {90, 90 + DISTANCE_AWAY_X, 90, 90 + DISTANCE_AWAY_X};
            maxX = new int[] {200, 200 + DISTANCE_AWAY_X, 200, 200 + DISTANCE_AWAY_X};
            minY = new int[] {100, 100, 100 + DISTANCE_AWAY_Y, 100 + DISTANCE_AWAY_Y};
            maxY = new int[] {250, 250, 250 + DISTANCE_AWAY_Y, 250 + DISTANCE_AWAY_Y};
        }
        else
        {
            minX = new int[] {330, 330 + DISTANCE_AWAY_X, 330, 330 + DISTANCE_AWAY_X};
            maxX = new int[] {440, 440 + DISTANCE_AWAY_X, 440, 440 + DISTANCE_AWAY_X};
            minY = new int[] {120, 120, 120 + DISTANCE_AWAY_Y, 120 + DISTANCE_AWAY_Y};
            maxY = new int[] {270, 270, 270 + DISTANCE_AWAY_Y, 270 + DISTANCE_AWAY_Y};
        }

        //checks if the click should be for the firstPress
        boolean runForLoop = true;
        for(int i = 0; i < 4 && runForLoop; i++)
        {
            if(buttonPressed == "" && e.getX() >= minX[i] && e.getX() <= maxX[i] && e.getY() >= minY[i] && e.getY() <= maxY[i] && valueArray[i] != "none")
            {
                firstPress = i + 1;
                runForLoop = false;
            }
        }

        //checks if the click should be for the secondPress
        runForLoop = true;
        for(int i = 0; i < 4 && runForLoop; i++)
        {
            if(firstPress != i + 1 && firstPress != 0 && buttonPressed != "" && e.getX() >= minX[i] && e.getX() <= maxX[i] && e.getY() >= minY[i] && e.getY() <= maxY[i] && valueArray[i] != "none")
            {
                secondPress = i + 1;
                runForLoop = false;
            }
        }

        //calls repaint()
        repaint();
    }

    //these 4 methods exist to make sure that the MouseListener works
    public void mouseClicked(MouseEvent e) { }
    public void mouseReleased(MouseEvent e) { }
    public void mouseEntered(MouseEvent e) { }
    public void mouseExited(MouseEvent e) { }

    //this is a setter method for buttonPressed, because the button is in the Instructions.java class. this method only works if firstPress is not 0 because
    //the operation cannot work without a value before it
    public void setButtonPressed(String buttonNumber)
    {
        if(firstPress != 0)
        {
            buttonPressed = buttonNumber;
            secondPress = 0;
        }
        repaint();
    }

    //this is a getter for the firstPress variable
    public int getFirstPress()
    {
        return firstPress;
    }

    //this is a getter for the secondPress variable
    public int getSecondPress()
    {
        return secondPress;
    }

    //this resets all the variables back to their originals except for alreadyPrintedFirstTime, the locations Generated, and the no change locations
    //generated. the valueArray and locations generated arrays are set to what the no change location arrays are storing
    public void resetCards()
    {
        firstPress = secondPress = count = 0;
        buttonPressed = "";
        answer = "";
        newDrawPositionArray = new boolean [] {false, false, false, false};
        valueArray = new String[4];

        allXLocationsGenerated = new int[] {-1, -1, -1, -1};
        allYLocationsGenerated = new int[] {-1, -1, -1, -1};

        for(int i = 0; i < 4; i++)
        {
            allXLocationsGenerated[i] = allXLocationsGeneratedNoChange[i] - 1;
            allYLocationsGenerated[i] = allYLocationsGeneratedNoChange[i] - 1;
            valueArray[i] = allXLocationsGenerated[i] + 1 + "";
        }

        repaint();
    }

    //getter for valueArray. if changeValueArray is true, nonsense is returned so that the class which asked for this knows that something changed
    public String[] getValueArray()
    {
        if(changeValueArray)
        {
            return valueArray;
        }
        else
        {
            changeValueArray = true;
            return new String[] {"", "", "", ""};
        }
    }

    //setter for levelsCompleted
    public void setLevelsCompleted(JLabel levelsCompletedIn)
    {
        levelsCompleted = levelsCompletedIn;
    }

    //setter for endlessDifficulty
    public void setEndlessDifficulty(int endlessDifficultyIn)
    {
        endlessDifficulty = endlessDifficultyIn;
    }

    //getter for level
    public int getLevel()
    {
        return level;
    }

    ///setter for level
    public void setLevel(int levelIn)
    {
        level = levelIn;
    }

    //gets the different card int values from ReadAnswerFile and stores them
    public void getNumbersFromFile()
    {
        ReadAnswerFile fileReader = new ReadAnswerFile();
        fileReader.getDemoFile();
        valueArray = new String[4];
        int[] tempValueArray = fileReader.setCardsLevel(level - 1);
        for(int i = 0; i < 4; i++)
        {
            allXLocationsGenerated[i] = tempValueArray[i] - 1;
            allXLocationsGeneratedNoChange[i] = allXLocationsGenerated[i] + 1;
        }

        for(int i = 0; i < 4; i++)
        {
            valueArray[i] = tempValueArray[i] + "";
        }
    }

    //resets the presses and buttonPressed and the newDrawPositionArray to the defaults
    public void resetPressAndButton()
    {
        firstPress = secondPress = 0;
        buttonPressed = "";
        newDrawPositionArray = new boolean[] {false, false, false, false};
    }

    //setter for card
    public void setCard(Cards cardIn)
    {
        card = cardIn;
    }

    //setter for timeLeft
    public void setTimeLeft(JLabel timeLeftIn)
    {
        timeLeft = timeLeftIn;
    }

    //checks actionPerformed for the timer
    public void actionPerformed(ActionEvent e)
    {
        //if timer exists, this runs
        if(timer != null)
        {
            //calculates the time left and displays it
            if(timeLeftSeconds > 0)
            {
                timeLeftSeconds--;
            }
            else
            {
                timeLeftMinutes--;
                timeLeftSeconds = 59;
            }

            //prints out a 0 before the timeLeftSeconds so as to not confuse the user
            if(timeLeftSeconds < 10)
            {
                timeLeft.setText("Time Left: " + timeLeftMinutes + ":0" + timeLeftSeconds);
            }
            else
            {
                timeLeft.setText("Time Left: " + timeLeftMinutes + ":" + timeLeftSeconds);
            }

            //if all the time is out, the timer is reset and the user is redirected to the "Lost Timed Trials" card
            if(timeLeftSeconds == 0 && timeLeftMinutes == 0)
            {
                timer = null;
                card.getLayoutOfCards().show(card, "Lost Timed Trials");
                card.getLostTimedTrialsInstance().requestTheFocus();
                alreadyPrintedFirstTime = false;
                timer = new Timer(1000, this);
                timeLeftMinutes = 10;
                timeLeftSeconds = 0;
//				timer.start();
            }
        }
    }

    public void startTimer()
    {
        timer.start();
    }

    public void stopTimer()
    {
        timer.stop();
    }
}
