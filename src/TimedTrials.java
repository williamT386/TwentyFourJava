/**
 * William Tang
 * TimedTrails.java
 * 5/21/2020
 *
 * This is a gamemode where the user has to complete 10 levels within 10 minutes. The difficulty is either "Casual" or "Challenging", and the user continues
 * until they win or run out of time. The time that they have left is displayed, and the number of levels that they have completed is also displayed. The user
 * can exit from this gamemode by pressing the exit button or typing 'e' or 'E'.
 */

//imports Color and Font for drawing
import java.awt.Color;
import java.awt.Font;

//imports JButton, JLabel, and JPanel for drawing
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

//imports Image, MediaTracker, and Toolkit to get the image
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.Toolkit;

//imports ActionEvent and ActionListener to check if a button was clicked
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

//imports FocusEvent, FocusListener, KeyEvent, and KeyListener to check if a key was typed
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class TimedTrials extends JPanel implements ActionListener, KeyListener, FocusListener
{
    private Image fullImage;

    private Cards card;
    private RunGame drawGame;

    private String math;

    private String[] valueArray;

    private JLabel levelsCompleted;

    //constructor
    public TimedTrials(Cards cardIn)
    {
        //sets card to cardIn
        card = cardIn;
        //sets math to a default string
        math = "";
        //sets the layout to null
        setLayout(null);
        //creates a font for later use
        Font myFont = new Font("Arial", Font.PLAIN, 20);

        //gets the image
        fullImage = Toolkit.getDefaultToolkit().getImage("smallcards.gif");
        waitForImage(this, fullImage);

        //creates a new Game instance and stores into drawGame
        drawGame = new RunGame(fullImage, "Timed Trials");
        drawGame.setBounds(0, 0, 600, 600);
        drawGame.setLayout(null);
        add(drawGame);

        //adds the JLabel for "Timed Trials"
        JLabel timeTrialsLabel = new JLabel("Timed Trails");
        timeTrialsLabel.setFont(new Font("Arial", Font.BOLD, 40));
        timeTrialsLabel.setForeground(new Color(1, 22, 30));
        timeTrialsLabel.setBounds(333, 0, 240, 120);
        drawGame.add(timeTrialsLabel);

        //creates the 4 JButtons for the operations and adds the ActionListener to each one
        JButton buttonArray[] = new JButton[] {new JButton("+"), new JButton("-"), new JButton("*"), new JButton("/")};
        for(int i = 0; i < 4; i++)
        {
            buttonArray[i].setFont(myFont);
            buttonArray[i].setBounds(250, 180 + 50*i, 30, 30);
            buttonArray[i].addActionListener(this);
            drawGame.add(buttonArray[i]);
        }

        //creates a JPanel on the right side
        JPanel rightSide = new JPanel();
        rightSide.setBounds(600, 0, 300, 600);
        rightSide.setLayout(null);
        rightSide.setBackground(new Color(232, 241, 242));
        add(rightSide);

        //adds a JLabel for the number of levels. this text is cut into 2 becaues the JFrame is not wide enough
        JLabel numberOfLevels = new JLabel("Number of Levels");
        numberOfLevels.setFont(new Font("Arial", Font.BOLD, 30));
        numberOfLevels.setForeground(new Color(1, 22, 30));
        numberOfLevels.setBounds(0, 10, 280, 120);
        rightSide.add(numberOfLevels);

        //this is the 2nd part of the JLabel for the number of levels completed.
        levelsCompleted = new JLabel("Completed: 0");
        levelsCompleted.setFont(new Font("Arial", Font.BOLD, 30));
        levelsCompleted.setForeground(new Color(1, 22, 30));
        levelsCompleted.setBounds(0, 45, 240, 120);
        rightSide.add(levelsCompleted);

        //this sets the levelsCompleted into the levelsCompleted in drawGame
        drawGame.setLevelsCompleted(levelsCompleted);

        //this JLabel shows how much time is left
        JLabel timeLeft = new JLabel("Time Left: 10:00");
        timeLeft.setFont(new Font("Arial", Font.BOLD, 30));
        timeLeft.setForeground(new Color(1, 22, 30));
        timeLeft.setBounds(0, 100, 260, 120);
        rightSide.add(timeLeft);

        //this sets the timeLEft into the timeLeft in drawGame
        drawGame.setTimeLeft(timeLeft);

        //adds a reset button with ActionListener
        JButton reset = new JButton("Reset");
        reset.setFont(myFont);
        reset.setBounds(110, 415, 150, 50);
        reset.addActionListener(this);
        rightSide.add(reset);

        //adds a exit button with ActionListener
        JButton exit = new JButton("Exit");
        exit.setFont(myFont);
        exit.setBounds(110, 485, 150, 50);
        exit.addActionListener(this);
        rightSide.add(exit);

        //adds the card into drawGame
        drawGame.setCard(card);

        //adds FocusListener and KeyListener
        this.addFocusListener(this);
        this.addKeyListener(this);
    }

    //adds the image
    public void waitForImage(JPanel component, Image image)
    {
        MediaTracker tracker = new MediaTracker(component);
        try
        {
            tracker.addImage(image, 0);
            tracker.waitForID(0);
        }
        catch(InterruptedException e)
        {
            e.printStackTrace();
        }
    }

    //checks if a button was clicked
    public void actionPerformed(ActionEvent e)
    {
        //stores the ActionCommand of e into command
        String command = e.getActionCommand();
        //if the user pressed the Exit button, the "Start" card is displayed. The operation is set to nothing and the press and button are reset.
        if(command.equals("Exit"))
        {
            card.getLayoutOfCards().show(card, "Start");
            math = "";
            drawGame.resetPressAndButton();
            drawGame.stopTimer();
        }
        //if the user clicks the Reset button, all the variables should be reset to the defaults. the focus is requested
        else if(command.equals("Reset"))
        {
            drawGame.resetCards();
            math = "";
            this.requestFocus();
        }
        //if the user clicks a button again, they would expect it to un-select the button. the focus is requested
        else if(command.equals(math) && valueArray.equals(drawGame.getValueArray()))
        {
            math = "";
            //this makes sure that the button that is pressed in drawGame has the same value as in this class
            drawGame.setButtonPressed(math);
            this.requestFocus();
        }
        //if the user clicks an operation button, the math variable is set to that. the focus is requested
        else if(command.equals("+") || command.equals("-") || command.equals("*") || command.equals("/"))
        {
            if(drawGame.getFirstPress() != 0)
            {
                math = command;
                //this makes sure that the button that is pressed in drawGame has the same value as in this class
                drawGame.setButtonPressed(math);
                valueArray = drawGame.getValueArray();
            }
            this.requestFocus();
        }
    }

    //checks if a key was typed
    public void keyTyped(KeyEvent e)
    {
        //gets the KeyChar of e and stores it into command
        char command = e.getKeyChar();
        //if the user typed 'e' or 'E', the same effect occurs as if they pressed the Exit button
        if(command == 'e' || command == 'E')
        {
            card.getLayoutOfCards().show(card, "Start");
            math = "";
            drawGame.resetPressAndButton();
            drawGame.stopTimer();
        }
        //if the user types 'r' or 'R', the same effect occurs as if they pressed the Reset button
        else if(command == 'r' || command == 'R')
        {
            drawGame.resetCards();
            math = "";
        }
        //if the user types a button again, they would expect it to un-select the button
        else if(math.length() > 0 && command == math.charAt(0) && valueArray.equals(drawGame.getValueArray()))
        {
            math = "";
            //this makes sure that the button that is pressed in drawGame has the same value as in this class
            drawGame.setButtonPressed(math);
        }
        //if the user types an operation button, the math variable is set to that
        else if(command == '+' || command == '-' || command == '*' || command == '/')
        {
            if(drawGame.getFirstPress() != 0)
            {
                math = "" + command;
                //this makes sure that the button that is pressed in drawGame has the same value as in this class
                drawGame.setButtonPressed(math);
                valueArray = drawGame.getValueArray();
            }
        }
    }

    //these 4 methods exist to make sure that KeyListener and FocusListener work
    public void keyPressed(KeyEvent e) {}
    public void keyReleased(KeyEvent e) {}
    public void focusGained(FocusEvent e) {}
    public void focusLost(FocusEvent e) { }

    //this requests the focus
    public void requestTheFocus()
    {
        this.requestFocus();
    }

    public RunGame getDrawGame()
    {
        return drawGame;
    }
}
