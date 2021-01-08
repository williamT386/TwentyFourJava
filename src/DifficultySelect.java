//DONE
/**
 * William Tang
 * DifficultySelect.java
 * 5/21/2020
 *
 * This class allows the user to select a difficulty for the Endless gamemode. The user can exit out of this class. This class runs 4 different buttons to
 * decide which difficulty for the Endless gamemode, and passes it into the Endless gamemode after one of those 4 buttons are clicked.
 */

//imports Color, Dimension, and Font for drawing
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

//imports JPanel, JLabel, and JButton
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JButton;

//imports FlowLayout as the FlowManager
import java.awt.FlowLayout;

//imports ActionEvent and ActionListener to check if the buttons are clicked
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

//imports FocusEvent, FocusListener, KeyEvent, KeyListener to check if the keys are clicked
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class DifficultySelect extends JPanel implements ActionListener, FocusListener, KeyListener
{
    //stores the card instance
    private Cards card;
    private final int buttonWidth = 290;
    private final int buttonHeight = 60;

    private JButton[] difficultyButtons;
    private String[] difficultyButtonNames;

    //constructor
    public DifficultySelect(Cards cardIn)
    {
        //adds card into cardIn
        card = cardIn;

        //sets the background to a white-ish color. layout is null. creates a font for later use
        setBackground(new Color(232, 241, 242));
        setLayout(null);
        Font myFont = new Font("Arial", Font.PLAIN, 45);

        //adds a JPanel on top
        JPanel panelTop = new JPanel();
        panelTop.setBackground(new Color(207, 186, 209));
        panelTop.setBounds(275,70,390,60);
        panelTop.setLayout(new FlowLayout(FlowLayout.CENTER));
        add(panelTop);

        //adds a JLabel for the difficulty inside panelTop
        JLabel selectDifficulty = new JLabel("Select A Difficulty");
        selectDifficulty.setFont(myFont);
        selectDifficulty.setForeground(new Color(1, 22, 30));
        panelTop.add(selectDifficulty);

        //adds a JPanel for difficulty buttons
        JPanel difficultyPanel = new JPanel();
        difficultyPanel.setBackground(new Color(74, 78, 105));
        difficultyPanel.setBounds(275,180,390,315);
        difficultyPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 14));
        add(difficultyPanel);

        //adds the JButtos for the 4 levels of difficulty inside difficultyPanel. The ActionListener is added
        difficultyButtons = new JButton[4];
        difficultyButtonNames = new String[] {"Novice", "Casual", "Challenging", "Insane"};
        for(int i = 0; i < 4; i++)
        {
            difficultyButtons[i] = new JButton(difficultyButtonNames[i]);
            difficultyButtons[i].setFont(myFont);
            difficultyButtons[i].addActionListener(this);
            difficultyButtons[i].setPreferredSize(new Dimension(buttonWidth, buttonHeight));
            difficultyPanel.add(difficultyButtons[i]);
        }

        //adds an exit button with the ActionListener
        JButton exit = new JButton("Exit");
        exit.setFont(new Font("Arial", Font.PLAIN, 25));
        exit.setBounds(730, 485, 110, 60);
        exit.addActionListener(this);
        add(exit);

        //adds the FocusListener and KeyListener
        this.addFocusListener(this);
        this.addKeyListener(this);
    }

    //responds to different buttons being pressed
    public void actionPerformed(ActionEvent e)
    {
        //stores the ActionCommand into command
        String command = e.getActionCommand();
        //if the user clicked Exit, the start card is displayed
        if(command.equals("Exit"))
        {
            card.getLayoutOfCards().show(card, "Start");
        }
        //depending on the button they chose, the difficulty is set to the index of the button
        else
        {
            int difficulty = 0;
            for(int i = 0; i < 4 && difficulty == 0; i++)
            {
                if(command.equals(difficultyButtonNames[i]))
                {
                    difficulty = i;
                }
            }

            //this sets the difficulty in the Endless instance. The Endless card is then displayed and the focus is requested in the endless instance
            card.getEndlessInstance().setDifficulty(difficulty);
            card.getLayoutOfCards().show(card, "Endless");
            card.getEndlessInstance().requestTheFocus();
        }
    }

    //this checks what the user types
    public void keyTyped(KeyEvent e)
    {
        //this stores the KeyChar of e into command
        char command = e.getKeyChar();
        //if the user types 'e' or 'E', the same response happens as if the user clicked the exit button
        if(command == 'e' || command == 'E')
        {
            card.getLayoutOfCards().show(card, "Start");
        }
    }

    //these 5 methods exist so the KeyListener and FocusListener work
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
