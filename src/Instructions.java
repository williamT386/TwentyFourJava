//DONE
/**
 * William Tang
 * Instructions.java
 * 5/21/2020
 * This class exists for the Instructions page. This class draws all the different instructions and JPanels and creates a Game instance. The class checks
 * which button was clicked and responds accordingly. For operation buttons, the chosen operation is sent into the Game instance.
 **/

//imports Color and Font for drawing
import java.awt.Color;
import java.awt.Font;
//imports GridLayout for positioning things in the grid
import java.awt.GridLayout;
//imports Image, MediaTracker, and Toolkit for getting and printing the image
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.Toolkit;
//imports JPanel, JScrollPane, JTextArea, JButton, JLabel, along with ActionListener and ActionEvent to respond to these buttons, scollpanes, and textareas
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
//imports FocusEvent, FocusListener, KeyEvent, and KeyListener to respond to the keys being pressed
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
//imports BorderFactory to establish a border around the JPanel
import javax.swing.BorderFactory;

public class Instructions extends JPanel implements ActionListener, KeyListener, FocusListener
{
    //stores the full image
    private Image fullImage;

    //holds the operation that the user chose based on the "+", "-", "*", and "/" buttons
    private String math;
    //holds the instance of Game
    private RunGame drawRunGame;

    //stores the Card instance
    private Cards card;

    //stores a copy of the 4 values in the program
    private String[] valueArray;

    //constructor for setting the default values and creating all the different components
    public Instructions(Cards cardIn)
    {
        //sets card into the cardIn value
        card = cardIn;

        //sets math to an empty string
        math = "";

        //gets the Image and sets it into fullImage
        fullImage = Toolkit.getDefaultToolkit().getImage("smallcards.gif");
        waitForImage(this, fullImage);

        //setting the entire background to a purple color and a GridLayout. This also creates the border around the program.
        setBackground(new Color(74, 78, 105));
        setLayout(new GridLayout(1, 2, 20, 0));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        //creates a font that will be used throughout this method
        Font myFont = new Font("Arial", Font.PLAIN, 20);

        //JPanel for the JTextArea and the JScrollPane
        JPanel left = new JPanel();
        left.setLayout(new GridLayout(1, 2));
        add(left);
        //creates the JTextArea and sets its font, line wrap, wrap style, and ability to be edited. adds the JTextArea to the left JPanel
        JTextArea instructionsTextArea = new JTextArea("In this game, you will be given 4 different numbers initially from a set of cards. The numbers will "
                + "be from 1 to 13 inclusive. You will try to set up a mathematical equation using only arithmetic with all 4 numbers to result in an "
                + "answer of exactly 24. If you get the answer of 24, you win. Otherwise, you can try press the reset button to try again. You have to click "
                + "a number before you click a card and before you click the 2nd card, just like how you would write it in math. Otherwise, this program does "
                + "not work and ignores your request. Attached on the right is a Demo that you can try out right now. For this page and all other pages, "
                + "you can use the buttons or press a single key to indicate what action you want to perform. If you press 'e' or 'E', that exists the page "
                + "if an Exit button exists. In the same manner, clicking the Exit button exists that page. You can use 'r' or 'R' as replacements for the "
                + "Reset button, or you can just click the Reset button. You can also type the operation instead of clicking if you prefer. The rest of the "
                + "buttons must be pressed, and cannot be used with the keyboard, because they operate very differently; in order to give you the best "
                + "experience possible, those were changed to only button presses. This game also has 3 different gamemodes along with the demo: Practice "
                + "mode, with many different levels and difficulties that you can try; Timed Trials mode, where you are timed to complete 10 different levels "
                + "within 10 minutes of difficulties \"Casual\" and \"Challenging\". If you win on that gamemode, you are added to the leaderboard! The last "
                + "mode is Endless mode, where you can choose which difficulty you wish to play at and you can just continously play the game without end! "
                + "It's completely stress-free and can help improve your skills before you rush into Timed Trials mode. Good luck and have fun!");
        instructionsTextArea.setFont(myFont);
        instructionsTextArea.setLineWrap(true);
        instructionsTextArea.setWrapStyleWord(true);
        instructionsTextArea.setEditable(false);
        left.add(instructionsTextArea);
        //creates a JScrollPane and attaches it to the JTextArea. adds the JScrollPane to the left JPanel
        JScrollPane instructionsScroller = new JScrollPane(instructionsTextArea);
        left.add(instructionsScroller);

        //Game instance created here and placed on the right side of the JFrame. the layout is null.
        drawRunGame = new RunGame(fullImage, "Instructions");
        drawRunGame.setLayout(null);
        add(drawRunGame);
        //adds a JLabel on the top of the Game instance and displays "Demo"
        JLabel demo = new JLabel("Demo");
        demo.setFont(new Font("Arial", Font.BOLD, 40));
        demo.setForeground(new Color(1, 22, 30));
        demo.setBounds(150, 0, 120, 120);
        drawRunGame.add(demo);
        //adds the reset button in the lower right corner with the correct font to the Game Instance. Adds the ActionListener to this
        JButton reset = new JButton("Reset");
        reset.setFont(myFont);
        reset.setBounds(250,450,80,60);
        reset.addActionListener(this);
        drawRunGame.add(reset);
        //adds the exit button in the lower right corner with the correct font to the Game Instance. Adds the ActionListener to this
        JButton exit = new JButton("Exit");
        exit.setFont(myFont);
        exit.setBounds(330,450,80,60);
        exit.addActionListener(this);
        drawRunGame.add(exit);
        //creates an array of buttons for the arithmetic operators
        JButton buttonArray[] = new JButton[] {new JButton("+"), new JButton("-"), new JButton("*"), new JButton("/")};
        //sets each button to the correct font, correct bounds, adds the ActionListener to each, and adds them to the Game instance
        for(int i = 0; i < 4; i++)
        {
            buttonArray[i].setFont(myFont);
            buttonArray[i].setBounds(40, 130 + 50*i, 30, 30);
            buttonArray[i].addActionListener(this);
            drawRunGame.add(buttonArray[i]);
        }

        //adds FocusListener and KeyListener
        this.addFocusListener(this);
        this.addKeyListener(this);
    }

