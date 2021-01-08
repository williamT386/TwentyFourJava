//DONE
/**
 * William Tang
 * LevelSelect.java
 * 5/21/2020
 *
 * This program allows the user to select a level to run for the Practice gamemode. The 4 difficulties are "Novice", "Casual", "Challenging", and "Insane".
 * Each difficulty has 10 different levels except for the "Insane" difficulty, which as only 8 levels. The levels can be repeated. The user can exit from this
 * class by pressing the Exit button or typing 'e' or 'E' on the keyboard.
 **/

//imports Color and Font for drawing
import java.awt.Color;
import java.awt.Font;

//imports JPanel, JLabel, and JButton for drawing
import javax.swing.JPanel;

import javax.swing.JLabel;
import javax.swing.JButton;

//imports the FlowLayout and GridLayout as layout managers
import java.awt.FlowLayout;
import java.awt.GridLayout;

//imports the ActionEvent and ActionListener to check if the user clicked a button
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

//imports FocusEvent, FocusListener, KeyEvent, and KeyListener to check if the user typed a button
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class LevelSelect extends JPanel implements ActionListener, KeyListener, FocusListener
{
    //stores the practice instance
    private Practice practiceInstance;
    //stores the card instance
    private Cards card;

    public LevelSelect(Cards cardIn)
    {
        //stores cardIn into card
        card = cardIn;

        //sets the background to a white-ish color. sets layout to null
        setBackground(new Color(232, 241, 242));
        setLayout(null);

        //sets 2 types of fonts for later use
        Font myFont = new Font("Arial", Font.PLAIN, 45);
        Font levelFont = new Font("Arial", Font.PLAIN, 25);

        //establishes a JPanel on top for the JLabel "Level Selection". this uses FlowLayout
        JPanel panelTop = new JPanel();
        panelTop.setBackground(new Color(207, 186, 209));
        panelTop.setBounds(235,30,430,60);
        panelTop.setLayout(new FlowLayout(FlowLayout.CENTER));
        add(panelTop);

        //JLabel inside panelTop for "Level Selection"
        JLabel chooseLevel = new JLabel("Level Selection");
        chooseLevel.setFont(myFont);
        chooseLevel.setForeground(new Color(1, 22, 30));
        panelTop.add(chooseLevel);

        //creates a JPanel that holds the other 4 JPanels that are in purple that are created below. this uses GridLayout
        JPanel panelLevelDifficulty = new JPanel();
        panelLevelDifficulty.setBackground(new Color(232, 241, 242));
        panelLevelDifficulty.setBounds(0, 110, 900, 470);
        panelLevelDifficulty.setLayout(new GridLayout(1, 4, 30, 0));
        add(panelLevelDifficulty);

        //creates 4 JPanels for the 4 different difficulty levels. they use FlowLayout
        JPanel level0 = new JPanel();
        JPanel level1 = new JPanel();
        JPanel level2 = new JPanel();
        JPanel level3 = new JPanel();
        JPanel [] panelArray = new JPanel[] {level0, level1, level2, level3};
        for(int i = 0; i < 4; i++)
        {
            panelArray[i].setBackground(new Color(207, 186, 209));
            panelArray[i].setLayout(new FlowLayout());
            panelLevelDifficulty.add(panelArray[i]);
        }

        //creates 4 JLabels for the 4 different difficulty levels
        JLabel level0Label = new JLabel("Novice");
        JLabel level1Label = new JLabel("Casual");
        JLabel level2Label = new JLabel("Challenging");
        JLabel level3Label = new JLabel("Insane");
        JLabel [] levelLabelArray = new JLabel[] {level0Label, level1Label, level2Label, level3Label};
        for(int i = 0; i < 4; i++)
        {
            levelLabelArray[i].setFont(levelFont);
            levelLabelArray[i].setForeground(new Color(1, 22, 30));
            panelArray[i].add(levelLabelArray[i]);
        }

        //creates 38 buttons without adding to the JPanels yet. adds the ActionListener to each one
        JButton [] levelButtonArray = new JButton[38];
        for(int i = 0; i < 38; i++)
        {
            levelButtonArray[i] = new JButton("Level " + (i + 1));
            levelButtonArray[i].setFont(levelFont);
            levelButtonArray[i].addActionListener(this);

        }

        //adds a separate JPanel for the back button. it runs FlowLayout
        JPanel exitPanel = new JPanel();
        exitPanel.setBackground(new Color(232, 241, 242));
        exitPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10 , 10));

        //adds the level buttons to the correct JPanel
        int counter = 0;
        for(int i = 0; i < 4; i++)
        {
            for(int j = 0; j < 10; j++)
            {
                if(counter < 38)
                {
                    panelArray[i].add(levelButtonArray[counter]);
                }
                else if(counter == 38)
                {
                    panelArray[i].add(exitPanel);
                }
                counter++;
            }
        }

        //creates and adds a back button. the ActionListener is added
        JButton exit = new JButton("Exit");
        exit.setFont(new Font("Arial", Font.PLAIN, 40));
        exit.addActionListener(this);
        exitPanel.add(exit);

        //adds the KeyListener and FocusListener
        this.addKeyListener(this);
        this.addFocusListener(this);
    }

    //checks if the user clicked a button
    public void actionPerformed(ActionEvent e)
    {
        //the practice instance isn't necessary until the user has clicked something on here. this also creates a delay for the program to create the Practice
        //instance
        practiceInstance = card.getPracticeInstance();
        //stores the ActionCommand in command
        String command = e.getActionCommand();
        //if the user clicked "Exit", the "Start" card is displayed
        if(command.equals("Exit"))
        {
            card.getLayoutOfCards().show(card, "Start");
        }
        //decides which level in int format was clicked based on the string
        else
        {
            command = command.substring(6);

            int commandInt = 0;
            if(command.length() == 1)
            {
                commandInt = command.charAt(0) - 48;
            }
            else
            {
                commandInt = (command.charAt(0) - 48)*10;
                commandInt += (command.charAt(1) - 48);
            }
            //sends the level to the practice instance
            practiceInstance.setLevel(commandInt);
            //changes cards to the "Practice" card and requests the focus in the PracticeInstance
            card.getLayoutOfCards().show(card, "Practice");
            card.getPracticeInstance().requestTheFocus();
        }
    }

    //checks if the user typed something
    public void keyTyped(KeyEvent e)
    {
        //stores the KeyChar of e into command
        char command = e.getKeyChar();

        //if the user typed 'e' or 'E', the same thing occurs as if the user pressed the Exit button
        if(command == 'e' || command == 'E')
        {
            card.getLayoutOfCards().show(card, "Start");
        }
    }

    //these 4 methods exist to make sure that the KeyListener and FocusListener work
    public void keyPressed(KeyEvent e) {}
    public void keyReleased(KeyEvent e) {}
    public void focusGained(FocusEvent e) {}
    public void focusLost(FocusEvent e) {}

    //this requests the the focus
    public void requestTheFocus()
    {
        this.requestFocus();
    }
}
