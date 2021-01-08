//DONE
/**
 * William Tang
 * Endless.java
 * 5/21/2020
 *
 * This is a gamemode in which the user plays more and more levels of the same difficulty for an endless number of times. Each time they beat a level, new
 *cards are generated to match the new level. The user can exit out of this class with 'e' or 'E' or the Exit button.
 */

//imports Color and Font for drawing
import java.awt.Color;
import java.awt.Font;

//imports JButton, JLabel, and JPanel for drawing
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

//imports Image, MediaTracker, and Toolkit to get the images
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.Toolkit;

//imports ActionEvent and ActionListener to respond to the button
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

//imports KeyEvent, KeyListener, FocusEvent, FocusListener to respond to the different keys pressed
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

public class Endless extends JPanel implements ActionListener, KeyListener, FocusListener
{
    private Image fullImage;

    private Cards card;
    private RunGame drawRunGame;

    private String math;

    private String[] valueArray;

    private int difficulty;

    //constructor
    public Endless(Cards cardIn)
    {
        //sets card into the cardIn value
        card = cardIn;
        //sets math to an empty string
        math = "";
        //creates a font to use later
        Font myFont = new Font("Arial", Font.PLAIN, 20);

        //sets the layout to null
        setLayout(null);

        //adds KeyListener and FocusListener and requests the focus
        addKeyListener(this);
        addFocusListener(this);
        requestFocus();

        //adds the image
        fullImage = Toolkit.getDefaultToolkit().getImage("smallcards.gif");
        waitForImage(this, fullImage);

        //creates a new Game instance with null layout and adds it to drawRunGame
        drawRunGame = new RunGame(fullImage, "Endless");
        drawRunGame.setBounds(0, 0, 640, 600);
        drawRunGame.setLayout(null);
        add(drawRunGame);

        //creats a Label that says "Endless"
        JLabel endlessLabel = new JLabel("Endless");
        endlessLabel.setFont(new Font("Arial", Font.BOLD, 40));
        endlessLabel.setForeground(new Color(1, 22, 30));
        endlessLabel.setBounds(375, 0, 240, 120);
        drawRunGame.add(endlessLabel);

        //creates 4 different buttons for the operations and adds them to drawRunGame
        JButton buttonArray[] = new JButton[] {new JButton("+"), new JButton("-"), new JButton("*"), new JButton("/")};
        for(int i = 0; i < 4; i++)
        {
            buttonArray[i].setFont(myFont);
            buttonArray[i].setBounds(250, 180 + 50*i, 30, 30);
            buttonArray[i].addActionListener(this);
            drawRunGame.add(buttonArray[i]);
        }

        //adds a JPanel on the right side with null layout
        JPanel rightSide = new JPanel();
        rightSide.setBounds(640, 0, 260, 600);
        rightSide.setLayout(null);
        rightSide.setBackground(new Color(232, 241, 242));
        add(rightSide);

        //adds a reset button and attaches ActionListener
        JButton reset = new JButton("Reset");
        reset.setFont(myFont);
        reset.setBounds(70, 415, 150, 50);
        reset.addActionListener(this);
        rightSide.add(reset);

        //adds an exit button and attaches ActionListener
        JButton exit = new JButton("Exit");
        exit.setFont(myFont);
        exit.setBounds(70, 485, 150, 50);
        exit.addActionListener(this);
        rightSide.add(exit);
    }

    //responds to the different buttons pressed
    public void actionPerformed(ActionEvent e)
    {
        //gets the ActionCommand and stores into command
        String command = e.getActionCommand();
        //if the user wants to exit, the start card is displayed and all the presses and buttons are reset. the operation is reset
        if(command.equals("Exit"))
        {
            card.getLayoutOfCards().show(card, "Start");
            math = "";
            drawRunGame.resetPressAndButton();
        }
        //if the user clicks reset, all the variables should be reset to the defaults. the focus is requested
        else if(command.equals("Reset"))
        {
            drawRunGame.resetCards();
            math = "";
            this.requestFocus();
        }
        //if the user clicks a button again, they would expect it to un-select the button. the focus is requested.
        else if(command.equals(math) && valueArray.equals(drawRunGame.getValueArray()))
        {
            math = "";
            //this makes sure that the button that is pressed in drawRunGame has the same value as in this class
            drawRunGame.setButtonPressed(math);
            this.requestFocus();
        }
        //if the user clicks the "+" button, the math variable is set to that
        else if(command.equals("+") || command.equals("-") || command.equals("*") || command.equals("/"))
        {
            if(drawRunGame.getFirstPress() != 0)
            {
                math = command;
                //this makes sure that the button that is pressed in drawRunGame has the same value as in this class
                drawRunGame.setButtonPressed(math);
                valueArray = drawRunGame.getValueArray();
            }
            this.requestFocus();
        }
    }

    //adds the image that needs to be drawn
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

    //sets the difficulty in this class and in drawRunGame
    public void setDifficulty(int difficultyIn)
    {
        difficulty = difficultyIn;
        drawRunGame.setEndlessDifficulty(difficulty);
    }

    //if a key is typed, it should perform the same actions as in actionPerformed()
    public void keyTyped(KeyEvent e)
    {
        //stores the KeyChar of e into command
        char command = e.getKeyChar();
        //if the user clicks 'e' or 'E', the same thing that happens in actionPerformed() occurs if they clicked the Exit button
        if(command == 'e' || command == 'E')
        {
            card.getLayoutOfCards().show(card, "Start");
            math = "";
            drawRunGame.resetPressAndButton();
        }
        //if the user clicks 'r' or 'R', the same thing that happens in actionPerformed() occurs if they clicked the Reset button
        else if(command == 'r' || command == 'R')
        {
            drawRunGame.resetCards();
            math = "";
        }
        //if the user clicks types a button again, they would expect it to un-select the button
        else if(math.length() > 0 && command == math.charAt(0) && valueArray.equals(drawRunGame.getValueArray()))
        {
            math = "";
            //this makes sure that the button that is pressed in the InstructionsImage instance has the same value as in this class
            drawRunGame.setButtonPressed(math);

        }
        //if the user types an operation button, the math variable is set to that
        else if(command == '+' || command == '-' || command == '*' || command == '/')
        {
            if(drawRunGame.getFirstPress() != 0)
            {
                math = "" + command;
                //this makes sure that the button that is pressed in the InstructionsImage instance has the same value as in this class
                drawRunGame.setButtonPressed(math);
                valueArray = drawRunGame.getValueArray();
            }
        }
    }
    //these 4 methods exist so that the KeyListener and FocusListener work
    public void keyPressed(KeyEvent e) {}
    public void keyReleased(KeyEvent e) {}
    public void focusGained(FocusEvent e) {}
    public void focusLost(FocusEvent e) { }

    //this requests the focus
    public void requestTheFocus()
    {
        this.requestFocus();
    }
}