    //gets image and adds it to the Image variable
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

    //reacts when the user clicks one of the buttons
    public void actionPerformed(ActionEvent evt)
    {
        //gets the ActionCommand and stores into the string command
        String command = evt.getActionCommand();

        //if the user clicked the exit button, the card switches to the Start card. The operation stored in this class is rest, and the cards, buttonPressed,
        //and cards pressed are reset to the defaults in the Game instance
        if(command.equals("Exit"))
        {
            card.getLayoutOfCards().show(card, "Start");
            math = "";
            drawRunGame.resetPressAndButton();
            drawRunGame.resetCards();
        }
        //if the user clicks reset, all the cards should be reset to the original. the operation should be rest. The focus is requested.
        else if(command.equals("Reset"))
        {
            drawRunGame.resetCards();
            math = "";
            this.requestFocus();
        }
        //if the user clicks a button again, they would expect it to un-select the button. the valueArray checking is to make sure that no values have changed,
        //so that would mean that they double clicked the button. if any of the values have changed, then the it just means they want to do the same operation,
        //which is allowed. The focus is requested.
        else if(command.equals(math) && valueArray.equals(drawRunGame.getValueArray()))
        {
            math = "";
            //this makes sure that the button that is pressed in the Game instance has the same value as in this class
            drawRunGame.setButtonPressed(math);
            this.requestFocus();

        }
        //if the user clicks an operation button after clicking a card, the operation is set to the operation button. The focus is requested.
        else if(command.equals("+") || command.equals("-") || command.equals("*") || command.equals("/"))
        {
            if(drawRunGame.getFirstPress() != 0)
            {
                math = command;
                //this makes sure that the button that is pressed in the Game instance has the same value as in this class
                drawRunGame.setButtonPressed(math);
                //this makes sure to update the valueArray after the values have been changed in the Game instance
                valueArray = drawRunGame.getValueArray();
            }
            this.requestFocus();
        }
    }

    //checks if a key is Typed
    public void keyTyped(KeyEvent e)
    {
        //if the user clicks 'e' or 'E', the same thing happens as if they clicked the Enter button
        char command = e.getKeyChar();
        if(command == 'e' || command == 'E')
        {
            card.getLayoutOfCards().show(card, "Start");
            math = "";
            drawRunGame.resetPressAndButton();
            drawRunGame.resetCards();
        }
        //if the user clicks 'r' or 'R', the same thing happens as if they clicked the Reset button
        else if(command == 'r' || command == 'R')
        {
            drawRunGame.resetCards();
            math = "";
        }
        //if the user typed one of the operators, then the same thing occurs as in actionPerformed()
        else if(math.length() > 0 && command == math.charAt(0) && valueArray.equals(drawRunGame.getValueArray()))
        {
            math = "";
            //this makes sure that the button that is pressed in the Game instance has the same value as in this class
            drawRunGame.setButtonPressed(math);

        }
        //if the user types an operation button after clicking a card, the operation is set to the operation button
        else if(command == '+' || command == '-' || command == '*' || command == '/')
        {
            if(drawRunGame.getFirstPress() != 0)
            {
                math = "" + command;
                //this makes sure that the button that is pressed in the Game instance has the same value as in this class
                drawRunGame.setButtonPressed(math);
                //this makes sure to update the valueArray after the values have been changed in the Game instance
                valueArray = drawRunGame.getValueArray();
            }
        }
    }
    //these 3 methods are included to make sure that the FocusListener and KeyListener work
    public void keyPressed(KeyEvent e) {}
    public void keyReleased(KeyEvent e) {}
    public void focusGained(FocusEvent e) {}

    //this makes sure the FocusListener is not lost
    public void focusLost(FocusEvent e) {
        this.requestFocus();
    }

    //this tells the JPanel to request the focus
    public void requestTheFocus()
    {
        this.requestFocus();
    }
}
