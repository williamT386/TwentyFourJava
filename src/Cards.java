//DONE
/**
 * William Tang
 * Cards.java
 * 5/21/2020
 *
 * This program has the JPanel and the CardLayout. This program creates the Start, Instructions, LevelSelect, Practice, TimedTrials, DifficultySelect,
 * Endless, GetName, WinTimedTrials, and LostTimedTrials instances and adds them to the CardLayout in order. This program also has a getter method for each
 * variable.
 **/

//imports the JPanel and CardLayout
import javax.swing.JPanel;
import java.awt.CardLayout;

public class Cards extends JPanel
{
    //creates the CardLayout instance
    private CardLayout layoutOfCards;

    //creates the Start, Instructions, LevelSelect, Practice instance
    private Start startInstance;
    private Instructions instructionsInstance;
    private LevelSelect levelSelectInstance;
    private Practice practiceInstance;
    private TimedTrials timedTrialsInstance;
    private DifficultySelect difficultySelectInstance;
    private Endless endlessInstance;
    private GetName getNameInstance;
    private WinTimedTrials winTimedTrialsInstance;
    private LostTimedTrials lostTimedTrialsInstance;

    //constructor
    public Cards()
    {
        //creates and adds the CardLayout instance
        layoutOfCards = new CardLayout();
        setLayout(layoutOfCards);

        //creates a Start instance and adds it to the CardLayout as the 1st card
        startInstance = new Start(this);
        add(startInstance, "Start");

        //creates an Instructions instance and adds it to the CardLayout as the 2nd card
        instructionsInstance = new Instructions(this);
        add(instructionsInstance,  "Instructions");

        //creates a LevelSelect instance and adds it to the CardLayout as the 3rd card
        levelSelectInstance = new LevelSelect(this);
        add(levelSelectInstance, "Level Select");

        //creates a Practice instance and adds it to the CardLayout as the 4th card
        practiceInstance = new Practice(this);
        add(practiceInstance, "Practice");

        //creates a TimedTrials instance and adds it to the CardLayout as the 5th card
        timedTrialsInstance = new TimedTrials(this);
        add(timedTrialsInstance, "Timed Trials");

        //creates a DifficultySelect instance and adds it to the CardLayout as the 6th card
        difficultySelectInstance = new DifficultySelect(this);
        add(difficultySelectInstance, "Difficulty Select");

        //creates a TimedTrials instance and adds it to the CardLayout as the 7th card
        endlessInstance = new Endless(this);
        add(endlessInstance, "Endless");

        //creates a GetName instance and adds it to the CardLayout as the 8th card
        getNameInstance = new GetName(this);
        add(getNameInstance, "Get Name");

        //creates a WinTimedTrials instance and adds it to the CardLayout as the 9th card
        winTimedTrialsInstance = new WinTimedTrials(this);
        add(winTimedTrialsInstance, "Win Timed Trials");

        //creates a LostTimedTrials instance and adds it to the CardLayout as the 10th card
        lostTimedTrialsInstance = new LostTimedTrials(this);
        add(lostTimedTrialsInstance, "Lost Timed Trials");

        startInstance.setTimedTrialsInstance(timedTrialsInstance);
    }

    //getter for layoutOfCards
    public CardLayout getLayoutOfCards()
    {
        return layoutOfCards;
    }

    //getter for practiceInstance
    public Practice getPracticeInstance()
    {
        return practiceInstance;
    }

    //getter for endlessInstance
    public Endless getEndlessInstance()
    {
        return endlessInstance;
    }

    //getter for winTimedTrialsInstance
    public WinTimedTrials getWinTimedTrialsInstance()
    {
        return winTimedTrialsInstance;
    }

    //getter for instructionsInstance
    public Instructions getInstructionsInstance()
    {
        return instructionsInstance;
    }

    //getter for timedTrialsInstance
    public TimedTrials getTimedTrialsInstance()
    {
        return timedTrialsInstance;
    }

    //getter for getNameInstance
    public GetName getGetNameInstance()
    {
        return getNameInstance;
    }

    //getter for difficultySelectInstance
    public DifficultySelect getDifficultySelectInstance()
    {
        return difficultySelectInstance;
    }

    //getter for levelSelectInstance
    public LevelSelect getLevelSelectInstance()
    {
        return levelSelectInstance;
    }

    //getter for lostTimedTrialsInstance
    public LostTimedTrials getLostTimedTrialsInstance()
    {
        return lostTimedTrialsInstance;
    }
}
