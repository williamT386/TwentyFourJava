//DONE
/**
 * William Tang
 * Practice.java
 * 5/21/2020
 *
 * This is a gamemode called Practice, where the user can decide a level in LevelSelect and the corresponding level appears in this class. For each level, the
 * corresponding cards are exactly the same. The way the program does this is it calls a separate class to find the value of the cards, which gives different
 * answers for each level input. The user can exit out of this class with the exit button or typing 'e' or 'E'.
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

//adds ActionEvent and ActionListener to check if a button was clicked
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

//adds FocusEvent, FocusListener, KeyEvent, and KeyListener to check if a key is pressed
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Practice extends JPanel implements ActionListener, FocusListener, KeyListener
{
    private Image fullImage;

    private int level;
    private Cards card;
    private RunGame drawRunGame;

    private JLabel levelLabel;
    private String math;

    private String[] valueArray;

    //constructor
    public Practice(Cards cardIn)
    {
        //sets card to cardIn
        card = cardIn;
        //sets math to an empty string
        math = "";
        //sets level to -1
        level = -1;
        //sets the layout to null
        setLayout(null);
        //creates a font for later use
        Font myFont = new Font("Arial", Font.PLAIN, 20);

        //adds the image
        fullImage = Toolkit.getDefaultToolkit().getImage("smallcards.gif");
        waitForImage(this, fullImage);

        //creates a Game instance and adds it to drawRunGame. the layout is null
        drawRunGame = new RunGame(fullImage, "Practice");
        drawRunGame.setBounds(0, 0, 700, 600);
        drawRunGame.setLayout(null);
        add(drawRunGame);

        //adds the 4 operation buttons with ActionListener to them. adds them to drawRunGame
        JButton buttonArray[] = new JButton[] {new JButton("+"), new JButton("-"), new JButton("*"), new JButton("/")};
        for(int i = 0; i < 4; i++)
        {
            buttonArray[i].setFont(myFont);
            buttonArray[i].setBounds(250, 180 + 50*i, 30, 30);
            buttonArray[i].addActionListener(this);
            drawRunGame.add(buttonArray[i]);
        }

        //creates a JPanel on the right side.
        JPanel rightSide = new JPanel();
        rightSide.setLayout(null);
        rightSide.setBackground(new Color(232, 241, 242));
        rightSide.setBounds(700, 0, 200, 600);
        add(rightSide);

        //adds a reset JButton to rightSide. adds the ActionListener
        JButton reset = new JButton("Reset");
        reset.setFont(myFont);
        reset.setBounds(10, 415, 150, 50);
        reset.addActionListener(this);
        rightSide.add(reset);

        //adds an exit JButton to rightSide. adds the ActionListener
        JButton exit = new JButton("Exit");
        exit.setFont(myFont);
        exit.setBounds(10, 485, 150, 50);
        exit.addActionListener(this);
        rightSide.add(exit);

        //creates a new JLabel for levelLabel
        levelLabel = new JLabel("Level");

        //adds the FocusListener and KeyListener
        this.addFocusListener(this);
        this.addKeyListener(this);
    }

    //setter for level. the levelLabel reflects the level that was sent in. if a new level is gained, then drawRunGame has to change cards
    public void setLevel(int levelIn)
    {
        level = levelIn;

        levelLabel.setFont(new Font("Arial", Font.BOLD, 40));
        levelLabel.setForeground(new Color(1, 22, 30));
        levelLabel.setBounds(365, 0, 165, 120);
        levelLabel.setText("Level " + level);
        drawRunGame.add(levelLabel);

        if(drawRunGame.getLevel() != level)
        {
            drawRunGame.setLevel(level);
            drawRunGame.getNumbersFromFile();
        }
    }

    //gets the image
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

    //checks if a button is pressed
    public void actionPerformed(ActionEvent e)
    {
        //gets the ActionCommand and stores it into command
        String command = e.getActionCommand();
        //if the user clicked the exit button, the press and button are reset. the Level Select card is displayed and the LevelSelectInstance has focus
        //requested
        if(command.equals("Exit"))
        {
            math = "";
            drawRunGame.resetPressAndButton();
            card.getLayoutOfCards().show(card, "Level Select");
            card.getLevelSelectInstance().requestTheFocus();
        }
        //if the user clicks reset, all the variables should be reset to the defaults. this requests the focus
        else if(command.equals("Reset"))
        {
            drawRunGame.resetCards();
            math = "";
            this.requestFocus();
        }
        //if the user clicks a button again, they would expect it to un-select the button. this requests the focus
        else if(command.equals(math) && valueArray.equals(drawRunGame.getValueArray()))
        {
            math = "";
            //this makes sure that the button that is pressed in  drawRunGame has the same value as in this class
            drawRunGame.setButtonPressed(math);
            this.requestFocus();
        }
        //if the user clicks the operation buttons, the math variable is set to that. the focus is requested
        else if(command.equals("+") || command.equals("-") || command.equals("*") || command.equals("/"))
        {
            if(drawRunGame.getFirstPress() != 0)
            {
                math = command;
                //this makes sure that the button that is pressed in the InstructionsImage instance has the same value as in this class
                drawRunGame.setButtonPressed(math);
                valueArray = drawRunGame.getValueArray();
            }
            this.requestFocus();
        }
    }

    //this checks if the user typed a button
    public void keyTyped(KeyEvent e)
    {
        //stores the KeyChar of e into command
        char command = e.getKeyChar();
        //if the user types 'e' or 'E', the same effect occurs as if they pressed the Exit button
        if(command == 'e' || command == 'E')
        {
            math = "";
            drawRunGame.resetPressAndButton();
            card.getLayoutOfCards().show(card, "Level Select");
            card.getLevelSelectInstance().requestTheFocus();
        }
        //if the user types 'r' or 'R', the same effect occurs as if they pressed the Reset button
        else if(command == 'r' || command == 'R')
        {
            drawRunGame.resetCards();
            math = "";
        }
        //if the user types a button again, they would expect it to un-select the button. does the same thing as the corresponding class in actionPerformed()
        else if(math.length() > 0 && command == math.charAt(0) && valueArray.equals(drawRunGame.getValueArray()))
        {
            math = "";
            //this makes sure that the button that is pressed in drawRunGamehas the same value as in this class
            drawRunGame.setButtonPressed(math);
        }
        //if the user types an operation button, the math variable is set to that
        else if(command == '+' || command == '-' || command == '*' || command == '/')
        {
            if(drawRunGame.getFirstPress() != 0)
            {
                math = "" + command;
                //this makes sure that the button that is pressed in drawRunGame has the same value as in this class
                drawRunGame.setButtonPressed(math);
                valueArray = drawRunGame.getValueArray();
            }
        }
    }

    //these 4 methods exist to make sure that the KeyListener and FocusListener work
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
